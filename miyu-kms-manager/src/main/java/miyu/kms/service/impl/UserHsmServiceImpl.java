package miyu.kms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import miyu.kms.entity.UserHsm;
import miyu.kms.service.UserHsmService;
import miyu.kms.mapper.UserHsmMapper;
import org.springframework.stereotype.Service;

/**
* @author xudean
* @description 针对表【T_USER_HSM(授权用户可以使用的HSM)】的数据库操作Service实现
* @createDate 2022-08-22 11:57:04
*/
@Service
public class UserHsmServiceImpl extends ServiceImpl<UserHsmMapper, UserHsm>
    implements UserHsmService{

}




