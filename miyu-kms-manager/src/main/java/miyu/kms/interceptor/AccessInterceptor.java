package miyu.kms.interceptor;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import miyu.kms.annotations.RequiresRoles;
import miyu.kms.constant.ResponseCode;
import miyu.kms.constant.SysConstant;
import miyu.kms.constant.UserType;
import miyu.kms.exceptions.BizException;
import miyu.kms.handler.TokenHandler;
import miyu.kms.handler.UserHolder;
import miyu.kms.model.ResponseVo;
import miyu.kms.model.user.dto.UserDTO;
import miyu.kms.utils.IpUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * @author xudean
 * @date 2021/4/9
 */
@Slf4j
@Component
public class AccessInterceptor implements HandlerInterceptor {

    static final String ENCODING = "UTF-8";
    static final String CONTENT_TYPE = "application/json;charset=utf-8";

    @Autowired
    private TokenHandler tokenHandler;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                             @NonNull Object handler) throws IOException {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        // 加入日志跟踪号
        addRequestId();
        log.info("Request Info: [Method = {}], [URI = {}], [Client-IP = {}], [userAgent = {}]", request.getMethod(),
                request.getRequestURI(), IpUtils.getIpAddr(request), request.getHeader("user-agent"));

        return checkRole(request, response, (HandlerMethod) handler);
    }

    private boolean checkRole(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                              @NonNull HandlerMethod handler) throws IOException {
        String token = request.getHeader(SysConstant.HEADER_TOKEN_KEY);
        if (StrUtil.isEmpty(token)) {
            token = request.getParameter(SysConstant.PARAMETER_TOKEN_KEY);
        }
        UserDTO user = null;
        if (!StrUtil.isEmpty(token)) {
            user = tokenHandler.getUser(token);
            if (null != user) {
                UserHolder.set(user);
                tokenHandler.refreshToken(token);
            } else {
                log.warn("Invalid token: {}", token);
            }
        }

        RequiresRoles roles = handler.getMethodAnnotation(RequiresRoles.class);
        if (null != roles && roles.value().length > 0) {
            if (user == null) {
                log.error("The user is not logged in!");
                throw new BizException(ResponseCode.UN_LOGIN);
            }

            boolean hasRole = false;
            UserType role = UserType.valueOf(user.getUserType());
            if (null != role) {
                for (UserType e : roles.value()) {
                    if (e == role) {
                        hasRole = true;
                        break;
                    }
                }
            }
            if (!hasRole) {
                throw new BizException(HttpStatus.HTTP_INTERNAL_ERROR, "The current user does not have permission!");
            }
        }
        return true;
    }

    @Override
    public void postHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                           @NonNull Object handler, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                @NonNull Object handler, Exception ex) {
        removeRequestId();
        UserHolder.remove();
    }

    void addRequestId() {
        MDC.put("requestId", UUID.randomUUID().toString().replaceAll("-", "").toLowerCase());
    }

    void removeRequestId() {
        MDC.clear();
    }

    void printResponse(HttpServletResponse response, ResponseCode respCode) throws IOException {
        response.setCharacterEncoding(ENCODING);
        response.setContentType(CONTENT_TYPE);
        response.getWriter().write(JSONUtil.toJsonStr(ResponseVo.create(respCode)));
    }
}
