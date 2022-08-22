package miyu.kms.model.login.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xuda
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginVo {
    private String token;
}
