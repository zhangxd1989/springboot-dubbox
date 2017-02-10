package cn.zhangxd.platform.system.api.exception.base;

/**
 * 系统业务异常.
 *
 * @author zhangxd
 */
public class SystemException extends RuntimeException {

    public SystemException() {
        super();
    }

    public SystemException(String message) {
        super(message);
    }

    public SystemException(Throwable cause) {
        super(cause);
    }

    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }
}