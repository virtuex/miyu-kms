package miyu.kms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author xudean
 */
@SpringBootApplication
@MapperScan("miyu.kms.mapper")
@EnableTransactionManagement
public class MiyuKmsManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiyuKmsManagerApplication.class, args);
    }

}
