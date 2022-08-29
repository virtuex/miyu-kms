package miyu.kms.engine.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import miyu.kms.constants.EngineType;
import miyu.kms.constants.KeyType;
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

/**
 * @author : xudean
 * @version V1.0
 * @Description: 基于keystore的文件加密机
 * @date Date : 2022年08月29日 上午11:26
 */
public class FileEngine implements IEngine {

    private static final String DEFAULT_KEYSTORE_PASSWORD = "Xuda@123456";


    private String getKeyStorePath(FileEngineConfig fileEngineConfig){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(fileEngineConfig.getPath()).append(File.separator)
                .append(fileEngineConfig.getUser()).append(File.separator).append(fileEngineConfig.getUser()+"."+"keystore");
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
        if(FileUtil.exist(getKeyStorePath((FileEngineConfig)params))){
            //如果已经存在就什么都不做
            return ;
        }
        try {
            createKeyStore(params);
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
    public KeyPair genKeyPair(KeyType type) throws CryptoException {
        return null;
    }

    @Override
    public KeyPair genKeyPair(int length) throws CryptoException {
        return null;
    }

    @Override
    public KeyPair[] getAllKeyPairStoreInEm() throws CryptoException {
        return new KeyPair[0];
    }

    @Override
    public KeyPair[] getAllKeyPairStoreInEm(KeyType type) throws CryptoException {
        return new KeyPair[0];
    }

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
        return new byte[0];
    }

    @Override
    public byte[] pubEnc(PublicKey pubKey, byte[] data) throws CryptoException {
        return new byte[0];
    }

    @Override
    public byte[] priDec(byte[] prvKeyBytes, byte[] data) throws CryptoException {
        return new byte[0];
    }

    @Override
    public byte[] priDec(PrivateKey prvKey, byte[] data) throws CryptoException {
        return new byte[0];
    }

    @Override
    public byte[] sign(byte[] prvKeyBytes, byte[] data) throws CryptoException {
        return new byte[0];
    }

    @Override
    public byte[] sign(byte[] prvKeyBytes, byte[] data, String sigAlgName) throws CryptoException {
        return new byte[0];
    }

    @Override
    public byte[] sign(PrivateKey prvKey, byte[] data) throws CryptoException {
        return new byte[0];
    }

    @Override
    public byte[] sign(PrivateKey prvKey, byte[] data, String sigAlgName) throws CryptoException {
        return new byte[0];
    }

    @Override
    public byte[] sign(PrivateKey prvKey, byte[] data, String sigAlgName, byte[] sm3UID) throws CryptoException {
        return new byte[0];
    }

    @Override
    public boolean verify(byte[] pubKeyBytes, byte[] data, byte[] sigBytes) throws CryptoException {
        return false;
    }

    @Override
    public boolean verify(byte[] pubKeyBytes, byte[] data, byte[] sigBytes, String sigAlgName) throws CryptoException {
        return false;
    }

    @Override
    public boolean verify(PublicKey pubKey, byte[] data, byte[] sigBytes) throws CryptoException {
        return false;
    }

    @Override
    public boolean verify(PublicKey pubKey, byte[] data, byte[] sigBytes, String sigAlgName) throws CryptoException {
        return false;
    }

    @Override
    public boolean verify(PublicKey pubKey, byte[] data, byte[] sigBytes, String sigAlgName, byte[] sm3UID) throws CryptoException {
        return false;
    }

    @Override
    public byte[] getRandom(int nRndLength) throws CryptoException {
        return new byte[0];
    }

    @Override
    public void getRandom(byte[] buffer) throws CryptoException {

    }

    @Override
    public SecretKey genSymmetricKey(String symmAlgName) throws CryptoException {
        return null;
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
    public KeyType[] enumKeyType() {
        return new KeyType[0];
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


////
////    public static void executeCommand(String[] arstringCommand) {
////        try {
////            Runtime.getRuntime().exec(arstringCommand);
////
////        } catch (Exception e) {
////            System.out.println(e.getMessage());
////        }
////    }
////
////
////    //生成密钥并保存到jks文件
////    public void buildKeyAndSaveToJksFile() {
////        String[] command = new String[]{
////                "cmd ",
////                "/k",
////                "start", // cmd Shell命令
////                "keytool",
////                "-genkeypair", //表示生成密钥
////                "-alias", //要处理的条目的别名（jks文件别名）
////                "sun",
////                "-keyalg", //密钥算法名称(如 RSA DSA（默认是DSA）)
////                "RSA",
////                "-keysize",//密钥位大小(长度)
////                "1024",
////                "-sigalg", //签名算法名称
////                "SHA1withRSA",
////                "-dname",// 唯一判别名,CN=(名字与姓氏), OU=(组织单位名称), O=(组织名称), L=(城市或区域名称),
////                // ST=(州或省份名称), C=(单位的两字母国家代码)"
////                "CN=(张三), OU=(人民单位), O=(人民组织), L=(广州), ST=(广东), C=(中国)",
////                "-validity", // 有效天数
////                "36500",
////                "-keypass",// 密钥口令(私钥的密码)
////                "123456",
////                "-keystore", //密钥库名称(jks文件路径)
////                "f:/demo.jks",
////                getKeyStorePath((FileEngineConfig) hsmConfig), // 密钥库口令(jks文件的密码)
////                DEFAULT_KEYSTORE_PASSWORD,
////                "-v"// 详细输出（秘钥库中证书的详细信息）
////        };
//        executeCommand(command);
//    }

}
