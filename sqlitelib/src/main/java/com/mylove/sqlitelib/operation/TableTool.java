package com.mylove.sqlitelib.operation;

import android.content.ContentValues;
import android.text.TextUtils;

import com.mylove.sqlitelib.annotation.ColumnName;
import com.mylove.sqlitelib.annotation.ID;
import com.mylove.sqlitelib.annotation.NotColumn;
import com.mylove.sqlitelib.annotation.NotNull;
import com.mylove.sqlitelib.annotation.TableBean;
import com.mylove.sqlitelib.exception.TableException;

import java.lang.reflect.Field;

/**
 * @author YanYi
 * @date 2019/3/29 1:25
 * @email ben@yanyi.red
 * @overview 表工具类
 */
final class TableTool {

    /**
     * 获取值
     *
     * @param t   数据
     * @param <T> 类型
     * @return 值集合
     */
    static <T> ContentValues values(T t) {
        ContentValues values = new ContentValues();
        Field[] fields = t.getClass().getDeclaredFields();
        for (Field field : fields) {
            NotColumn notColumn = field.getAnnotation(NotColumn.class);
            if (notColumn != null) {
                boolean b = notColumn.notColumn();
                if (b) {
                    continue;
                }
            }
            if (!TextUtils.isEmpty(field.getName()) && !"null".equals(field.getName().toLowerCase().trim())
                    && field.getName().trim().length() != 0 && !field.getName().trim().equals("$change")
                    && !field.getName().trim().equals("serialVersionUID")) {
                ID annotation = field.getAnnotation(ID.class);
                NotNull notNull = field.getAnnotation(NotNull.class);
                ColumnName columnName = field.getAnnotation(ColumnName.class);
                String columnNameStr = field.getName();
                if (columnName != null) {
                    String value = columnName.value();
                    if (!TextUtils.isEmpty(value) && !value.toLowerCase().trim().equals("null")
                            && !value.trim().equals("")) {
                        columnNameStr = value;
                    }
                }
                try {
//                    PropertyDescriptor descriptor = new PropertyDescriptor(field.getName(), t.getClass());
//                    Method method = descriptor.getReadMethod();//获得读方法
                    field.setAccessible(true);
                    if (annotation != null) {//判断是否为id
                        boolean boo = annotation.increase();
                        if (boo) {//判断ID是否自增
                            if (!field.getType().getSimpleName().toLowerCase().equals("long")) {//判断id的类型
//                                String invoke = String.valueOf(method.invoke(t));
                                String invoke = String.valueOf(field.get(t));
                                if (TextUtils.isEmpty(invoke) || "null".equals(invoke) || invoke.trim().length() <= 0) {
                                    throw new TableException(columnNameStr + "不能为空或null,或将" + columnNameStr + "类型设置为long");
                                }
                            } else {
                                continue;
                            }
                        }
                    }
//                    String invoke = String.valueOf(method.invoke(t));
                    String invoke = String.valueOf(field.get(t));
                    if (notNull != null) {//判断是否设置为不能为空值
                        boolean b = notNull.notNull();
                        if (b) {
                            if (TextUtils.isEmpty(invoke) || "null".equals(invoke.toLowerCase().trim()) || invoke.trim().length() <= 0) {
                                throw new TableException(columnNameStr + "不能为空值或null值");
                            }
                        }
                    }
                    contentValue(values, columnNameStr, field, invoke);
                } catch (Exception e) {
                    String errorMsg = e.getMessage();
                    if (e.getMessage().contains("set")) {
                        String msgMethod = "set";
                        errorMsg = getTabName(t.getClass()) + "表中" + columnNameStr + "列对应的"
                                + t.getClass().getSimpleName() + "类中" + field.getName() + "变量缺少"
                                + msgMethod + "方法";
                    } else if (e.getMessage().contains("get")) {
                        String msgMethod = "get";
                        errorMsg = getTabName(t.getClass()) + "表中" + columnNameStr + "列对应的"
                                + t.getClass().getSimpleName() + "类中" + field.getName() + "变量缺少"
                                + msgMethod + "方法";
                    }

                    throw new TableException(errorMsg);
                }

            }
        }
        return values;
    }

    /**
     * 类型处理
     *
     * @param values     值所添加到的集合
     * @param columnName 列名
     * @param field      变量反射方法
     * @param invoke     值
     */
    private static void contentValue(ContentValues values, String columnName, Field field, String invoke) {
        contentValue(values, columnName, field.getType().getSimpleName().toLowerCase().trim(), invoke);
    }

    /**
     * 值转换
     *
     * @param values     值所添加到的集合
     * @param columnName 列名
     * @param type       列中值的类型
     * @param invoke     值
     */
    private static void contentValue(ContentValues values, String columnName, String type, String invoke) {
        if (!TextUtils.isEmpty(invoke) && !"null".equals(invoke.toLowerCase().trim()) && invoke.trim().length() != 0) {
            switch (type.toLowerCase()) {
                case "boolean":
                    values.put(columnName, (invoke.equals("true") ? 1 : 0));
                    break;
                case "byte":
                    values.put(columnName, Byte.parseByte(invoke));
                    break;
                case "int":
                case "integer":
                    values.put(columnName, Integer.parseInt(invoke));
                    break;
                case "long":
                    values.put(columnName, Long.parseLong(invoke));
                    break;
                case "short":
                    values.put(columnName, Short.parseShort(invoke));
                    break;
                case "float":
                    values.put(columnName, Float.parseFloat(invoke));
                    break;
                case "double":
                    values.put(columnName, Double.parseDouble(invoke));
                    break;
                case "char":
                case "character":
                    values.put(columnName, invoke);
                    break;
                default:
                    values.put(columnName, invoke);
                    break;
            }
        } else {
            values.put(columnName, invoke);
        }
    }

    /**
     * 获取表名
     *
     * @param tClass 类名
     * @param <T>
     * @return
     */
    static <T> String getTabName(Class<T> tClass) {
        TableBean annotation = tClass.getAnnotation(TableBean.class);
        if (annotation != null) {
            String value = annotation.value();
            if (!TextUtils.isEmpty(value) && !"null".equals(value) && value.trim().length() != 0) {
                return value;
            }
        }
        return tClass.getSimpleName();
    }

    static String where(Field field, Object object) {
        String invoke = null;
        if (object != null) {
            invoke = object.toString();
        }
        String type = field.getType().getSimpleName().toLowerCase().trim();
        if (!TextUtils.isEmpty(invoke) && !"null".equals(invoke.toLowerCase().trim()) && invoke.trim().length() != 0) {
            switch (type.toLowerCase()) {
                case "boolean":
                    return (invoke.equals("true") ? 1 : 0) + "";
                case "byte":
                    return String.valueOf(Byte.parseByte(invoke));
                case "int":
                case "integer":
                    return Integer.parseInt(invoke) + "";
                case "long":
                    return Long.parseLong(invoke) + "";
                case "short":
                    return Short.parseShort(invoke) + "";
                case "float":
                    return Float.parseFloat(invoke) + "";
                case "double":
                    return Double.parseDouble(invoke) + "";
                case "char":
                case "character":
                    return invoke;
                default:
                    return invoke;
            }
        } else {
            return invoke;
        }
    }
}
