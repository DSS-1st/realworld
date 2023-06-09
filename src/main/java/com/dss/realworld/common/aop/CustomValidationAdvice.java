package com.dss.realworld.common.aop;

import com.dss.realworld.common.error.exception.CustomValidationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Collectors;

@Aspect
@Component
public class CustomValidationAdvice {

    @Pointcut(value = "@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void postMapping() {
    }

    @Pointcut(value = "@annotation(org.springframework.web.bind.annotation.PutMapping)")
    public void putMapping() {
    }

    @Around(value = "postMapping() || putMapping()")
    public Object validateAdvice(final ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        for (Object arg : proceedingJoinPoint.getArgs()) checkOrThrow(arg);

        return proceedingJoinPoint.proceed();
    }

    private void checkOrThrow(final Object arg) {
        if (arg instanceof BindingResult && ((BindingResult) arg).hasErrors()) {
            List<String> messageList = ((BindingResult) arg).getFieldErrors().stream()
                    .map(fieldError -> fieldError.getField() + " : " + fieldError.getDefaultMessage())
                    .collect(Collectors.toList());
            throw new CustomValidationException(messageList);
        }
    }
}