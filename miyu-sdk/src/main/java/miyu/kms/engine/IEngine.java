package miyu.kms.engine;

import miyu.kms.constants.EngineType;
import miyu.kms.constants.SymmAlgo;
import miyu.kms.domain.EMSecretKey;
import miyu.kms.domain.EncryptSecretKey;
import miyu.kms.domain.HsmConfig;
import miyu.kms.exceptions.CryptoException;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;

/**
 * 与密码机接口
 * @author xudean
 */
public interface IEngine {

	/**
	 * 空密钥
	 */
	static final KeyPair NULL_KEY_PAIR = new KeyPair(null, null);

	/**
	 * 用于CBC的默认向量，如果用于16字节的密钥，则将此向量重复一次，构成16字节即可。
	 */
	static final byte[] iv = { 0x0a, 0x01, 0x02, 0x03, 0x04, 0x0b, 0x0c,
			0x0d };
	/**
	 * SM3带公钥摘要时的默认UID
	 */
	static final byte[] SM3_DEF_UID = "1234567812345678".getBytes();

	/**
	 * 初始化Engine 传入Engine使用的基本变量，如加密机类型，IP地址，端口号
	 *
	 * @param params
	 * @throws CryptoException
	 */
	void initEngine(HsmConfig params) throws CryptoException, IOException, CertificateException, KeyStoreException, NoSuchAlgorithmException;

	/**
	 * 重新初始化Engine 传入Engine使用的基本变量，如加密机类型，IP地址，端口号
	 *
	 * @param params
	 * @throws CryptoException
	 */
	void reinitEngine(HsmConfig params) throws CryptoException;

	/**
	 * 重新初始化Engine 传入Engine使用的基本变量，IP地址，端口号，不改变类型
	 *
	 * @param ip
	 * @param port
	 * @throws CryptoException
	 */
	void reinitEngine(String ip, int port) throws CryptoException;

	/**
	 * 生成指定密钥类型用户密钥对
	 */
	KeyPair genKeyPair(String algorithm) throws CryptoException;

	/**
	 * 根据指定密钥长度生成密钥对
	 *
	 * @param length
	 *            ，密钥长度，只支持512,1024,2048
	 * @return
	 * @throws CryptoException
	 */
	KeyPair genKeyPair(int length) throws CryptoException;

	/**
	 * 得到所有保存在加密设备中的非对称密钥对，对于文件设备，私钥是正常私钥值，对于硬件加密设备 ，
	 * 由于使用加密机时都是在面板上生成，私钥中保存的是调用句柄。 同时，由于程序操作不会改变密钥对，所以在使用本函数时，可以只调用一次，
	 * 保存到一个静态变量中 把复使用即可。 同时，该函数返回的密钥对数组，其顺序是根据加密机保存密钥对的位置顺序来调用的。
	 *
	 * @return
	 * @throws CryptoException
	 */
	KeyPair[] getAllKeyPairStoreInEm() throws CryptoException;

	/**
	 * 获取加密机中，指定密钥类型的所有密钥对。其中硬件加密机时，私钥为加密机的句柄(索引)。
	 *
	 * @param algorithm
	 *            密钥类型
	 * @return
	 * @throws CryptoException
	 */
	KeyPair[] getAllKeyPairStoreInEm(String algorithm) throws CryptoException;

	/**
	 * 获取加密设备中已经存在的指定位置的密钥对，对于文件设备，私钥是正常私钥值，对于硬件加密设备 ，
	 * 由于使用加密机时都是在面板上生成，私钥中保存的是调用句柄。
	 *
	 * @param index
	 *            加密机主密钥的索引值
	 * @return
	 * @throws CryptoException
	 */
	KeyPair getKeyPair(int index) throws CryptoException;

	/**
	 * 获取加密设备中已经存在的指定位置的密钥对，对于文件设备，私钥是正常私钥值，对于硬件加密设备 ，
	 * 由于使用加密机时都是在面板上生成，私钥中保存的是调用句柄。 本函数用于直接使用在证书中获取的公钥获取对应的密钥对
	 *
	 * @param pubKey
	 * @return
	 * @throws CryptoException
	 */
	KeyPair getKeyPair(PublicKey pubKey) throws CryptoException;

	/**
	 * 使用公钥的KeyId来获得指定位置的密钥对 KeyId名,即公钥SHA-1摘要B64编码的小写字符串的byte编码
	 *
	 * @param pubKeyId
	 * @return
	 * @throws CryptoException
	 */
	KeyPair getKeyPair(byte[] pubKeyId) throws CryptoException;

	/**
	 * 公钥加密
	 *
	 * @param pubKeyBytes
	 * @param data
	 * @return
	 * @throws CryptoException
	 */
	byte[] pubEnc(byte[] pubKeyBytes, byte[] data) throws CryptoException;

