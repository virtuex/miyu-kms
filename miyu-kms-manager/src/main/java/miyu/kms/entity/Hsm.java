package miyu.kms.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 密码机信息表
 * @TableName T_HSM
 */
@TableName(value ="T_HSM")
@Data
public class Hsm implements Serializable {
    /**
     * 密码机ID
     */
    @TableId
    private Long hsmId;

    /**
     * 密码机标识
     */
    private String hsmIdentifier;

    /**
     * 密码机地址
     */
    private String hsmHost;

    /**
     * 密码机端口
     */
    private Long hsmPort;

    /**
     * 密码机用户名
     */
    private String hsmUser;

    /**
     * 密码机用户的密码
     */
    private String hsmPassword;

    /**
     * 密码机类型、硬件or软件
     */
    private String hsmType;

    /**
     * 密码机状态
     */
    private String hsmStatus;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createdBy;

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
    @TableField(fill = FieldFill.UPDATE)
    private Date updatedTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}