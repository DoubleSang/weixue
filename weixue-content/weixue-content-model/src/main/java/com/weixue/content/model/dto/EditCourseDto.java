package com.weixue.content.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: Sang
 * @Date: 2024/05/03/22:20
 * @Description:
 */
@Data
public class EditCourseDto extends AddCourseDto{
    @ApiModelProperty(value = "课程id",required = true)
    private Long id;
}
