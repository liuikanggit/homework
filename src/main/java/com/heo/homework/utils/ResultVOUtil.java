package com.heo.homework.utils;


import com.heo.homework.enums.ResultEnum;
import com.heo.homework.vo.ResultVO;

public class ResultVOUtil {

    public static ResultVO success(Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setData(object);
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        return resultVO;
    }

    public static ResultVO success() {
        return success(null);
    }

    public static ResultVO error(Integer code, String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }

    public static ResultVO error(ResultEnum em) {
        return error(em,null);
    }


    public static ResultVO error(ResultEnum em,String error) {
        ResultVO resultVO = error(em.getCode(),em.getMessage());
        resultVO.setData(error);
        return resultVO;
    }
}
