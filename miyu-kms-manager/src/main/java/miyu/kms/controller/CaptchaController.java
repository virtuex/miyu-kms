package miyu.kms.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.IdUtil;
import cn.hutool.http.HttpStatus;
import lombok.extern.slf4j.Slf4j;
import miyu.kms.constant.SysConstant;
import miyu.kms.exceptions.BizException;
import miyu.kms.handler.CaptchaHandler;
import miyu.kms.model.ResponseVo;
import miyu.kms.model.login.vo.CaptchaVo;
import org.springframework.http.MediaType;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author xuda
 */
@Slf4j
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class CaptchaController {
    static final String CAPTCHA_FORMAT = "jpg";

    @Resource
    private CaptchaHandler captchaHandler;

    @GetMapping("/captcha")
    public ResponseVo<CaptchaVo> getCaptcha() {
        String uuid = IdUtil.simpleUUID();
        String captchaCode = captchaHandler.saveCaptchaCode(uuid);
        log.debug("uuid:{},captcha code: {}", uuid, captchaCode);
        // 定义图形验证码的长、宽、验证码字符数、干扰线宽度.
        // 长宽先默认写死
        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(80, 30);
        BufferedImage image = (BufferedImage)captcha.createImage(captchaCode);

        // 转换流信息写出
        FastByteArrayOutputStream os = null;
        String captchaStr;
        try {
            os = new FastByteArrayOutputStream();
            ImageIO.write(image, CAPTCHA_FORMAT, os);
            captchaStr = Base64.encode(os.toByteArray());
        } catch (IOException e) {
            throw new BizException(HttpStatus.HTTP_INTERNAL_ERROR,"验证码获取失败！");
        } finally {
            if (null != os) {
                os.close();
            }
        }
        return ResponseVo.createSuccess(CaptchaVo.builder().uuid(uuid).captcha(genFullB64ImageStr(captchaStr)).build());
    }

    private String genFullB64ImageStr(String originImageB64Str) {
        return SysConstant.CAPTCHA_IMAGE_PREFIX + originImageB64Str;
    }
}
