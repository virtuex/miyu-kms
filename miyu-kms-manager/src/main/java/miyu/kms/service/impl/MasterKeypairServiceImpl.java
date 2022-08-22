package miyu.kms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import miyu.kms.entity.MasterKeypair;
import miyu.kms.service.MasterKeypairService;
import miyu.kms.mapper.MasterKeypairMapper;
import org.springframework.stereotype.Service;

/**
* @author xudean
* @description 针对表【T_MASTER_KEYPAIR(主密钥，非对称密钥)】的数据库操作Service实现
* @createDate 2022-08-22 18:29:24
*/
@Service
public class MasterKeypairServiceImpl extends ServiceImpl<MasterKeypairMapper, MasterKeypair>
    implements MasterKeypairService{

}




