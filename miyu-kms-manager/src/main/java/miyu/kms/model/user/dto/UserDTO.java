package miyu.kms.model.user.dto;

import lombok.*;
import miyu.kms.entity.User;

/**
 * @author : xudean
 * @version V1.0
 * @Description: TODO
 * @date Date : 2022年08月22日 下午2:18
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends User {
}
