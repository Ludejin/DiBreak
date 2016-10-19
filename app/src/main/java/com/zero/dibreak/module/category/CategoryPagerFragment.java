package com.zero.dibreak.module.category;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zero.dibreak.R;
import com.zero.dibreak.adapter.MultiTypeAdapter;
import com.zero.dibreak.app.DiApplication;
import com.zero.dibreak.data.RepositoryUtils;
import com.zero.dibreak.databinding.FragmentCategoryBinding;
import com.zero.dibreak.domain.interactor.DefaultSubscriber;
import com.zero.dibreak.domain.model.base.BaseResponse;
import com.zero.dibreak.domain.model.response.ItemInfo;
import com.zero.dibreak.module.detail.DetailActivity;
import com.zero.dibreak.view.base.LazyFragment;
import com.zero.loadmore.LoadMoreListener;
import com.zero.loadmore.LoadMoreRecyclerView;
import com.zero.progressstatelayout.ProgressStateLayout;

/**
 * Created by Jin_ on 2016/10/16
 * 邮箱：Jin_Zboy@163.com
 */

public class CategoryPagerFragment extends LazyFragment implements SwipeRefreshLayout.OnRefreshListener,
        LoadMoreListener {

    private static final String TAG = "CategoryPagerFragment";

    private static final String CATEGORY_TYPE = "category_type";
    private static final int VIEW_TYPE_WITH_IMG = 0;
    private static final int VIEW_TYPE_NORMAL = 1;

    FragmentCategoryBinding mCategoryBinding;

    private int mCategoryType = 0;
    private int mPage = 1;
    private boolean isRefresh = true;

    private MultiTypeAdapter mMultiTypeAdapter;

    /**
     * 组件
     */
    private LoadMoreRecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefreshLayout;
    private ProgressStateLayout mStateLayout;

    public static CategoryPagerFragment newInstance(int position) {
        CategoryPagerFragment fragment = new CategoryPagerFragment();
        Bundle args = new Bundle();
        args.putInt(CATEGORY_TYPE, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCategoryType = getArguments().getInt(CATEGORY_TYPE);
        }
    }

    private void init() {

        mRecyclerView = mCategoryBinding.recycleView;
        mRefreshLayout = mCategoryBinding.refLayout;
        mStateLayout = mCategoryBinding.stateLayout;
        mStateLayout.bindView(mRecyclerView);

        mMultiTypeAdapter = new MultiTypeAdapter(_mActivity);
        mMultiTypeAdapter.addViewTypeToLayoutMap(VIEW_TYPE_NORMAL, R.layout.item_category);
        mMultiTypeAdapter.addViewTypeToLayoutMap(VIEW_TYPE_WITH_IMG, R.layout.item_category_img);

        mMultiTypeAdapter.setPresenter(new MultiTypeAdapter.Presenter<ItemInfo>() {
            @Override
            public void onItemClick(ItemInfo itemInfo) {
                Intent intent = new Intent();
                intent.setClass(_mActivity, DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_URL,
                        itemInfo.getUrl());
                startActivity(intent);
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));

        mRecyclerView.setAdapter(mMultiTypeAdapter);

        mRecyclerView.setCanloadMore(true);
        mRecyclerView.setLoadMoreListener(this);

        mRefreshLayout.setOnRefreshListener(this);
    }

    private String getCategoryTypeName(int position) {
        String categoryName = "";
        switch (position) {
            case 0:
                categoryName = "all";
                break;
            case 1:
                categoryName = "Android";
                break;
            case 2:
                categoryName = "iOS";
                break;
            case 3:
                categoryName = "前端";
                break;
            case 4:
                categoryName = "福利";
                break;
        }
        return categoryName;
    }

    private MultiTypeAdapter.MultiViewTyper getMultiViewTyper() {
        return new MultiTypeAdapter.MultiViewTyper() {
            @Override
            public int getViewType(Object item) {
                if (item instanceof ItemInfo) {
                    if (((ItemInfo) item).getImages().size() > 0) {
                        return VIEW_TYPE_WITH_IMG;
                    }
                    return VIEW_TYPE_NORMAL;
                }
                return 0;
            }
        };
    }

    /**
     * 获取数据
     *
     * @param page     页码
     * @param pageSize 一页显示的数据
     * @see com.zero.dibreak.api.DiApi#getCategoryList(String, int, int)
     */
    private void getData(int pageSize, int page) {
        DiApplication.getInstance().getApplicationComponent().getDiService()
                .getDiApi().getCategoryList(getCategoryTypeName(mCategoryType), pageSize, page)
                .compose(RepositoryUtils.<BaseResponse<ItemInfo>>handleData())
                .subscribe(new DefaultSubscriber<BaseResponse<ItemInfo>>(_mActivity) {
                    @Override
                    public void onNext(BaseResponse<ItemInfo> itemInfoBaseResponse) {
                        mStateLayout.showSuccess();
                        if (isRefresh) {
                            if (0 == itemInfoBaseResponse.getData().size()) {
                                mStateLayout.showEmpty("没有数据");
                                return;
                            }
                            mRefreshLayout.setRefreshing(false);
                            mMultiTypeAdapter.set(itemInfoBaseResponse.getData(), getMultiViewTyper());
                        } else {
                            if (0 == itemInfoBaseResponse.getData().size()) {
                                mRecyclerView.loadMoreEnd();
                            } else {
                                mMultiTypeAdapter.addAll(itemInfoBaseResponse.getData(), getMultiViewTyper());
                                mRecyclerView.loadMoreComplete();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (isRefresh) {
                            mStateLayout.showError();
                        }
                    }
                });
    }

    @Override
    protected void fetchData() {
        mStateLayout.showLoading();
        onRefresh();
    }

    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mCategoryBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_category, container, false);
        init();
        initEvent();
        return mCategoryBinding.getRoot();
    }

    private void initEvent() {

    }

    @Override
    public void onRefresh() {
        mPage = 1;
        isRefresh = true;
        getData(15, mPage);
    }

    @Override
    public void onLoadMore() {
        isRefresh = false;
        getData(15, ++mPage);
    }
}
