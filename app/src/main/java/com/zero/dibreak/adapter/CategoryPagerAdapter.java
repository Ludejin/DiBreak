package com.zero.dibreak.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.zero.dibreak.module.category.CategoryPagerFragment;

/**
 * Created by Jin_ on 2016/10/16
 * 邮箱：Jin_Zboy@163.com
 */

public class CategoryPagerAdapter extends FragmentStatePagerAdapter {

    private String[] mTitles = {"全部", "Android", "IOS", "前端", "福利"};

    public CategoryPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return CategoryPagerFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
