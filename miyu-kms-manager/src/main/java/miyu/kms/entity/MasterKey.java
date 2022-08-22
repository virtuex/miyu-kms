package miyu.kms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 主密钥，对称密钥
 * @TableName T_MASTER_KEY
 */
@TableName(value ="T_MASTER_KEY")
@Data
public class MasterKey implements Serializable {
    /**
     * 主密钥ID
     */
    @TableId
    private Long mkId;

    /**
     * 别名
     */
    private String mkAlias;

    /**
     * 描述
     */
    private String mkDesc;

    /**
     * 主密钥类型，是谁使用密钥
     */
    private String mkType;

    /**
     * 密钥算法类型
     */
    private String mkAlgorithm;

    /**
     * 填充
     */
    private String mkPadding;

    /**
     * 模式
     */
    private String mkMode;

    /**
     * mk对应的用户
     */
    private Long mkUserId;

    /**
     * 对应密钥空间ID
     */
    private Long mkKeyspaceId;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 更新人
     */
    private Long updatedBy;

    /**
     * 创建人
     */
    private Long createdBy;

    /**
     * 更新时间
     */
    private Date updatedTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}