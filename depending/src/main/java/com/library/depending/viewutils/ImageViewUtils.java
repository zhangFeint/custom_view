package com.library.depending.viewutils;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class ImageViewUtils {
    private static ImageViewUtils imageViewUtils;

    /**
     * 单例模式
     */
    public static ImageViewUtils getInstance() {
        if (imageViewUtils == null) {
            imageViewUtils = new ImageViewUtils();
        }
        return imageViewUtils;
    }

    /**
     * 改变矢量图形的颜色
     *
     * @param activity
     * @param resId    图片id
     * @param colour   颜色
     * @return
     */
    public Drawable resetVectorgraphColor(Activity activity, @DrawableRes int resId, int colour) {
        VectorDrawableCompat vectorDrawableCompat4 = VectorDrawableCompat.create(activity.getResources(), resId, activity.getTheme());
        vectorDrawableCompat4.setTint(activity.getResources().getColor(colour)); //你需要改变的颜色
        return vectorDrawableCompat4;
    }

    /**
     * Glide 加载图片
     *
     * @param activity
     * @param imageView
     * @param path
     */
    public static void setImage(final Activity activity, @NonNull final ImageView imageView, String path, @Nullable Drawable drawable) {
        setImage(activity, imageView, path, drawable, drawable);
    }

    /**
     * Glide 加载图片
     *
     * @param activity
     * @param imageView
     * @param path
     */
    public static void setImage(final Activity activity, @NonNull final ImageView imageView, String path, @Nullable Drawable placeholDerdrawable, @Nullable Drawable errorDrawable) {
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
