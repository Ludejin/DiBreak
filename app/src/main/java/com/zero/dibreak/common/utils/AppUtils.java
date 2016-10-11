/*
 * The GPL License (GPL)
 *
 * Copyright (c) 2016 MarkZhai (http://zhaiyifan.cn)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.zero.dibreak.common.utils;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;

import java.util.List;

/**
 * app信息获取
 * <p>
 * Created by zhaiyifan on 2015/7/31.
 */
public final class AppUtils {

    private static Bundle sOwnAppMetaInfo;

    private AppUtils() {
        // static usage.
    }

    public static boolean isForeground(Context context) {
        if (isScreenLocked(context)) {
            return false;
        }
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> a = mActivityManager.getRunningTasks(1);
        return context.getPackageName().equals(a.get(0).baseActivity.getPackageName())
                && context.getPackageName().equals(a.get(0).topActivity.getPackageName());
    }

    private static boolean isScreenLocked(Context context) {
        KeyguardManager mKeyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        return mKeyguardManager.inKeyguardRestrictedInputMode();
    }

    public static int getVersionCode(Context context) {
        if (context == null) {
            return 0;
        }
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (Throwable e) {
            return 0;
        }
    }

    /**
     * 获取带有指定配置信息的ApplicationInfo
     *
     * @param flags 比如 {@link PackageManager#GET_META_DATA}
     * @see #getSimpleAppInfo(Context)
     * @see <a href="https://code.google.com/p/android/issues/detail?id=37968" >why this method</a>
     */
    public static ApplicationInfo getAppInfoWithFlags(Context ctx, int flags) {
        try {
            return ctx == null ? null : ctx.getPackageManager().getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取基本的applicationInfo
     *
     * @see #getAppInfoWithFlags(Context, int)
     */
    public static ApplicationInfo getSimpleAppInfo(Context ctx) {
        return ctx == null ? null : ctx.getApplicationInfo();
    }

    /**
     * Check whether corresponding package is installed.
     *
     * @param context     Application context.
     * @param packageName Package name.
     * @return Whether corresponding package is installed.
     */
    public static boolean isPackageInstalled(Context context, String packageName) {
        if (!TextUtils.isEmpty(packageName)) {
            try {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
                return packageInfo != null;
            } catch (PackageManager.NameNotFoundException e) {
                // ignore.
            }
        }
        return false;
    }

    /**
     * Get application info of own package.
     *
     * @param context Application context.
     * @return Application info of own package.
     */
    public static Bundle getApplicationMetaInfo(Context context) {
        if (sOwnAppMetaInfo == null) {
            synchronized (AppUtils.class) {
                if (sOwnAppMetaInfo == null) {
                    sOwnAppMetaInfo = getApplicationMetaInfo(context, context.getPackageName());
                }
            }
        }
        return sOwnAppMetaInfo != null ? new Bundle(sOwnAppMetaInfo) : null;
    }

    /**
     * Get application meta info of corresponding package.
     *
     * @param context     Application context.
     * @param packageName Package name.
     * @return Application meta info of corresponding package.
     */
    public static Bundle getApplicationMetaInfo(Context context, String packageName) {
        ApplicationInfo appInfo = packageName.equals(context.getPackageName()) ?
                context.getApplicationInfo() : null;
        Bundle metaInfo = appInfo != null ? appInfo.metaData : null;
        if (metaInfo == null) {
            try {
                appInfo = context.getPackageManager().getApplicationInfo(
                        packageName, PackageManager.GET_META_DATA);
                metaInfo = appInfo.metaData;
            } catch (Throwable e) {
                // ignore.
            }
        }
        return metaInfo;
    }
}
