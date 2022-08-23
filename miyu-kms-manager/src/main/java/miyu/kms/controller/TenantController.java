package miyu.kms.controller;

import cn.hutool.core.bean.BeanUtil;
import miyu.kms.model.ResponseVo;
import miyu.kms.model.login.req.TenantAddReq;
import miyu.kms.service.TenantService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
    public ResponseVo addTenant(@RequestBody @Validated TenantAddReq tenantAddReq){
        tenantService.addTenant(tenantAddReq);
        return ResponseVo.createSuccess();
    }
}
