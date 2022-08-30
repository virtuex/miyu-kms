package miyu.kms.utils;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.ExtendedKeyUsage;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.pkcs.PKCS10CertificationRequestBuilder;
import org.bouncycastle.pkcs.jcajce.JcaPKCS10CertificationRequest;
import org.bouncycastle.pkcs.jcajce.JcaPKCS10CertificationRequestBuilder;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Random;


public class X509CertHandler {
    /**
     * 创建CA根证书
     * 
     * @param signAlg
     *            签名算法
     * @param subject
     *            证书持有者信息
     * @param keyUsage
     *            证书用途
     * @param publicKey
     *            证书持有者公钥
     * @param privateKey
     *            证书签名私钥,创建CA根证书时使用证书持有者的私钥自签名
     * @param beginDate
     *            证书有效期: 开始时间
     * @param endDate
     *            证书有效期: 结束时间
     * @param extendedKeyUsage
     *            证书扩展用途
     * @return X509证书
     * @throws CertificateException
     *             CertificateException
     * @throws CertIOException
     *             CertIOException
     * @throws OperatorCreationException
     *             OperatorCreationException
     */
    public static X509Certificate createRootCert(String signAlg, X500Name subject, KeyUsage keyUsage,
        PublicKey publicKey, PrivateKey privateKey, Date beginDate, Date endDate, ExtendedKeyUsage extendedKeyUsage)
        throws CertificateException, CertIOException, OperatorCreationException {

        return createCert(true, signAlg, subject, subject, keyUsage, publicKey, privateKey, beginDate, endDate,
            extendedKeyUsage);
    }

    /**
     * 创建子证书
     * 
     * @param isCaCert
     *            是否是CA证书
     * @param signAlg
     *            签名算法
     * @param parentCert
     *            证书签发者证书
     * @param request
     *            证书CSR
     * @param keyUsage
     *            证书用途
     * @param privateKey
     *            证书签发者私钥
     * @param beginDate
     *            证书有效期: 开始时间
     * @param endDate
     *            证书有效期: 结束时间
     * @param extendedKeyUsage
     *            证书扩展用途
     * @return X509证书
     * @throws NoSuchAlgorithmException
     *             NoSuchAlgorithmException
     * @throws InvalidKeyException
     *             InvalidKeyException
     * @throws CertificateException
     *             CertificateException
     * @throws CertIOException
     *             CertIOException
     * @throws OperatorCreationException
     *             OperatorCreationException
     */
    public static X509Certificate createChildCert(boolean isCaCert, String signAlg, X509Certificate parentCert,
        PKCS10CertificationRequest request, KeyUsage keyUsage, PrivateKey privateKey, Date beginDate, Date endDate,
        ExtendedKeyUsage extendedKeyUsage) throws NoSuchAlgorithmException, InvalidKeyException, CertificateException,
        CertIOException, OperatorCreationException {

        JcaPKCS10CertificationRequest jcaRequest = new JcaPKCS10CertificationRequest(request);
        PublicKey publicKey = jcaRequest.getPublicKey();
        return createCert(isCaCert, signAlg, request.getSubject(),
            X500Name.getInstance(parentCert.getSubjectX500Principal().getEncoded()), keyUsage, publicKey, privateKey,
            beginDate, endDate, extendedKeyUsage);
    }

    /**
     * 创建证书
     * 
     * @param isCaCert
     *            是否是CA证书
     * @param signAlg
     *            签名算法
     * @param subject
     *            证书持有者信息
     * @param issuer
     *            证书签发者信息
     * @param keyUsage
     *            证书用途
     * @param publicKey
     *            证书公钥
     * @param privateKey
     *            证书签发者私钥
     * @param beginDate
     *            证书有效期: 开始时间
     * @param endDate
     *            证书有效期: 结束时间
     * @param extendedKeyUsage
     *            证书扩展用途
     * @return X509证书
     * @throws CertIOException
     *             CertIOException
     * @throws OperatorCreationException
     *             OperatorCreationException
     * @throws CertificateException
     *             CertificateException
     */
    public static X509Certificate createCert(boolean isCaCert, String signAlg, X500Name subject, X500Name issuer,
        KeyUsage keyUsage, PublicKey publicKey, PrivateKey privateKey, Date beginDate, Date endDate,
        ExtendedKeyUsage extendedKeyUsage) throws CertIOException, OperatorCreationException, CertificateException {

        X509v3CertificateBuilder v3CertBuilder = new JcaX509v3CertificateBuilder(issuer,
            BigInteger.probablePrime(64, new Random()), beginDate, endDate, subject, publicKey);

        v3CertBuilder.addExtension(Extension.basicConstraints, false, new BasicConstraints(isCaCert));

        if (null != keyUsage) {
            v3CertBuilder.addExtension(Extension.keyUsage, false, keyUsage);
        }

        if (null != extendedKeyUsage) {
            v3CertBuilder.addExtension(Extension.extendedKeyUsage, false, extendedKeyUsage);
        }

        JcaContentSignerBuilder contentSignerBuilder = new JcaContentSignerBuilder(signAlg);
        contentSignerBuilder.setProvider(BouncyCastleProvider.PROVIDER_NAME);
        ContentSigner signer = contentSignerBuilder.build(privateKey);

        X509CertificateHolder holder = v3CertBuilder.build(signer);

        return new JcaX509CertificateConverter().setProvider(BouncyCastleProvider.PROVIDER_NAME).getCertificate(holder);
    }

    /**
     * 创建CSR
     * 
     * @param signAlg
     *            签名算法
     * @param subject
     *            证书持有者信息
     * @param publicKey
     *            证书公钥
     * @param privateKey
     *            证书私钥
     * @return CSR信息
     * @throws OperatorCreationException
     *             OperatorCreationException
     */
    public static PKCS10CertificationRequest createCsr(String signAlg, X500Name subject, PublicKey publicKey,
        PrivateKey privateKey) throws OperatorCreationException {

        PKCS10CertificationRequestBuilder csrBuilder = new JcaPKCS10CertificationRequestBuilder(subject, publicKey);
        ContentSigner contentSigner =
            new JcaContentSignerBuilder(signAlg).setProvider(BouncyCastleProvider.PROVIDER_NAME).build(privateKey);
        return csrBuilder.build(contentSigner);
    }
}
