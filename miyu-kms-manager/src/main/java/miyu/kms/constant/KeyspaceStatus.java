package miyu.kms.constant;

/**
 * @author : xudean
 * @version V1.0
 * @Description: TODO
 * @date Date : 2022年08月23日 下午6:04
 */
public enum KeyspaceStatus {
    CREATING(0,"创建中"),
    ENABLED(1,"正常"),
    DISABLED(-1,"已禁用");

    private int code;
    private String msg;

    KeyspaceStatus(int code,String msg){
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
