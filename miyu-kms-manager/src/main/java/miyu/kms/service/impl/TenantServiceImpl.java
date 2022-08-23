package miyu.kms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import miyu.kms.entity.Tenant;
import miyu.kms.model.tenant.req.TenantAddReq;
import miyu.kms.model.tenant.req.TenantUpdateReq;
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

    @Override
    public IPage<Tenant> listTenant(long page, long size) {
        IPage<Tenant> iPage = new Page<>(page, size);
        IPage<Tenant> result = tenantMapper.selectPage(iPage, Wrappers.emptyWrapper());
        return result;
    }

    @Override
    public int querySizeOfTenant() {
        return tenantMapper.selectCount(Wrappers.emptyWrapper()).intValue();
    }

    @Override
    public Tenant queryTenantById(Long id) {
        return tenantMapper.selectById(id);
    }

    @Override
    public void updateTenant(TenantUpdateReq tenantUpdateReq) {
        Tenant tenant = BeanUtil.copyProperties(tenantUpdateReq, Tenant.class);
        tenantMapper.updateById(tenant);
    }
}




