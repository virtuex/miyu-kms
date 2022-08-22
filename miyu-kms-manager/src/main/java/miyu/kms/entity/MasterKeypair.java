package miyu.kms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 主密钥，非对称密钥
 * @TableName T_MASTER_KEYPAIR
 */
@TableName(value ="T_MASTER_KEYPAIR")
@Data
public class MasterKeypair implements Serializable {
    /**
     * 主密钥ID
     */
    @TableId
    private Long mkpId;

    /**
     * 别名
     */
    private String mkpAlias;

    /**
     * 描述
     */
    private String mkpDesc;

    /**
     * 主密钥类型，是谁使用密钥
     */
    private String mkpType;

    /**
     * 算法类型
     */
    private String mkpAlgorithm;

    /**
     * mk对应的user id
     */
    private Long mkpUserId;

    /**
     * 对应密钥空间ID
     */
    private String mkpKeyspaceId;

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

}