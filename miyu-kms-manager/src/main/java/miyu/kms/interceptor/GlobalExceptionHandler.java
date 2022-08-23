package miyu.kms.interceptor;

import cn.hutool.http.HttpStatus;
import miyu.kms.exceptions.BizException;
import miyu.kms.model.ResponseVo;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author : xudean
 * @version V1.0
 * @Description: TODO
 * @date Date : 2022年08月23日 下午6:23
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BizException.class)
    @ResponseBody
    public ResponseVo bizException(BizException e) {
        e.printStackTrace();
        return ResponseVo.create(HttpStatus.HTTP_INTERNAL_ERROR, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseVo exception(Exception e) {
        e.printStackTrace();
        return ResponseVo.create(HttpStatus.HTTP_INTERNAL_ERROR, e.getMessage());
    }

}
