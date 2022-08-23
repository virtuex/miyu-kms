package miyu.kms.model.tenant.vo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author : xudean
 * @version V1.0
 * @Description: TODO
 * @date Date : 2022年08月23日 上午11:07
 */
@Data
@Builder
public class TenantListVO {
    private List<TenantDetailVO> tenants;
    /**
     * 记录总数量
     */
    private Integer totalSize;

    /**
     * 当前页数量
     */
    private Integer pageSize;

    /**
     * 当前页数
     */
    private Integer page;
}
