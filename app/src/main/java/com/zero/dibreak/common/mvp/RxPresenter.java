package com.zero.dibreak.common.mvp;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Jin_ on 2016/9/24
 * 邮箱：Jin_Zboy@163.com
 */

public class RxPresenter<T extends BaseView> implements BasePresenter<T> {

    protected T mView;
    protected CompositeSubscription mSubscriptions;

    private void unSubscribe() {
        if (mSubscriptions != null) {
            mSubscriptions.unsubscribe();
        }
    }

    protected void addSubscribe(Subscription subscription) {
        if (mSubscriptions == null) {
            mSubscriptions = new CompositeSubscription();
        }
        mSubscriptions.add(subscription);
    }

    @Override
    public void attachView(T view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
        unSubscribe();
    }
}
