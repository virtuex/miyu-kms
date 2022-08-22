package miyu.kms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import miyu.kms.entity.Keyspace;
import miyu.kms.service.KeyspaceService;
import miyu.kms.mapper.KeyspaceMapper;
import org.springframework.stereotype.Service;

/**
* @author xudean
* @description 针对表【T_KEYSPACE(用户密钥空间，对用户密钥进行隔离，分场景使用，分密码机使用)】的数据库操作Service实现
* @createDate 2022-08-22 11:57:04
*/
@Service
public class KeyspaceServiceImpl extends ServiceImpl<KeyspaceMapper, Keyspace>
    implements KeyspaceService{

}




