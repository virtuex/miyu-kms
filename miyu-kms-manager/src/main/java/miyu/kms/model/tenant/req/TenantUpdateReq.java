package miyu.kms.model.tenant.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author : xudean
 * @version V1.0
 * @Description: TODO
 * @date Date : 2022年08月23日 上午11:44
 */
@Data
public class TenantUpdateReq{
    @NotBlank
    private String tenantId;

    private String tenantDes;
}
