package com.benyanyi.sqlitelib.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author YanYi
 * @date 2019/3/26 16:55
 * @email ben@yanyi.red
 * @overview 定义类为表结构（默认表名为类名）
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TableBean {
    String value() default "";
}
