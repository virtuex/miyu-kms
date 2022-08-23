package miyu.kms.model.user.req;

/**
 * @author : xudean
 * @version V1.0
 * @Description: TODO
 * @date Date : 2022年08月23日 下午2:48
 */
public class UserUpdateReq {
    /**
     * 用户ID
     */

    private Long userId;

    /**
     * 用户类型
     */
    private String userType;

    /**
     * 租户ID
     */
    private Long userTenantId;

    /**
     * 用户描述
     */
    private String userDesc;
}
