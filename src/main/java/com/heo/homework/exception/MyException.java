package com.heo.homework.exception;

import com.heo.homework.enums.ResultEnum;
import lombok.Getter;

@Getter
public class MyException extends RuntimeException{
    private Integer code;

    public MyException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public MyException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
