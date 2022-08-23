package miyu.kms.controller;

import miyu.kms.annotations.RequiresRoles;
import miyu.kms.constant.UserType;
import miyu.kms.model.ResponseVo;
import miyu.kms.model.keyspace.req.KeySpaceInitReq;
import miyu.kms.model.keyspace.req.KeySpaceUpdateReq;
import miyu.kms.service.KeyspaceService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author : xudean
 * @version V1.0
 * @Description: TODO
 * @date Date : 2022年08月23日 下午5:49
 */
@RestController
public class KeySpaceController {

    @Resource
    private KeyspaceService keyspaceService;


    @PostMapping("/keyspace")
    @RequiresRoles(UserType.USER)
    public ResponseVo initKeySpace(@RequestBody @Validated KeySpaceInitReq keySpaceInitReq){
        keyspaceService.initKeyspace(keySpaceInitReq);
        return ResponseVo.createSuccess();
    }


    @PutMapping("/keyspace")
    @RequiresRoles(UserType.USER)
    public ResponseVo updateKeyspace(@RequestBody @Validated KeySpaceUpdateReq keySpaceUpdateReq){
        keyspaceService.updateKeyspace(keySpaceUpdateReq);
        return ResponseVo.createSuccess();
    }

}
