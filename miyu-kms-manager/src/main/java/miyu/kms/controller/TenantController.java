package miyu.kms.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import miyu.kms.entity.Tenant;
import miyu.kms.model.ResponseVo;
import miyu.kms.model.login.req.TenantAddReq;
import miyu.kms.model.tenant.vo.TenantDetailVO;
import miyu.kms.model.tenant.vo.TenantListVO;
import miyu.kms.service.TenantService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author : xudean
 * @version V1.0
 * @Description: TODO
 * @date Date : 2022年08月22日 下午6:21
 */
@RestController
public class TenantController {
    @Resource
    private TenantService tenantService;

    @PostMapping("/tenant")
    public ResponseVo addTenant(@RequestBody @Validated TenantAddReq tenantAddReq) {
        tenantService.addTenant(tenantAddReq);
        return ResponseVo.createSuccess();
    }

    @GetMapping("/tenants")
    public ResponseVo<TenantListVO> queryTenantPage(@RequestParam @NotNull long page, @RequestParam @NotNull long size) {
        IPage<Tenant> iPage = tenantService.listTenant(page, size);
        int total = tenantService.querySizeOfTenant();
        List<Tenant> records = iPage.getRecords();
        List<TenantDetailVO> tenantDetails = BeanUtil.copyToList(records, TenantDetailVO.class);
        TenantListVO listVO = TenantListVO.builder().tenants(tenantDetails)
                .pageSize(tenantDetails.size())
                .page(Long.valueOf(page).intValue())
                .totalSize(total)
                .build();
        return ResponseVo.createSuccess(listVO);
    }


}
