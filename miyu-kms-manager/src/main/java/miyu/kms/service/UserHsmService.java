package miyu.kms.service;

import miyu.kms.entity.UserHsm;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author xudean
* @description 针对表【T_USER_HSM(授权用户可以使用的HSM)】的数据库操作Service
* @createDate 2022-08-22 11:57:04
*/
public interface UserHsmService extends IService<UserHsm> {
    /**
     * 创建用户和密码机的分配关系
     * @param hsmIds
     * @param userId
     */
    void saveAllUserHsm(String hsmIds, Long userId);

    /**
     * 更新分配关系
     * @param hsmIds
     * @param userId
     */
    void UpdateAllUserHsm(String hsmIds, Long userId);

}
