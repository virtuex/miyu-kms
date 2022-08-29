package miyu.kms.domain;

/**
 * @author : xudean
 * @version V1.0
 * @Description: 加密机信息
 * @date Date : 2022年08月22日 下午5:30
 */
public class HsmConfig {
    private String host;
    private String port;
    private String user;
    private String password;


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
