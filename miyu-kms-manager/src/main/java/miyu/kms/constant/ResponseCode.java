package miyu.kms.constant;

/**
 * @author xudean
 * @date 2022/4/14
 */
public enum ResponseCode {
    /**
     * 成功
     */
    SUCCESS(10000, "请求成功!"),

    /**
     * 失败
     */
    FAIL(20000, "请求失败!");

    private final int code;
    private final String msg;

    ResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static ResponseCode getByCode(int code) {
        for (ResponseCode e : values()) {
            if (e.getCode() == code) {
                return e;
            }
        }
        return null;
    }
}
