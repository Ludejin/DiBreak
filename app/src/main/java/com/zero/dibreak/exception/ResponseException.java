package com.zero.dibreak.exception;


import com.zero.dibreak.domain.model.base.BaseResponse;

/**
 * Created by Jin_ on 2016/9/23
 * 邮箱：Jin_Zboy@163.com
 */

public class ResponseException extends Exception {
    private static final String TAG = "ResponseException";

    public static final int STATUS_CODE_SUCCESS =  0;
    public static final int STATUS_CODE_FAIL    = -1;

    private final int mStatusCode;

    /**
     * @param response 全局响应格式
     */
    public ResponseException(BaseResponse response) {
        super(response.getMessage());
        mStatusCode = response.getCode();
        /**
         *
         * 根据StatusCode处理错误信息，什么StatusCode提示什么错误
         *
         */
    }

    /**
     * @return 全局响应代码，非0（0为成功）
     */
    public int getStatusCode() {
        return mStatusCode;
    }
}
