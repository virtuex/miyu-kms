package miyu.kms.controller;

import cn.hutool.core.bean.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import miyu.kms.handler.TokenHandler;
import miyu.kms.handler.UserHolder;
import miyu.kms.model.ResponseVo;
import miyu.kms.model.login.dto.UserLoginDTO;
import miyu.kms.model.login.req.LoginReq;
import miyu.kms.model.login.vo.LoginVo;
import miyu.kms.service.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author xuda
 */
@Slf4j
@RestController
public class LoginController {

    @Resource
    private UserServiceImpl userService;
    @Resource
    private TokenHandler tokenHandler;

    @PostMapping("/login")
    public ResponseVo<LoginVo> login(@RequestBody @Valid LoginReq loginReq) {
        // 默认密码登录
        UserLoginDTO userLoginDTO = BeanUtil.copyProperties(loginReq, UserLoginDTO.class);
        String token = userService.login(userLoginDTO);
        return ResponseVo.createSuccess(LoginVo.builder().token(token).build());
    }

    @PostMapping("/logout")
    public ResponseVo<?> logout() {
        tokenHandler.removeToken(UserHolder.getUserDTO().getUserId());
        return ResponseVo.createSuccess();
    }

}
