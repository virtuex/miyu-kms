package miyu.kms.annotations;

import miyu.kms.constant.UserType;

import java.lang.annotation.*;

/**
 * @author xu
 * @date 2022/4/14
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequiresRoles {
    UserType[] value();
}
