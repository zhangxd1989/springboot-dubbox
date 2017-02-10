package cn.zhangxd.platform.common.utils;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * 数字工具类,继承自org.apache.commons.lang3.math.NumberUtils类
 *
 * @author zhangxd
 */
public class NumberHelper extends NumberUtils {

    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NumberHelper.class);

    /**
     * 默认除法运算精度
     */
    private static final int DEF_DIV_SCALE = 5;

    /**
     * 使用系统默认方式格式化一个数字为字符串,小数位2,不使用千分位.
     * 这个是整个应用系统的"风格",一般情况下,前台显示,只能使用该函数.
     *
     * @param number 要处理的数字.
     * @return 格式化数字 string
     */
    public static String format(Number number) {
        return format(number, "##0.00");
    }

    /**
     * 格式化数字
     * <p>
     * 格式化前：1231423.3823
     * "#.##" 格式化后：1231423.38
     * "0000000000.000000" 格式化后：0001231423.382300
     * "-##,###.##" 格式化后：-1,231,423.382
     * "#.##E000" 格式化后：1.23E006
     * 模式中的"#"表示如果该位存在字符，则显示字符，如果不存在，则不显示。
     *
     * @param number 要处理的数字.
     * @param format 格式
     * @return 格式化数字 string
     */
    public static String format(Number number, String format) {
        return new DecimalFormat(format).format(number);
    }

    /**
     * 格式化一个数字为字符串.
     *
     * @param number 要处理的数字.
     * @param digits 小数位数.
     * @param usesep 是否使用千分位.
     * @return 格式化数字 string
     */
    public static String format(Number number, int digits, boolean usesep) {
        return getFormatter(digits, usesep).format(number);
    }

    /**
     * 获得数字格式
     *
     * @param digits 位数
     * @param usesep 使用千分位
     * @return NumberFormat
     */
    private static NumberFormat getFormatter(int digits, boolean usesep) {
        StringBuilder sb = new StringBuilder();
        sb.append(usesep ? ",##0" : "0");

        if (digits > 0) {
            sb.append(".");
            for (int i = 0; i < digits; i++) {
                sb.append("0");
            }
        }
        return new DecimalFormat(sb.toString());
    }

    /**
     * 将 obj.toString() 转成 int,obj null 值安全
     *
     * @param obj          null 值安全
     * @param defaultValue 默认值
     * @return int int
     */
    public static int toInt(Object obj, int defaultValue) {
        if (obj == null) {
            return defaultValue;
        }
        double db = toDouble(obj.toString(), defaultValue);
        return (int) db;
    }

    /**
     * 尝试将一个值转化为int,如果失败,返回defaultValue
     *
     * @param val Object
     * @param def 默认值
     * @return int int
     */
    public static int getInt(Object val, int def) {
        if (val instanceof Number) {
            return ((Number) val).intValue();
        } else if (val instanceof String) {
            String sval = ((String) val).replace(",", "");
            try {
                return Integer.parseInt(sval);
            } catch (Exception ex) {
                LOGGER.warn("getInt Exception", ex);
                for (String format : new String[]{"#"}) {
                    try {
                        return new DecimalFormat(format).parse(sval).intValue();
                    } catch (Exception e) {
                        LOGGER.error("转为int失败", e);
                    }
                }
            }
        }

        return def;
    }

    /**
     * 将 obj.toString() 转成 float,obj null 值安全
     *
     * @param obj          null 值安全
     * @param defaultValue 默认值
     * @return float float
     */
    public static float toFloat(Object obj, float defaultValue) {
        return obj == null ? defaultValue : toFloat(obj.toString(), defaultValue);
    }

    /**
     * 将 obj.toString() 转成 double,obj null 值安全
     *
     * @param obj          null 值安全
     * @param defaultValue 默认值
     * @return double double
     */
    public static double toDouble(Object obj, double defaultValue) {
        return obj == null ? defaultValue : toDouble(obj.toString(), defaultValue);
    }

    /**
     * 将 obj.toString() 转成 long,obj null 值安全
     *
     * @param obj          null 值安全
     * @param defaultValue 默认值
     * @return long long
     */
    public static long toLong(Object obj, long defaultValue) {
        return obj == null ? defaultValue : toLong(obj.toString(), defaultValue);
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果 double
     */
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * 提供精确的加法运算。
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和 double
     */
    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确的减法运算。
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差 double
     */
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积 double
     */
    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，
     * 精确到小数点以后10位，以后的数字四舍五入。
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商 double
     */
    public static double div(double v1, double v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。
     * 当发生除不尽的情况时，由scale参数指定精度，以后的数字四舍五入。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商 double
     */
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, RoundingMode.HALF_UP).doubleValue();
    }

}
