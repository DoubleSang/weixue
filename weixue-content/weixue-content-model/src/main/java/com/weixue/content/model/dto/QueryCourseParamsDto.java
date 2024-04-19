package com.weixue.content.model.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: Sang
 * @Date: 2024/04/19/12:37
 * @Description:课程查询条件模型类
 */
public class QueryCourseParamsDto {
    //审核状态
    @ApiModelProperty(value = "审核状态")
    private String auditStatus;
    //课程名称
    @ApiModelProperty(value = "课程名称")
    private String courseName;
    //发布状态
    @ApiModelProperty(value = "发布状态")
    private String publishStatus;

}
