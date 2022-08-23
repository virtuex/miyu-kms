package miyu.kms.model.hsm.req;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * @author : xudean
 * @version V1.0
 * @Description: TODO
 * @date Date : 2022年08月23日 下午3:33
 */
@Data
public class HsmAddReq {
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
     * 密码机标识
     */
    @NotEmpty
    private String hsmIdentifier;


    /**
     * 密码机用户的密码
     */
    private String hsmPassword;

    /**
     * 密码机类型、硬件or软件
     */
    @NotEmpty
    private String hsmType;
}
