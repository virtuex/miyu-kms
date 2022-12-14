package miyu.kms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import miyu.kms.entity.Tenant;
import com.baomidou.mybatisplus.extension.service.IService;
import miyu.kms.model.tenant.req.TenantAddReq;
import miyu.kms.model.tenant.req.TenantUpdateReq;

/**
 * @author xudean
 * @description 针对表【T_TENANT(租户表)】的数据库操作Service
 * @createDate 2022-08-22 18:29:24
 */
public interface TenantService extends IService<Tenant> {
    /**
     * 添加租户
     * @param tenantAddReq
     */
    void addTenant(TenantAddReq tenantAddReq);

    /**
     * 分页查询租户列表
     * @param page
     * @param size
     * @return
     */
    IPage<Tenant> listTenant(long page, long size);

    /**
     * 查询租户总数
     * @return
     */
    int querySizeOfTenant();

    /**
     * 根据id查询租户
     * @param id
     * @return
     */
    Tenant queryTenantById(Long id);

    /**
     * 更新租户
     * @param tenantUpdateReq
     */
    void updateTenant(TenantUpdateReq tenantUpdateReq);
}