	/**
	 * 公钥加密
	 *
	 * @param pubKey
	 * @param data
	 * @return
	 * @throws CryptoException
	 */
	byte[] pubEnc(PublicKey pubKey, byte[] data) throws CryptoException;

	/**
	 * 私钥解密
	 *
	 * @param prvKeyBytes
	 * @param data
	 * @return
	 * @throws CryptoException
	 */
	byte[] priDec(byte[] prvKeyBytes, byte[] data) throws CryptoException;

	/**
	 * 私钥解密
	 *
	 * @param prvKey
	 * @param data
	 * @return
	 * @throws CryptoException
	 */
	byte[] priDec(PrivateKey prvKey, byte[] data) throws CryptoException;

	/**
	 * 签名
	 *
	 * @param prvKeyBytes
	 * @param data
	 * @return
	 * @throws CryptoException
	 */
	byte[] sign(byte[] prvKeyBytes, byte[] data) throws CryptoException;

	/**
	 * 签名
	 *
	 * @param prvKeyBytes
	 * @param data
	 * @param sigAlgName
	 *            签名算法
	 * @return
	 * @throws CryptoException
	 */
	byte[] sign(byte[] prvKeyBytes, byte[] data, String sigAlgName)
			throws CryptoException;

	/**
	 * 签名
	 *
	 * @param prvKey
	 * @param data
	 * @return
	 * @throws CryptoException
	 */
	byte[] sign(PrivateKey prvKey, byte[] data) throws CryptoException;

	/**
	 * 签名
	 *
	 * @param prvKey
	 * @param data
	 * @param sigAlgName
	 *            签名算法
	 * @return
	 * @throws CryptoException
	 */
	byte[] sign(PrivateKey prvKey, byte[] data, String sigAlgName)
			throws CryptoException;

	/**
	 * 签名
	 * @param prvKey 私钥
	 * @param data 原文数据
	 * @param sigAlgName 签名算法
	 * @param sm3UID SM3带公钥ID运算需要的UID
	 * @return
	 * @throws CryptoException
	 */
	byte[] sign(PrivateKey prvKey, byte[] data, String sigAlgName, byte[] sm3UID)
			throws CryptoException;

	/**
	 * 验证
	 *
	 * @param pubKeyBytes
	 * @param data
	 * @param sigBytes
	 * @return
	 * @throws CryptoException
	 */
	boolean verify(byte[] pubKeyBytes, byte[] data, byte[] sigBytes)
			throws CryptoException;

	/**
	 * 验证
	 *
	 * @param pubKeyBytes
	 * @param data
	 * @param sigBytes
	 * @param sigAlgName
	 *            签名算法
	 * @return
	 * @throws CryptoException
	 */
	boolean verify(byte[] pubKeyBytes, byte[] data, byte[] sigBytes,
			String sigAlgName) throws CryptoException;

	/**
	 * 验证
	 *
	 * @param pubKey
	 * @param data
	 * @param sigBytes
	 * @return
	 * @throws CryptoException
	 */
	boolean verify(PublicKey pubKey, byte[] data, byte[] sigBytes)
			throws CryptoException;

	/**
	 * 验证
	 *
	 * @param pubKey
	 * @param data
	 * @param sigBytes
	 * @param sigAlgName
	 *            签名算法
	 * @return
	 * @throws CryptoException
	 */
	boolean verify(PublicKey pubKey, byte[] data, byte[] sigBytes,
			String sigAlgName) throws CryptoException;

	/**
	 * 验证
	 * @param pubKey 公钥
	 * @param data 原文
	 * @param sigBytes 签名数据
	 * @param sigAlgName 签名算法
	 * @param sm3UID SM3带公钥ID运算需要的UID
	 * @return
	 * @throws CryptoException
	 */
	boolean verify(PublicKey pubKey, byte[] data, byte[] sigBytes,
			String sigAlgName, byte[] sm3UID) throws CryptoException;

	/**
	 * 获取随机数
	 *
	 * @param nRndLength
	 *            随机数长度,其长度输入值受限，如SJY15时该值不能超过1528
	 *
	 * @return
	 * @throws CryptoException
	 */
	byte[] getRandom(int nRndLength) throws CryptoException;

	/**
	 * 根据传入的buffer长度，填入随机数
	 *
	 * @param buffer
	 * @throws CryptoException
	 */
	void getRandom(byte[] buffer) throws CryptoException;

