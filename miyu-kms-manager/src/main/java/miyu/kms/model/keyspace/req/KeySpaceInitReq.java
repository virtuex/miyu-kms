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
public class KeySpaceInitReq {
    @NotNull
    private Long spaceHsmId;

    private String spaceDesc;
}
