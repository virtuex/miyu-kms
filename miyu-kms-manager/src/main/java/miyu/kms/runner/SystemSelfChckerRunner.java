package miyu.kms.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author : xudean
 * @version V1.0
 * @Description: TODO
 * @date Date : 2022年08月25日 上午10:35
 */
@Component
@Slf4j
public class SystemSelfChckerRunner implements ApplicationRunner {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //检查redis连接性
        redisTemplate.opsForValue().get("HEALTH_TEST");
        log.info("Redis health check success");
        //检查mysql连接性
        jdbcTemplate.execute("SELECT 1");
        log.info("MySQL health check success");
    }
}
