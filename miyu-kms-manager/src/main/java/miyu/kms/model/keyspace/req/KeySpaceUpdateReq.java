package miyu.kms.model.keyspace.req;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author : xudean
 * @version V1.0
 * @Description: TODO
 * @date Date : 2022年08月23日 下午5:51
 */
@Data
public class KeySpaceUpdateReq {
    @NotNull
    private String spaceId;

    @NotNull
    private String spaceDesc;
}
