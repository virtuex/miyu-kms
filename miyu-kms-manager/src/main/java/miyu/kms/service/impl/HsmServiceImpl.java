package miyu.kms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import miyu.kms.entity.Hsm;
import miyu.kms.service.HsmService;
import miyu.kms.mapper.HsmMapper;
import org.springframework.stereotype.Service;

/**
* @author xudean
* @description 针对表【T_HSM(密码机信息表)】的数据库操作Service实现
* @createDate 2022-08-22 18:29:24
*/
@Service
public class HsmServiceImpl extends ServiceImpl<HsmMapper, Hsm>
    implements HsmService{

}




