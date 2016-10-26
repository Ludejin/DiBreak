package com.zero.dibreak.di;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Jin_ on 2016/10/26
 * 邮箱：Jin_Zboy@163.com
 */

@Documented
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface DiFragment {
}
