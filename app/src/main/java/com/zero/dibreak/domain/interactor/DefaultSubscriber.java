package com.zero.dibreak.domain.interactor;

import android.content.Context;
import android.util.Log;

import com.zero.dibreak.common.utils.ToastUtils;
import com.zero.dibreak.domain.exception.DefaultErrorBundle;
import com.zero.dibreak.domain.exception.IErrorBundle;
import com.zero.dibreak.exception.ErrorMessageFactory;
import com.zero.dibreak.exception.ResponseException;

import rx.Subscriber;

/**
 * Created by Jin_ on 2016/9/24
 * 邮箱：Jin_Zboy@163.com
 */

public class DefaultSubscriber<T> extends Subscriber<T> {

    private static final String TAG = "DefaultSubscriber";

    private Context mContext;

    public DefaultSubscriber(Context context) {
        mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        if (handleCommonResponseError((Exception) e)) {
            if (e != null && e.getMessage() != null) {
                Log.w(TAG, e.getMessage());
            }
            showErrorMessage(new DefaultErrorBundle((Exception) e));
        }
    }

    @Override
    public void onNext(T t) {

    }

    protected boolean handleCommonResponseError(Exception exception) {
        boolean handled = false;
        if (exception instanceof ResponseException) {
            ResponseException responseException = (ResponseException) exception;
            switch (responseException.getStatusCode()) {
                case ResponseException.ERROR_CODE_NEED_LOGIN:
                    /**
                     *  session失效，重新登录
                     */
                    handled = true;
                    break;
                case ResponseException.ERROR_CODE_NEED_PERFECT_PROFILE:

                    handled = true;
                    break;
                case ResponseException.ERROR_CODE_NEED_THIRD_PARTY_BIND:
                    handled = true;
                    break;
            }
        }
        handled = true;
        return handled;
    }

    protected void showErrorMessage(IErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(getContext(), errorBundle.getException());
        showErrorMessage(errorMessage);
    }

    private void showErrorMessage(String errorMessage) {
        ToastUtils.show(getContext(), errorMessage);
    }
}
