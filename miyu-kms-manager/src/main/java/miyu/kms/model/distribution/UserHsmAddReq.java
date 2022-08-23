package miyu.kms.model.distribution;

import lombok.Data;

/**
 * @author : xudean
 * @version V1.0
 * @Description: TODO
 * @date Date : 2022年08月23日 下午4:44
 */
@Data
public class UserHsmAddReq {
    /**
     * 密码机id的id集合，用逗号分割
     */
    private String userHsmHsmIds;

    /**
     * 用户id
     */
    private Long userHsmUserId;
}
