package com.weixue.base.exception;

import java.io.Serializable;

/**
 * @Author: Sang
 * @Date: 2024/05/02/20:54
 * @Description: 与前端约定返回的一次信息模式
 */
public class RestErrorResponse implements Serializable {
    private String errMessage;

    public RestErrorResponse(String errMessage){
        this.errMessage=errMessage;
    }

    public String getErrMessage(){
        return this.errMessage;
    }

    public void setErrMessage(String errMessage){
        this.errMessage=errMessage;
    }
}
