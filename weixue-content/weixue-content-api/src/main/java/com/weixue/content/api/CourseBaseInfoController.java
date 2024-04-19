package com.weixue.content.api;

import com.weixue.base.model.PageParams;
import com.weixue.base.model.PageResult;
import com.weixue.content.model.dto.QueryCourseParamsDto;
import com.weixue.content.model.po.CourseBase;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Sang
 * @Date: 2024/04/19/12:44
 * @Description:
 */
@Api(value = "课程信息管理接口" ,tags = "课程信息管理接口")
@RestController//相当于controller和responseBody
public class CourseBaseInfoController {
    /**
     * 课程请求接口
     * @param pageParams
     * @param queryCourseParams
     * @return
     */
    @ApiOperation(value = "课程查询接口")
    @PostMapping("/course/list")
    public PageResult<CourseBase> list(PageParams pageParams, @RequestBody(required = false) QueryCourseParamsDto queryCourseParams) {

        CourseBase courseBase = new CourseBase();
        courseBase.setName("测试名称");
        courseBase.setCreateDate(LocalDateTime.now());
        List<CourseBase> courseBases = new ArrayList();
        courseBases.add(courseBase);
        PageResult pageResult = new PageResult<CourseBase>(courseBases,10,1,10);
        return pageResult;

    }

}
