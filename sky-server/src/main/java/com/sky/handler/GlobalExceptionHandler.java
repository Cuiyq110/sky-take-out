package com.sky.handler;

import com.sky.constant.MessageConstant;
import com.sky.exception.BaseException;
import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    public Result exceptionHandler(SQLIntegrityConstraintViolationException ex) {
        log.error("异常信息：{}", ex.getMessage());
//        返回具体哪个用户重复
        String errorMessage = ex.getMessage();
        if (errorMessage.contains("Duplicate entry")) {
            // 假设错误信息格式为 "Duplicate entry 'user3' for key 'employee.idx_username'"
            String[] parts = errorMessage.split(" ");
            for (int i = 0; i < parts.length; i++) {
                if (parts[i].equals("entry")) {
                    if (i + 1 < parts.length) {
                        String entryWithQuotes = parts[i + 1];
                        // 去掉引号
                        String replace = entryWithQuotes.replace("'", "");
                        return Result.error(MessageConstant.ALREADY_EXISTS + "：" + replace);
                    }
                }
            }
        }
        return Result.error(MessageConstant.UNKNOWN_ERROR);
    }


    /**
     * 捕获业务异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex) {
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

}
