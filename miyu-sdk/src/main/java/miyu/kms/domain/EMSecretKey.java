package miyu.kms.domain;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.digest.DigestUtil;

import javax.crypto.SecretKey;

/**
 * @author : xudean
 * @version V1.0
 * @Description: TODO
 * @date Date : 2022年08月22日 下午5:29
 */
public class EMSecretKey {
    private SecretKey secretKey;
    private String alias;

    public static EMSecretKey buildEMSSecretKey(SecretKey secretKey){
        EMSecretKey emSecretKey = new EMSecretKey();
        emSecretKey.setAlias(Base64.encode(DigestUtil.sha1(secretKey.getEncoded())));
        emSecretKey.setSecretKey(secretKey);
        return emSecretKey;
    }
    public SecretKey getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
