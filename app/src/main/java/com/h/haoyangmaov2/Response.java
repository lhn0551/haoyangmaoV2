package com.h.haoyangmaov2;


/**
 * User: ljx
 * Date: 2018/10/21
 * Time: 13:16
 */
public class Response<T> {

    private int status;
    private String msg;
    private T data;


    public int getCode() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setErrorCode(int errorCode) {
        this.status = errorCode;
    }

    public void setErrorMsg(String errorMsg) {
        this.msg = errorMsg;
    }
}
