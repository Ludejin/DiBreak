package com.zero.dibreak.domain.exception;

/**
 * Created by Jin_ on 2016/9/24
 * 邮箱：Jin_Zboy@163.com
 */

public class DefaultErrorBundle implements IErrorBundle {

    private static final String DEFAULT_ERROR_MSG = "未知错误";

    private final Exception exception;

    public DefaultErrorBundle(Exception exception) {
        this.exception = exception;
    }

    @Override
    public Exception getException() {
        return exception;
    }

    @Override
    public String getErrorMessage() {
        return (exception != null) ? this.exception.getMessage() : DEFAULT_ERROR_MSG;
    }
}
