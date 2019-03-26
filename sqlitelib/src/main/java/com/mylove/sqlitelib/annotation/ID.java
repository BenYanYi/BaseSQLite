package com.mylove.sqlitelib.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author YanYi
 * @date 2019/3/26 16:58
 * @email ben@yanyi.red
 * @overview id(默认不自增)
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface ID {
    boolean increase() default false;
}
