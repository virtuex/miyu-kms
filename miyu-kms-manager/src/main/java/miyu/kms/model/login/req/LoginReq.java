package miyu.kms.model.login.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author xuda
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginReq {

    @NotBlank(message = "uuid不能为空")
    private String uuid;

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

    @NotBlank(message = "验证码不能为空")
    private String captchaCode;
}
