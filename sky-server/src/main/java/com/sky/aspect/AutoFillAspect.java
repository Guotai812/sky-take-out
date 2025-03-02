package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.constant.MessageConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * 自定义切面， 实现公共字段自动填充
 */
@Aspect
@Component
@Slf4j
public class AutoFillAspect {
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointCut() {}

    @Before("autoFillPointCut()")
    public void before(JoinPoint joinPoint) {
         log.info("开始进行公共字段填充");
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        AutoFill annotation = methodSignature.getMethod().getAnnotation(AutoFill.class);
        OperationType value = annotation.value();

        Object[] args = joinPoint.getArgs();
        if (args == null && args.length == 0) {
            return;
        }
        Object target = args[0];
        LocalDateTime now = LocalDateTime.now();
        Long currentId = BaseContext.getCurrentId();
        if (value == OperationType.INSERT) {
            try {
                Method setCreateTime = target.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                Method setCreateUser = target.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
                Method setUpdateTime = target.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = target.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
                setUpdateTime.invoke(target, now);
                setUpdateUser.invoke(target, currentId);
                setCreateUser.invoke(target, currentId);
                setCreateTime.invoke(target, now);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if (value == OperationType.UPDATE) {
            try {
                Method setUpdateTime = target.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = target.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
                setUpdateTime.invoke(target, now);
                setUpdateUser.invoke(target, currentId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
