package miyu.kms.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import miyu.kms.entity.UserHsm;
import miyu.kms.exceptions.BizException;
import miyu.kms.handler.UserHolder;
import miyu.kms.service.UserHsmService;
import miyu.kms.mapper.UserHsmMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author xudean
 * @description 针对表【T_USER_HSM(授权用户可以使用的HSM)】的数据库操作Service实现
 * @createDate 2022-08-22 11:57:04
 */
@Service
public class UserHsmServiceImpl extends ServiceImpl<UserHsmMapper, UserHsm>
        implements UserHsmService {

    @Resource
    private UserHsmMapper userHsmMapper;

    @Override
    public void saveAllUserHsm(String hsmIds, Long userId) {
        checkHsmIds(hsmIds);
        remove(new LambdaQueryWrapper<UserHsm>().eq(UserHsm::getUserHsmUserId, userId));
        String[] ids = hsmIds.split(",");
        List<UserHsm> userHsms = new ArrayList<>();
        Arrays.stream(ids).forEach(id -> {
            userHsms.add(UserHsm.builder().userHsmUserId(userId).userHsmHsmId(Long.valueOf(id)).build());
        });
        saveOrUpdateBatch(userHsms);
    }

    @Override
    public void UpdateAllUserHsm(String hsmIds, Long userId) {
        remove(new LambdaQueryWrapper<UserHsm>().eq(UserHsm::getUserHsmUserId, userId));
        if (StrUtil.isBlank(hsmIds)) {
            //如果是个空白字符串，直接清空
            return;
        }
        String[] ids = hsmIds.split(",");
        List<UserHsm> userHsms = new ArrayList<>();
        Arrays.stream(ids).forEach(id -> {
            userHsms.add(UserHsm.builder().userHsmUserId(userId)
                    .userHsmHsmId(Long.valueOf(id))
                    .updatedBy(UserHolder.getUserDTO().getUserId())
                    .updatedTime(new Date())
                    .build());
        });
        saveOrUpdateBatch(userHsms);
    }

    private void checkHsmIds(String hsmIds) {
        if (StrUtil.isEmpty(hsmIds) || StrUtil.contains(hsmIds, "，")) {
            throw new BizException(HttpStatus.HTTP_INTERNAL_ERROR, "Invalid parameter: hsmIds");
        }
    }

}




