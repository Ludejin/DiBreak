package com.zero.dibreak.domain.model.base;


import com.fasterxml.jackson.annotation.JacksonInject;

/**
 * Created by Jin_ on 2016/9/23
 * 邮箱：Jin_Zboy@163.com
 */

public class BaseResponse<T> {

    @JacksonInject("error")
    private boolean error;

    @JacksonInject("results")
    private T data;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return !error;
    }

    /**
     * @return 返回错误信息，gank.io 没有这个字段，一般自己的接口是有的
     */
    public String getMessage() {
        return "请求返回错误message";
    }

    /**
     * @see #getMessage() 类似（-1代表错误请求code）
     * @return 返回请求的code
     */
    public int getCode() {
        return -1;
    }
}
