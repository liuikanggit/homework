package com.heo.homework.service.impl;

import com.heo.homework.entity.Class;
import com.heo.homework.enums.ResultEnum;
import com.heo.homework.exception.MyException;
import com.heo.homework.form.ClassForm;
import com.heo.homework.form.ClassIdForm;
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

    /**
     * 根据传过来的表单，创建班级
     * @param classForm
     */
    @Override
    @Transactional
    public ResultVO createClass(ClassForm classForm) {
        Class mClass = new Class();
        /** 设置基本信息 名称，头像，年级，科目，描述，密码，创建教师id */
        mClass.setClassInfo(classForm);
        mClass.setClassId(KeyUtil.getClassKey(classRepository.findAllClassId()));
        mClass = classRepository.save(mClass);

        ClassVO classVO = new ClassVO();
        BeanUtils.copyProperties(mClass,classVO);
        return ResultVOUtil.success(classVO);
    }

    /**
     * 修改班级信息
     * @param classForm
     * @return
     */
    @Override
    @Transactional
    public ResultVO modifyClass(ClassForm classForm) {

       Class mClass =getClassByClassId(classForm.getClassId());

        /** 班级创建的id与老师id不一致 抛出异常 */
        if (mClass.getTeacherId().equals(classForm.getId())){
            throw new MyException(ResultEnum.NO_AUTH);
        }
        mClass.setClassInfo(classForm);
        mClass = classRepository.save(mClass);

        ClassVO classVO = new ClassVO();
        BeanUtils.copyProperties(mClass,classVO);
        return ResultVOUtil.success(classVO);
    }

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
     * @param classIdForm
     * @return
     */
    @Override
    public ResultVO getClassInfo(ClassIdForm classIdForm) {
        Class mClass = getClassByClassId(classIdForm.getClassId());
        ClassVO classVO = new ClassVO();
        BeanUtils.copyProperties(mClass,classVO);
        return ResultVOUtil.success(classVO);
    }

    @Override
    public ResultVO getClassMap() {
        List<Object[]> classList = classRepository.findClassMap();

        List<Map> classMap = new ArrayList<Map>();
        classList.forEach(o -> {
            Map<String,String> map = new HashMap<>();
            map.put("classId",o[0].toString());
            map.put("className",o[1].toString());
            classMap.add(map);
        });

        return ResultVOUtil.success(classMap);
    }
}
