package com.zero.dibreak.module.category;

import android.content.Context;

import com.zero.dibreak.api.DiService;
import com.zero.dibreak.common.mvp.RxPresenter;
import com.zero.dibreak.data.RepositoryUtils;
import com.zero.dibreak.data.repository.ItemInfoRepository;
import com.zero.dibreak.domain.interactor.DefaultSubscriber;
import com.zero.dibreak.domain.model.base.BaseResponse;
import com.zero.dibreak.domain.model.response.ItemInfo;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by Jin_ on 2016/10/26
 * 邮箱：Jin_Zboy@163.com
 */

public class CategoryPresenter extends RxPresenter<CategoryContract.View> implements
        CategoryContract.Presenter<CategoryContract.View> {

    private DiService mService;
    private Context mContext;
    private ItemInfoRepository mRepository;

    @Inject
    public CategoryPresenter(DiService service, Context context) {
        mService = service;
        mContext = context;
        mRepository = new ItemInfoRepository(mService);
    }

    @Override
    public void getData(String category, int pageSize, int page) {
        Subscription subscription = mRepository.getItems(category, pageSize, page)
                .compose(RepositoryUtils.<BaseResponse<ItemInfo>>handleData())
                .subscribe(new DefaultSubscriber<BaseResponse<ItemInfo>>(mContext) {
                    @Override
                    public void onNext(BaseResponse<ItemInfo> response) {
                        mView.loadData(response.getData());
                    }
                });
        addSubscribe(subscription);
    }
}
