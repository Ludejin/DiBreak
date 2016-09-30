package com.zero.dibreak.di.module;

import com.zero.dibreak.api.DiService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Jin_ on 2016/9/30
 * 邮箱：Jin_Zboy@163.com
 */

@Module
public class DiApiModule {

    private final boolean useRxJava;
    public DiApiModule(boolean useRxJava) {
        this.useRxJava = useRxJava;
    }

    @Provides
    protected DiService provideDjService() {
        return DiService.getInstance(useRxJava);
    }
}
