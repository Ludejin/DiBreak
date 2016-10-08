package com.zero.dibreak.exception;


import com.zero.dibreak.domain.model.base.BaseResponse;

/**
 * Created by Jin_ on 2016/9/23
 * 邮箱：Jin_Zboy@163.com
 */

public class ResponseException extends Exception {
    private static final String TAG = "ResponseException";

    public static final int STATUS_CODE_SUCCESS = 0;

    public static final int ERROR_CODE_NEED_LOGIN = -1000;
    public static final int ERROR_CODE_NEED_PERFECT_PROFILE = -1010;
    public static final int ERROR_CODE_NEED_THIRD_PARTY_BIND = -1020;

    private final int mStatusCode;

    /**
     * @param response 全局响应格式
     */
    public ResponseException(BaseResponse response) {
        super(response.getMessage());
        mStatusCode = response.getCode();

        if (mStatusCode == ERROR_CODE_NEED_PERFECT_PROFILE) {
            try {

            } catch (ClassCastException e) {
//                Logger.e(TAG, e);
            }
        }
    }

    /**
     * -1	    普通异常，详见 status_msg 字段描述
     * -1000	用户登录凭证不合法，请先登录或重新登录
     * -1010	请前往完善用户信息（昵称、头像）
     * -1020	第三方账号登陆后，请前往绑定官方账号（详见文档）
     * -9990	APPID 不合法
     * -9991	APPID 对应的应用信息不存在，请联系管理员
     * -9992	APPVER 不合法
     * -9999	应用传输数据解密失败，请联系管理员
     * -999	    未知的其他异常
     * -404	    指定目标不存在或已删除
     * -2001	请输入正确的图形验证码
     *
     * @return 全局响应代码，非0（0为成功）
     */
    public int getStatusCode() {
        return mStatusCode;
    }
}
