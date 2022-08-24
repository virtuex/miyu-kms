package miyu.kms.config;

import miyu.kms.interceptor.AccessInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

/**
 * @author Oliver
 * @date 2021/8/11
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Autowired
    private AccessInterceptor accessInterceptor;

    /**
     * 添加拦截器
     *
     * @param registry
     *            拦截器注册机
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(accessInterceptor).addPathPatterns("/**");
    }

    /**
     * 跨域配置
     *
     * @return 跨域过滤器
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOriginPatterns(Arrays.asList("*"));
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.setMaxAge(36000L);
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS"));

        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
        corsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

        return new CorsFilter(corsConfigurationSource);
    }
}
