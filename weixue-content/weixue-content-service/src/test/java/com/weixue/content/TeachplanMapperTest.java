package com.weixue.content;

import com.weixue.content.mapper.CourseCategoryMapper;
import com.weixue.content.mapper.TeachplanMapper;
import com.weixue.content.model.dto.CourseCategoryTreeDto;
import com.weixue.content.model.dto.TeachplanDto;
import com.weixue.content.model.po.TeachplanMedia;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @Author: Sang
 * @Date: 2024/05/01/18:56
 * @Description: 课程计划mapper测试
 */
@SpringBootTest
public class TeachplanMapperTest {

    @Autowired
    TeachplanMapper teachplanMapper;

    @Test
    public void testTeachplanMapper() {

        List<TeachplanDto> teachplanDtos = teachplanMapper.selectTreeNodes(117l);
        System.out.println(teachplanDtos);
    }
}
