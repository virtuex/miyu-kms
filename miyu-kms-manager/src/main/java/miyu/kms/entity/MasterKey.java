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
     * 密钥在密钥机中的索引或别称
     */
    private String mkIndexInHsm;

    /**
     * 对应密钥空间ID
     */
    private String mkKeyspaceId;

    /**
     * 主密钥，预留。可以以后存储主密钥密文
     */
    private String mkMasterKey;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 更新人
     */
    private String updatedBy;

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
        MasterKey other = (MasterKey) that;
        return (this.getMkId() == null ? other.getMkId() == null : this.getMkId().equals(other.getMkId()))
            && (this.getMkAlias() == null ? other.getMkAlias() == null : this.getMkAlias().equals(other.getMkAlias()))
            && (this.getMkDesc() == null ? other.getMkDesc() == null : this.getMkDesc().equals(other.getMkDesc()))
            && (this.getMkType() == null ? other.getMkType() == null : this.getMkType().equals(other.getMkType()))
            && (this.getMkAlgorithm() == null ? other.getMkAlgorithm() == null : this.getMkAlgorithm().equals(other.getMkAlgorithm()))
            && (this.getMkPadding() == null ? other.getMkPadding() == null : this.getMkPadding().equals(other.getMkPadding()))
            && (this.getMkMode() == null ? other.getMkMode() == null : this.getMkMode().equals(other.getMkMode()))
            && (this.getMkUserId() == null ? other.getMkUserId() == null : this.getMkUserId().equals(other.getMkUserId()))
            && (this.getMkIndexInHsm() == null ? other.getMkIndexInHsm() == null : this.getMkIndexInHsm().equals(other.getMkIndexInHsm()))
            && (this.getMkKeyspaceId() == null ? other.getMkKeyspaceId() == null : this.getMkKeyspaceId().equals(other.getMkKeyspaceId()))
            && (this.getMkMasterKey() == null ? other.getMkMasterKey() == null : this.getMkMasterKey().equals(other.getMkMasterKey()))
            && (this.getCreatedTime() == null ? other.getCreatedTime() == null : this.getCreatedTime().equals(other.getCreatedTime()))
            && (this.getUpdatedBy() == null ? other.getUpdatedBy() == null : this.getUpdatedBy().equals(other.getUpdatedBy()))
            && (this.getCreatedBy() == null ? other.getCreatedBy() == null : this.getCreatedBy().equals(other.getCreatedBy()))
            && (this.getUpdatedTime() == null ? other.getUpdatedTime() == null : this.getUpdatedTime().equals(other.getUpdatedTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getMkId() == null) ? 0 : getMkId().hashCode());
        result = prime * result + ((getMkAlias() == null) ? 0 : getMkAlias().hashCode());
        result = prime * result + ((getMkDesc() == null) ? 0 : getMkDesc().hashCode());
        result = prime * result + ((getMkType() == null) ? 0 : getMkType().hashCode());
        result = prime * result + ((getMkAlgorithm() == null) ? 0 : getMkAlgorithm().hashCode());
        result = prime * result + ((getMkPadding() == null) ? 0 : getMkPadding().hashCode());
        result = prime * result + ((getMkMode() == null) ? 0 : getMkMode().hashCode());
        result = prime * result + ((getMkUserId() == null) ? 0 : getMkUserId().hashCode());
        result = prime * result + ((getMkIndexInHsm() == null) ? 0 : getMkIndexInHsm().hashCode());
        result = prime * result + ((getMkKeyspaceId() == null) ? 0 : getMkKeyspaceId().hashCode());
        result = prime * result + ((getMkMasterKey() == null) ? 0 : getMkMasterKey().hashCode());
        result = prime * result + ((getCreatedTime() == null) ? 0 : getCreatedTime().hashCode());
        result = prime * result + ((getUpdatedBy() == null) ? 0 : getUpdatedBy().hashCode());
        result = prime * result + ((getCreatedBy() == null) ? 0 : getCreatedBy().hashCode());
        result = prime * result + ((getUpdatedTime() == null) ? 0 : getUpdatedTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", mkId=").append(mkId);
        sb.append(", mkAlias=").append(mkAlias);
        sb.append(", mkDesc=").append(mkDesc);
        sb.append(", mkType=").append(mkType);
        sb.append(", mkAlgorithm=").append(mkAlgorithm);
        sb.append(", mkPadding=").append(mkPadding);
        sb.append(", mkMode=").append(mkMode);
        sb.append(", mkUserId=").append(mkUserId);
        sb.append(", mkIndexInHsm=").append(mkIndexInHsm);
        sb.append(", mkKeyspaceId=").append(mkKeyspaceId);
        sb.append(", mkMasterKey=").append(mkMasterKey);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", updatedBy=").append(updatedBy);
        sb.append(", createdBy=").append(createdBy);
        sb.append(", updatedTime=").append(updatedTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}