package miyu.kms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;
import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import miyu.kms.config.system.MiyuKmsConfig;
import miyu.kms.entity.User;
import miyu.kms.exceptions.BizException;
import miyu.kms.handler.CaptchaHandler;
import miyu.kms.handler.TokenHandler;
import miyu.kms.handler.UserHolder;
import miyu.kms.model.login.dto.UserLoginDTO;
import miyu.kms.model.user.dto.UserDetailDTO;
import miyu.kms.model.user.dto.UserDTO;
import miyu.kms.model.user.vo.UserDetailVO;
import miyu.kms.service.UserService;
import miyu.kms.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xudean
 * @description 针对表【T_USER(用户表)】的数据库操作Service实现
 * @createDate 2022-08-23 14:05:19
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private CaptchaHandler captchaHandler;

    @Resource
    private TokenHandler tokenHandler;

    @Resource
    private MiyuKmsConfig miyuKmsConfig;
    @Override
    public List<UserDetailDTO> listUsers(long page, long size, long tenantId) {
        IPage<User> iPage = new Page<User>(page, size);
        IPage<User> result = userMapper.selectPage(iPage, new LambdaQueryWrapper<User>().eq(User::getUserTenantId, tenantId));
        return BeanUtil.copyToList(result.getRecords(), UserDetailDTO.class);
    }

    @Override
    public int countUserByTenantId(long tenantId) {
        Long aLong = userMapper.selectCount(new LambdaQueryWrapper<User>().eq(User::getUserTenantId, tenantId));
        return aLong.intValue();
    }


    public String login(UserLoginDTO userLoginDTO) throws BizException {
        // step 1: 校验验证码
        if (miyuKmsConfig.getCaptchaConfig().isCheckCode()) {
            checkCaptchaCode(userLoginDTO.getUuid(), userLoginDTO.getCaptchaCode());
        }
        // step2: 校验密码
        UserDTO userDto = checkPassword(userLoginDTO.getUsername(), userLoginDTO.getPassword());
        return tokenHandler.createToken(userDto);
    }

    @Override
    public UserDetailVO getLoginUserInfo() {
        UserDTO userDTO = UserHolder.getUserDTO();
        return BeanUtil.copyProperties(userDTO, UserDetailVO.class);
    }


    protected UserDTO checkPassword(String username, String password) throws BizException {
        User user = userMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getUserName, username));
        if (user == null || !BCrypt.checkpw(password, user.getUserPassword())) {
            throw new BizException(HttpStatus.HTTP_INTERNAL_ERROR, "用户名或密码错误");
        }
        return BeanUtil.copyProperties(user, UserDTO.class);
    }

    /**
     * 校验验证码
     */
    protected void checkCaptchaCode(String uuid, String captchaCodeFromWeb) throws BizException {
        String captchaCodeInCache = captchaHandler.getCaptchaCode(uuid);
        if (StrUtil.isBlank(captchaCodeInCache)) {
            throw new BizException(HttpStatus.HTTP_INTERNAL_ERROR, "验证码过期！");
        }
        // 大小写不敏感
        if (!StrUtil.equals(captchaCodeFromWeb, captchaCodeInCache, Boolean.TRUE)) {
            throw new BizException(HttpStatus.HTTP_INTERNAL_ERROR, "验证码错误！");
        }
        // 验证成功后删除验证码
        captchaHandler.deleteCaptchaCode(uuid);
    }


}




