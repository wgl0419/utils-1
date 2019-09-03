package com.github.chenlijia1111.utils.core;

import com.github.chenlijia1111.utils.list.Lists;

import java.util.List;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 *
 * @author chenlijia
 * @date 下午 1:11 2019/9/3 0003
 **/
public class StringUtils {

    //整数正则
    private static final String INT_REG = "^\\d+$";
    //小数正则(包含了整数)  小数点可以不存在
    private static final String DOUBLE_REG = "^\\d+(.\\d+)?$";


    /**
     * 判断是否为空
     *
     * @param str 1
     * @author chenlijia
     * @date 下午 1:12 2019/9/3 0003
     **/
    public static boolean isEmpty(String str) {
        return null == str || str.length() == 0;
    }


    /**
     * 判断非空
     *
     * @param str 1
     * @author chenlijia
     * @date 下午 1:13 2019/9/3 0003
     **/
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }


    /**
     * 去除空格 判断是否为空
     *
     * @param str 1
     * @author chenlijia
     * @date 下午 1:12 2019/9/3 0003
     **/
    public static boolean isEmptyTrimWhitespace(String str) {
        if (isNotEmpty(str)) {
            int len = str.length();
            for (int i = 0; i < len; i++) {
                char c = str.charAt(i);
                if (!Character.isWhitespace(c)) {
                    //但凡有一个不是空格的字符串就返回true
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * 去除空格 判断非空
     *
     * @param str 1
     * @author chenlijia
     * @date 下午 1:13 2019/9/3 0003
     **/
    public static boolean isNotEmptyTrimWhitespace(String str) {
        return !isEmptyTrimWhitespace(str);
    }


    /**
     * 判断字符串是否是整数
     *
     * @param str 1
     * @author chenlijia
     * @date 下午 7:33 2019/9/3 0003
     **/
    public static boolean isInt(String str) {
        return null != str && Pattern.matches(INT_REG, str);
    }

    /**
     * 判断字符串是否是小数(包含整数)
     *
     * @param str 1
     * @author chenlijia
     * @date 下午 7:33 2019/9/3 0003
     **/
    public static boolean isDouble(String str) {
        return null != str && Pattern.matches(DOUBLE_REG, str);
    }

    /**
     * 判断字符串是否包含了这些字符串
     * 只要包含一个就返回true
     * <p>
     * 可用于判断敏感词汇
     *
     * @param str     1
     * @param strList 2
     * @return boolean
     * @author chenlijia
     * @date 下午 7:54 2019/9/3 0003
     **/
    public static boolean containsStrList(String str, List<String> strList) {
        if (null != str && str.length() > 0 && Lists.isNotEmpty(strList)) {
            for (String s : strList) {
                if (str.contains(s)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 切割字符串为集合
     *
     * @param str         要被切割的字符串
     * @param spiltSymbol 用于切割的标志符号
     * @return java.util.List<java.lang.String>
     * @author chenlijia
     * @date 下午 8:00 2019/9/3 0003
     **/
    public static List<String> spiltStrToList(String str, String spiltSymbol) {
        if (null != str && null != spiltSymbol) {
            String[] split = str.split(spiltSymbol);
            List<String> list = Lists.asList(split);
            return list;
        }
        return null;
    }


    public static void main(String[] args) {
        System.out.println(isDouble("1.23"));
        System.out.println(isDouble("123"));
        System.out.println(isDouble("asa"));
        System.out.println(isDouble("as.a"));
    }

}
