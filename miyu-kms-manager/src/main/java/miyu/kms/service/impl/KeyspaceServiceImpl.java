package miyu.kms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import miyu.kms.constant.KeyspaceStatus;
import miyu.kms.entity.Keyspace;
import miyu.kms.handler.UserHolder;
import miyu.kms.model.keyspace.req.KeySpaceInitReq;
import miyu.kms.model.keyspace.req.KeySpaceUpdateReq;
import miyu.kms.service.KeyspaceService;
import miyu.kms.mapper.KeyspaceMapper;
import org.springframework.stereotype.Service;

/**
* @author xudean
* @description 针对表【T_KEYSPACE(用户密钥空间，对用户密钥进行隔离，分场景使用，分密码机使用)】的数据库操作Service实现
* @createDate 2022-08-22 18:29:24
*/
@Service
public class KeyspaceServiceImpl extends ServiceImpl<KeyspaceMapper, Keyspace>
    implements KeyspaceService{

    /**
     * 初始化密钥空间
     * @param keySpaceInitReq
     */
    @Override
    public void initKeyspace(KeySpaceInitReq keySpaceInitReq) {
        Keyspace keyspace = BeanUtil.copyProperties(keySpaceInitReq, Keyspace.class);
        keyspace.setSpaceUserId(UserHolder.getUserDTO().getUserId());
        keyspace.setSpaceStatus(KeyspaceStatus.CREATING.getCode());
        save(keyspace);
        //TODO 保存成功后需要异步发送消息给engine,来进行初始化。这个初始化步骤对不同的加密机步骤是不一样的
        //TODO 1.硬件加密机： 连接、测试    2.软件加密机： 初始化keystore
        sendMessageAsync();
    }

    @Override
    public void updateKeyspace(KeySpaceUpdateReq keySpaceUpdateReq) {
        Keyspace keyspace = BeanUtil.copyProperties(keySpaceUpdateReq, Keyspace.class);
        updateById(keyspace);
    }

    private void sendMessageAsync(){

    }
}




