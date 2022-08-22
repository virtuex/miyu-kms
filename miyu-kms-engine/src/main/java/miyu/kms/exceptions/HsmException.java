package miyu.kms.exceptions;


/**
 * @author xudean
 * @date 2022/4/14
 */
public class HsmException extends RuntimeException {
    private static final long serialVersionUID = -256915252083508315L;
    private int code;
    private String msg;

    public HsmException() {
        super();
    }

    public HsmException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
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
