package miyu.kms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import miyu.kms.entity.User;
import miyu.kms.exceptions.BizException;
import miyu.kms.model.login.dto.UserLoginDTO;

/**
 * @author xudean
 * @description 针对表【T_USER(用户表)】的数据库操作Service
 * @createDate 2022-08-22 11:57:04
 */
public interface UserService extends IService<User> {
    /**
     * 用户登录
     * @param userLoginDTO
     * @return
     * @throws BizException
     */
    String login(UserLoginDTO userLoginDTO) throws BizException;
}
