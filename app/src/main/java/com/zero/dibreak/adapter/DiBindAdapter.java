package com.zero.dibreak.adapter;

import android.databinding.BindingAdapter;
import android.net.Uri;

import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by Jin_ on 2016/10/10
 * 邮箱：Jin_Zboy@163.com
 */

public class DiBindAdapter {
    @BindingAdapter(value = "imageUrl")
    public static void loadImageFromUrl(SimpleDraweeView imageView, String url) {
        Uri uri = Uri.parse(url);
        imageView.setImageURI(uri);
    }
}
