package miyu.kms.model.login.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 添加租户的实体类
 * @author : xudean
 * @version V1.0
 * @Description: TODO
 * @date Date : 2022年08月22日 下午6:34
 */
@Data
public class TenantAddReq {
    @NotBlank
    private String tenantName;

    private String tenantDes;
}
