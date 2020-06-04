package com.benyanyi.sqlitelib.operation;

import android.content.ContentValues;
import android.text.TextUtils;

import com.benyanyi.sqlitelib.annotation.ID;
import com.benyanyi.sqlitelib.annotation.NotColumn;
import com.benyanyi.sqlitelib.annotation.NotNull;
import com.benyanyi.sqlitelib.annotation.TableBean;
import com.benyanyi.sqlitelib.exception.TableException;
import com.benyanyi.sqlitelib.annotation.ColumnName;

import java.lang.reflect.Field;

/**
 * @author YanYi
 * @date 2019/3/29 1:25
 * @email ben@yanyi.red
 * @overview 表工具类
 */
class TableTool {

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
            boolean boo = !TextUtils.isEmpty(field.getName()) && !"null".equals(field.getName().toLowerCase().trim())
                    && field.getName().trim().length() != 0 && !"$change".equals(field.getName().trim())
                    && !"serialVersionUID".equals(field.getName().trim());
            if (boo) {
                ID annotation = field.getAnnotation(ID.class);
                NotNull notNull = field.getAnnotation(NotNull.class);
                ColumnName columnName = field.getAnnotation(ColumnName.class);
                String columnNameStr = field.getName();
                if (columnName != null) {
                    String value = columnName.value();
                    boolean boo1 = !TextUtils.isEmpty(value) && !"null".equals(value.toLowerCase().trim())
                            && !"".equals(value.trim());
                    if (boo1) {
                        columnNameStr = value;
                    }
                }
                try {
                    field.setAccessible(true);
                    //判断是否为id
                    if (annotation != null) {
                        boolean boo1 = annotation.increase();
                        //判断ID是否自增
                        if (boo1) {
                            //判断id的类型
                            boolean boo2 = !"long".equals(field.getType().getSimpleName().toLowerCase());
                            if (boo2) {
                                String invoke = String.valueOf(field.get(t));
                                if (TextUtils.isEmpty(invoke) || "null".equals(invoke) || invoke.trim().length() <= 0) {
                                    throw new TableException(columnNameStr + "不能为空或null,或将" + columnNameStr + "类型设置为long");
                                }
                            } else {
                                continue;
                            }
                        }
                    }
                    String invoke = String.valueOf(field.get(t));
                    //判断是否设置为不能为空值
                    if (notNull != null) {
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
        boolean boo = !TextUtils.isEmpty(invoke) && !"null".equals(invoke.toLowerCase().trim()) && invoke.trim().length() != 0;
        if (boo) {
            switch (type.toLowerCase()) {
                case "boolean":
                    values.put(columnName, ("true".equals(invoke) ? 1 : 0));
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
            boolean boo = !TextUtils.isEmpty(value) && !"null".equals(value) && value.trim().length() != 0;
            if (boo) {
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
        boolean boo = !TextUtils.isEmpty(invoke) && !"null".equals(invoke.toLowerCase().trim()) && invoke.trim().length() != 0;
        if (boo) {
            switch (type.toLowerCase()) {
                case "boolean":
                    return ("true".equals(invoke) ? 1 : 0) + "";
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
