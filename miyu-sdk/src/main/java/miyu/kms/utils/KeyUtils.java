package miyu.kms.utils;

import miyu.kms.constants.SignatureAlg;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.generators.ECKeyPairGenerator;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECKeyGenerationParameters;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.math.ec.custom.gm.SM2P256V1Curve;

import java.math.BigInteger;
import java.security.*;
import java.security.spec.ECGenParameterSpec;

/**
 * @author Oliver
 * @date 2022/3/23
 */
public class KeyUtils extends BaseUtils {
    public static final String ALGORITHM_RSA = "RSA";
    public static final String ALGORITHM_EC = "EC";
    public static final String ALGORITHM_ECDSA = "ECDSA";

    public static final String CURVE_NAME_SECP256R1 = "secp256r1";
    public static final String CURVE_NAME_SM2P256V1 = "sm2p256v1";

    public static final SM2P256V1Curve CURVE = new SM2P256V1Curve();
    public final static BigInteger SM2_ECC_GX =
        new BigInteger("32C4AE2C1F1981195F9904466A39C9948FE30BBFF2660BE1715A4589334C74C7", 16);
    public final static BigInteger SM2_ECC_GY =
        new BigInteger("BC3736A2F4F6779C59BDCEE36B692153D0A9877CC62A474002DF32E52139F0A0", 16);
    public final static BigInteger SM2_ECC_N = CURVE.getOrder();
    public final static BigInteger SM2_ECC_H = CURVE.getCofactor();
    public static final org.bouncycastle.math.ec.ECPoint G_POINT = CURVE.createPoint(SM2_ECC_GX, SM2_ECC_GY);
    public static final ECDomainParameters DOMAIN_PARAMS = new ECDomainParameters(CURVE, G_POINT, SM2_ECC_N, SM2_ECC_H);

    static final SecureRandom RANDOM = new SecureRandom();

    /**
     * Generate Rsa KeyPair
     */
    public static KeyPair generateRsaKeyPair() {
        KeyPairGenerator kpg;
        KeyPair keyPair = null;
        try {
            kpg = KeyPairGenerator.getInstance(ALGORITHM_RSA);
            kpg.initialize(2048);
            keyPair = kpg.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return keyPair;
    }

    /**
     * Generate ECDSA KeyPair
     */
    public static KeyPair generateEcdsaKeyPair() {
        return generateKeyPair(ALGORITHM_ECDSA, CURVE_NAME_SECP256R1);
    }

    /**
     * Generate SM2 KeyPair
     */
    public static KeyPair generateSm2KeyPair() {
        return generateKeyPair(ALGORITHM_EC, CURVE_NAME_SM2P256V1);
    }

    private static KeyPair generateKeyPair(String algorithm, String curveName) {
        KeyPairGenerator kpg;
        KeyPair keyPair = null;
        try {
            kpg = KeyPairGenerator.getInstance(algorithm, BouncyCastleProvider.PROVIDER_NAME);
            ECGenParameterSpec ecGenParameterSpec = new ECGenParameterSpec(curveName);
            kpg.initialize(ecGenParameterSpec, RANDOM);
            keyPair = kpg.generateKeyPair();
        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return keyPair;
    }

    public static KeyPair generateSm2KeyPairByParameters()
        throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException {

        return generateKeyPair(DOMAIN_PARAMS, RANDOM);
    }

    public static KeyPair generateKeyPair(ECDomainParameters domainParameters, SecureRandom random)
        throws NoSuchProviderException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {

        KeyPairGenerator kpg = KeyPairGenerator.getInstance(ALGORITHM_EC, BouncyCastleProvider.PROVIDER_NAME);
        ECParameterSpec parameterSpec = new ECParameterSpec(domainParameters.getCurve(), domainParameters.getG(),
            domainParameters.getN(), domainParameters.getH());
        kpg.initialize(parameterSpec, random);
        return kpg.generateKeyPair();
    }

    public static AsymmetricCipherKeyPair generateSm2KeyPairParameter() {
        return generateKeyPairParameter(DOMAIN_PARAMS, RANDOM);
    }

    public static AsymmetricCipherKeyPair generateKeyPairParameter(ECDomainParameters domainParameters,
        SecureRandom random) {
        ECKeyGenerationParameters keyGenerationParams = new ECKeyGenerationParameters(domainParameters, random);
        ECKeyPairGenerator keyGen = new ECKeyPairGenerator();
        keyGen.init(keyGenerationParams);
        return keyGen.generateKeyPair();
    }

    /**
     * 根据签名算法生成密钥对
     *
     * @param signAlg
     * @return
     */
    public static KeyPair generateKeyPairBySignAlg(String signAlg) throws Exception {
        KeyPair keyPair = null;
        if (SignatureAlg.SIGNATURE_SM2.equals(signAlg)) {
            keyPair = KeyUtils.generateSm2KeyPair();
        } else if (SignatureAlg.SIGNATURE_ECDSA.equals(signAlg)) {
            keyPair = KeyUtils.generateEcdsaKeyPair();
        } else if (SignatureAlg.SIGNATURE_RSA.equals(signAlg)) {
            keyPair = KeyUtils.generateRsaKeyPair();
        } else {
            throw new Exception("Unsupported certificate algorithm！");
        }
        return keyPair;
    }

}