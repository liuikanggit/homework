package com.heo.homework.service.impl;

import com.heo.homework.entity.Class;
import com.heo.homework.enums.ResultEnum;
import com.heo.homework.exception.MyException;
import com.heo.homework.repository.ClassRepository;
import com.heo.homework.repository.Student2ClassRepository;
import com.heo.homework.service.ClassService;
import com.heo.homework.utils.ResultVOUtil;
import com.heo.homework.vo.ClassVO;
import com.heo.homework.vo.ResultVO;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ClassServiceImpl implements ClassService{

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private Student2ClassRepository student2ClassRepository;


    private Class getClassByClassId(String classId){
        if(Strings.isBlank(classId)){
            throw new MyException(ResultEnum.REQUEST_EXCEPTION);
        }
        Class mClass = classRepository.findByClassId(classId);
        if (Objects.isNull(mClass)){
            throw new MyException(ResultEnum.CLASS_NOT_EXIST);
        }
        return mClass;
    }

    /**
     * 根据classId获取班级
     * @param classId
     * @return
     */
    @Override
    public ResultVO getClassInfo(String classId,boolean isTeacher) {
        Class mClass = getClassByClassId(classId);
        ClassVO classVO = new ClassVO(mClass,student2ClassRepository.countByClassId(mClass.getClassId()));
        if (!isTeacher){
            classVO.setPassword(null);
        }
        return ResultVOUtil.success(classVO);
    }

    @Override
    public ResultVO getClassInfo(String teacherId) {
        List<Class> classList = classRepository.getAllByTeacherId(teacherId);
        List<ClassVO> classVOList = new ArrayList<>();
        classList.forEach(clazz ->{
            classVOList.add(new ClassVO(clazz,student2ClassRepository.countByClassId(clazz.getClassId())));
        });
        return ResultVOUtil.success(classVOList);
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
