package com.zero.dibreak.data.repository;

import android.util.Log;

import com.zero.dibreak.api.DiService;
import com.zero.dibreak.data.source.ItemInfoSource;
import com.zero.dibreak.domain.model.base.BaseResponse;
import com.zero.dibreak.domain.model.response.ItemInfo;

import javax.inject.Singleton;

import rx.Observable;

/**
 * Created by Jin_ on 2016/10/26
 * 邮箱：Jin_Zboy@163.com
 */

@Singleton
public class ItemInfoRepository implements ItemInfoSource {
    private static final String TAG = "ItemInfoRepository";

    private DiService mService;

    public ItemInfoRepository(DiService service) {
        mService = service;
    }

    @Override
    public Observable<BaseResponse<ItemInfo>> getItems(String category, int pageSize, int page) {
        Observable<BaseResponse<ItemInfo>> from = mService.getDiApi()
                .getCategoryList(category,pageSize,page);
        Log.i(TAG,"来自网络");
        return from;
    }
}
