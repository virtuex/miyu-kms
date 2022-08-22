package miyu.kms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author xudean
 */
@SpringBootApplication
@MapperScan("miyu.kms.mapper")
public class MiyuKmsManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiyuKmsManagerApplication.class, args);
    }

}
