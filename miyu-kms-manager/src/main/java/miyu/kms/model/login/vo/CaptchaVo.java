package miyu.kms.model.login.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author xuda
 */
@Data
@Builder
public class CaptchaVo {

    private String uuid;

    private String captcha;
}
