package miyu.kms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
     * 创建人
     */
    private Long createdBy;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 更新人
     */
    private Long updatedBy;

    /**
     * 更新时间
     */
    private Date updatedTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}