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
        Hsm other = (Hsm) that;
        return (this.getHsmId() == null ? other.getHsmId() == null : this.getHsmId().equals(other.getHsmId()))
            && (this.getHsmHost() == null ? other.getHsmHost() == null : this.getHsmHost().equals(other.getHsmHost()))
            && (this.getHsmPort() == null ? other.getHsmPort() == null : this.getHsmPort().equals(other.getHsmPort()))
            && (this.getHsmUser() == null ? other.getHsmUser() == null : this.getHsmUser().equals(other.getHsmUser()))
            && (this.getHsmPassword() == null ? other.getHsmPassword() == null : this.getHsmPassword().equals(other.getHsmPassword()))
            && (this.getHsmType() == null ? other.getHsmType() == null : this.getHsmType().equals(other.getHsmType()))
            && (this.getCreatedBy() == null ? other.getCreatedBy() == null : this.getCreatedBy().equals(other.getCreatedBy()))
            && (this.getCreatedTime() == null ? other.getCreatedTime() == null : this.getCreatedTime().equals(other.getCreatedTime()))
            && (this.getUpdatedBy() == null ? other.getUpdatedBy() == null : this.getUpdatedBy().equals(other.getUpdatedBy()))
            && (this.getUpdatedTime() == null ? other.getUpdatedTime() == null : this.getUpdatedTime().equals(other.getUpdatedTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getHsmId() == null) ? 0 : getHsmId().hashCode());
        result = prime * result + ((getHsmHost() == null) ? 0 : getHsmHost().hashCode());
        result = prime * result + ((getHsmPort() == null) ? 0 : getHsmPort().hashCode());
        result = prime * result + ((getHsmUser() == null) ? 0 : getHsmUser().hashCode());
        result = prime * result + ((getHsmPassword() == null) ? 0 : getHsmPassword().hashCode());
        result = prime * result + ((getHsmType() == null) ? 0 : getHsmType().hashCode());
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
        sb.append(", hsmId=").append(hsmId);
        sb.append(", hsmHost=").append(hsmHost);
        sb.append(", hsmPort=").append(hsmPort);
        sb.append(", hsmUser=").append(hsmUser);
        sb.append(", hsmPassword=").append(hsmPassword);
        sb.append(", hsmType=").append(hsmType);
        sb.append(", createdBy=").append(createdBy);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", updatedBy=").append(updatedBy);
        sb.append(", updatedTime=").append(updatedTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}