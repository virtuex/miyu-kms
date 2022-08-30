package miyu.kms.utils;

import java.io.*;
import java.security.*;
import java.security.cert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import miyu.kms.utils.BaseUtils;
import miyu.kms.utils.X500NameInfo;
import miyu.kms.utils.X509CertHandler;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.ExtendedKeyUsage;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.cert.X509CRLHolder;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cert.jcajce.JcaX509CRLConverter;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMException;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.openssl.jcajce.JcaPKCS8Generator;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.util.io.pem.PemGenerationException;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;


public class CertUtils extends BaseUtils {

    /**
     * Create RootCertificate
     *
     * @param signAlg
     *            signature algorithm,the type of the corresponding key
     * @param subject
     *            subject
     * @param publicKey
     *            the public key bound by the certificate,used to decrypt the signature
     * @param privateKey
     *            the private key used for encryption to generate the signature
     * @param beginDate
     *            beginDate of the certificate
     * @param endDate
     *            endDate of the certificate
     * @return RootCertificate
     */
    public static X509Certificate createRootCertificate(String signAlg, X500NameInfo subject, PublicKey publicKey,
                                                        PrivateKey privateKey, Date beginDate, Date endDate) {
        X509Certificate cert = null;
        try {
            KeyUsage keyUsage = new KeyUsage(KeyUsage.digitalSignature | KeyUsage.keyCertSign | KeyUsage.cRLSign);
            try {
                cert = X509CertHandler.createRootCert(signAlg, new X500Name(subject.toString()), keyUsage, publicKey,
                    privateKey, beginDate, endDate, null);
            } catch (CertIOException e) {
                throw new RuntimeException(e);
            } catch (OperatorCreationException e) {
                throw new RuntimeException(e);
            }
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        return cert;
    }

    /**
     * create CertificationRequest
     *
     * @param signAlg
     *            signature algorithm,the type of the corresponding key
     * @param subject
     *            subject of the csr
     * @param pubKey
     *            the public key bound by the certificate,used to decrypt the signature
     * @param priKey
     *            the private key used for encryption to generate the signature
     * @return CertificationRequest
     */
    public static PKCS10CertificationRequest createCertRequest(String signAlg, X500NameInfo subject, PublicKey pubKey,
        PrivateKey priKey) {
        PKCS10CertificationRequest request = null;
        try {
            request = X509CertHandler.createCsr(signAlg, new X500Name(subject.toString()), pubKey, priKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return request;
    }



    public static PKCS10CertificationRequest createCertRequest(String signAlg, String subject, PublicKey pubKey,
                                                               PrivateKey priKey) {
        PKCS10CertificationRequest request = null;
        try {
            request = X509CertHandler.createCsr(signAlg, new X500Name(subject), pubKey, priKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return request;
    }

    /**
     * create Child Certificate
     *
     * @param isCaCert
     *            root certificate mark
     * @param signAlg
     *            signature algorithm,the type of the corresponding key
     * @param parentCertificate
     *            certificate of the issuer
     * @param request
     *            certification request
     * @param keyUsage
     *            scenarios where the certificate can be used
     * @param beginDate
     *            beginDate of the certificate
     * @param endDate
     *            endDate of the certificate
     * @param privateKey
     *            the private key used for encryption to generate the signature
     * @param extendedKeyUsage
     *            证书扩展用途
     * @return the generated certificate
     */
    public static X509Certificate createChildCertificate(boolean isCaCert, String signAlg,
        X509Certificate parentCertificate, PKCS10CertificationRequest request, KeyUsage keyUsage, Date beginDate,
        Date endDate, PrivateKey privateKey, ExtendedKeyUsage extendedKeyUsage) {
        X509Certificate childCert = null;
        try {
            childCert = X509CertHandler.createChildCert(isCaCert, signAlg, parentCertificate, request, keyUsage,
                privateKey, beginDate, endDate, extendedKeyUsage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return childCert;
    }

    /**
     * 公/私钥写入文件
     *
     * @param key
     *            公/私钥
     * @param filepath
     *            公/私钥路劲
     */
    public static void writeKey(Key key, String filepath) {
        writeToFile(key, filepath);
    }

    /**
     * 使用Pkcs8格式保存私钥
     *
     * @param privateKey
     *            私钥
     * @param filepath
     *            保存路径
     */
    public static void writePrivateKeyAsPkcs8(PrivateKey privateKey, String filepath) {
        try {
            JcaPKCS8Generator pkcs8Generator = new JcaPKCS8Generator(privateKey, null);
            writeToFile(pkcs8Generator, filepath);
        } catch (PemGenerationException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用Pkcs8格式保存私钥
     *
     * @param privateKey
     *            私钥
     * @return Pkcs8格式私钥
     */
    public static String writePrivateKeyAsPkcs8(PrivateKey privateKey) {
        try {
            JcaPKCS8Generator pkcs8Generator = new JcaPKCS8Generator(privateKey, null);
            return writeToString(pkcs8Generator);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 证书写入文件
     *
     * @param certificate
     *            证书
     * @param filepath
     *            证书路劲
     */
    public static void writeCrt(X509Certificate certificate, String filepath) {
        writeToFile(certificate, filepath);
    }

    /**
     * csr信息写入文件
     *
     * @param request
     *            csr信息
     * @param filepath
     *            csr文件路劲
     */
    public static void writeCsr(PKCS10CertificationRequest request, String filepath) {
        writeToFile(request, filepath);
    }

    /**
     * Crl信息写入文件
     *
     * @param crl
     *            Crl信息
     * @param filepath
     *            Crl文件路劲
     */
    public static void writeCrl(X509CRL crl, String filepath) {
        writeToFile(crl, filepath);
    }

    public static void writeToFile(Object object, String filePath) {
        try (JcaPEMWriter pw = new JcaPEMWriter(new FileWriter(filePath))) {
            pw.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String writeToString(Object object) throws IOException {
        StringWriter writer = new StringWriter();
        JcaPEMWriter pw = new JcaPEMWriter(writer);
        pw.writeObject(object);
        pw.flush();

        String str = writer.toString();
        pw.close();
        writer.close();
        return str;
    }

    /**
     * 读取私钥
     *
     * @param filepath
     *            私钥路劲
     * @return 秘钥对
     */
    public static KeyPair readKey(String filepath) throws PEMException {
        Object obj = readFile(filepath);
        if (obj instanceof PEMKeyPair) {
            return new JcaPEMKeyConverter().setProvider(BouncyCastleProvider.PROVIDER_NAME).getKeyPair((PEMKeyPair)obj);
        }
        return null;
    }

    /**
     * 读取PKCS8格式的私钥
     *
     * @param filepath
     *            私钥路劲
     * @return 私钥
     */
    public static PrivateKey readPrivateKeyFromPkcs8(String filepath) {
        Object obj = readFile(filepath);
        if (obj instanceof PrivateKeyInfo) {
            try {
                return new JcaPEMKeyConverter().setProvider(BouncyCastleProvider.PROVIDER_NAME)
                    .getPrivateKey((PrivateKeyInfo)obj);
            } catch (PEMException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 读取PKCS8格式的私钥
     *
     * @param content
     *            Pkcs8格式私钥
     * @return 私钥
     */
    public static PrivateKey readPrivateKeyFromPkcs8String(String content) {
        Object obj = readFromString(content);
        if (obj instanceof PrivateKeyInfo) {
            try {
                return new JcaPEMKeyConverter().setProvider(BouncyCastleProvider.PROVIDER_NAME)
                    .getPrivateKey((PrivateKeyInfo)obj);
            } catch (PEMException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 读取证书
     *
     * @param filepath
     *            证书路劲
     * @return 证书信息
     * @throws CertificateException
     *             CertificateException
     */
    public static X509Certificate readCrt(String filepath) throws CertificateException {
        Object obj = readFile(filepath);
        if (obj instanceof X509CertificateHolder) {
            return new JcaX509CertificateConverter().setProvider(BouncyCastleProvider.PROVIDER_NAME)
                .getCertificate((X509CertificateHolder)obj);
        }
        return null;
    }

    /**
     * 读取证书
     *
     * @param content
     *            证书路劲
     * @return 证书信息
     * @throws CertificateException
     *             CertificateException
     */
    public static X509Certificate readCrtString(String content) throws CertificateException {
        Object obj = readFromString(content);
        if (obj instanceof X509CertificateHolder) {
            return new JcaX509CertificateConverter().setProvider(BouncyCastleProvider.PROVIDER_NAME)
                .getCertificate((X509CertificateHolder)obj);
        }
        return null;
    }

    public static PKCS10CertificationRequest readCsr(String filepath) {
        Object obj = readFile(filepath);
        if (obj instanceof PKCS10CertificationRequest) {
            return (PKCS10CertificationRequest)obj;
        }
        return null;
    }

    public static X509CRL readCrl(String filepath) throws CRLException {
        Object obj = readFile(filepath);
        if (obj instanceof X509CRLHolder) {
            return new JcaX509CRLConverter().setProvider(BouncyCastleProvider.PROVIDER_NAME).getCRL((X509CRLHolder)obj);
        }
        return null;
    }

    public static Object readFile(String filepath) {
        PemReader pemReader = null;
        Object object = null;
        try {
            pemReader = new PemReader(new FileReader(filepath));
            PEMParser pemParser = new PEMParser(pemReader);
            object = pemParser.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (pemReader != null) {
                try {
                    pemReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return object;
    }

    public static Object readFromString(String str) {
        PemReader pemReader = null;
        Object object = null;
        try {
            pemReader = new PemReader(new StringReader(str));
            PEMParser pemParser = new PEMParser(pemReader);
            object = pemParser.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (pemReader != null) {
                try {
                    pemReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return object;
    }

    /**
     * 把证书链文件合并写入到符合PKCS7标准的文件
     *
     * @param certificateList
     *            证书链文件
     * @param filepath
     *            Pkcs7标准文件,格式xxx.p7b
     * @throws CertificateEncodingException
     *             证书编码异常
     * @throws CMSException
     *             CMSException
     * @throws IOException
     *             IOException
     */
    public static void writeCertsAsPkcs7(List<X509Certificate> certificateList, String filepath)
        throws CertificateEncodingException, CMSException, IOException {

        CMSSignedDataGenerator gen = new CMSSignedDataGenerator();
        JcaCertStore jcaCertStore = new JcaCertStore(certificateList);
        gen.addCertificates(jcaCertStore);

        CMSProcessableByteArray msg = new CMSProcessableByteArray("Hello JuGo!".getBytes());
        CMSSignedData cmsSignedData = gen.generate(msg);
        PemObject pemObject = new PemObject("PKCS7", cmsSignedData.toASN1Structure().getEncoded(ASN1Encoding.DER));

        writeToFile(pemObject, filepath);
    }

    /**
     * 从Pkcs7标准的证书文件中读取证书链
     *
     * @param filepath
     *            Pkcs7标准文件
     * @return 证书链
     */
    public static List<X509Certificate> readCertsFromPkcs7(String filepath) {
        List<X509Certificate> certList = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filepath)) {
            CertificateFactory cf = CertificateFactory.getInstance("X.509", BouncyCastleProvider.PROVIDER_NAME);
            CertPath certPath = cf.generateCertPath(fis, "PKCS7");
            List<?> list = certPath.getCertificates();

            if (!list.isEmpty()) {
                for (Object o : list) {
                    certList.add((X509Certificate)o);
                }
            }
        } catch (IOException | CertificateException | NoSuchProviderException e) {
            e.printStackTrace();
        }
        return certList;
    }
}
