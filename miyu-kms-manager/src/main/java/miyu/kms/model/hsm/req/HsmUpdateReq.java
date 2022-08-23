package miyu.kms.model.hsm.req;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author : xudean
 * @version V1.0
 * @Description: TODO
 * @date Date : 2022年08月23日 下午3:33
 */
@Data
public class HsmUpdateReq {

    @NotNull
    private Long hsmId;
    /**
     * 密码机地址
     */
    @Pattern(regexp = "((?:(?:25[0-5]|2[0-4]\\d|[01]?\\d?\\d)\\.){3}(?:25[0-5]|2[0-4]\\d|[01]?\\d?\\d))")
    private String hsmHost;

    /**
     * 密码机端口
     */
    private Long hsmPort;

    /**
     * 密码机用户名
     */
    private String hsmUser;

    /**
     * 密码机用户的密码
     */
    private String hsmPassword;
}
