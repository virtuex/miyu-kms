package miyu.kms.mapper;

import miyu.kms.entity.Keyspace;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author xudean
* @description 针对表【T_KEYSPACE(用户密钥空间，对用户密钥进行隔离，分场景使用，分密码机使用)】的数据库操作Mapper
* @createDate 2022-08-22 18:29:24
* @Entity miyu.kms.entity.Keyspace
*/
public interface KeyspaceMapper extends BaseMapper<Keyspace> {

}




