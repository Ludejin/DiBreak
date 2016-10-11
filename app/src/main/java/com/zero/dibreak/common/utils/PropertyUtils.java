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

import android.text.TextUtils;
import android.util.Log;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;

/**
 * Utils to access android system properties.
 *
 * @author markzhai on 16/3/5
 */
public final class PropertyUtils {

    public final static String PROPERTY_DNS_PRIMARY = "net.dns1";
    public final static String PROPERTY_DNS_SECONDARY = "net.dns2";
    private final static String TAG = "PropertyUtils";
    private final static String CMD_GET_PROP = "getprop";

    private static Class sClassSystemProperties;
    private static Method sMethodGetString;

    static {
        try {
            sClassSystemProperties = Class.forName("android.os.SystemProperties");
            sMethodGetString = sClassSystemProperties.getDeclaredMethod("get", String.class, String.class);

        } catch (Throwable e) {
            Log.w(TAG, e);
        }
    }

    private PropertyUtils() {
        // static usage.
    }

    /**
     * Get property of corresponding key.
     *
     * @param key      Property key.
     * @param defValue Default value.
     * @return Property of corresponding key.
     */
    public static String get(String key, String defValue) {
        if (TextUtils.isEmpty(key)) {
            return defValue;
        }
        String value = getWithReflect(key, null);
        if (TextUtils.isEmpty(value)) {
            value = getWithCmd(key, null);
        }
        if (TextUtils.isEmpty(value)) {
            value = defValue;
        }
        return value;
    }

    /**
     * Get property of corresponding key quickly (but provide less validity than {@link #get(String, String)}).
     *
     * @param key      Property key.
     * @param defValue Default value.
     * @return Property of corresponding key.
     */
    public static String getQuickly(String key, String defValue) {
        if (TextUtils.isEmpty(key)) {
            return defValue;
        }
        return getWithReflect(key, defValue);
    }

    private static String getWithReflect(String key, String defValue) {
        if (sClassSystemProperties == null || sMethodGetString == null) {
            return defValue;
        }
        String value = defValue;
        try {
            value = (String) sMethodGetString.invoke(sClassSystemProperties, key, defValue);

        } catch (Throwable e) {
            Log.w(TAG, e);
        }
        return value;
    }

    private static String getWithCmd(String key, String defValue) {
        String value = defValue;
        try {
            Process process = Runtime.getRuntime().exec(CMD_GET_PROP + " " + key);
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
                String readValue = builder.toString();
                if (!TextUtils.isEmpty(readValue)) {
                    // if read value is valid, use it.
                    value = readValue;
                }

            } catch (IOException e) {
                //
            } finally {
                if (reader != null) {
                    reader.close();
                }
            }
            // clean job.
            process.destroy();

        } catch (Throwable e) {
            Log.w(TAG, e);
        }
        return value;
    }
}

