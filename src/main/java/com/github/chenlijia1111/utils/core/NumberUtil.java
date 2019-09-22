package com.github.chenlijia1111.utils.core;

import com.github.chenlijia1111.utils.common.AssertUtil;

/**
 * 数字工具类
 * 包含 10进制 16进制 2进制 以及字节数组之间的相互转换
 * 以及数据的常用方法
 * <p>
 * 大端模式:byte[0] 表示的是高位
 * 小端模式:byte[0] 表示的是低位
 * <p>
 * 一般都会使用小端模式
 * 即 byte[0] 表示的低位的数据
 * 因为不需要占满全部的字节就可以表示一个数据
 * 如果是大端模式，那么需要表示一个 int 就必须要有四个字节才可以表示
 * <p>
 * double 转 byte 以及 float 转 byte 都是用的小端模式
 * double 转 byte 先把 double 转成 long 再利用 long 进行转换
 * float 转 byte 先把 float 转成 int 再利用 int 进行转换
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/9/6 0006 下午 8:48
 **/
public class NumberUtil {

    /**
     * byte 数组转16进制字符串
     * 原理  一个字节由8位组成
     * 16 进制和 2 进制的关系
     * 0xFF 可以表示为 11111111
     * 0x0F 可以表示为 00001111
     * 即16进制 的一位可以表示 2进制里的 4位
     * <p>
     * 所以一个字节可以跟 0xFF 做 & 运算 得出他的 10 进制值 再转换为 16进制
     * <p>
     * 如果想要方便显示阅读 可以自己在生成后的 16 进制前面加上 0X
     *
     * @param bytes 1
     * @return java.lang.String
     * @since 下午 8:50 2019/9/6 0006
     **/
    public static String byteToHex(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() < 2) {
                sb.append(0);
            }
            sb.append(hex);
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 将字节数组转换为 位的形式 以字符串输出
     * 如 1
     * 输出为 00000001
     *
     * @param bytes
     * @return
     */
    public static String byteToBitString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        if (null != bytes && bytes.length > 0) {
            for (int i = 0; i < bytes.length; i++) {
                int integer = bytes[i] & 0xFF;
                String string = Integer.toBinaryString(integer);
                string = StringUtils.completeStrToFixedLengthStr(null, 8, string);
                sb.append(string);
                if (i + 1 < bytes.length) {
                    sb.append(" ");
                }
            }
        }
        return sb.toString();
    }


    /**
     * 字节数组转整数
     * 大端模式 byte[0] 表示高位
     * 低字节存储的是高位
     * 大端模式 字节数组必须要保证是4个字节
     * 因为如果不满4个字节的话，无法确定低位是什么，就不能确定具体的数字大小
     * <p>
     * 在 java 中 int 用 32 位表示
     * 也就是 4 个 byte
     * 第一个 byte 左移 24 位
     * 第二个 byte 左移 16 位
     * 第三个 byte 左移 8 位
     *
     * @param bytes 长度最长为4
     * @return java.lang.Integer
     * @since 下午 2:25 2019/9/21 0021
     **/
    public static Integer bytesToIntegerHight(byte[] bytes) {
        //校验参数
        AssertUtil.isTrue(null != bytes && bytes.length == 4, "bytes数组不合法");

        int value;
        value = ((bytes[0] & 0xFF) << 24) | ((bytes[1] & 0xFF) << 16) | ((bytes[2] & 0xFF) << 8)
                | ((bytes[3]) & 0xFF);
        return value;
    }

    /**
     * 字节数组转整数
     * 小端模式
     * 低字节存储的是低位
     * byte[0] 表示低位
     * <p>
     *
     * @param bytes 长度最长为4
     * @return java.lang.Integer
     * @since 下午 2:25 2019/9/21 0021
     **/
    public static Integer bytesToIntegerLower(byte[] bytes) {
        //校验参数
        AssertUtil.isTrue(null != bytes && bytes.length != 0, "bytes数组不合法");

        int length = bytes.length > 4 ? 4 : bytes.length;
        int value = 0;
        for (int i = 0; i < length; i++) {
            value |= ((bytes[i] & 0xFF) << i * 8);
        }
        return value;
    }

    /**
     * 整数转 byte 数组
     * 高位放在字节数组的低地址中
     * byte[0] 表示高位
     * 即高位放在高位
     * 大端模式
     *
     * @param i
     * @return byte[]
     * @since 下午 2:35 2019/9/21 0021
     **/
    public static byte[] intToBytesHight(int i) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (i >> 24 & 0xFF);
        bytes[1] = (byte) (i >> 16 & 0xFF);
        bytes[2] = (byte) (i >> 8 & 0xFF);
        bytes[3] = (byte) (i & 0xFF);
        return bytes;
    }


    /**
     * 整数转 byte 数组
     * 低位放在字节数组的低地址中
     * byte[0] 为低位
     * 小端模式
     *
     * @param i
     * @return byte[]
     * @since 下午 2:35 2019/9/21 0021
     **/
    public static byte[] intToBytesLower(int i) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (i & 0xFF);
        bytes[1] = (byte) (i >> 8 & 0xFF);
        bytes[2] = (byte) (i >> 16 & 0xFF);
        bytes[3] = (byte) (i >> 24 & 0xFF);
        return bytes;
    }


    /**
     * 字节数组转长整数
     * 大端模式
     * byte[0] 表示高位
     * <p>
     * 在 java 中 long 用 64 位表示
     * 也就是 8 个 byte
     * 第一个 byte 左移 56 位
     * 第二个 byte 左移 48 位
     * 第三个 byte 左移 40 位
     * 第四个 byte 左移 32 位
     * 第五个 byte 左移 24 位
     * 第六个 byte 左移 16 位
     * 第七个 byte 左移 8 位
     *
     * @param bytes 长度最长为8
     * @return java.lang.Integer
     * @since 下午 2:25 2019/9/21 0021
     **/
    public static Long bytesToLongHeight(byte[] bytes) {
        //校验参数
        AssertUtil.isTrue(null != bytes && bytes.length == 8, "bytes数组不合法");

        long value = 0;

        for (int i = 0; i < bytes.length; i++) {
            value |= (((long) (bytes[i] & 0xFF)) << (bytes.length - i - 1) * 8);
        }
        return value;
    }

    /**
     * 字节数组转长整数
     * 小端模式
     * byte[0] 表示低位
     * <p>
     *
     * @param bytes 长度最长为8
     * @return java.lang.Integer
     * @since 下午 2:25 2019/9/21 0021
     **/
    public static Long bytesToLongLower(byte[] bytes) {
        //校验参数
        AssertUtil.isTrue(null != bytes && bytes.length > 0, "bytes数组不合法");

        long value = 0;
        int length = bytes.length > 8 ? 8 : bytes.length;
        for (int i = 0; i < length; i++) {
            value |= (((long) (bytes[i] & 0xFF)) << i * 8);
        }
        return value;
    }

    /**
     * 长整数转 byte 数组
     * 大端模式
     * byte[0] 表示高位
     *
     * @param i
     * @return byte[]
     * @since 下午 2:35 2019/9/21 0021
     **/
    public static byte[] longToBytesHeight(long i) {
        byte[] bytes = new byte[8];
        bytes[0] = (byte) (i >> 56 & 0xFF);
        bytes[1] = (byte) (i >> 48 & 0xFF);
        bytes[2] = (byte) (i >> 40 & 0xFF);
        bytes[3] = (byte) (i >> 32 & 0xFF);
        bytes[4] = (byte) (i >> 24 & 0xFF);
        bytes[5] = (byte) (i >> 16 & 0xFF);
        bytes[6] = (byte) (i >> 8 & 0xFF);
        bytes[7] = (byte) (i & 0xFF);
        return bytes;
    }

    /**
     * 长整数转 byte 数组
     * 小端模式
     * byte[0] 表示低位
     *
     * @param i
     * @return byte[]
     * @since 下午 2:35 2019/9/21 0021
     **/
    public static byte[] longToBytesLower(long i) {
        byte[] bytes = new byte[8];
        bytes[0] = (byte) (i & 0xFF);
        bytes[1] = (byte) (i >> 8 & 0xFF);
        bytes[2] = (byte) (i >> 16 & 0xFF);
        bytes[3] = (byte) (i >> 24 & 0xFF);
        bytes[4] = (byte) (i >> 32 & 0xFF);
        bytes[5] = (byte) (i >> 40 & 0xFF);
        bytes[6] = (byte) (i >> 48 & 0xFF);
        bytes[7] = (byte) (i >> 56 & 0xFF);
        return bytes;
    }

    /**
     * 字节数组转short
     * short 由 16个字节组成
     *
     * @param bytes 1
     * @return short
     * @since 下午 2:59 2019/9/21 0021
     **/
    public static short bytesToShort(byte[] bytes) {
        //校验参数
        AssertUtil.isTrue(null != bytes && bytes.length > 0, "bytes数组不能为空");

        int value = 0;
        int length = bytes.length > 2 ? 2 : bytes.length;
        for (int i = 0; i < length; i++) {
            byte aByte = bytes[i];
            value = value | aByte << (length - 1 - i) * 8;
        }
        return (short) value;
    }

    /**
     * short 转字节数组
     *
     * @param i 1
     * @return byte[]
     * @since 下午 3:05 2019/9/21 0021
     **/
    public static byte[] shortToBytes(short i) {
        byte[] bytes = new byte[2];
        bytes[0] = (byte) (i >> 8 & 0xFF);
        bytes[1] = (byte) (i & 0xFF);
        return bytes;
    }


    /**
     * 字节数组转 浮点型数据
     * float 为32 位
     * 跟 int 一样,所以利用int进行转换
     *
     * @param bytes 1
     * @return float
     * @since 下午 3:07 2019/9/21 0021
     **/
    public static float bytesToFloat(byte[] bytes) {
        Integer integer = bytesToIntegerLower(bytes);
        float v = Float.intBitsToFloat(integer);
        return v;
    }

    /**
     * float 转 byte 数组
     *
     * @param i 1
     * @return byte[]
     * @since 下午 3:27 2019/9/21 0021
     **/
    public static byte[] floatToBytes(float i) {
        int i1 = Float.floatToRawIntBits(i);
        byte[] bytes = intToBytesLower(i1);
        return bytes;
    }

    /**
     * 字节数组转 double数据
     * double 为64 位
     * 跟 long 一样,所以利用long进行转换
     *
     * @param bytes 1
     * @return float
     * @since 下午 3:07 2019/9/21 0021
     **/
    public static double bytesToDouble(byte[] bytes) {
        Long aLong = bytesToLongLower(bytes);
        double v = Double.longBitsToDouble(aLong);
        return v;
    }

    /**
     * double 转 byte 数组
     *
     * @param i 1
     * @return byte[]
     * @since 下午 3:27 2019/9/21 0021
     **/
    public static byte[] doubleToBytes(double i) {
        long l = Double.doubleToRawLongBits(i);
        byte[] bytes = longToBytesLower(l);
        return bytes;
    }


}
