package miyu.kms.exceptions;

import miyu.kms.constant.ResponseCode;

/**
 * @author xudean
 * @date 2022/4/14
 */
public class BizException extends RuntimeException {
    private static final long serialVersionUID = -256915252083508315L;
    private int code;
    private String msg;

    public BizException() {
        super();
    }

    public BizException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public BizException(ResponseCode responseCode) {
        super(responseCode.getMsg());
        this.code = responseCode.getCode();
        this.msg = responseCode.getMsg();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
