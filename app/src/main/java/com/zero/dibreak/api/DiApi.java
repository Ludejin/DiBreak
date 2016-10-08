package com.zero.dibreak.api;

import com.zero.dibreak.domain.model.base.BaseResponse;
import com.zero.dibreak.domain.model.response.Sister;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Jin_ on 2016/9/23
 * 邮箱：Jin_Zboy@163.com
 */

public interface DiApi {
    @GET("random/data/福利/" + 10)
    Observable<BaseResponse<Sister>> getSister();
}
