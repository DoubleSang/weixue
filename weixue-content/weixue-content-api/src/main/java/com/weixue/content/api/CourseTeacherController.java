package com.weixue.content.api;

import com.weixue.content.model.po.CourseTeacher;
import com.weixue.content.service.CourseTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Sang
 * @Date: 2024/05/21/18:19
 * @Description: 师资管理编辑接口
 */
@RestController
@Api(value = "师资管理编辑接口",tags = "师资管理编辑接口")
public class CourseTeacherController {

    @Autowired
    CourseTeacherService courseTeacherService;

    @ApiOperation("查询师资信息")
    @GetMapping("/courseTeacher/list/{courseId}")
    public List<CourseTeacher> getTeacherInfo(@PathVariable Long courseId){
        return courseTeacherService.getTeacherInfo(courseId);
    }

    @ApiOperation("新增师资信息")
    @PostMapping("/courseTeacher")
    public CourseTeacher insertTeacherInfo(@RequestBody CourseTeacher courseTeacher){
        return courseTeacherService.saveTeacherInfo(courseTeacher);
    }
    @ApiOperation("修改师资信息")
    @PutMapping("/courseTeacher")
    public CourseTeacher updateTeacherInfo(@RequestBody CourseTeacher courseTeacher){
        return courseTeacherService.saveTeacherInfo(courseTeacher);
    }

    @ApiOperation("删除师资信息")
    @DeleteMapping("/courseTeacher/course/{courseId}/{teacherId}")
    public void deleteTeacherInfo(@PathVariable Long courseId,@PathVariable Long teacherId){
        courseTeacherService.deleteTeacherInfo(courseId,teacherId);
    }

}
