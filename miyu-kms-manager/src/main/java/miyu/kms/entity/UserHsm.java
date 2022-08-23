package miyu.kms.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 授权用户可以使用的HSM
 * @TableName T_USER_HSM
 */
@TableName(value ="T_USER_HSM")
@Data
public class UserHsm implements Serializable {
    /**
     * 租户号
     */
    @TableId
    private Long userHsmId;

    /**
     * 密码机id
     */
    private Long userHsmlHsmId;

    /**
     * 用户id
     */
    private Long userHsmUserId;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createdTime;

    /**
     * 更新人
     */
    @TableField(fill = FieldFill.UPDATE)
    private Long updatedBy;

    /**
     * 更新时间
     */
    @TableField( fill = FieldFill.UPDATE)
    private Date updatedTime;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createdBy;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}