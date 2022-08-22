package miyu.kms.model.login.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author : xudean
 * @version V1.0
 * @Description: TODO
 * @date Date : 2022年08月22日 下午3:28
 */
@Data
@Builder
public class UserLoginDTO {
    /**
     * uuid
     */
    private String uuid;

    /**
     * username
     */
    private String username;

    /**
     * password
     */
    private String password;

    /**
     * captchaCode
     */
    private String captchaCode;
}
