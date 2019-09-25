package com.rongdu.loans.sys.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by lee on 2018/5/4.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface LockAndSyncRequest {
    boolean locked() default true;

    String redisKeyPre() default "lock_key:";

    String redisKeyAfterByRequestName() default "";

    int seconds() default 120;
}
