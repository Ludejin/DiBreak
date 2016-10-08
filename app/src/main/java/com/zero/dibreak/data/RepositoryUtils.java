package com.zero.dibreak.data;

import android.accounts.NetworkErrorException;
import android.util.Log;

import com.zero.dibreak.domain.model.base.BaseResponse;
import com.zero.dibreak.exception.ResponseException;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Jin_ on 2016/10/8
 * 邮箱：Jin_Zboy@163.com
 */

public class RepositoryUtils {

    private static final String TAG = "RepositoryUtils";

    /**
     * 处理响应
     * @param <T>
     * @return
     */
    public static <T extends BaseResponse> Observable.Transformer<T, T> handleData() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable.flatMap(new Func1<T, Observable<T>>() {
                    @Override
                    public Observable<T> call(T response) {
                        if (null == response) {
                            return Observable.error(new NetworkErrorException());
                        } else if (response.isSuccess()) {
                            return Observable.just(response);
                        } else {
                            Log.e(TAG, response.getMessage());
                            return Observable.error(new ResponseException(response));
                        }
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
