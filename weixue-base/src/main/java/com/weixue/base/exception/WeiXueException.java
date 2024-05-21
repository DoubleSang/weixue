package com.weixue.base.exception;

/**
 * @Author: Sang
 * @Date: 2024/05/02/20:58
 * @Description:
 */
public class WeiXueException extends RuntimeException {

    private String errMessage;

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    public WeiXueException() {

    }

    public WeiXueException(String message) {
        super(message);
        this.errMessage = message;
    }

    public static void cast(String message) {
        throw new WeiXueException(message);
    }
    public static void cast(CommonError commonError) {
        throw new WeiXueException(commonError.getErrMessage());
    }


}
