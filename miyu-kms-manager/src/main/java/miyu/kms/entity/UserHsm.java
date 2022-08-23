package miyu.kms.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 授权用户可以使用的HSM。这里应该使用userHsmHsmId和userHsmUserId作为复合主键，但是MBP不支持，暂时放弃。
 * @author xudean
 * @TableName T_USER_HSM
 */
@TableName(value ="T_USER_HSM")
@Data
@Builder
public class UserHsm implements Serializable {


    @TableId
    private Long userHsmId;

    /**
     * 密码机id
     */
    private Long userHsmHsmId;

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