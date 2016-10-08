package com.zero.dibreak.domain.exception;

/**
 * Created by Jin_ on 2016/9/24
 * 邮箱：Jin_Zboy@163.com
 */

public interface IErrorBundle {
    Exception getException();

    String getErrorMessage();
}
