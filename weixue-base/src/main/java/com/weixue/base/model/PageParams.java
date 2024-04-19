package com.weixue.base.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @Author: Sang
 * @Date: 2024/04/19/12:13
 * @Description: 分页查询的参数
 */
@Data
@ToString
public class PageParams {

    //当前页码
    @ApiModelProperty(value = "当前页码")
    private Long pageNo=1L;
    //每页记录数的默认值
    @ApiModelProperty(value = "每页记录数的默认值")
    private Long pageSize=10L;
    public PageParams(){

    }
    public PageParams(Long pageNo, Long pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }
}
