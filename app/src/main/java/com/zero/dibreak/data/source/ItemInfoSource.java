package com.zero.dibreak.data.source;

import com.zero.dibreak.domain.model.base.BaseResponse;
import com.zero.dibreak.domain.model.response.ItemInfo;

import rx.Observable;

/**
 * Created by Jin_ on 2016/10/26
 * 邮箱：Jin_Zboy@163.com
 */

public interface ItemInfoSource {
    Observable<BaseResponse<ItemInfo>> getItems(String category, int pageSize, int page);
}
