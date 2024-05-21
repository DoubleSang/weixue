package com.weixue.base.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Sang
 * @Date: 2024/05/02/21:16
 * @Description:
 */
@Slf4j
@ControllerAdvice
//@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 对项目的自定义异常类型进行处理
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(WeiXueException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestErrorResponse customException(WeiXueException e) {

        //记录异常
        log.error("系统异常{}",e.getErrMessage(),e);

        //解析出异常信息
        String errMessage = e.getErrMessage();
        RestErrorResponse restErrorResponse = new RestErrorResponse(errMessage);
        return restErrorResponse;

    }
    /**
     * 对项目运行时异常进行处理
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestErrorResponse exception(Exception e) {

        log.error("系统异常{}",e.getMessage(),e);

        return new RestErrorResponse(CommonError.UNKNOWN_ERROR.getErrMessage());
    }
    /**
     * 解析MethodArgumentNotValidException
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestErrorResponse MethodArgumentNotValidExceptionException(MethodArgumentNotValidException e) {

        BindingResult bindingResult = e.getBindingResult();
        //存储错误信息
        List<String> errors=new ArrayList<>();
        bindingResult.getFieldErrors().stream().forEach(item->{
            errors.add(item.getDefaultMessage());
        });
        //将list中的错误信息拼接起来
        String errMessage = StringUtils.join(errors, ",");
        //记录异常
        log.error("系统异常{}",e.getMessage(),errMessage);

        return new RestErrorResponse(errMessage);
    }


}
