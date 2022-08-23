package miyu.kms.service;

import miyu.kms.entity.Hsm;
import com.baomidou.mybatisplus.extension.service.IService;
import miyu.kms.model.hsm.vo.HsmDetailVO;
import miyu.kms.model.hsm.vo.HsmListVO;

import java.util.List;

/**
 * @author xudean
 * @description 针对表【T_HSM(密码机信息表)】的数据库操作Service
 * @createDate 2022-08-23 15:41:05
 */
public interface HsmService extends IService<Hsm> {
    /**
     * 列出所有密码机
     * @param page
     * @param size
     * @return
     */
    HsmListVO listHsmDetail(Long page, Long size);
}
