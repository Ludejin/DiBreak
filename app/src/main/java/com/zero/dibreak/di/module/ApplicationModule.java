package com.zero.dibreak.di.module;

import android.content.Context;

import com.zero.dibreak.app.DiApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Jin_ on 2016/9/22
 * 邮箱：Jin_Zboy@163.com
 */

@Module
public class ApplicationModule {
    private final DiApplication mApplication;

    public ApplicationModule(DiApplication application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return mApplication;
    }
}
