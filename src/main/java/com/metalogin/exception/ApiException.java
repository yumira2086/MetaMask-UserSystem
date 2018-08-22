package com.metalogin.exception;


import com.metalogin.common.ResultCode;

/**
 * Created by: Yumira.
 * Created on: 2018/7/26-下午3:54.
 * Description:
 */
public class ApiException extends Exception {

    private ResultCode code;
    private String message;

    public ApiException(ResultCode code, String message){
        this.code = code;
        this.message = message;
    }

    public ResultCode getCode() {
        return code;
    }

    public void setCode(ResultCode code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
