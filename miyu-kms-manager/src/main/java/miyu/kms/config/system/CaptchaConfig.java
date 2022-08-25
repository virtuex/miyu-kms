package miyu.kms.config.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 验证码配置
 *
 * @author xuda
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaptchaConfig {
    private static final String NEED_CHECK_CODE = "on";
    private static final String NEED_NOT_CHECK_CODE = "off";

    /**
     * 验证码长度
     */
    private int length;
    /**
     * 验证码过期时间
     */
    private int expireTime;

    /**
     * 是否验证验证码 on or off
     */
    private String checkCode;


    public Boolean isCheckCode() {
        return NEED_CHECK_CODE.equals(this.getCheckCode()) ? Boolean.TRUE : Boolean.FALSE;
    }
}
