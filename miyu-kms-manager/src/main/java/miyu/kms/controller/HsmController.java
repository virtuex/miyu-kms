package miyu.kms.controller;

import cn.hutool.core.bean.BeanUtil;
import miyu.kms.annotations.RequiresRoles;
import miyu.kms.constant.HsmStatus;
import miyu.kms.constant.UserType;
import miyu.kms.entity.Hsm;
import miyu.kms.model.ResponseVo;
import miyu.kms.model.hsm.req.HsmAddReq;
import miyu.kms.model.hsm.req.HsmUpdateReq;
import miyu.kms.model.hsm.vo.HsmDetailVO;
import miyu.kms.model.hsm.vo.HsmListVO;
import miyu.kms.service.HsmService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author : xudean
 * @version V1.0
 * @Description: TODO
 * @date Date : 2022年08月23日 下午3:32
 */
@RestController
public class HsmController {

    @Resource
    private HsmService hsmService;

    @PostMapping("/hsm")
    @RequiresRoles(UserType.ADMIN)
    public ResponseVo addHsm(@RequestBody @Validated HsmAddReq hsmAddReq){
        Hsm hsm = BeanUtil.copyProperties(hsmAddReq, Hsm.class);
        hsm.setHsmStatus(HsmStatus.CHECKING.name());
        hsmService.save(hsm);
        return ResponseVo.createSuccess();
    }

    @PutMapping("/hsm")
    @RequiresRoles(UserType.ADMIN)
    public ResponseVo updateHsm(@RequestBody @Validated HsmUpdateReq hsmUpdateReq){
        Hsm hsm = BeanUtil.copyProperties(hsmUpdateReq, Hsm.class);
        hsmService.updateById(hsm);
        return ResponseVo.createSuccess();
    }

    @GetMapping("/hsm/{hsmId}")
    @RequiresRoles(UserType.ADMIN)
    public ResponseVo<HsmDetailVO> getHsmById(@PathVariable Long hsmId){
        Hsm hsm = hsmService.getById(hsmId);
        return ResponseVo.createSuccess(BeanUtil.copyProperties(hsm, HsmDetailVO.class));
    }
    @GetMapping("/hsms")
    @RequiresRoles(UserType.ADMIN)
    public ResponseVo<HsmListVO> getHsmList(@RequestParam Long page,@RequestParam Long size){
        return ResponseVo.createSuccess(hsmService.listHsmDetail(page,size));
    }

    @DeleteMapping("/hsm/{hsmId}")
    @RequiresRoles(UserType.ADMIN)
    public ResponseVo deleteHsm(@PathVariable Long hsmId){
        hsmService.removeById(hsmId);
        return ResponseVo.createSuccess();
    }
}
