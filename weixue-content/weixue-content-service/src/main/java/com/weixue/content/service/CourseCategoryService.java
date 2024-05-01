package com.weixue.content.service;

import com.weixue.content.model.dto.CourseCategoryTreeDto;

import java.util.List;

/**
 * @Author: Sang
 * @Date: 2024/05/01/19:03
 * @Description:
 */
public interface CourseCategoryService {
    /**
     * 课程分类树形结构查询
     *
     * @return
     */
    public List<CourseCategoryTreeDto> queryTreeNodes(String id);

}
