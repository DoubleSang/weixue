package com.weixue.content.model.dto;

import com.weixue.content.model.po.CourseCategory;
import lombok.Data;

import java.util.List;

/**
 * @Author: Sang
 * @Date: 2024/05/01/17:02
 * @Description:
 */
@Data
public class CourseCategoryTreeDto extends CourseCategory implements java.io.Serializable{
    //子结点
    List<CourseCategoryTreeDto> childrenTreeNodes;
}
