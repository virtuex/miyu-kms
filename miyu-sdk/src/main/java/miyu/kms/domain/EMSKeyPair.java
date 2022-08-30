package miyu.kms.domain;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.digest.DigestUtil;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author : xudean
 * @version V1.0
 * @Description: TODO
 * @date Date : 2022年08月29日 下午5:28
 */
public class EMSKeyPair {
    private PrivateKey privateKey;
    private PublicKey publicKey;
    /**
     * 密钥存储在密码机中的索引或别名
     */
    private String alias;

    public static EMSKeyPair buildEmsKeyPair(KeyPair keyPair){
        EMSKeyPair emsKeyPair = new EMSKeyPair();
        emsKeyPair.setPublicKey(keyPair.getPublic());
        emsKeyPair.setAlias(Base64.encode(DigestUtil.sha1(keyPair.getPublic().getEncoded())));
        return emsKeyPair;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
