package com.weixue.content;

import com.weixue.base.model.PageParams;
import com.weixue.base.model.PageResult;
import com.weixue.content.model.dto.CourseCategoryTreeDto;
import com.weixue.content.model.dto.QueryCourseParamsDto;
import com.weixue.content.model.po.CourseBase;
import com.weixue.content.service.CourseBaseInfoService;
import com.weixue.content.service.CourseCategoryService;
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
public class CourseCategoryServiceTests {
    @Autowired
    CourseCategoryService courseCategoryService;

    @Test
    public void testCourseCategoryService() {
        List<CourseCategoryTreeDto> categoryTreeDtoList = courseCategoryService.queryTreeNodes("1");

        System.out.println(categoryTreeDtoList);

    }
}
