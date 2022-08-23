package miyu.kms.handler;

import lombok.NonNull;
import miyu.kms.model.user.dto.UserDTO;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author xudean
 * @date 2022/4/15
 */
@Component
public class TokenHandler {
    static final String REDIS_TOKEN_PREFIX_KEY = "MIYU:KMS:TOKEN:";
    static final String REDIS_USER_PREFIX_KEY = "MIYU:KMS:USER:";

    private final Long DEFAULT_LOGIN_EXPIRE_TIME = TimeUnit.MINUTES.toMillis(5L);
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public String generateTokenKey(Long id) {
        return REDIS_TOKEN_PREFIX_KEY + id;
    }

    public String generateUserKey(String token) {
        return REDIS_USER_PREFIX_KEY + token;
    }

    public static String generateToken() {
        String uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        return uuid + System.currentTimeMillis();
    }

    public String createToken(@NonNull UserDTO user) {
        String tokenKey = generateTokenKey(user.getUserId());

        // 检查token是否存在,如果存在则删除(后登录的用户可以踢掉之前登录的用户)
        String token = (String)redisTemplate.opsForValue().get(tokenKey);
        if (!StringUtils.isEmpty(token)) {
            redisTemplate.delete(tokenKey);
            redisTemplate.delete(generateUserKey(token));
        }

        token = generateToken();
        redisTemplate.opsForValue().set(tokenKey, token, DEFAULT_LOGIN_EXPIRE_TIME, TimeUnit.MILLISECONDS);
        redisTemplate.opsForValue().set(generateUserKey(token), user, DEFAULT_LOGIN_EXPIRE_TIME,
            TimeUnit.MILLISECONDS);
        return token;
    }

    /**
     * 根据用户ID获取Token
     *
     * @param id
     *            用户ID
     * @return Token
     */
    public String getToken(@NonNull Long id) {
        return (String)redisTemplate.opsForValue().get(generateTokenKey(id));
    }

    /**
     * 根据Token获取用户信息
     *
     * @param token
     *            Token
     * @return 用户信息
     */
    public UserDTO getUser(@NonNull String token) {
        return (UserDTO)redisTemplate.opsForValue().get(generateUserKey(token));
    }

    /**
     * 删除Token
     *
     * @param token
     *            Token
     */
    public void removeToken(@NonNull String token) {
        String userKey = generateUserKey(token);
        UserDTO user = (UserDTO)redisTemplate.opsForValue().get(userKey);
        if (null != user) {
            redisTemplate.delete(generateTokenKey(user.getUserId()));
            redisTemplate.delete(userKey);
        }
    }

    /**
     * 根据用户ID删除Token
     *
     * @param id
     *            用户ID
     */
    public void removeToken(@NonNull Long id) {
        String tokenKey = generateTokenKey(id);
        String token = (String)redisTemplate.opsForValue().get(tokenKey);
        if (!StringUtils.isEmpty(token)) {
            redisTemplate.delete(tokenKey);
            redisTemplate.delete(generateUserKey(token));
        }
    }

    /**
     * 刷新Token
     *
     * @param token
     *            Token
     */
    public void refreshToken(@NonNull String token) {
        String userKey = generateUserKey(token);
        UserDTO user = (UserDTO)redisTemplate.opsForValue().get(userKey);
        if (null != user) {
            redisTemplate.expire(generateTokenKey(user.getUserId()), DEFAULT_LOGIN_EXPIRE_TIME, TimeUnit.MILLISECONDS);
            redisTemplate.expire(userKey, DEFAULT_LOGIN_EXPIRE_TIME, TimeUnit.MILLISECONDS);
        }
    }

    /**
     * 刷新用户信息
     *
     * @param user
     *            用户信息
     */
    public void refreshUser(@NonNull UserDTO user) {
        String token = getToken(user.getUserId());
        if (!StringUtils.isEmpty(token)) {
            redisTemplate.opsForValue().set(generateUserKey(token), user, DEFAULT_LOGIN_EXPIRE_TIME,
                TimeUnit.MILLISECONDS);
            redisTemplate.expire(generateTokenKey(user.getUserId()), DEFAULT_LOGIN_EXPIRE_TIME, TimeUnit.MILLISECONDS);
        }
    }
}
