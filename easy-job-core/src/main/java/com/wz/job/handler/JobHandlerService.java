package com.wz.job.handler;

import org.springframework.stereotype.Service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>Handler注解</p>
 * Created by wangzi on 2017-09-22.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Service
public @interface JobHandlerService {
    String value();
}
