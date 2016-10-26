package com.zero.dibreak.module.category;

import com.zero.dibreak.common.mvp.BasePresenter;
import com.zero.dibreak.common.mvp.BaseView;
import com.zero.dibreak.domain.model.response.ItemInfo;

import java.util.List;

/**
 * Created by Jin_ on 2016/10/26
 * 邮箱：Jin_Zboy@163.com
 */

public interface CategoryContract {

    interface Presenter<T> extends BasePresenter<T> {
        void getData(String category, int pageSize, int page);
    }

    interface View extends BaseView {
        void loadData(List<ItemInfo> items);
    }
}
