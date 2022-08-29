package miyu.kms.engine.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.KeyUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import miyu.kms.constants.EngineType;
import miyu.kms.constants.SymmAlgo;
import miyu.kms.domain.EMSecretKey;
import miyu.kms.domain.EncryptSecretKey;
import miyu.kms.domain.HsmConfig;
import miyu.kms.domain.config.FileEngineConfig;
import miyu.kms.engine.IEngine;
import miyu.kms.exceptions.CryptoException;
import miyu.kms.exceptions.CryptoExpCode;

import javax.crypto.SecretKey;
import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;

/**
 * @author : xudean
 * @version V1.0
 * @Description: 基于keystore的文件加密机
 * @date Date : 2022年08月29日 上午11:26
 */
public class FileEngine implements IEngine {

    private static final String DEFAULT_KEYSTORE_PASSWORD = "Xuda@123456";
    private KeyStore keyStore;


    private String getKeyStorePath(FileEngineConfig fileEngineConfig) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(fileEngineConfig.getPath()).append(File.separator)
                .append(fileEngineConfig.getUser()).append(File.separator).append(fileEngineConfig.getUser() + "." + "keystore");
        return stringBuffer.toString();
    }

    @Override
    public void initEngine(HsmConfig params) throws CryptoException, IOException, CertificateException, KeyStoreException, NoSuchAlgorithmException {
        if (!(params instanceof FileEngineConfig)) {
            throw new CryptoException(CryptoExpCode.PARAM_ERROR, "param should instance of miyu.kms.domain.config.FileEngineConfig");
        }
        if (StrUtil.isEmpty(params.getUser()) || StrUtil.isEmpty((((FileEngineConfig) params).getPath()))) {
            throw new CryptoException(CryptoExpCode.PARAM_ERROR, "user or path is needed");
        }
        if (FileUtil.exist(getKeyStorePath((FileEngineConfig) params))) {
            //如果已经存在就什么都不做
            return;
        }
        try {
            keyStore = createKeyStore(params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void reinitEngine(HsmConfig params) throws CryptoException {
        try {
            initEngine(params);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CertificateException e) {
            throw new RuntimeException(e);
        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void reinitEngine(String ip, int port) throws CryptoException {

    }

    @Override
    public KeyPair genKeyPair(String algorithm) throws CryptoException {
        //应该把私钥存起来，只返回公钥和私钥对应的索引。先都返回，再优化
        return KeyUtil.generateKeyPair(algorithm);
    }

    @Override
    public KeyPair genKeyPair(int length) throws CryptoException {
//        默认创建一个RSA的
        return KeyUtil.generateKeyPair("RSA");
    }

    @Override
    public KeyPair[] getAllKeyPairStoreInEm() throws CryptoException {
        ///空实现
        return new KeyPair[0];
    }

    @Override
    public KeyPair[] getAllKeyPairStoreInEm(String type) throws CryptoException {
        return new KeyPair[0];
    }

    /**
     * index是索引值，如果需要根据字符串类型找，那么就用string.hashcode
     *
     * @param index 加密机主密钥的索引值
     * @return
     * @throws CryptoException
     */
    @Override
    public KeyPair getKeyPair(int index) throws CryptoException {
        return null;
    }

    @Override
    public KeyPair getKeyPair(PublicKey pubKey) throws CryptoException {
        return null;
    }

    @Override
    public KeyPair getKeyPair(byte[] pubKeyId) throws CryptoException {
        return null;
    }

    @Override
    public byte[] pubEnc(byte[] pubKeyBytes, byte[] data) throws CryptoException {
        //keystore默认只存rsa
        RSA rsa = new RSA(null, pubKeyBytes);
        return rsa.encrypt(data, KeyType.PublicKey);
    }

    @Override
    public byte[] pubEnc(PublicKey pubKey, byte[] data) throws CryptoException {
        RSA rsa = new RSA(null, pubKey);
        return rsa.encrypt(data, KeyType.PublicKey);
    }

    @Override
    public byte[] priDec(byte[] prvKeyBytes, byte[] data) throws CryptoException {
        RSA rsa = new RSA(prvKeyBytes, null);
        return rsa.decrypt(data, KeyType.PrivateKey);
    }

    @Override
    public byte[] priDec(PrivateKey prvKey, byte[] data) throws CryptoException {
        RSA rsa = new RSA(prvKey, null);
        return rsa.decrypt(data, KeyType.PrivateKey);
    }

    /**
     * 签名算法参考{@link SignAlgorithm}
     *
     * @param prvKeyBytes
     * @param data
     * @return
     * @throws CryptoException
     */

    @Override
    public byte[] sign(byte[] prvKeyBytes, byte[] data) throws CryptoException {
        sign(prvKeyBytes, data, "SHA256withRSA");
        return new byte[0];
    }

    @Override
    public byte[] sign(byte[] prvKeyBytes, byte[] data, String sigAlgName) throws CryptoException {
        Sign sign = new Sign(sigAlgName, prvKeyBytes, null);
        return sign.sign(data);
    }

    @Override
    public byte[] sign(PrivateKey prvKey, byte[] data) throws CryptoException {
        return sign(prvKey, data, "SHA256withRSA");
    }

    @Override
    public byte[] sign(PrivateKey prvKey, byte[] data, String sigAlgName) throws CryptoException {
        Sign sign = new Sign(sigAlgName, prvKey, null);
        return sign.sign(data);
    }

    @Override
    public byte[] sign(PrivateKey prvKey, byte[] data, String sigAlgName, byte[] sm3UID) throws CryptoException {
        throw new CryptoException(CryptoExpCode.CRYPTO_ERROR, "not support sm2 sign");
    }

    @Override
    public boolean verify(byte[] pubKeyBytes, byte[] data, byte[] sigBytes) throws CryptoException {
        return verify(pubKeyBytes,data,sigBytes,"SHA256withRSA");
    }

    @Override
    public boolean verify(byte[] pubKeyBytes, byte[] data, byte[] sigBytes, String sigAlgName) throws CryptoException {
        Sign sign = new Sign(sigAlgName, null, pubKeyBytes);
        return sign.verify(data,sigBytes);
    }

    @Override
    public boolean verify(PublicKey pubKey, byte[] data, byte[] sigBytes) throws CryptoException {
        return verify(pubKey,data,sigBytes,"SHA256withRSA");
    }

    @Override
    public boolean verify(PublicKey pubKey, byte[] data, byte[] sigBytes, String sigAlgName) throws CryptoException {
        Sign sign = new Sign(sigAlgName, null, pubKey);
        return sign.verify(data,sigBytes);
    }

    @Override
    public boolean verify(PublicKey pubKey, byte[] data, byte[] sigBytes, String sigAlgName, byte[] sm3UID) throws CryptoException {
        throw new CryptoException(CryptoExpCode.CRYPTO_ERROR, "not support sm2 verify");
    }

    @Override
    public byte[] getRandom(int nRndLength) throws CryptoException {
        byte[] bytes = new byte[nRndLength];
        new SecureRandom().nextBytes(bytes);
        return bytes;
    }

    @Override
    public void getRandom(byte[] buffer) throws CryptoException {
        new SecureRandom().nextBytes(buffer);
    }

    @Override
    public SecretKey genSymmetricKey(String symmAlgName) throws CryptoException {
        return KeyUtil.generateKey(symmAlgName);
    }

    @Override
    public SecretKey[] getAllSymmetricKeyStoreInEm() throws CryptoException {
        return new SecretKey[0];
    }

    @Override
    public void delAllSymmetricKeyStoreInEm() throws CryptoException {

    }

    @Override
    public void delSymmetricKeyStoreInEm(SecretKey key) throws CryptoException {

    }

    @Override
    public SecretKey importSymmetricKeyStoreInEm(byte[] mainKeyValue, String symmAlgName) throws CryptoException {
        return null;
    }

    @Override
    public EncryptSecretKey exportMainKey() throws CryptoException {
        return null;
    }

    @Override
    public SecretKey importMainKey(String mainKeyValue, String symmAlgName, String refValue, String pubkeyAlias) throws CryptoException {
        return null;
    }

    @Override
    public byte[] symmetricEnc(SecretKey key, byte[] data, String symmAlgName) throws CryptoException {
        return new byte[0];
    }

    @Override
    public byte[] symmetricEnc(byte[] keyValue, byte[] data, String symmAlgName) throws CryptoException {
        return new byte[0];
    }

    @Override
    public byte[] symmetricDec(SecretKey key, byte[] data, String symmAlgName) throws CryptoException {
        return new byte[0];
    }

    @Override
    public byte[] symmetricDec(byte[] keyValue, byte[] data, String symmAlgName) throws CryptoException {
        return new byte[0];
    }

    @Override
    public byte[] genPKCS10Req(KeyPair keyPair, String subjectDN, String sigAlgName) throws CryptoException {
        return new byte[0];
    }

    @Override
    public boolean checkPair(PublicKey pubKey, PrivateKey prvKey) {
        return false;
    }

    @Override
    public String getVersionDesc() {
        return null;
    }

    @Override
    public EngineType getType() {
        return null;
    }

    @Override
    public String getEngineDesc() {
        return null;
    }

    @Override
    public String[] enumKeyType() {
        return new String[0];
    }

    @Override
    public SymmAlgo[] enumSymmAlgo() {
        return new SymmAlgo[0];
    }

    @Override
    public EMSecretKey GenerateKeyWithIPK(int keyIndex, int bytesLen) throws CryptoException {
        return null;
    }

    @Override
    public EMSecretKey ImportKeyWithISK(int keyIndex, byte[] encryptedContent) throws CryptoException {
        return null;
    }

    @Override
    public EMSecretKey GenerateKeyWithEPK(PublicKey pubKey, int bytesLen) throws CryptoException {
        return null;
    }

    @Override
    public EMSecretKey GenerateKeyWithKEK(SymmAlgo algo, int keyIndex, int bytesLen) throws CryptoException {
        return null;
    }

    @Override
    public EMSecretKey ImportKeyWithKEK(SymmAlgo algo, int keyIndex, byte[] encryptedContent) throws CryptoException {
        return null;
    }


    public KeyStore createKeyStore(HsmConfig hsmConfig) throws Exception {
        File file = new File(getKeyStorePath((FileEngineConfig) hsmConfig));
        KeyStore keyStore = KeyStore.getInstance("JKS");
        if (file.exists()) {
            // if exists, load
            keyStore.load(new FileInputStream(file), DEFAULT_KEYSTORE_PASSWORD.toCharArray());
        } else {
            file.getParentFile().mkdirs();
            // if not exists, create
            keyStore.load(null, null);
            keyStore.store(new FileOutputStream(file), DEFAULT_KEYSTORE_PASSWORD.toCharArray());
        }
        return keyStore;
    }
}
