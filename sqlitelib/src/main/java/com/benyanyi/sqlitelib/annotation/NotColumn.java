package com.benyanyi.sqlitelib.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author YanYi
 * @date 2019/5/17 10:08
 * @email ben@yanyi.red
 * @overview 设置当前变量不为表的列，默认为
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotColumn {
    boolean notColumn() default true;
}