	/**
	 * 生成对称密钥,不同的加密设备表现可能不同？如SJY15只能导出被主密钥加密的工作密钥？
	 *
	 * @param symmAlgName
	 *            对称加密算法，如Des，SSF33
	 * @return
	 * @throws CryptoException
	 */
	SecretKey genSymmetricKey(String symmAlgName) throws CryptoException;

	/**
	 * 列出所有保存在加密机中的对称密钥对（SJY15好象不保存任何这种值）
	 *
	 * @return
	 * @throws CryptoException
	 */
	SecretKey[] getAllSymmetricKeyStoreInEm() throws CryptoException;

	/**
	 * 删除所有保存在加密机中的对称密钥对
	 *
	 * @throws CryptoException
	 */
	void delAllSymmetricKeyStoreInEm() throws CryptoException;

	/**
	 * 删除保存在加密机中的指定对称密钥对
	 *
	 * @param key
	 * @throws CryptoException
	 */
	void delSymmetricKeyStoreInEm(SecretKey key) throws CryptoException;

	/**
	 * 将指定算法的对称密钥对导入到加密机中保存， 用于调用指定算法（如SSF33）进行数字信封的解密
	 *
	 * @param mainKeyValue
	 *            经过加密保护的主密钥字符串
	 * @param symmAlgName
	 *            加密算法
	 * @return
	 * @throws CryptoException
	 */
	SecretKey importSymmetricKeyStoreInEm(byte[] mainKeyValue,
			String symmAlgName) throws CryptoException;

	/**
	 * 导出加密保护的KM业务主密钥
	 *
	 * @return
	 *
	 * @throws CryptoException
	 */
	EncryptSecretKey exportMainKey() throws CryptoException;

	/**
	 * 导入受保护的KMC业务主密钥
	 *
	 * @param mainKeyValue
	 *            经过加密保护的主密钥byte数组经Base64编码后生成的字符串
	 * @param symmAlgName
	 *            加密算法
	 * @param refValue
	 *            参考值
	 * @param pubkeyAlias
	 *            用于保护主密钥公钥的公钥ID
	 * @return
	 * @throws CryptoException
	 */
	SecretKey importMainKey(String mainKeyValue, String symmAlgName,
			String refValue, String pubkeyAlias) throws CryptoException;

	/**
	 * 对称密钥加密
	 *
	 * @param key
	 *            对称密钥
	 * @param data
	 *            要加密的数据
	 * @param symmAlgName
	 *            加密算法,传入时必须使用"算法/加密方式/填充方法"，如"DES/ECB/PKCS5Padding"
	 * @return 加密出的密文
	 * @throws CryptoException
	 */
	byte[] symmetricEnc(SecretKey key, byte[] data, String symmAlgName)
			throws CryptoException;

	/**
	 * 对称密钥加密
	 *
	 * @param keyValue
	 *            对称密钥的二进制值
	 * @param data
	 *            要加密的数据
	 * @param symmAlgName
	 *            加密算法,传入时必须使用"算法/加密方式/填充方法"，如"DES/ECB/PKCS5Padding"
	 * @return 加密出的密文
	 * @throws CryptoException
	 */
	byte[] symmetricEnc(byte[] keyValue, byte[] data, String symmAlgName)
			throws CryptoException;

	/**
	 * 对称密钥解密
	 *
	 * @param key
	 *            对称密钥
	 * @param data
	 *            要解密的数据
	 * @param symmAlgName
	 *            加密算法,传入时必须使用"算法/加密方式/填充方法"，如"DES/ECB/PKCS5Padding"
	 * @return 解密出的密文
	 * @throws CryptoException
	 */
	byte[] symmetricDec(SecretKey key, byte[] data, String symmAlgName)
			throws CryptoException;

	/**
	 * 对称密钥解密
	 *
	 * @param keyValue
	 *            对称密钥的二进制值
	 * @param data
	 *            要解密的数据
	 * @param symmAlgName
	 *            加密算法,传入时必须使用"算法/加密方式/填充方法"，如"DES/ECB/PKCS5Padding"
	 * @return 解密出的密文
	 * @throws CryptoException
	 */
	byte[] symmetricDec(byte[] keyValue, byte[] data, String symmAlgName)
			throws CryptoException;

	/**
	 * 根据指定的密钥对和DN对象生成PKCS10请求，使用ECC时请务必调用此方法
	 *
	 * @param keyPair
	 * @param subjectDN
	 * @param sigAlgName
	 *            签名算法
	 * @return
	 * @throws CryptoException
	 */
	byte[] genPKCS10Req(KeyPair keyPair, String subjectDN,
			String sigAlgName) throws CryptoException;

	/**
	 * 检查公私钥对是否匹配
	 *
	 * @param pubKey
	 * @param prvKey
	 * @return
	 */
	boolean checkPair(PublicKey pubKey, PrivateKey prvKey);

