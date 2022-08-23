package miyu.kms.model.hsm.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @author : xudean
 * @version V1.0
 * @Description: TODO
 * @date Date : 2022年08月23日 下午3:50
 */
@Data
@Builder
public class HsmDetailVO {
    private Long hsmId;

    private String hsmIdentifier;

    private String hsmHost;

    private Long hsmPort;

    private String hsmUser;

    @JsonIgnore
    private String hsmPassword;

    private String hsmType;

    private String hsmStatus;

    private Long createdBy;

    private Date createdTime;

    private Long updatedBy;

    private Date updatedTime;

}
