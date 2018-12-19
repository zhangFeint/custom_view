package com.library.depending.utils;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

/**
 * 功能：
 *
 * @author：zhangerpeng
 * @create：2018\12\19 0019 11:58
 * @version：2018 1.0
 * Created with IntelliJ IDEA
 */
public class ViewUtils {


    /**
     * Glide 加载图片
     * @param activity
     * @param imageView
     * @param path
     */
    public static void setImage(final Activity activity, @NonNull final ImageView imageView, String path,@Nullable Drawable drawable) {
        setImage(activity,imageView,path,drawable,drawable);
    }
    /**
     * Glide 加载图片
     * @param activity
     * @param imageView
     * @param path
     */
    public static void setImage(final Activity activity, @NonNull final ImageView imageView, String path,@Nullable Drawable placeholDerdrawable,@Nullable Drawable errorDrawable) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(placeholDerdrawable)
                .error(errorDrawable)
                .fallback(new ColorDrawable(Color.GRAY));

        Glide.with(activity)
                .load(path)
                .apply(requestOptions)
                .into(imageView);
    }

}
