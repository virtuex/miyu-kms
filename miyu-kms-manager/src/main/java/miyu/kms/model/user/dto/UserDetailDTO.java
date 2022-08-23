package miyu.kms.model.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import miyu.kms.entity.User;

/**
 * @author : xudean
 * @version V1.0
 * @Description: TODO
 * @date Date : 2022年08月23日 下午2:17
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class UserDetailDTO extends User {
    /**
     * 密码
     */
    @JsonIgnore
    private String userPassword;
}
