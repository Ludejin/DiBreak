package com.zero.dibreak.di.components;

import android.app.Activity;

import com.zero.dibreak.di.DiActivity;
import com.zero.dibreak.di.module.ActivityModule;

import dagger.Component;

/**
 * Created by Jin_ on 2016/9/22
 * 邮箱：Jin_Zboy@163.com
 */

@DiActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    Activity activity();


}
