package chapter18;

/**
 * 日志
 *
 * @author 孙证杰
 * @email 200765821@qq.com
 * @date 2019/7/26 13:05
 */
public class Log {

    /**
     * loglevel: timestamp  msg
     */
    public static final String TEMPLATE = "%s: %d  %s";

    public static void debug(String msg) {
        System.out.println(String.format(TEMPLATE, "debug", System.currentTimeMillis(), msg));
    }

    public static void info(String msg) {
        System.out.println(String.format(TEMPLATE, "info", System.currentTimeMillis(), msg));
    }

    public static void warn(String msg, Throwable throwable) {
        System.out.println(String.format(TEMPLATE, "warn", System.currentTimeMillis(), msg));
        throwable.printStackTrace();
    }

    public static void error(String msg, Throwable throwable) {
        System.out.println(String.format(TEMPLATE, "error", System.currentTimeMillis(), msg));
        throwable.printStackTrace();
    }
}
