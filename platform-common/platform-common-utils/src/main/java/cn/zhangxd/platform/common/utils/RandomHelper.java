package cn.zhangxd.platform.common.utils;

import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

/**
 * 封装各种生成唯一性ID算法的工具类.
 *
 * @author zhangxd
 */
public final class RandomHelper {

    /**
     * 随机数生成器
     */
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private RandomHelper() {
        throw new IllegalAccessError("Utility class");
    }

    /**
     * 封装JDK自带的UUID, 长度32, 中间无-分割.
     *
     * @return 一个随机码, 形如:5ec24ed3ff1a41c18d23a37af006bbb3
     */
    public static String uuid() {
        return randomStringByUUID().replaceAll("-", "");
    }

    /**
     * 生成一个随机码,长度36.
     *
     * @return 一个随机码, 形如:5ec24ed3-ff1a-41c1-8d23-a37af006bbb3
     */
    public static String randomString() {
        return randomStringByUUID();
    }

    /**
     * 生成一个随机码,长度36,全大写.
     *
     * @return 一个随机码, 形如:5EC24ED3-FF1A-41C1-8D23-A37AF006BBB3
     */
    public static String randomStringUpper() {
        return randomString().toUpperCase();
    }


    /**
     * 生成UUID
     *
     * @return string
     */
    private static String randomStringByUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 使用SecureRandom随机生成Long.
     *
     * @return the long
     */
    public static long randomLong() {
        long number = SECURE_RANDOM.nextLong();
        if (Long.MIN_VALUE == number) {
            return Long.MAX_VALUE;
        } else {
            return Math.abs(number);
        }
    }

    /**
     * 指定位数数字
     *
     * @param charCount 位数
     * @return String rand num
     */
    public static String getRandNum(int charCount) {
        StringBuilder charValue = new StringBuilder();
        for (int i = 0; i < charCount; i++) {
            char c = (char) (randomInt(0, 10) + '0');
            charValue.append(String.valueOf(c));
        }
        return charValue.toString();
    }

    /**
     * 范围内随机数字
     *
     * @param from 开始
     * @param to   结束
     * @return int int
     */
    public static int randomInt(int from, int to) {
        Random r = new Random();
        return from + r.nextInt(to - from);
    }

}
