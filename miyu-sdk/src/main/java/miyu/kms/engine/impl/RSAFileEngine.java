package miyu.kms.engine.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.compiler.DiagnosticUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.KeyUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import cn.hutool.crypto.digest.DigestUtil;
import miyu.kms.constants.EngineType;
import miyu.kms.domain.EMSKeyPair;
import miyu.kms.domain.EMSecretKey;
import miyu.kms.domain.EncryptSecretKey;
import miyu.kms.domain.HsmConfig;
import miyu.kms.domain.config.FileEngineConfig;
import miyu.kms.engine.IEngine;
import miyu.kms.exceptions.CryptoException;
import miyu.kms.exceptions.CryptoExpCode;
import miyu.kms.utils.CertUtils;
import miyu.kms.utils.X500NameInfo;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.Charset;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

/**
 * @author : xudean
 * @version V1.0
 * @Description: 基于keystore的文件加密机
 * @date Date : 2022年08月29日 上午11:26
 */
public class RSAFileEngine implements IEngine {

    private static final String DEFAULT_KEYSTORE_PASSWORD = "Xuda@123456";
    private KeyStore keyStore;
    private String keyStorePath;


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
        try {
            keyStore = initKeyStore(params);
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
    public EMSKeyPair genKeyPair(String algorithm) throws CryptoException {
        KeyPair keyPair = KeyUtil.generateKeyPair(algorithm, 2048);
        saveKeyPairInKeystore(keyPair);
        //应该把私钥存起来，只返回公钥和私钥对应的索引。先都返回，再优化
        return EMSKeyPair.buildEmsKeyPair(keyPair);
    }

    @Override
    public EMSKeyPair genKeyPair(int length) throws CryptoException {
        KeyPair keyPair = KeyUtil.generateKeyPair("RSA", length);
        saveKeyPairInKeystore(keyPair);
//        默认创建一个RSA的
        return EMSKeyPair.buildEmsKeyPair(keyPair);
    }

    @Override
    public EMSKeyPair[] getAllKeyPairStoreInEm() throws CryptoException {
        ///空实现
        List<EMSKeyPair> emsKeyPairs = new ArrayList<>();
        try {
            Enumeration<String> aliases = keyStore.aliases();
            while (aliases.hasMoreElements()) {
                String s = aliases.nextElement();
                EMSKeyPair emsKeyPair = new EMSKeyPair();
                emsKeyPair.setAlias(s);
                emsKeyPairs.add(emsKeyPair);
            }
        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        }

        return ArrayUtil.toArray(emsKeyPairs, EMSKeyPair.class);
    }

    @Override
    public EMSKeyPair[] getAllKeyPairStoreInEm(String type) throws CryptoException {
        //这里默认就返回RSA
        return getAllKeyPairStoreInEm();
    }

    /**
     * 对于文件加密机而言，因为无法在加密机内部运算，所以需要先把私钥读取出来，然后再计算
     * index是索引值，如果需要根据字符串类型找，那么就用string.hashcode
     *
     * @param index 加密机主密钥的索引值
     * @return
     * @throws CryptoException
     */
    @Override
    public EMSKeyPair getKeyPair(String index) throws CryptoException {
        KeyPair keyPair = KeyUtil.getKeyPair(keyStore, DEFAULT_KEYSTORE_PASSWORD.toCharArray(), index + "");
        EMSKeyPair emsKeyPair = EMSKeyPair.buildEmsKeyPair(keyPair);
        emsKeyPair.setPrivateKey(keyPair.getPrivate());
        return emsKeyPair;
    }

    @Override
    public EMSKeyPair getKeyPair(PublicKey pubKey) throws CryptoException {
        String index = Base64.encode(DigestUtil.sha1(pubKey.getEncoded()));
        return getKeyPair(index);
    }

    /**
     * 对文件加密机来说，这个keyId就是key的byte[]
     *
     * @param pubKeyId
     * @return
     * @throws CryptoException
     */
    @Override
    public EMSKeyPair getKeyPair(byte[] pubKeyId) throws CryptoException {
        PublicKey publicKey = KeyUtil.generateRSAPublicKey(pubKeyId);
        return getKeyPair(publicKey);
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
     *K
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
        return verify(pubKeyBytes, data, sigBytes, "SHA256withRSA");
    }

    @Override
    public boolean verify(byte[] pubKeyBytes, byte[] data, byte[] sigBytes, String sigAlgName) throws CryptoException {
        Sign sign = new Sign(sigAlgName, null, pubKeyBytes);
        return sign.verify(data, sigBytes);
    }

    @Override
    public boolean verify(PublicKey pubKey, byte[] data, byte[] sigBytes) throws CryptoException {
        return verify(pubKey, data, sigBytes, "SHA256withRSA");
    }

    @Override
    public boolean verify(PublicKey pubKey, byte[] data, byte[] sigBytes, String sigAlgName) throws CryptoException {
        Sign sign = new Sign(sigAlgName, null, pubKey);
        return sign.verify(data, sigBytes);
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
    public EMSecretKey genSymmetricKey(String symmAlgName) throws CryptoException {
        SecretKey secretKey = KeyUtil.generateKey(symmAlgName);
        return EMSecretKey.buildEMSSecretKey(secretKey);
    }

    @Override
    public EMSecretKey[] getAllSymmetricKeyStoreInEm() throws CryptoException {
        //存储在加密机里的对称密钥，这里返回空
        return new EMSecretKey[0];
    }

    @Override
    public void delAllSymmetricKeyStoreInEm() throws CryptoException {

    }

    @Override
    public void delSymmetricKeyStoreInEm(SecretKey key) throws CryptoException {

    }

    @Override
    public EMSecretKey importSymmetricKeyStoreInEm(byte[] mainKeyValue, String symmAlgName) throws CryptoException {
        return null;
    }

    @Override
    public EncryptSecretKey exportMainKey() throws CryptoException {
        return null;
    }

    @Override
    public EMSecretKey importMainKey(String mainKeyValue, String symmAlgName, String refValue, String pubkeyAlias) throws CryptoException {
        byte[] decode = Base64.decode(mainKeyValue);
        SecretKey originalKey = new SecretKeySpec(decode, 0, decode.length, symmAlgName);
        saveSecretKeyInKeystore(originalKey);
        EMSecretKey emSecretKey = new EMSecretKey();
        emSecretKey.setSecretKey(originalKey);
        emSecretKey.setAlias(generateSecretKeyAlias(originalKey));
        return emSecretKey;
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
        PKCS10CertificationRequest certRequest = CertUtils.createCertRequest(sigAlgName, subjectDN, keyPair.getPublic(), keyPair.getPrivate());
        byte[] encoded;
        try {
            encoded = certRequest.getEncoded();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return encoded;
    }

    @Override
    public boolean checkPair(PublicKey pubKey, PrivateKey prvKey) {
        byte[] bytes = pubEnc(pubKey, "123".getBytes(Charset.defaultCharset()));
        byte[] plainText = priDec(prvKey, bytes);
        if ("123".equals(new String(plainText))) {
            return true;
        }
        return false;
    }

    @Override
    public String getVersionDesc() {
        return "RSA版本文件加密机";
    }

    @Override
    public EngineType getType() {
        return EngineType.FILE;
    }

    @Override
    public String getEngineDesc() {
        return EngineType.FILE.getDesc();
    }

    @Override
    public String[] enumKeyType() {
        return new String[]{"RSA"};
    }

    @Override
    public String[] enumSymmAlgo() {
        return new String[]{"AES", "DES"};
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
    public EMSecretKey GenerateKeyWithKEK(String algo, int keyIndex, int bytesLen) throws CryptoException {
        return null;
    }

    @Override
    public EMSecretKey ImportKeyWithKEK(String algo, int keyIndex, byte[] encryptedContent) throws CryptoException {
        return null;
    }


    public KeyStore initKeyStore(HsmConfig hsmConfig) throws Exception {
        keyStorePath = getKeyStorePath((FileEngineConfig) hsmConfig);
        File file = new File(keyStorePath);
        KeyStore keyStore = KeyStore.getInstance("JCEKS");
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

    private void saveKeyPairInKeystore(KeyPair keyPair) {
        int hashCode = keyPair.getPublic().hashCode();
        X500NameInfo x500NameInfo = new X500NameInfo();
        x500NameInfo.setCommonName(hashCode + "");
        X509Certificate rootCertificate = CertUtils.createRootCertificate(SignAlgorithm.SHA256withRSA.getValue(), x500NameInfo, keyPair.getPublic(), keyPair.getPrivate(), new Date(), DateUtil.offsetMonth(new Date(), 10 * 12));
        Certificate[] certificates = new Certificate[]{rootCertificate};
        try {
            keyStore.setKeyEntry(generateAlias(keyPair), keyPair.getPrivate(), DEFAULT_KEYSTORE_PASSWORD.toCharArray(), certificates);
            keyStore.store(new FileOutputStream(keyStorePath), DEFAULT_KEYSTORE_PASSWORD.toCharArray());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void saveSecretKeyInKeystore(SecretKey secretKey) {
        try {
            keyStore.setEntry(generateSecretKeyAlias(secretKey), new KeyStore.SecretKeyEntry(secretKey), new KeyStore.PasswordProtection(DEFAULT_KEYSTORE_PASSWORD.toCharArray()));
            keyStore.store(new FileOutputStream(keyStorePath), DEFAULT_KEYSTORE_PASSWORD.toCharArray());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public String generateAlias(KeyPair keyPair) {
        return Base64.encode(DigestUtil.sha1(keyPair.getPublic().getEncoded()));
    }

    public String generateSecretKeyAlias(SecretKey secretKey) {
        return Base64.encode(DigestUtil.sha1(secretKey.getEncoded()));
    }


}
