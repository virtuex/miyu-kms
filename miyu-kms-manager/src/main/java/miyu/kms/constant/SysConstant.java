package miyu.kms.constant;

/**
 * @author : xudean
 * @version V1.0
 * @Description: TODO
 * @date Date : 2022年08月22日 下午2:29
 */
public class SysConstant {
    /**
     * 请求头token key值
     */
    public static final String HEADER_TOKEN_KEY = "Access-Token";

    /**
     * 请求头token key值
     */
    public static final String PARAMETER_TOKEN_KEY = "token";

    /**
     * 系统默认时间格式
     */
    public static final String DEFAULT_TIME_PATTERN = "yyyy/MM/dd HH:mm:ss";

    /**
     * 时间格式: yyyy-MM-dd HH:mm:ss
     */
    public static final String TIME_PATTERN_FOR_HYPHEN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 年月日用"."分割的时间格式
     */
    public static final String TIME_PATTERN_BY_POINT = "yyyy.MM.dd HH:mm:ss";

    /**
     * 系统默认日期格式
     */
    public static final String DEFAULT_DATE_PATTERN = "yyyy/MM/dd";

    /**
     * 时区默认东八区北京时间
     */
    public static final String DEFAULT_TIMEZONE = "GMT+8";

    public static final String ENCODING_UTF_8 = "UTF-8";
    public static final String HEADER_CONTENT_DISPOSITION = "Content-Disposition";
    public static final String HEADER_CONTENT_DISPOSITION_ATTACHMENT = "attachment;fileName=";
    public static final String EXCEL_CONTENT_TYPE = "application/vnd.ms-excel";

    /**
     * 验证码图片格式
     */
    public static final String CAPTCHA_IMAGE_PREFIX = "data:image/jpg;base64,";
}
