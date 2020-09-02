package com.king.nowedge.ryx_kf.utils;

import com.king.nowedge.ryx_kf.pojo.KfGetOrgServiceParam;
//import org.junit.Test;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fan
 * @program ListOrMapUtil
 * @description: TODO 对象与map集合互转
 * @date 2018/8/31 14:06
 */
public class ListOrMapUtil {
    /**
     * @Description: TODO
     * @Param: [obj]
     * @return: java.util.Map<java.lang.String               ,               java.lang.Object>
     * @Author: fan
     * @Date: 2018/09/01 14:03
     **/
    public static Map<String, Object> objectToMap(Object obj) {
        if (obj == null)
            return null;
        try {
            Map<String, Object> map = new HashMap<String, Object>();

            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                //默认PropertyDescriptor会有一个class对象，剔除之
                if (key.compareToIgnoreCase("class") == 0) {
                    continue;
                }
                Method getter = property.getReadMethod();
                Object value = getter != null ? getter.invoke(obj) : null;
                map.put(key, value);
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @Description: TODO Map转实体对象
     * @Param: [map, clazz]
     * @return: java.lang.Object
     * @Author: fan
     * @Date: 2018/8/31
     */
    public static Object mapToObject(Map<String, Object> map, Class<?> clazz) {
        if (map == null)
            return null;
        try {
            Object obj = clazz.newInstance();
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                Method setter = property.getWriteMethod();
                if (setter != null) {
                    setter.invoke(obj, map.get(property.getName()));
                }
            }
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @Description: TODO List集合复制（改变地址）
     * @Param: [list]
     * @return: java.util.List<T>
     * @Author: fan
     * @Date: 2018/09/01 14:04
     **/
    public static <T> List<T> listDeepCopy(List<T> list) {
        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(list);
            ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            ObjectInputStream in = new ObjectInputStream(byteIn);
            @SuppressWarnings("unchecked")
            List<T> newList = (List<T>) in.readObject();
            return newList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}