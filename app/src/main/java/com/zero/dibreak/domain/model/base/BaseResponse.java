package com.zero.dibreak.domain.model.base;


import com.fasterxml.jackson.annotation.JacksonInject;

/**
 * Created by Jin_ on 2016/9/23
 * 邮箱：Jin_Zboy@163.com
 */

public class BaseResponse<T> {

    @JacksonInject("code")
    private int code;

    @JacksonInject("message")
    private String message;

    @JacksonInject("data")
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        if (0 == code) {
            return true;
        } else {
            return false;
        }
    }
}
