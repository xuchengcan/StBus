package com.chen.stbus.Utils;

import java.util.Collection;
import java.util.Map;

/**
 * 数据检查和获取帮助类
 */
public class TextUtil {

    public static boolean isValidate(String content) {
        return content != null && !"".equals(content.trim()) && !"null".equals(content.trim());
    }

    public static boolean isValidate(Collection<?> list) {
        return list != null && list.size() > 0;
    }

    public static boolean isValidate(Map<?, ?> map) {
        return map != null && map.size() > 0;

    }
    public static boolean isBlank(String str) {
        return (str == null || str.trim().length() == 0);
    }

    public static boolean isEmpty(String str) {
        return (str == null || str.length() == 0);
    }
}
