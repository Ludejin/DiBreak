package com.zero.dibreak.exception;

import android.content.Context;
import android.content.res.Resources;

import com.zero.dibreak.R;
import com.zero.dibreak.common.utils.StringUtils;

import java.net.SocketTimeoutException;

import retrofit2.adapter.rxjava.HttpException;

/**
 * Created by Jin_ on 2016/9/24
 * 邮箱：Jin_Zboy@163.com
 */

public class ErrorMessageFactory {
    private static final String TAG = "ErrorMessageFactory";

    private ErrorMessageFactory() {
        //empty
    }

    /**
     * Creates a String representing an error message.
     *
     * @param context   Context needed to retrieve string resources.
     * @param exception An exception used as a condition to retrieve the correct error message.
     * @return {@link String} an error message.
     */
    public static String create(Context context, Exception exception) {
        if (StringUtils.isNotEmpty(exception.getMessage())) {

        }

        String message = context.getString(R.string.exception_message_generic);
        if (exception instanceof SocketTimeoutException) {
            message = context.getString(R.string.exception_message_time_out);
        } else if (exception instanceof NetworkConnectionException) {
            message = context.getString(R.string.exception_message_no_connection);
        } else if (exception instanceof Resources.NotFoundException) {
            message = context.getString(R.string.exception_message_not_found);
        } else if (exception instanceof ResponseException) {
            message = exception.getMessage();
        } else if (exception instanceof HttpException) {
            message = exception.getMessage();
        }
        return message;
    }
}
