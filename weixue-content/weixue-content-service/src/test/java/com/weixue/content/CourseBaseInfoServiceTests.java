package com.weixue.content;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weixue.base.model.PageParams;
import com.weixue.base.model.PageResult;
import com.weixue.content.mapper.CourseBaseMapper;
import com.weixue.content.model.dto.QueryCourseParamsDto;
import com.weixue.content.model.po.CourseBase;
import com.weixue.content.service.CourseBaseInfoService;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @Author: Sang
 * @Date: 2024/04/19/17:30
 * @Description:
 */
@SpringBootTest
public class CourseBaseInfoServiceTests {
    @Autowired
    CourseBaseInfoService courseBaseInfoService;

    @Test
    public void testCourseBaseInfoService() {
        //查询条件
        QueryCourseParamsDto courseParamsDto = new QueryCourseParamsDto();
        courseParamsDto.setCourseName("java");
        courseParamsDto.setAuditStatus("202004");//设置审核通过
        courseParamsDto.setPublishStatus("203002");
        PageParams pageParams=new PageParams();
        pageParams.setPageNo(2L);
        pageParams.setPageSize(2L);
        PageResult<CourseBase> courseBasePageResult = courseBaseInfoService.queryCourseBaseList(pageParams, courseParamsDto);

        System.out.println(courseBasePageResult);

    }
}
