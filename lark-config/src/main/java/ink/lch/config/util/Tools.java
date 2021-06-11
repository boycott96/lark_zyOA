package ink.lch.config.util;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 工具类
 */
@Slf4j
public class Tools {
    /**
     * 判断对象是否为空
     *
     * @param o 对象
     * @return false/true
     */
    public static boolean isEmptyObject(Object o) {
        try {
            for (Field field : o.getClass().getDeclaredFields()) {
                field.setAccessible(true);

                Object object = field.get(o);
                if (object instanceof CharSequence) {
                    if (!org.springframework.util.ObjectUtils.isEmpty(object)) {
                        return false;
                    }
                } else {
                    if (null != object) {
                        return false;
                    }
                }
            }
        } catch (Exception e) {
            log.error("判断对象属性为空异常", e);
        }
        return true;
    }

    /**
     * 将Object对象里面的属性和值转化成Map对象
     *
     * @param o 对象
     * @return map对象
     * @throws IllegalAccessException 访问权限异常
     */
    public static Map<String, Object> objectToMap(Object o) throws IllegalAccessException {
        if (null == o) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        Field[] declaredFields = o.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            // （此处如果不设置 无法获取对象的私有属性）
            field.setAccessible(true);
            map.put(field.getName(), field.get(o));
        }
        return map;
    }
}
