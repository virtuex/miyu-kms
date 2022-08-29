package miyu.kms.domain.config;

import miyu.kms.domain.HsmConfig;

/**
 * @author : xudean
 * @version V1.0
 * @Description: 文件密码机的配置
 * @date Date : 2022年08月29日 上午11:47
 */
public class FileEngineConfig extends HsmConfig {
    /**
     * 用于指定keystore存放路径，会以[path]_[user]的命名来存
     */
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
