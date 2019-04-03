package com.mylove.sqlitelib.operation;

import android.content.ContentValues;
import android.text.TextUtils;

import com.googlecode.openbeans.PropertyDescriptor;
import com.mylove.sqlitelib.annotation.ID;
import com.mylove.sqlitelib.annotation.NotNull;
import com.mylove.sqlitelib.annotation.TableBean;
import com.mylove.sqlitelib.exception.TableException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author YanYi
 * @date 2019/3/29 13:25
 * @email ben@yanyi.red
 * @overview
 */
public class TableTool {
    public static <T> ContentValues values(T t) {
        ContentValues values = new ContentValues();
        try {
            Field[] fields = t.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (!field.getName().equals("$change") && !field.getName().equals("serialVersionUID") &&
                        !TextUtils.isEmpty(field.getName()) && !"null".equals(field.getName()) && field.getName().trim().length() != 0) {
                    ID annotation = field.getAnnotation(ID.class);
                    NotNull notNull = field.getAnnotation(NotNull.class);
                    PropertyDescriptor descriptor = new PropertyDescriptor(field.getName(), t.getClass());
                    Method method = descriptor.getReadMethod();//获得读方法
                    if (annotation != null) {
                        boolean boo = annotation.increase();
                        if (boo) {
                            if (!field.getType().getSimpleName().toLowerCase().equals("long")) {
                                String invoke = String.valueOf(method.invoke(t));
                                if (TextUtils.isEmpty(invoke) || "null".equals(invoke) || invoke.trim().length() != 0) {
                                    throw new TableException(field.getName() + "不能为空值,或将" + field.getName() + "类型设置为long");
                                }
                            }
                        } else if (notNull != null) {
                            boolean b = notNull.notNull();
                            if (b) {
                                String invoke = String.valueOf(method.invoke(t));
                                if (TextUtils.isEmpty(invoke) || "null".equals(invoke) || invoke.trim().length() != 0) {
                                    throw new TableException(field.getName() + "不能为空值");
                                }
                            }
                        }
                    } else {
                        String invoke = String.valueOf(method.invoke(t));
                        if (notNull != null) {
                            boolean b = notNull.notNull();
                            if (b) {
                                if (TextUtils.isEmpty(invoke) || "null".equals(invoke) || invoke.trim().length() == 0) {
                                    throw new TableException(field.getName() + "不能为空值");
                                } else {
                                    contentValue(values, field, invoke);
                                }
                            } else {
                                contentValue(values, field, invoke);
                            }
                        } else {
                            contentValue(values, field, invoke);
                        }
                    }
                }
            }
        } catch (Exception e) {
            return null;
        }
        return values;
    }

    private static void contentValue(ContentValues values, Field field, String invoke) {
        if (!TextUtils.isEmpty(invoke) && !"null".equals(invoke) && invoke.trim().length() != 0) {
            switch (field.getType().getSimpleName().toLowerCase()) {
                case "boolean":
                    values.put(field.getName(), (invoke.equals("true") ? 1 : 0));
                    break;
                case "byte":
                    values.put(field.getName(), Byte.parseByte(invoke));
                    break;
                case "int":
                case "integer":
                    values.put(field.getName(), Integer.parseInt(invoke));
                    break;
                case "long":
                    values.put(field.getName(), Long.parseLong(invoke));
                    break;
                case "short":
                    values.put(field.getName(), Short.parseShort(invoke));
                    break;
                case "float":
                    values.put(field.getName(), Float.parseFloat(invoke));
                    break;
                case "double":
                    values.put(field.getName(), Double.parseDouble(invoke));
                    break;
                case "char":
                case "character":
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

    public static <T> String getTabName(Class<T> tClass) {
        TableBean annotation = tClass.getAnnotation(TableBean.class);
        if (annotation != null) {
            String value = annotation.value();
            if (!TextUtils.isEmpty(value) && !"null".equals(value) && value.trim().length() != 0) {
                return value;
            }
        }
        return tClass.getSimpleName();
    }
}
