package miyu.kms.service;

import miyu.kms.entity.Tenant;
import com.baomidou.mybatisplus.extension.service.IService;
import miyu.kms.model.login.req.TenantAddReq;

/**
* @author xudean
* @description 针对表【T_TENANT(租户表)】的数据库操作Service
* @createDate 2022-08-22 18:29:24
*/
public interface TenantService extends IService<Tenant> {
    void addTenant(TenantAddReq tenantAddReq);
}
