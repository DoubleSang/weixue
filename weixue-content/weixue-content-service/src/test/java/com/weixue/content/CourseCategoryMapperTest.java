package com.weixue.content;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weixue.base.model.PageParams;
import com.weixue.base.model.PageResult;
import com.weixue.content.mapper.CourseBaseMapper;
import com.weixue.content.mapper.CourseCategoryMapper;
import com.weixue.content.model.dto.CourseCategoryTreeDto;
import com.weixue.content.model.dto.QueryCourseParamsDto;
import com.weixue.content.model.po.CourseBase;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @Author: Sang
 * @Date: 2024/05/01/18:56
 * @Description:
 */
@SpringBootTest
public class CourseCategoryMapperTest {

    @Autowired
    CourseCategoryMapper courseCategoryMapper;

    @Test
    public void testCourseCategoryMapper() {

        List<CourseCategoryTreeDto> courseCategoryTreeDtos = courseCategoryMapper.selectTreeNodes("1");
        System.out.println(courseCategoryTreeDtos);
    }
}
