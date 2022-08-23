package miyu.kms.service;

import miyu.kms.entity.Keyspace;
import com.baomidou.mybatisplus.extension.service.IService;
import miyu.kms.model.keyspace.req.KeySpaceInitReq;
import miyu.kms.model.keyspace.req.KeySpaceUpdateReq;

/**
* @author xudean
* @description 针对表【T_KEYSPACE(用户密钥空间，对用户密钥进行隔离，分场景使用，分密码机使用)】的数据库操作Service
* @createDate 2022-08-22 18:29:24
*/
public interface KeyspaceService extends IService<Keyspace> {
    /**
     * 初始户密钥空间
     * @param keySpaceInitReq
     */
    void initKeyspace(KeySpaceInitReq keySpaceInitReq);

    void updateKeyspace(KeySpaceUpdateReq keySpaceUpdateReq);
}