	/**
	 * 获取版本信息
	 */
	String getVersionDesc();

	/**
	 * 获取加密机类型
	 */
	EngineType getType();

	/**
	 * 获取加密机描述
	 */
	String getEngineDesc();

	/**
	 * 枚举支持的非对称密钥类型
	 */
	String[] enumKeyType();

	/**
	 * 枚举支持的对称算法类型
	 */
	SymmAlgo[] enumSymmAlgo();

	/**
	 * 生成会话密钥并用指定索引的内部加密公钥加密输出<br>
	 * 一般使用此方法进行系统使用的主密钥的生成<br>
	 *
	 * 该值一般应该在系统启动时加载，并在系统停止时销毁对应的密钥句柄<br>
	 * 为了防止系统异常导致未及时销毁而占用句柄，应该生成或导入成功后<br>
	 * 记录其句柄，并在下次导入前进行旧句柄的销毁。<br>
	 * 此函数应该与ImportKeyWithISK结合使用<br>
	 * 销毁密钥时应该调用delSymmetricKeyStoreInEm函数
	 *
	 * @param keyIndex
	 *            非对称密钥索引
	 * @param bytesLen
	 *            密钥字节长，注意是byte而不是bit
	 * @return 存储了加密数据和句柄的主密钥
	 */
	EMSecretKey GenerateKeyWithIPK(int keyIndex, int bytesLen)
			throws CryptoException;

	/**
	 * 导入会话密钥并用内部私钥解密，同时返回密钥句柄<br>
	 * 介绍参看GenerateKeyWithIPK方法
	 *
	 * 注意由于传入了EMSecretKey对象，导入成功后，<br>
	 * 其中的Handle应该被更新为正确的Handle
	 *
	 * @param keyIndex
	 *            非对称密钥索引
	 * @param encryptedContent 被加密的对称密钥
	 * @return 存储了加密数据和句柄的主密钥
	 * @throws CryptoException
	 */
	EMSecretKey ImportKeyWithISK(int keyIndex, byte[] encryptedContent)
			throws CryptoException;

	/**
	 * 本函数主要用于会话密钥的生成，生成会话密钥并用外部ECC公钥加密输出
	 *
	 * 生成好应该在函数内部即调用delSymmetricKeyStoreInEm销毁对应的会话密钥<br>
	 * 只将密文传出，一般用于密钥不落地时的会话密钥生成。
	 *
	 * @param pubKey
	 *            外部公钥
	 * @param bytesLen
	 *            密钥字节长，注意是byte而不是bit
	 * @return
	 * @throws CryptoException
	 */
	EMSecretKey GenerateKeyWithEPK(PublicKey pubKey, int bytesLen)
			throws CryptoException;

	/**
	 * 生成会话密钥并用密钥加密密钥加密输出，同时返回密钥句柄<br>
	 * 此方法也用于主密钥，但要解决密钥加密密钥备份的问题
	 *
	 * 经实际测试发现，对于SJY42CGB版本中使用此函数生成的对称密钥句柄<br>
	 * 必须在5~6秒后才能生效调用，因此在实现各个版本的接口时，应该注意<br>
	 * 此特征，可以考虑将5~6秒的间隔直接纳入本函数对应的具体函数实现中<br>
	 * 即parseGenKeyWithKEKReponse中
	 *
	 * @param algo
	 *            对称加密算法
	 * @param keyIndex
	 *            密钥加密密钥索引
	 * @param bytesLen
	 *            密钥字节长，注意是byte而不是bit
	 * @return
	 * @throws CryptoException
	 */
	EMSecretKey GenerateKeyWithKEK(SymmAlgo algo, int keyIndex,
			int bytesLen) throws CryptoException;

	/**
	 * 导入会话密钥并用密钥加密密钥解密，同时返回会话密钥句柄<br>
	 * 此方法也用于主密钥，但要解决密钥加密密钥备份的问题
	 *
	 * 经实际测试发现，对于SJY42CGB版本中使用此函数生成的对称密钥句柄<br>
	 * 必须在5~6秒后才能生效调用，因此在实现各个版本的接口时，应该注意<br>
	 * 此特征，可以考虑将5~6秒的间隔直接纳入本函数对应的具体函数实现中<br>
	 * 即parseImportKeyWithKEKReponse中
	 *
	 * @param algo
	 *            对称加密算法
	 * @param keyIndex
	 *            对称密钥索引
	 * @param encryptedContent 被加密的对称密钥
	 * @return
	 * @throws CryptoException
	 */
	EMSecretKey ImportKeyWithKEK(SymmAlgo algo, int keyIndex,
			byte[] encryptedContent) throws CryptoException;


}
