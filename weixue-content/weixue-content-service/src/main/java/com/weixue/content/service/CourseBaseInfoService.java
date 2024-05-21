package com.weixue.content.service;

import com.weixue.base.model.PageParams;
import com.weixue.base.model.PageResult;
import com.weixue.content.model.dto.AddCourseDto;
import com.weixue.content.model.dto.CourseBaseInfoDto;
import com.weixue.content.model.dto.EditCourseDto;
import com.weixue.content.model.dto.QueryCourseParamsDto;
import com.weixue.content.model.po.CourseBase;

/**
 * @Author: Sang
 * @Date: 2024/04/20/12:19
 * @Description: 课程信息管理接口
 */
public interface CourseBaseInfoService {
    /**
    * @Description: 课程分页查询
    * @Param: [pageParams, courseParamsDto]
    * @return: com.weixue.base.model.PageResult<com.weixue.content.model.po.CourseBase>
    * @Author: Sang
    * @Date: 2024/4/20
    */
    public PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamsDto courseParamsDto);

    /**
     * 新增课程
     * @param companyId 机构id
     * @param addCourseDto 课程信息
     * @return 课程详细课程信息
     */
    public CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto addCourseDto);

    /**
     * 根据课程id查询课程信息
     * @param courseId 课程id
     * @return 课程详情信息
     */
    public CourseBaseInfoDto getCourseBaseInfo(Long courseId);

    /**
     * 修改课程
     * @param companyId 机构id
     * @param editCourseDto 修改课程西悉尼
     * @return 课程详细信息
     */
    public CourseBaseInfoDto updateCourseBase(Long companyId,EditCourseDto editCourseDto);

    /**
     * 删除课程
     * @param companyId
     * @param courseId
     */
    public void deleteCourseBase(Long companyId,Long courseId);
}
