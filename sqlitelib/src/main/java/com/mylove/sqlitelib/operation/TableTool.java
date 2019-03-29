package com.mylove.sqlitelib.operation;

import android.content.ContentValues;
import android.text.TextUtils;

import com.googlecode.openbeans.PropertyDescriptor;
import com.mylove.sqlitelib.annotation.ID;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author YanYi
 * @date 2019/3/29 13:25
 * @email ben@yanyi.red
 * @overview
 */
class TableTool {
    static <T> ContentValues values(T t) {
        ContentValues values = new ContentValues();
        Class<?> aClass = t.getClass();
        try {
            Field[] fields = t.getClass().getDeclaredFields();
            for (Field field : fields) {

//                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), aClass);
////                Method rM = pd.getReadMethod();//获得读方法
////                Integer num = (Integer) rM.invoke(t);//因为知道是int类型的属性,所以转换成integer就是了。。也可以不转换直接打印
////                System.out.println(num);

                ID annotation = field.getAnnotation(ID.class);
                PropertyDescriptor descriptor = new PropertyDescriptor(field.getName(), aClass);
                Method method = descriptor.getReadMethod();//获得读方法
                if (annotation != null) {
                    boolean boo = annotation.increase();
                    if (!boo) {
                        Boolean invoke = (Boolean) method.invoke(t);
                        values.put(field.getName(), (invoke ? 1 : 0));
                    }
                } else {
                    String invoke = String.valueOf(method.invoke(t));
                    contentValue(values, field, invoke);
                }
            }
        } catch (Exception e) {
            return null;
        }
        return values;
    }

    private static void contentValue(ContentValues values, Field field, String invoke) {
        if (!TextUtils.isEmpty(invoke) && !"null".equals(invoke) && invoke.trim().length() != 0) {
            switch (field.getType().getSimpleName()) {
                case "boolean":
                case "Boolean":
                    values.put(field.getName(), (invoke.equals("true") ? 1 : 0));
                    break;
                case "byte":
                case "Byte":
                    values.put(field.getName(), Byte.parseByte(invoke));
                    break;
                case "int":
                case "Integer":
                    values.put(field.getName(), Integer.parseInt(invoke));
                    break;
                case "long":
                case "Long":
                    values.put(field.getName(), Long.parseLong(invoke));
                    break;
                case "short":
                case "Short":
                    values.put(field.getName(), Short.parseShort(invoke));
                    break;
                case "float":
                case "Float":
                    values.put(field.getName(), Float.parseFloat(invoke));
                    break;
                case "double":
                case "Double":
                    values.put(field.getName(), Double.parseDouble(invoke));
                    break;
                case "char":
                case "Character":
                    values.put(field.getName(), invoke);
                    break;
                default:
                    values.put(field.getName(), invoke);
                    break;
            }
        } else {
            values.put(field.getName(), invoke);
        }
    }
}
