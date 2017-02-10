package cn.zhangxd.platform.common.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 汉字转拼音工具类
 *
 * @author zhangxd
 */
public final class PinyinUtil {

    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PinyinUtil.class);

    private PinyinUtil() {
        throw new IllegalAccessError("Utility class");
    }

    /**
     * 将汉字转换为全拼
     *
     * @param src 源汉字
     * @return String pin yin
     */
    public static String getPinYin(String src) {
        char[] t1 = src.toCharArray();
        String[] t2;
        // 设置汉字拼音输出的格式
        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
        t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        t3.setVCharType(HanyuPinyinVCharType.WITH_V);
        StringBuilder t4 = new StringBuilder();
        try {
            for (char aT1 : t1) {
                // 判断是否为汉字字符
                if (Character.toString(aT1).matches("[\\u4E00-\\u9FA5]+")) {
                    t2 = PinyinHelper.toHanyuPinyinStringArray(aT1, t3);// 将汉字的几种全拼都存到t2数组中
                    t4.append(t2[0]);// 取出该汉字全拼的第一种读音并连接到字符串t4后
                } else {
                    // 如果不是汉字字符，直接取出字符并连接到字符串t4后
                    t4.append(Character.toString(aT1));
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            LOGGER.error("", e);
        }
        return t4.toString();
    }

    /**
     * 将汉字转换为全拼
     *
     * @param src 源汉字
     * @return String camel pin yin
     */
    public static String getCamelPinYin(String src) {
        char[] t1 = src.toCharArray();
        String[] t2;
        // 设置汉字拼音输出的格式
        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
        t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        t3.setVCharType(HanyuPinyinVCharType.WITH_V);
        StringBuilder t4 = new StringBuilder();
        String t;
        try {
            for (char aT1 : t1) {
                // 判断是否为汉字字符
                if (Character.toString(aT1).matches("[\\u4E00-\\u9FA5]+")) {
                    t2 = PinyinHelper.toHanyuPinyinStringArray(aT1, t3);// 将汉字的几种全拼都存到t2数组中
                    t = t2[0];// 取出该汉字全拼的第一种读音并连接到字符串t4后
                } else {
                    // 如果不是汉字字符，直接取出字符并连接到字符串t4后
                    t = Character.toString(aT1);
                }
                t = t.substring(0, 1).toUpperCase() + t.substring(1);
                t4.append(t);
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            LOGGER.error("", e);
        }
        return t4.toString();
    }

    /**
     * 提取每个汉字的首字母
     *
     * @param str 源汉字
     * @return String pin yin head char
     */
    public static String getPinYinHeadChar(String str) {
        StringBuilder convert = new StringBuilder();
        for (int j = 0; j < str.length(); j++) {
            char word = str.charAt(j);
            // 提取汉字的首字母
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                convert.append(pinyinArray[0].charAt(0));
            } else {
                convert.append(word);
            }
        }
        return convert.toString();
    }

    /**
     * 提取每个汉字的大写首字母
     *
     * @param str 源汉字
     * @return String pin yin head upper char
     */
    public static String getPinYinHeadUpperChar(String str) {
        return getPinYinHeadChar(str).toUpperCase();
    }

}
