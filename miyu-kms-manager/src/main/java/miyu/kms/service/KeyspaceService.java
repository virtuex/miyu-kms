package miyu.kms.service;

import miyu.kms.entity.Keyspace;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author xudean
* @description 针对表【T_KEYSPACE(用户密钥空间，对用户密钥进行隔离，分场景使用，分密码机使用)】的数据库操作Service
* @createDate 2022-08-22 11:57:04
*/
public interface KeyspaceService extends IService<Keyspace> {

}
