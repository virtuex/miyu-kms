package miyu.kms.exceptions;

/**
 * @author : xudean
 * @version V1.0
 * @Description: TODO
 * @date Date : 2022年08月29日 下午3:39
 */
public enum CryptoExpCode {
    /**
     * 参数错误
     */
    PARAM_ERROR(1001),
    KEY_STORE_ERROR(1002);
    int code;
    CryptoExpCode(int code){
        this.code = code;
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
