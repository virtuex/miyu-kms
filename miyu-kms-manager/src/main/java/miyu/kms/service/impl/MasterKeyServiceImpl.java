package miyu.kms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import miyu.kms.entity.MasterKey;
import miyu.kms.service.MasterKeyService;
import miyu.kms.mapper.MasterKeyMapper;
import org.springframework.stereotype.Service;

/**
* @author xudean
* @description 针对表【T_MASTER_KEY(主密钥，对称密钥)】的数据库操作Service实现
* @createDate 2022-08-22 18:29:24
*/
@Service
public class MasterKeyServiceImpl extends ServiceImpl<MasterKeyMapper, MasterKey>
    implements MasterKeyService{

}




