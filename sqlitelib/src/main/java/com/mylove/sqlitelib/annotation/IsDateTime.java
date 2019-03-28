package com.mylove.sqlitelib.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author YanYi
 * @date 2019/3/28 17:22
 * @email ben@yanyi.red
 * @overview
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IsDateTime {
    boolean isDateTime() default true;
}
