package miyu.kms.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import miyu.kms.entity.TUserHsm;
import miyu.kms.mapper.TUserHsmMapper;
import org.springframework.stereotype.Service;

/**
* @author xudean
* @description 针对表【T_USER_HSM(授权用户可以使用的HSM)】的数据库操作Service实现
* @createDate 2022-08-22 18:29:24
*/
@Service
public class TUserHsmServiceImpl extends ServiceImpl<TUserHsmMapper, TUserHsm>
    implements IService<TUserHsm> {

}




