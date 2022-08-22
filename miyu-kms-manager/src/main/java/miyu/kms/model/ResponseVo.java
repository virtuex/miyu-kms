package miyu.kms.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import miyu.kms.constant.ResponseCode;

/**
 * @author Oliver
 * @date 2021/8/11
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseVo<T> {
    private final Integer code;

    private final String msg;

    private T data;

    private ResponseVo(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private ResponseVo(int code, String msg, T data) {
        this(code, msg);
        this.data = data;
    }

    private ResponseVo(ResponseCode respCode) {
        this(respCode.getCode(), respCode.getMsg());
    }

    private ResponseVo(ResponseCode respCode, T data) {
        this(respCode.getCode(), respCode.getMsg(), data);
    }

    public static <T> ResponseVo<T> createSuccess() {
        return new ResponseVo<>(ResponseCode.SUCCESS);
    }

    public static <T> ResponseVo<T> createSuccess(T data) {
        return new ResponseVo<>(ResponseCode.SUCCESS, data);
    }

    public static <T> ResponseVo<T> create(ResponseCode respCode) {
        return new ResponseVo<>(respCode);
    }

    public static <T> ResponseVo<T> create(ResponseCode respCode, T data) {
        return new ResponseVo<>(respCode, data);
    }

    public static <T> ResponseVo<T> create(int code, String msg) {
        return new ResponseVo<>(code, msg);
    }

    public static <T> ResponseVo<T> create(int code, String msg, T data) {
        return new ResponseVo<>(code, msg, data);
    }
}
