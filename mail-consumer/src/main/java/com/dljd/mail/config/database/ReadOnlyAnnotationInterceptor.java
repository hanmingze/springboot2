package com.dljd.mail.config.database;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by jiaozhiguang on 2018/3/10.
 */
@Aspect
@Component
public class ReadOnlyAnnotationInterceptor {

    public static final Logger LOGGER = LoggerFactory.getLogger(ReadOnlyAnnotationInterceptor.class);

    @Around("@annotation(readOnlyAnnotation)")
    public Object proceed(ProceedingJoinPoint joinPoint, ReadOnlyAnnotation readOnlyAnnotation) throws Throwable{

        try {
            LOGGER.info("set database connection to read only");
            DataBaseContextHolder.setDataBaseType(DataBaseContextHolder.DataBaseType.SLAVE);
            Object result = joinPoint.proceed();
            return result;
        } finally {
            DataBaseContextHolder.clearDataBaseType();
            LOGGER.info("restore database connection");
        }
    }

}
