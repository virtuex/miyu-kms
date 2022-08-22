package miyu.kms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户密钥空间，对用户密钥进行隔离，分场景使用，分密码机使用
 * @TableName T_KEYSPACE
 */
@TableName(value ="T_KEYSPACE")
@Data
public class Keyspace implements Serializable {
    /**
     * 空间ID
     */
    @TableId
    private Long spaceId;

    /**
     * 该密钥空间下用户授权使用的密码机
     */
    private Long spaceHsmId;

    /**
     * 密钥空间对应的用户ID
     */
    private Long spaceUserId;

    /**
     * 密钥空间说明
     */
    private String spaceDesc;

    /**
     * 空间状态，启动、禁止等
     */
    private Integer spaceStatus;

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
        Keyspace other = (Keyspace) that;
        return (this.getSpaceId() == null ? other.getSpaceId() == null : this.getSpaceId().equals(other.getSpaceId()))
            && (this.getSpaceHsmId() == null ? other.getSpaceHsmId() == null : this.getSpaceHsmId().equals(other.getSpaceHsmId()))
            && (this.getSpaceUserId() == null ? other.getSpaceUserId() == null : this.getSpaceUserId().equals(other.getSpaceUserId()))
            && (this.getSpaceDesc() == null ? other.getSpaceDesc() == null : this.getSpaceDesc().equals(other.getSpaceDesc()))
            && (this.getSpaceStatus() == null ? other.getSpaceStatus() == null : this.getSpaceStatus().equals(other.getSpaceStatus()))
            && (this.getCreatedBy() == null ? other.getCreatedBy() == null : this.getCreatedBy().equals(other.getCreatedBy()))
            && (this.getCreatedTime() == null ? other.getCreatedTime() == null : this.getCreatedTime().equals(other.getCreatedTime()))
            && (this.getUpdatedBy() == null ? other.getUpdatedBy() == null : this.getUpdatedBy().equals(other.getUpdatedBy()))
            && (this.getUpdatedTime() == null ? other.getUpdatedTime() == null : this.getUpdatedTime().equals(other.getUpdatedTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSpaceId() == null) ? 0 : getSpaceId().hashCode());
        result = prime * result + ((getSpaceHsmId() == null) ? 0 : getSpaceHsmId().hashCode());
        result = prime * result + ((getSpaceUserId() == null) ? 0 : getSpaceUserId().hashCode());
        result = prime * result + ((getSpaceDesc() == null) ? 0 : getSpaceDesc().hashCode());
        result = prime * result + ((getSpaceStatus() == null) ? 0 : getSpaceStatus().hashCode());
        result = prime * result + ((getCreatedBy() == null) ? 0 : getCreatedBy().hashCode());
        result = prime * result + ((getCreatedTime() == null) ? 0 : getCreatedTime().hashCode());
        result = prime * result + ((getUpdatedBy() == null) ? 0 : getUpdatedBy().hashCode());
        result = prime * result + ((getUpdatedTime() == null) ? 0 : getUpdatedTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", spaceId=").append(spaceId);
        sb.append(", spaceHsmId=").append(spaceHsmId);
        sb.append(", spaceUserId=").append(spaceUserId);
        sb.append(", spaceDesc=").append(spaceDesc);
        sb.append(", spaceStatus=").append(spaceStatus);
        sb.append(", createdBy=").append(createdBy);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", updatedBy=").append(updatedBy);
        sb.append(", updatedTime=").append(updatedTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}