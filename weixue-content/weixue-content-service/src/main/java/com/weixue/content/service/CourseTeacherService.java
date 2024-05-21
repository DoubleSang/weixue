package com.weixue.content.service;

import com.weixue.content.model.po.CourseTeacher;

import java.util.List;

/**
 * @Author: Sang
 * @Date: 2024/05/21/18:22
 * @Description:师资管理编辑接口
 */
public interface CourseTeacherService {

    /**
     * 查询教师信息
     * @param courseId
     * @return
     */
    public List<CourseTeacher> getTeacherInfo(Long courseId);

    /**
     * 新增/修改教师信息
     * @param courseTeacher
     * @return
     */
    public CourseTeacher saveTeacherInfo(CourseTeacher courseTeacher);


    public void deleteTeacherInfo(Long courseId,Long courseTeacherId);
}
