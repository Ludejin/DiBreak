package com.zero.dibreak.view.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.zero.dibreak.app.DiApplication;
import com.zero.dibreak.di.components.ApplicationComponent;
import com.zero.dibreak.di.module.ActivityModule;


/**
 * Created by Jin_ on 2016/9/30
 * 邮箱：Jin_Zboy@163.com
 */

public abstract class BaseActivity extends AppCompatActivity {
    private ActivityModule mActivityModule;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApplicationComponent().inject(this);
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((DiApplication)getApplication()).getApplicationComponent();
    }

    protected ActivityModule getActivityModule() {
        if (null == mActivityModule) {
            mActivityModule = new ActivityModule(this);
        }
        return mActivityModule;
    }

    protected Context getContext() {
        return this;
    }
}
