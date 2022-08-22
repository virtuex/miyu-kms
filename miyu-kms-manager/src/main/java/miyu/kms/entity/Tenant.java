package miyu.kms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 租户表
 * @TableName T_TENANT
 */
@TableName(value ="T_TENANT")
@Data
public class Tenant implements Serializable {
    /**
     * 租户主键id
     */
    @TableId
    private Long tenantId;

    /**
     * 租户名称
     */
    private String tenantName;

    /**
     * 租户描述
     */
    private String tenantDes;

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

    /**
     * 创建人
     */
    private Long createdBy;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}