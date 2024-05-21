package com.weixue.content.model.dto;

import com.weixue.content.model.po.Teachplan;
import com.weixue.content.model.po.TeachplanMedia;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @Author: Sang
 * @Date: 2024/05/20/16:27
 * @Description: 课程计划信息模型类
 */
@Data
@ToString
public class TeachplanDto extends Teachplan {


    //与媒资关联的信息
    private TeachplanMedia teachplanMedia;

    //小章节列表
    private List<TeachplanDto>teachPlanTreeNodes;


}
