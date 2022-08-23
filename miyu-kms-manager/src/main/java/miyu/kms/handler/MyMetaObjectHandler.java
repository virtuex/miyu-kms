package miyu.kms.handler;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author : xudean
 * @version V1.0
 * @Description: mybatis的时间处理器
 * @date Date : 2022年08月23日 上午9:43
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {


    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        // 起始版本 3.3.3(推荐)
        this.strictInsertFill(metaObject, "createdTime", () -> new Date(), Date.class);
        this.strictInsertFill(metaObject, "createdBy", () -> UserHolder.getUserDTO().getUserId(), Long.class);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        // 起始版本 3.3.3(推荐)
        this.strictUpdateFill(metaObject, "updatedTime", () -> new Date(), Date.class);
        this.strictUpdateFill(metaObject, "updatedBy", () -> UserHolder.getUserDTO().getUserId(), Long.class);
    }
}