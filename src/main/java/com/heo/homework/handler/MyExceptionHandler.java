package com.heo.homework.handler;

import com.heo.homework.exception.MyException;
import com.heo.homework.utils.ResultVOUtil;
import com.heo.homework.vo.ResultVO;
import lombok.Data;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class MyExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultVO handle(Exception e){
        if(e instanceof MyException) {
            MyException myException = (MyException) e;
            return ResultVOUtil.error(myException.getCode(), myException.getMessage());
        }else {
            log.error("[系统错误{}]", e);
            return ResultVOUtil.error(-1, "未知错误");
        }
    }
}
