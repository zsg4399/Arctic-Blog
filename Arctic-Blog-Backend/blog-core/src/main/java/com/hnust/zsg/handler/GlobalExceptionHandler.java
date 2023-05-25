package com.hnust.zsg.handler;

import com.hnust.zsg.Exception.ServiceLimitException;
import com.hnust.zsg.Excption.validation.EmailillegalException;
import com.hnust.zsg.Excption.validation.NoSuchFieldException;
import com.hnust.zsg.Excption.validation.PasswordillegalException;
import com.hnust.zsg.Excption.validation.UsernameillegalException;
import com.hnust.zsg.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.TransactionTimedOutException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ServiceLimitException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Result ServiceLimitExceptionHandler(HttpServletRequest request, HttpServletResponse response, ServiceLimitException exception) {
        log.warn(exception.getMessage());
        return Result.fail(exception.getMessage());
    }

    @ExceptionHandler(value = UsernameillegalException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Result UsernameillegalExcption(HttpServletRequest request, HttpServletResponse response, UsernameillegalException excption) {
        String message=excption.getMessage();
        log.info("ErrorResult:{}",message);
        return Result.fail(message);
    }

    @ExceptionHandler(value = PasswordillegalException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Result PasswordillegalExcptionHandler(HttpServletRequest request, HttpServletResponse response, PasswordillegalException excption) {
        String message = excption.getMessage();
        log.info("ErrorResult:{}", message);
        return Result.fail(message);
    }

    @ExceptionHandler(value = EmailillegalException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Result EmailillegalExcptionHandler(HttpServletRequest request, HttpServletResponse response, EmailillegalException excption) {
        String message = excption.getMessage();
        log.info("ErrorResult:{}", message);
        return Result.fail(message);
    }

    @ExceptionHandler(value = TransactionTimedOutException.class)
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    public Result EmailillegalExcptionHandler(HttpServletRequest request, HttpServletResponse response, TransactionTimedOutException excption) {
        String message = excption.getMessage();
        log.error("ErrorResult:{}", message);
        return Result.fail("系统响应超时，请稍后重试");
    }

    @ExceptionHandler(value = NoSuchFieldException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Result NoSuchFieldExcptionHandler(HttpServletRequest request, HttpServletResponse response, NoSuchFieldException excption) {
        String message = excption.getMessage();
        log.error("ErrorResult:{}", message);
        return Result.fail(message);
    }

    @ExceptionHandler(value = DateTimeParseException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Result DateTimeParseExcptionHandler(HttpServletRequest request, HttpServletResponse response, DateTimeParseException excption) {
        String message = excption.getMessage();
        log.error("ErrorResult:{}", message);
        return Result.fail(message);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Result IllegalArgumentExcptionHandler(HttpServletRequest request, HttpServletResponse response, DateTimeParseException excption) {
        String message = excption.getMessage();
        log.error("ErrorResult:{}", message);
        return Result.fail(message);
    }

}
