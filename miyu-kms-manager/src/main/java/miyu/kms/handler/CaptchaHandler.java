package miyu.kms.handler;

import cn.hutool.core.util.RandomUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 验证码存储
 *
 * @author xuda
 */
@Component
public class CaptchaHandler {
    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "MIYU:KMS:CAPTCHA_CODES:";

    /**
     * 默认过期时间
     */
    private static final Integer EXPIRE_TIME = 5;

    /**
     * 默认验证码长度
     */
    private static final Integer DEFAULT_CODE_LENGTH=4;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    private String generateCaptchaKey(String id) {
        return CAPTCHA_CODE_KEY + id;

    }

    /**
     * 生成验证码字符串
     */
    private String generateCaptchaCode() {
        return RandomUtil.randomNumbers(DEFAULT_CODE_LENGTH);
    }

    /**
     * 生成验证码存入缓存
     *
     */
    public String saveCaptchaCode(String id) {
        String code = generateCaptchaCode();
        redisTemplate.opsForValue().set(generateCaptchaKey(id), code, EXPIRE_TIME,
            TimeUnit.MINUTES);
        return code;
    }

    /**
     * 根据id查找对应的验证码
     *
     */
    public String getCaptchaCode(String id) {
        Object codeObj = redisTemplate.opsForValue().get(generateCaptchaKey(id));
        if (codeObj == null) {
            return null;
        }
        return String.valueOf(codeObj);
    }

    /**
     * 删除对应的验证码
     */
    public void deleteCaptchaCode(String id) {
        redisTemplate.delete(generateCaptchaKey(id));
    }
}
