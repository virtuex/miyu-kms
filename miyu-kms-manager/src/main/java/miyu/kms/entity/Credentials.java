package miyu.kms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 凭据表
 * @TableName T_CREDENTIALS
 */
@TableName(value ="T_CREDENTIALS")
@Data
public class Credentials implements Serializable {
    /**
     * 凭据ID
     */
    @TableId
    private Long creId;

    /**
     * 凭据内容
     */
    private String creContent;

    /**
     * 凭据类型
     */
    private String creType;

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