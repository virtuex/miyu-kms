package miyu.kms.constant;

/**
 * @author : xudean
 * @version V1.0
 * @Description: TODO
 * @date Date : 2022年08月23日 下午3:26
 */
public enum HsmStatus {
    RUNNING("运行中"),
    CHECKING("状态检查中"),
    SHUTDOWN("密码机关闭"),
    DISABLED("已禁用");
    private String desc;
    HsmStatus(String desc){
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
