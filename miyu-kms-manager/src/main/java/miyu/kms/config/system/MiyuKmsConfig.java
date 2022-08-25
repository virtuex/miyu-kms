package miyu.kms.config.system;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author : xudean
 * @version V1.0
 * @Description: TODO
 * @date Date : 2022年08月25日 上午9:46
 */
@Data
@Component
@ConfigurationProperties(prefix = "miyu-kms.config")
public class MiyuKmsConfig {
    /**
     * 验证码长度
     */
    private CaptchaConfig captchaConfig;


}
