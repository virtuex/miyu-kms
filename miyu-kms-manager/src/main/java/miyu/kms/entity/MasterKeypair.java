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
     * 密钥在密钥机中的索引或别称
     */
    private String mkpIndexInHsm;

    /**
     * 对应密钥空间ID
     */
    private String mkpKeyspaceId;

    /**
     * 公钥
     */
    private String mkpPublicKey;

    /**
     * 私钥，预留
     */
    private String mkpPrivateKey;

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

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        MasterKeypair other = (MasterKeypair) that;
        return (this.getMkpId() == null ? other.getMkpId() == null : this.getMkpId().equals(other.getMkpId()))
            && (this.getMkpAlias() == null ? other.getMkpAlias() == null : this.getMkpAlias().equals(other.getMkpAlias()))
            && (this.getMkpDesc() == null ? other.getMkpDesc() == null : this.getMkpDesc().equals(other.getMkpDesc()))
            && (this.getMkpType() == null ? other.getMkpType() == null : this.getMkpType().equals(other.getMkpType()))
            && (this.getMkpAlgorithm() == null ? other.getMkpAlgorithm() == null : this.getMkpAlgorithm().equals(other.getMkpAlgorithm()))
            && (this.getMkpUserId() == null ? other.getMkpUserId() == null : this.getMkpUserId().equals(other.getMkpUserId()))
            && (this.getMkpIndexInHsm() == null ? other.getMkpIndexInHsm() == null : this.getMkpIndexInHsm().equals(other.getMkpIndexInHsm()))
            && (this.getMkpKeyspaceId() == null ? other.getMkpKeyspaceId() == null : this.getMkpKeyspaceId().equals(other.getMkpKeyspaceId()))
            && (this.getMkpPublicKey() == null ? other.getMkpPublicKey() == null : this.getMkpPublicKey().equals(other.getMkpPublicKey()))
            && (this.getMkpPrivateKey() == null ? other.getMkpPrivateKey() == null : this.getMkpPrivateKey().equals(other.getMkpPrivateKey()))
            && (this.getCreatedTime() == null ? other.getCreatedTime() == null : this.getCreatedTime().equals(other.getCreatedTime()))
            && (this.getUpdatedBy() == null ? other.getUpdatedBy() == null : this.getUpdatedBy().equals(other.getUpdatedBy()))
            && (this.getUpdatedTime() == null ? other.getUpdatedTime() == null : this.getUpdatedTime().equals(other.getUpdatedTime()))
            && (this.getCreatedBy() == null ? other.getCreatedBy() == null : this.getCreatedBy().equals(other.getCreatedBy()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getMkpId() == null) ? 0 : getMkpId().hashCode());
        result = prime * result + ((getMkpAlias() == null) ? 0 : getMkpAlias().hashCode());
        result = prime * result + ((getMkpDesc() == null) ? 0 : getMkpDesc().hashCode());
        result = prime * result + ((getMkpType() == null) ? 0 : getMkpType().hashCode());
        result = prime * result + ((getMkpAlgorithm() == null) ? 0 : getMkpAlgorithm().hashCode());
        result = prime * result + ((getMkpUserId() == null) ? 0 : getMkpUserId().hashCode());
        result = prime * result + ((getMkpIndexInHsm() == null) ? 0 : getMkpIndexInHsm().hashCode());
        result = prime * result + ((getMkpKeyspaceId() == null) ? 0 : getMkpKeyspaceId().hashCode());
        result = prime * result + ((getMkpPublicKey() == null) ? 0 : getMkpPublicKey().hashCode());
        result = prime * result + ((getMkpPrivateKey() == null) ? 0 : getMkpPrivateKey().hashCode());
        result = prime * result + ((getCreatedTime() == null) ? 0 : getCreatedTime().hashCode());
        result = prime * result + ((getUpdatedBy() == null) ? 0 : getUpdatedBy().hashCode());
        result = prime * result + ((getUpdatedTime() == null) ? 0 : getUpdatedTime().hashCode());
        result = prime * result + ((getCreatedBy() == null) ? 0 : getCreatedBy().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", mkpId=").append(mkpId);
        sb.append(", mkpAlias=").append(mkpAlias);
        sb.append(", mkpDesc=").append(mkpDesc);
        sb.append(", mkpType=").append(mkpType);
        sb.append(", mkpAlgorithm=").append(mkpAlgorithm);
        sb.append(", mkpUserId=").append(mkpUserId);
        sb.append(", mkpIndexInHsm=").append(mkpIndexInHsm);
        sb.append(", mkpKeyspaceId=").append(mkpKeyspaceId);
        sb.append(", mkpPublicKey=").append(mkpPublicKey);
        sb.append(", mkpPrivateKey=").append(mkpPrivateKey);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", updatedBy=").append(updatedBy);
        sb.append(", updatedTime=").append(updatedTime);
        sb.append(", createdBy=").append(createdBy);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}