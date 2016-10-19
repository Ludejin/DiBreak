package com.zero.progressstatelayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by Jin_ on 2016/10/19
 * 邮箱：Jin_Zboy@163.com
 */

public class ProgressStateLayout extends FrameLayout {
    private Context mContext;
    private View mEmptyView;
    private View mBindView;
    private View mErrorView;
    private AppCompatButton mBtnReset;
    private View mLoadingView;
    private AppCompatTextView mEmptyText;
    private AppCompatTextView tvLoadingText;

    public ProgressStateLayout(Context context) {
        this(context, null);
    }

    public ProgressStateLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressStateLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;

        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        //居中
        params.gravity = Gravity.CENTER;

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.EmptyLayout, 0, 0);
        //数据为空时的布局
        int emptyLayout = ta.getResourceId(R.styleable.EmptyLayout_elEmptyLayout, R.layout.view_empty);
        mEmptyView = View.inflate(context, emptyLayout, null);
        mEmptyText = (AppCompatTextView) mEmptyView.findViewById(R.id.tvEmptyText);
        addView(mEmptyView, params);

        //加载中的布局
        int loadingLayout = ta.getResourceId(R.styleable.EmptyLayout_elLoadingLayout, R.layout.view_loading);
        mLoadingView = View.inflate(context, loadingLayout, null);
        tvLoadingText = (AppCompatTextView) mLoadingView.findViewById(R.id.tvLoadingText);
        addView(mLoadingView, params);

        //错误时的布局
        int errorLayout = ta.getResourceId(R.styleable.EmptyLayout_elErrorLayout, R.layout.view_error);
        mErrorView = View.inflate(context, errorLayout, null);
        mBtnReset = (AppCompatButton) mErrorView.findViewById(R.id.btnRetry);
        addView(mErrorView, params);

        //全部隐藏
        setGone();
    }

    public void setEmptyView(int resId) {
        setEmptyView(View.inflate(mContext, resId, null));
    }

    public void setEmptyView(View v) {
        if (indexOfChild(mEmptyView) != -1) {
            removeView(mEmptyView);
        }
        mEmptyView = v;
        addView(mEmptyView);
        setGone();
    }

    public void bindView(View view) {
        mBindView = view;
    }

    public void showEmpty(String emptyText) {
        if (mBindView != null) mBindView.setVisibility(View.GONE);
        setGone();
        mEmptyView.setVisibility(View.VISIBLE);
        mEmptyText.setText(emptyText);
    }

    public void showError() {
        showError(null);
    }

    public void showError(String text) {
        if (mBindView != null) mBindView.setVisibility(View.GONE);
        if (!TextUtils.isEmpty(text)) mBtnReset.setText(text);
        setGone();
        mErrorView.setVisibility(View.VISIBLE);
    }

    public void showLoading(String text) {
        if (mBindView != null) mBindView.setVisibility(View.GONE);
        if (!TextUtils.isEmpty(text)) tvLoadingText.setText(text);
        setGone();
        mLoadingView.setVisibility(View.VISIBLE);
    }

    public void showLoading() {
        showLoading(null);
    }

    public void setOnButtonClick(OnClickListener listener) {
        mBtnReset.setOnClickListener(listener);
    }

    /**
     * 全部隐藏
     */
    private void setGone() {
        mEmptyView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.GONE);
        mLoadingView.setVisibility(View.GONE);
    }

    public void showSuccess() {
        if (mBindView != null) mBindView.setVisibility(View.VISIBLE);
        setGone();
    }
}
