package com.heo.homework.service.impl;

import com.heo.homework.entity.Class;
import com.heo.homework.enums.ResultEnum;
import com.heo.homework.exception.MyException;
import com.heo.homework.form.ClassForm;
import com.heo.homework.repository.ClassRepository;
import com.heo.homework.service.ClassService;
import com.heo.homework.utils.KeyUtil;
import com.heo.homework.utils.ResultVOUtil;
import com.heo.homework.vo.ClassVO;
import com.heo.homework.vo.ResultVO;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class ClassServiceImpl implements ClassService{

    @Autowired
    private ClassRepository classRepository;


    private Class getClassByClassId(String classId){
        if(!Strings.isEmpty(classId)){
            throw new MyException(ResultEnum.REQUEST_EXCEPTION);
        }
        Class mClass = classRepository.findByClassId(classId);
        if (Objects.isNull(mClass)){
            throw new MyException(ResultEnum.CLASS_NOT_EMPTY);
        }
        return mClass;
    }

    /**
     * 根据classId获取班级
     * @param classId
     * @return
     */
    @Override
    public ResultVO getClassInfo(String classId) {
        Class mClass = getClassByClassId(classId);
        ClassVO classVO = new ClassVO();
        BeanUtils.copyProperties(mClass,classVO);
        return ResultVOUtil.success(classVO);
    }

    @Override
    public ResultVO getClassMap() {
        List<Object[]> classList = classRepository.findClassMap();

        List<Map> classMap = new ArrayList<>();
        classList.forEach(o -> {
            Map<String,String> map = new HashMap<>();
            map.put("classId",o[0].toString());
            map.put("className",o[1].toString());
            classMap.add(map);
        });

        return ResultVOUtil.success(classMap);
    }
}
