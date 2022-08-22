package miyu.kms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;
import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import miyu.kms.entity.User;
import miyu.kms.exceptions.BizException;
import miyu.kms.handler.CaptchaHandler;
import miyu.kms.handler.TokenHandler;
import miyu.kms.model.login.dto.UserLoginDTO;
import miyu.kms.module.user.dto.UserDTO;
import miyu.kms.service.UserService;
import miyu.kms.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author xudean
* @description 针对表【T_USER(用户表)】的数据库操作Service实现
* @createDate 2022-08-22 11:57:04
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{
    @Resource
    private UserMapper userMapper;
    @Resource
    private CaptchaHandler captchaHandler;

    @Resource
    private TokenHandler tokenHandler;

    public String login(UserLoginDTO userLoginDTO) throws BizException {
        // step 1: 校验验证码

        checkCaptchaCode(userLoginDTO.getUuid(), userLoginDTO.getCaptchaCode());

        // step2: 校验密码
        UserDTO userDto = checkPassword(userLoginDTO.getUsername(), userLoginDTO.getPassword());
        return tokenHandler.createToken(userDto);
    }



    protected UserDTO checkPassword(String username, String password) throws BizException {
        // 只查询状态正常的用户
        User user = userMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getUserName, username));
        if (user == null || !BCrypt.checkpw(password, user.getUserPassword())) {
            throw new BizException(HttpStatus.HTTP_INTERNAL_ERROR,"用户名或密码错误");
        }
        // 更新登录时间
        userMapper.updateById(user);
        return BeanUtil.copyProperties(user, UserDTO.class);
    }

    /**
     * 校验验证码
     */
    protected void checkCaptchaCode(String uuid, String captchaCodeFromWeb) throws BizException {
        String captchaCodeInCache = captchaHandler.getCaptchaCode(uuid);
        if (StrUtil.isBlank(captchaCodeInCache)) {
            throw new BizException(HttpStatus.HTTP_INTERNAL_ERROR,"验证码过期！");
        }
        // 大小写不敏感
        if (!StrUtil.equals(captchaCodeFromWeb, captchaCodeInCache, Boolean.TRUE)) {
            throw new BizException(HttpStatus.HTTP_INTERNAL_ERROR,"验证码错误！");
        }
        // 验证成功后删除验证码
        captchaHandler.deleteCaptchaCode(uuid);
    }
}



