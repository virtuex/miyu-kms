package miyu.kms.annotations;

import java.lang.annotation.*;

/**
 * @author xudean
 * @date 2022/8/22
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequiresPermissions {
    String[] value();
}
