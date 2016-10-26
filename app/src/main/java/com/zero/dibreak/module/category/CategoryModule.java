package com.zero.dibreak.module.category;

import android.content.Context;

import com.zero.dibreak.api.DiService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Jin_ on 2016/10/26
 * 邮箱：Jin_Zboy@163.com
 */

@Module
public class CategoryModule {
    private DiService mService;
    private Context mContext;

    public CategoryModule(DiService service, Context context) {
        mService = service;
        mContext = context;
    }

    @Provides DiService getService() {
        return mService;
    }

    @Provides Context getContext() {
        return mContext;
    }
}
