package miyu.kms.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.digest.BCrypt;
import miyu.kms.annotations.RequiresRoles;
import miyu.kms.constant.UserType;
import miyu.kms.entity.User;
import miyu.kms.model.ResponseVo;
import miyu.kms.model.user.dto.UserDetailDTO;
import miyu.kms.model.user.req.UserAddReq;
import miyu.kms.model.user.req.UserUpdateReq;
import miyu.kms.model.user.vo.UserDetailVO;
import miyu.kms.model.user.vo.UserListVO;
import miyu.kms.service.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : xudean
 * @version V1.0
 * @Description: TODO
 * @date Date : 2022年08月23日 下午2:00
 */
@RestController
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/user")
    @RequiresRoles(UserType.ADMIN)
    public ResponseVo addUser(@RequestBody @Validated UserAddReq userAddReq){
        User user = BeanUtil.copyProperties(userAddReq, User.class);
        user.setUserPassword(BCrypt.hashpw(userAddReq.getUserPassword()));
        userService.save(user);
        return ResponseVo.createSuccess();
    }

    @GetMapping("/users")
    @RequiresRoles(UserType.ADMIN)
    public ResponseVo<UserListVO> listAllUser(@RequestParam long page, @RequestParam long size, @RequestParam long tenantId){
        List<UserDetailDTO> userDetailDTOS = userService.listUsers(page, size, tenantId);
        List<UserDetailVO> userListVOS = BeanUtil.copyToList(userDetailDTOS, UserDetailVO.class);
        UserListVO userListVO = UserListVO.builder().users(userListVOS)
                .totalSize(userService.countUserByTenantId(tenantId))
                .pageSize(Long.valueOf(userListVOS.size()).intValue())
                .page(Long.valueOf(page).intValue())
                .build();
        return ResponseVo.createSuccess(userListVO);
    }

    @GetMapping("user/{userId}")
    @RequiresRoles(UserType.ADMIN)
    public ResponseVo<UserDetailVO> findUserById(@PathVariable long userId){
        User user = userService.getById(userId);
        UserDetailVO userDetailVO = BeanUtil.copyProperties(user, UserDetailVO.class);
        return ResponseVo.createSuccess(userDetailVO);
    }

    @PutMapping("user")
    @RequiresRoles(UserType.ADMIN)
    public ResponseVo updateUserById(@RequestBody @Validated UserUpdateReq userUpdateReq){
        userService.updateById(BeanUtil.copyProperties(userUpdateReq,User.class));
        return ResponseVo.createSuccess();
    }
}
