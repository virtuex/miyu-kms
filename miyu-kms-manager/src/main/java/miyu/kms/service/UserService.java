package miyu.kms.service;

import miyu.kms.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import miyu.kms.exceptions.BizException;
import miyu.kms.model.login.dto.UserLoginDTO;
import miyu.kms.model.user.dto.UserDetailDTO;
import miyu.kms.model.user.vo.UserDetailVO;

import java.util.List;

/**
* @author xudean
* @description 针对表【T_USER(用户表)】的数据库操作Service
* @createDate 2022-08-23 14:05:19
*/
public interface UserService extends IService<User> {
    /**
     * 列出所有用户
     * @param page
     * @param size
     * @return
     */
    List<UserDetailDTO> listUsers(long page,long size,long tenantId);

    /**
     * 统计某个租户下有多少个用户
     * @param tenantId
     * @return
     */
    int countUserByTenantId(long tenantId);

    /**
     * 用户登录
     * @param userLoginDTO
     * @return
     * @throws BizException
     */
    String login(UserLoginDTO userLoginDTO) throws BizException;

    /**
     * 获取当前的登录的用户信息
     * @return
     */
    UserDetailVO getLoginUserInfo();
}
