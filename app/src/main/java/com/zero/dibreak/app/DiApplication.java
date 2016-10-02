package com.zero.dibreak.app;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.zero.dibreak.common.Const;
import com.zero.dibreak.di.components.ApplicationComponent;
import com.zero.dibreak.di.components.DaggerApplicationComponent;
import com.zero.dibreak.di.module.ApplicationModule;
import com.zero.dibreak.di.module.DiApiModule;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Jin_ on 2016/9/30
 * 邮箱：Jin_Zboy@163.com
 */

public class DiApplication extends Application {
    private static final String TAG = "DiApplication";

    private ApplicationComponent mApplicationComponent;
    private static DiApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        this.sInstance = this;

        /** 初始化Realm配置 */
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name(Const.DB_NAME).build();
        Realm.setDefaultConfiguration(realmConfiguration);

        initInjector();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public static DiApplication getInstance() {
        return sInstance;
    }

    private void initInjector() {
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .diApiModule(new DiApiModule(true))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }
}
