package com.mylove.sqlitelib.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author YanYi
 * @date 2019/3/26 16:55
 * @email ben@yanyi.red
 * @overview 定义类为表结构
 */
@Target(ElementType.TYPE)//表示注解方法
@Retention(RetentionPolicy.RUNTIME)//会持续保存到JVM运行时，可以通过反射来获取
public @interface TableBean {
    String value() default "";//自定义表名
}
