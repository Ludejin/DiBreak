package com.zero.dibreak.di.module;

import android.app.Activity;

import com.zero.dibreak.di.DiActivity;

import dagger.Module;
import dagger.Provides;


/**
 * Created by Jin_ on 2016/9/22
 * 邮箱：Jin_Zboy@163.com
 */

@Module
public class ActivityModule {
    private final Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    @DiActivity
    Activity activity() {
        return mActivity;
    }

}
