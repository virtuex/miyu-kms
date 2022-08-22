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

}