package miyu.kms.engine;

import miyu.kms.constants.EngineType;
import miyu.kms.engine.impl.RSAFileEngine;

/**
 * @author : xudean
 * @version V1.0
 * @Description: 先做一个简单工厂
 * @date Date : 2022年08月26日 上午11:46
 */
public class EngineFactory {
    public static IEngine getInstance(EngineType type){
        switch (type){
            case RSA_FILE_ENGINE:
                return new RSAFileEngine();
        }
        throw new RuntimeException("unsuported engineType:"+type.name());
    }
}
