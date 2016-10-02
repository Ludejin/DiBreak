package com.zero.dibreak.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by Jin_ on 2016/9/23
 * 邮箱：Jin_Zboy@163.com
 */

public class DiService {

    private static final String API_DEV_URL = "";
    private static final String API_PRODUCT_URL = "";

    private boolean IS_DEV = true;

    private static Retrofit mRetrofit;
    private DiApi mApi;

    private OkHttpClient getClient() {
        // log用拦截器
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

        // 开发模式记录整个body，否则只记录基本信息如返回200，http协议版本等
        if (IS_DEV) {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        }

        return new OkHttpClient.Builder()
                .addInterceptor(logging)
                // 连接超时时间设置
                .connectTimeout(10, TimeUnit.SECONDS)
                // 读取超时时间设置
                .readTimeout(10, TimeUnit.SECONDS)
                // 这里我们使用host name作为cookie保存的key
                .cookieJar(new CookieJar() {
                    private final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();

                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        cookieStore.put(HttpUrl.parse(url.host()), cookies);
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        List<Cookie> cookies = cookieStore.get(HttpUrl.parse(url.host()));
                        return cookies != null ? cookies : new ArrayList<Cookie>();
                    }
                })
                .build();
    }

    public DiService(boolean useRxJava) {

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(IS_DEV ? API_DEV_URL : API_PRODUCT_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(getClient());

        if (useRxJava) {
            builder.addCallAdapterFactory(RxJavaCallAdapterFactory.create());
        }
        mRetrofit = builder.build();

        mApi = mRetrofit.create(DiApi.class);
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final DiService RXINSTANCE = new DiService(true);
        private static final DiService INSTANCE = new DiService(false);
    }

    //获取单例
    public static DiService getInstance(boolean useRxJava) {
        if (useRxJava) {
            return SingletonHolder.RXINSTANCE;
        }
        return SingletonHolder.INSTANCE;
    }

    public DiApi getDiApi() {
        if (null == mApi) {
            mApi = mRetrofit.create(DiApi.class);
        }
        return mApi;
    }
}
