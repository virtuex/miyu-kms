package miyu.kms.handler;


import miyu.kms.module.user.dto.UserDTO;

/**
 * @author xudean
 * @date 2022/4/15
 */
public class UserHolder {
    private static final ThreadLocal<UserDTO> USER_THREAD_LOCAL = new ThreadLocal<>();

    private UserHolder() {}

    public static UserDTO get() {
        return USER_THREAD_LOCAL.get();
    }

    public static void set(UserDTO user) {
        if (null != get()) {
            remove();
        }
        USER_THREAD_LOCAL.set(user);
    }

    public static void remove() {
        USER_THREAD_LOCAL.remove();
    }
}
