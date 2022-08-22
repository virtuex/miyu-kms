package miyu.kms;

import cn.hutool.core.date.DateTime;
import cn.hutool.crypto.digest.BCrypt;
import miyu.kms.constant.UserType;
import miyu.kms.entity.User;
import miyu.kms.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class MiyuKmsManagerApplicationTests {

    @Resource
    private UserMapper userMapper;

    @Test
    void contextLoads() {
        userMapper.selectById(1L);
    }
    @Test
    void buildPwd(){
        String hashpw = BCrypt.hashpw("123456");
        System.out.println(hashpw);
    }
    @Test
    void insertAdmin(){
        User user  = new User();
        user.setUserName("admin");
        user.setUserType(UserType.ADMIN.name());
        user.setUserPassword("$2a$10$zK3OSE4nctibSFupK7MiI.oFU.JIgB48BEwcTIS5kPDUk3HwzkipW");
        user.setUserDesc("管理员");
        user.setCreatedTime(DateTime.now());
        user.setCreatedBy(-1L);
        user.setUpdatedTime(DateTime.now());
        user.setUpdatedBy(-1L);
        userMapper.insert(user);
    }


}
