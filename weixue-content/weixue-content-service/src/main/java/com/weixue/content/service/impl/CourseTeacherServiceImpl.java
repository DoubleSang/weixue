package com.weixue.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.weixue.base.exception.WeiXueException;
import com.weixue.content.mapper.CourseTeacherMapper;
import com.weixue.content.model.po.CourseTeacher;
import com.weixue.content.service.CourseTeacherService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: Sang
 * @Date: 2024/05/21/19:55
 * @Description:师资管理编辑接口
 */
@Service
@Slf4j
public class CourseTeacherServiceImpl implements CourseTeacherService {

    @Autowired
    CourseTeacherMapper courseTeacherMapper;


    @Override
    public List<CourseTeacher> getTeacherInfo(Long courseId) {
        LambdaQueryWrapper<CourseTeacher> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CourseTeacher::getCourseId,courseId);
        return courseTeacherMapper.selectList(queryWrapper);
    }

    @Override
    public CourseTeacher saveTeacherInfo(CourseTeacher courseTeacher) {
        Long id = courseTeacher.getId();
        if (id == null) {//无id，新增教师信息
            CourseTeacher dbTeacher = new CourseTeacher();
            BeanUtils.copyProperties(courseTeacher, dbTeacher);
            dbTeacher.setCreateDate(LocalDateTime.now());
            int flag = courseTeacherMapper.insert(dbTeacher);
            if (flag <= 0)
                WeiXueException.cast("教师信息新增失败");
            return dbTeacher;
        } else {//有id，修改教师信息
            CourseTeacher teacher = new CourseTeacher();
            BeanUtils.copyProperties(courseTeacher, teacher);
            int flag1 = courseTeacherMapper.updateById(teacher);
            if (flag1 <= 0)
                WeiXueException.cast("教师信息修改失败");
            return teacher;
        }
    }


    @Override
    public void deleteTeacherInfo(Long courseId, Long courseTeacherId) {
        LambdaQueryWrapper<CourseTeacher> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CourseTeacher::getCourseId,courseId).eq(CourseTeacher::getId,courseTeacherId);
        int delete = courseTeacherMapper.delete(queryWrapper);
        if(delete<=0){
            WeiXueException.cast("教师信息删除失败");
        }
    }
}
