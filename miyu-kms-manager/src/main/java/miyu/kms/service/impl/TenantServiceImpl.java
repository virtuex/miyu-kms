package miyu.kms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import miyu.kms.entity.Tenant;
import miyu.kms.handler.UserHolder;
import miyu.kms.model.login.req.TenantAddReq;
import miyu.kms.module.user.dto.UserDTO;
import miyu.kms.service.TenantService;
import miyu.kms.mapper.TenantMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author xudean
 * @description 针对表【T_TENANT(租户表)】的数据库操作Service实现
 * @createDate 2022-08-22 18:29:24
 */
@Service
public class TenantServiceImpl extends ServiceImpl<TenantMapper, Tenant>
        implements TenantService {

    @Resource
    private TenantMapper tenantMapper;
    @Override
    public void addTenant(TenantAddReq tenantAddReq) {
        Tenant tenant = BeanUtil.copyProperties(tenantAddReq, Tenant.class);
        tenantMapper.insert(tenant);
    }
}




