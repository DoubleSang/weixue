package com.weixue.content.service;

import com.weixue.content.model.dto.SaveTeachplanDto;
import com.weixue.content.model.dto.TeachplanDto;

import java.util.List;

/**
 * @Author: Sang
 * @Date: 2024/05/20/18:26
 * @Description: 课程计划管理相关接口
 */
public interface TeachplanService {
    /**
     * 根据课程id查询课程计划
     * @param courseId 课程id
     * @return
     */
    public List<TeachplanDto> findTeachplanTree(Long courseId);

    /**
     * 新增/修改/保存
     * @param saveTeachplanDto
     */
    public void saveTeachplan(SaveTeachplanDto saveTeachplanDto);

    /**
     * 删除
     * 删除第一级别的大章节时要求大章节下边没有小章节时方可删除。
     * 删除第二级别的小章节的同时需要将teachplan_media表关联的信息也删除。
     * @param teachplanId
     */
    public void deleteTeachplan(Long teachplanId);

    public void orderByTeachplan(String moveType,Long teachplanId);
}
