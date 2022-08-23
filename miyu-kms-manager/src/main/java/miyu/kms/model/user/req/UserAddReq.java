package miyu.kms.model.user.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @author : xudean
 * @version V1.0
 * @Description: TODO
 * @date Date : 2022年08月23日 下午2:02
 */
@Data
public class UserAddReq {

    private Long userId;

    @NotBlank
    private String userName;

    @NotBlank
    private String userType;

    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度最小6位且不超过20位")
    @Pattern(regexp = "^(?=.*[A-Z].*)(?=.*\\d)(?=.*[a-z].*)[\\da-zA-Z~!@#$%^&*()?,./;'<>:\"_-]{6,20}$",
            message = "密码格式错误,密码为6-20个字符，同时包含大小写字母和数字")
    private String userPassword;

    private Long userTenantId;

    private Date createdTime;

    private Long updatedBy;

    private Date updatedTime;

    private Long createdBy;

    private String userDesc;
}
