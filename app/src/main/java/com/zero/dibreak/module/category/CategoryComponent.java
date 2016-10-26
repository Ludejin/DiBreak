package com.zero.dibreak.module.category;

import com.zero.dibreak.di.DiFragment;

import dagger.Component;

/**
 * Created by Jin_ on 2016/10/26
 * 邮箱：Jin_Zboy@163.com
 */

@DiFragment
@Component(modules = CategoryModule.class)
public interface CategoryComponent {
    CategoryPresenter inject();
}
