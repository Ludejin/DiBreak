package com.zero.dibreak.di.components;

import android.content.Context;

import com.zero.dibreak.api.DiService;
import com.zero.dibreak.di.module.ApplicationModule;
import com.zero.dibreak.di.module.DiApiModule;
import com.zero.dibreak.view.base.BaseActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Jin_ on 2016/9/30
 * 邮箱：Jin_Zboy@163.com
 */

@Singleton
@Component(modules = {ApplicationModule.class, DiApiModule.class})
public interface ApplicationComponent {
    void inject(BaseActivity baseActivity);

    Context context();

    DiService getDiService();
}
