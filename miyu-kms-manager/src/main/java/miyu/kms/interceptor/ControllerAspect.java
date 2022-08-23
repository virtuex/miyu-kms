package miyu.kms.interceptor;


import cn.hutool.http.HttpStatus;
import lombok.extern.slf4j.Slf4j;
import miyu.kms.constant.ResponseCode;
import miyu.kms.exceptions.BizException;
import miyu.kms.model.ResponseVo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author xudean
 * @date 2020/12/18
 */
@Slf4j
@Aspect
@Component
public class ControllerAspect {

    @Pointcut("execution(* miyu.kms.controller..*.*(..))")
    public void pointCut() {

    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint pjp) {
        long start = System.currentTimeMillis();

        // 被拦截的类
        String clazzName = pjp.getTarget().getClass().getName();
        // 被拦截的方法签名
        MethodSignature methodSignature = (MethodSignature)pjp.getSignature();
        // 被拦截的方法
        Method method = methodSignature.getMethod();
        // 被拦截的方法名
        String methodName = methodSignature.getName();
        // 被拦截的方法
        Class<?> returnType = method.getReturnType();
        // 请求参数
        Object[] args = pjp.getArgs();

        log.debug("Request {}.{} start... ...", clazzName, methodName);
        if (null != args && args.length > 0) {
            log.debug("Request parameters: {}.", args[0]);
        }

        Object returnObj;
        try {
            returnObj = pjp.proceed(args);
        } catch (BizException e) {
            log.warn("A biz exception occurred on the request. Error message: " + e.getMsg());
            log.error(e.getMsg(), e);
            returnObj = ResponseVo.create(e.getCode(), e.getCause().getMessage());
        } catch (Throwable e) {
            log.error("An exception occurred on the request.", e);
            returnObj = ResponseVo.create(HttpStatus.HTTP_INTERNAL_ERROR,e.getCause().getMessage());
        }

        log.debug("End of request, Used Time: {}ms, Return result: {}.", (System.currentTimeMillis() - start),
            returnObj);
        return returnObj;
    }
}
