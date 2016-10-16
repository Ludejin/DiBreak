package com.zero.dibreak.view.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by Jin_ on 2016/9/23
 * 邮箱：Jin_Zboy@163.com
 */

public abstract class BaseFragment extends Fragment {

    protected BaseActivity _mActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof BaseActivity) {
            this._mActivity = (BaseActivity) activity;
        } else {
            throw new RuntimeException(activity.toString() + "must extends BaseActivity!");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return initView(inflater, container, savedInstanceState);
    }

    protected abstract View initView(LayoutInflater inflater,
                                     @Nullable ViewGroup container,
                                     @Nullable Bundle savedInstanceState);

}
