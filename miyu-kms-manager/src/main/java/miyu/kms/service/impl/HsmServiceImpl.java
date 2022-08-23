package miyu.kms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import miyu.kms.entity.Hsm;
import miyu.kms.model.hsm.vo.HsmDetailVO;
import miyu.kms.model.hsm.vo.HsmListVO;
import miyu.kms.service.HsmService;
import miyu.kms.mapper.HsmMapper;
import org.springframework.boot.autoconfigure.web.ConditionalOnEnabledResourceChain;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author xudean
* @description 针对表【T_HSM(密码机信息表)】的数据库操作Service实现
* @createDate 2022-08-23 15:41:05
*/
@Service
public class HsmServiceImpl extends ServiceImpl<HsmMapper, Hsm>
    implements HsmService{

    @Resource
    private HsmMapper  hsmMapper;
    @Override
    public HsmListVO listHsmDetail(Long page, Long size) {
        IPage<Hsm> iPage = new Page<>(page,size,true);
        IPage<Hsm> result = hsmMapper.selectPage(iPage, Wrappers.emptyWrapper());
        return HsmListVO.builder().hsms(BeanUtil.copyToList(result.getRecords(), HsmDetailVO.class))
                .pageSize(result.getRecords().size())
                .page(Long.valueOf(result.getCurrent()).intValue())
                .totalSize(Long.valueOf(result.getTotal()).intValue())
                .build();
    }
}




