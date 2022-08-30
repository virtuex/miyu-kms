package miyu.kms.engine.impl;

import cn.hutool.core.codec.Base64;
import miyu.kms.domain.EMSKeyPair;
import miyu.kms.domain.EMSecretKey;
import miyu.kms.domain.config.FileEngineConfig;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.Optional;

public class FileEngineTest {
    RSAFileEngine fileEngine = new RSAFileEngine();

    public void initParam() throws CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException {
        FileEngineConfig fileEngineConfig = new FileEngineConfig();
        fileEngineConfig.setUser("xuda");
        fileEngineConfig.setPath("/home/xudean/myworkspace/miyu-kms/miyu-sdk");
        fileEngine.initEngine(fileEngineConfig);
        System.out.println();
    }


    public static void main(String[] args) throws CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException {
        FileEngineTest fileEngineTest = new FileEngineTest();
        fileEngineTest.initParam();
        EMSKeyPair[] allKeyPairStoreInEm = fileEngineTest.getFileEngine().getAllKeyPairStoreInEm();
        fileEngineTest.getFileEngine().genKeyPair(2048);
       Arrays.stream(allKeyPairStoreInEm).forEach(e->{
           System.out.println(e.getAlias());
       });
        EMSKeyPair keyPair = fileEngineTest.getFileEngine().getKeyPair("zx+embyvml/xvc0k6o7lhkb9xh4=");
        System.out.println(keyPair.getAlias()+keyPair.getPublicKey().getAlgorithm());
//        RSAFileEngine fileEngine1 = fileEngineTest.getFileEngine();
//        EMSecretKey emSecretKey = fileEngine1.genSymmetricKey("AES");
//        String encode = Base64.encode(emSecretKey.getSecretKey().getEncoded());
//        System.out.println(emSecretKey.getSecretKey());
//        fileEngine1.importMainKey(encode,"RSA",null,fileEngine1.generateSecretKeyAlias(emSecretKey.getSecretKey()));
    }

    public RSAFileEngine getFileEngine() {
        return fileEngine;
    }
}