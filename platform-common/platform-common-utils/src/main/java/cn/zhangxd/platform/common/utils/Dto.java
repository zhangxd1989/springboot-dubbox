package cn.zhangxd.platform.common.utils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Map接口的扩展.
 *
 * @author zhangxd
 */
public interface Dto extends Map<String, Object> {

    /**
     * 通过键，返回一个字符串的值.
     *
     * @param key the key
     * @return the string
     */
    String getString(String key);

    /**
     * 通过键，返回一个数组类型的对象.
     *
     * @param <E> the type parameter
     * @param key the key
     * @return the list
     */
    <E> List<E> getList(String key);

    /**
     * 通过键，返回一个整数值.
     *
     * @param key the key
     * @return the int
     */
    int getInt(String key);

    /**
     * 通过键，返回一个long值.
     *
     * @param key the key
     * @return the long
     */
    long getLong(String key);

    /**
     * 通过键，返回一个浮点型数值.
     *
     * @param key the key
     * @return the double
     */
    double getDouble(String key);

    /**
     * 通过键，返回一个BigDecimal的值.
     *
     * @param key the key
     * @return the decimal
     */
    BigDecimal getDecimal(String key);

    /**
     * 通过键，返回一个日期型的值.
     *
     * @param key the key
     * @return the date
     */
    Date getDate(String key);

}