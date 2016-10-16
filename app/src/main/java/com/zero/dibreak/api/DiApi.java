package com.zero.dibreak.api;

import com.zero.dibreak.domain.model.base.BaseResponse;
import com.zero.dibreak.domain.model.response.ItemInfo;
import com.zero.dibreak.domain.model.response.Sister;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Jin_ on 2016/9/23
 * 邮箱：Jin_Zboy@163.com
 */

public interface DiApi {
    @GET("random/data/福利/{pageSize}")
    Observable<BaseResponse<Sister>> getSister(@Path("pageSize") int pageSize);

    @GET("data/{category}/{pageSize}/{page}")
    Observable<BaseResponse<ItemInfo>> getCategoryList(@Path("category") String category,
                                                       @Path("pageSize") int pageSize,
                                                       @Path("page") int page);
}
