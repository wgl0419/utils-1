package com.github.chenlijia1111.utils.core;


import com.github.chenlijia1111.utils.list.Lists;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Objects;

/**
 * @Author chenlijia
 * @Date 2019/4/8 0008 上午 10:23
 * @Description 属性检测
 * @Version 1.0
 **/
public class PropertyCheckUtil {


    /**
     * 获取父类属性在内的所有属性
     *
     * @param object 1
     * @return java.lang.reflect.Field[]
     * @author chenlijia
     * @Description TODO
     * @Date 下午 6:47 2019/5/21 0021
     **/
    public static Field[] getAllFields(Object object) {
        Class<?> aClass = object.getClass();

        return getAllFields(aClass);
    }

    /**
     * 获取父类属性在内的所有属性
     *
     * @param aClass 1
     * @return java.lang.reflect.Field[]
     * @author chenlijia
     * @Description TODO
     * @Date 下午 6:47 2019/5/21 0021
     **/
    public static Field[] getAllFields(Class aClass) {

        ArrayList<Field> fields = new ArrayList<>();
        while (aClass != null) {
            fields.addAll(Lists.asList(aClass.getDeclaredFields()));
            aClass = aClass.getSuperclass();
        }

        Field[] fields1 = new Field[fields.size()];
        fields.toArray(fields1);
        return fields1;
    }


    /**
     * 获取属性值,递归获取父类的属性
     *
     * @param object 1
     * @return java.lang.reflect.Field[]
     * @author chenlijia
     * @Description TODO
     * @Date 下午 6:47 2019/5/21 0021
     **/
    public static Object getFieldValue(Object object, Class objectClass, String propertyName) {

        if (Objects.nonNull(object) && Objects.nonNull(objectClass) && StringUtils.isNotEmpty(propertyName)) {
            try {
                Field declaredField = objectClass.getDeclaredField(propertyName);
                if (Objects.nonNull(declaredField)) {
                    declaredField.setAccessible(true);
                    Object o = declaredField.get(object);
                    return o;
                }
            } catch (NoSuchFieldException e) {
                //当前类没有这个属性,寻找父类
                objectClass = objectClass.getSuperclass();
                return getFieldValue(object, objectClass, propertyName);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * 递归判断是否有这个属性
     *
     * @param object       1
     * @param objectClass  2
     * @param propertyName 3
     * @return boolean
     * @author chenlijia
     * @Description TODO
     * @Date 上午 10:47 2019/6/29 0029
     **/
    public static boolean checkHasPropertity(Object object, Class objectClass, String propertyName) {
        if (Objects.nonNull(object) && Objects.nonNull(objectClass) && StringUtils.isNotEmpty(propertyName)) {
            try {
                Field declaredField = objectClass.getDeclaredField(propertyName);
                if (Objects.nonNull(declaredField)) {
                    return true;
                }
            } catch (NoSuchFieldException e) {
                //当前类没有这个属性,寻找父类
                objectClass = objectClass.getSuperclass();
                return checkHasPropertity(object, objectClass, propertyName);
            }
        }
        return false;
    }


    /**
     * 检测参数是否为空，为空则实例化对象
     * 防止参数为空
     *
     * @param t 1
     * @return T
     * @author chenlijia
     * @Description TODO
     * @Date 上午 11:44 2019/7/13 0013
     **/
    public static <T> T transferObjectNotNull(T t) {
        if (Objects.isNull(t)) {
            Class<?> aClass = t.getClass();
            try {
                t = (T) aClass.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return t;
    }
}
