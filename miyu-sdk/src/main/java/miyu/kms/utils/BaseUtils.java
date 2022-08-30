package miyu.kms.utils;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;


public class BaseUtils {
    static {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }
}
