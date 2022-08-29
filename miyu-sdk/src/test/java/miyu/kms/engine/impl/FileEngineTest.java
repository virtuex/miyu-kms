package miyu.kms.engine.impl;

import miyu.kms.domain.config.FileEngineConfig;

import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public class FileEngineTest {
    FileEngine fileEngine = new FileEngine();

    public void initParam() throws CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException {
        FileEngineConfig fileEngineConfig = new FileEngineConfig();
        fileEngineConfig.setUser("xuda");
        fileEngineConfig.setPath("/home/xudean/myworkspace/miyu-kms/miyu-sdk");
        fileEngine.initEngine(fileEngineConfig);
        KeyPair keyPair = fileEngine.genKeyPair("RSA");
        System.out.println();
    }


    public static void main(String[] args) throws CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException {
        FileEngineTest fileEngineTest = new FileEngineTest();
        fileEngineTest.initParam();

    }

  
}