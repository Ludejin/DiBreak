package com.zero.dibreak.module.category;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;

import com.zero.dibreak.R;
import com.zero.dibreak.adapter.CategoryPagerAdapter;
import com.zero.dibreak.databinding.ActivityCategoryBinding;
import com.zero.dibreak.view.base.BackActivity;

/**
 * Created by Jin_ on 2016/10/16
 * 邮箱：Jin_Zboy@163.com
 */

public class CategoryActivity extends BackActivity {

    ActivityCategoryBinding mCategoryBinding;

    private CategoryPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        mPagerAdapter = new CategoryPagerAdapter(getSupportFragmentManager());
        mCategoryBinding.vpContainer.setAdapter(mPagerAdapter);
        mCategoryBinding.vpContainer.setOffscreenPageLimit(mPagerAdapter.getCount() - 1);

        mCategoryBinding.tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mCategoryBinding.tabLayout.setupWithViewPager(mCategoryBinding.vpContainer);
    }

    @Override
    protected Toolbar setToolbar() {
        return mCategoryBinding.incToolbar.toolbar;
    }

    @Override
    protected void setContentView() {
        mCategoryBinding = DataBindingUtil.setContentView(this, R.layout.activity_category);
    }
}
