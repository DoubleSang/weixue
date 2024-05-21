package com.weixue.content.api;

import com.weixue.base.exception.ValidationGroups;
import com.weixue.base.model.PageParams;
import com.weixue.base.model.PageResult;
import com.weixue.content.model.dto.AddCourseDto;
import com.weixue.content.model.dto.CourseBaseInfoDto;
import com.weixue.content.model.dto.EditCourseDto;
import com.weixue.content.model.dto.QueryCourseParamsDto;
import com.weixue.content.model.po.CourseBase;
import com.weixue.content.service.CourseBaseInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.nio.channels.Pipe;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Sang
 * @Date: 2024/04/19/12:44
 * @Description:
 */
@Api(value = "课程信息管理接口", tags = "课程信息管理接口")
@RestController//相当于controller和responseBody
public class CourseBaseInfoController {
    @Autowired
    CourseBaseInfoService courseBaseInfoService;

    /**
     * 课程请求接口
     *
     * @param pageParams
     * @param queryCourseParams
     * @return
     */
    @ApiOperation(value = "课程分页查询接口")
    @PostMapping("/course/list")
    public PageResult<CourseBase> list(PageParams pageParams, @RequestBody(required = false) QueryCourseParamsDto queryCourseParams) {
        PageResult<CourseBase> pageResult = courseBaseInfoService.queryCourseBaseList(pageParams, queryCourseParams);
        return pageResult;
    }

    /**
     * 新增课程
     *
     * @param addCourseDto
     * @return
     */
    @ApiOperation("新增课程")
    @PostMapping("/course")
    public CourseBaseInfoDto createCourseBase(@RequestBody @Validated(ValidationGroups.Insert.class) AddCourseDto addCourseDto) {
        Long companyId = 1232141425L;
        //获取用户所属机构的id
        return courseBaseInfoService.createCourseBase(companyId, addCourseDto);
    }

    /**
     * 根据课程id查询接口
     * PathVariable用于占位符
     *
     * @param courseId
     * @return
     */
    @ApiOperation("根据课程id查询接口")
    @GetMapping("/course/{courseId}")
    public CourseBaseInfoDto getCourseBaseId(@PathVariable Long courseId) {
        return courseBaseInfoService.getCourseBaseInfo(courseId);
    }

    /**
     * 修改课程信息
     * PathVariable用于占位符
     *
     * @param editCourseDto
     * @return CourseBaseInfoDto
     */
    @ApiOperation("修改课程信息")
    @PutMapping("/course")
    public CourseBaseInfoDto editCourseBaseId(@RequestBody @Validated(ValidationGroups.Update.class) EditCourseDto editCourseDto) {
        Long companyId = 1232141425L;
        CourseBaseInfoDto courseBaseInfoDto = courseBaseInfoService.updateCourseBase(companyId, editCourseDto);
        return courseBaseInfoDto;
    }

    @ApiOperation("删除课程信息")
    @DeleteMapping("/course/{courseId}")
    public void deleteCourseBase(@PathVariable Long courseId){
        Long companyId = 1232141425L;
        courseBaseInfoService.deleteCourseBase(companyId,courseId);
    }


}
