package miyu.kms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import miyu.kms.entity.Credentials;
import miyu.kms.service.CredentialsService;
import miyu.kms.mapper.CredentialsMapper;
import org.springframework.stereotype.Service;

/**
* @author xudean
* @description 针对表【T_CREDENTIALS(凭据表)】的数据库操作Service实现
* @createDate 2022-08-22 18:29:24
*/
@Service
public class CredentialsServiceImpl extends ServiceImpl<CredentialsMapper, Credentials>
    implements CredentialsService{

}




