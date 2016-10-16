package com.zero.dibreak.view.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.zero.dibreak.R;


/**
 * Created by Jin_ on 2016/9/23
 * 邮箱：Jin_Zboy@163.com
 */

public abstract class BackActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();

        Toolbar mToolbar = setToolbar();
        if (null != mToolbar) {
            setBackToolbar(mToolbar);
        }
    }

    private void setBackToolbar(Toolbar toolbar) {
        toolbar.setTitle(getTitle());
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected abstract Toolbar setToolbar();
    protected abstract void setContentView();

}
