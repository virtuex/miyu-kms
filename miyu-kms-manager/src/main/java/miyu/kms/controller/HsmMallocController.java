package miyu.kms.controller;

import miyu.kms.annotations.RequiresRoles;
import miyu.kms.constant.UserType;
import miyu.kms.model.ResponseVo;
import miyu.kms.model.distribution.UserHsmAddReq;
import miyu.kms.service.UserHsmService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 给用户分配密码机的处理接口，操作表T_USER_HSM
 *
 * @author : xudean
 * @version V1.0
 * @Description: TODO
 * @date Date : 2022年08月23日 下午4:40
 */
@RestController
public class HsmMallocController {
    @Resource
    private UserHsmService userHsmService;

    @PostMapping("/hsm/distribution")
    @RequiresRoles(UserType.ADMIN)
    public ResponseVo addHsmMalloc(@RequestBody UserHsmAddReq userHsmAddReq) {
        userHsmService.saveAllUserHsm(userHsmAddReq.getUserHsmHsmIds(),userHsmAddReq.getUserHsmUserId());
        return ResponseVo.createSuccess();
    }

    @PutMapping("/hsm/distribution")
    @RequiresRoles(UserType.ADMIN)
    public ResponseVo updateHsmMalloc(@RequestBody UserHsmAddReq userHsmAddReq) {
        userHsmService.UpdateAllUserHsm(userHsmAddReq.getUserHsmHsmIds(),userHsmAddReq.getUserHsmUserId());
        return ResponseVo.createSuccess();
    }
}
