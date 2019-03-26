package com.heo.homework.handler;

import com.heo.homework.enums.ResultEnum;
import com.heo.homework.exception.MyException;
import com.heo.homework.utils.ResultVOUtil;
import com.heo.homework.vo.ResultVO;
import lombok.Data;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.text.ParseException;

@ControllerAdvice
@Slf4j
public class MyExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultVO handle(Exception e){
        if(e instanceof MyException) {
            MyException myException = (MyException) e;
            return ResultVOUtil.error(myException.getCode(), myException.getMessage());
        }
        else if(e instanceof MissingServletRequestParameterException){
            return ResultVOUtil.error(ResultEnum.MISSING_PARAMETERS,e.getMessage());
        }
        else if(e instanceof HttpRequestMethodNotSupportedException){
            return ResultVOUtil.error(ResultEnum.METHOD_NOT_SUPPORTED,e.getMessage());
        }
        else if(e instanceof QueryTimeoutException){
            log.error("redis连接超时{}",e);
            return ResultVOUtil.error(ResultEnum.QUERY_TIMEOUT,e.getMessage());
        }
        else if(e instanceof ParseException){
            return ResultVOUtil.error(ResultEnum.TIME_PARSE_ERROR);
        }
        else {
            log.error("[系统错误{}]", e);
            return ResultVOUtil.error(-1, "未知错误");
        }
    }
}
