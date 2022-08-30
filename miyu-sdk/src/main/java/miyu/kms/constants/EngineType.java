package miyu.kms.constants;

/**
 * @author : xudean
 * @version V1.0
 * @Description: TODO
 * @date Date : 2022年08月22日 下午5:19
 */
public enum EngineType {
    RSA_FILE_ENGINE("RSA文件引擎");

    private String desc;

    EngineType(String desc){
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
