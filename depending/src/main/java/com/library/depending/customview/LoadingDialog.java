package com.library.depending.customview;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.library.depending.R;


/**
 * 带有圆形加载进度的对话框
 */
public class LoadingDialog extends Dialog {
    private static final String TAG = "LoadingDialog";
    private Context context;
    private String tible;
    private int mImageId, id;
    private boolean mCancelable = true, isFrameAnim = false;
    private int scale = 4;
    private RotateAnimation mRotateAnimation;
    private float dialogBackground = 0.3f;
    private AnimationDrawable animationDrawable;

    public LoadingDialog(Context context) {
        this(context, "");
    }

    public LoadingDialog(Context context, String tible) {
        this(context, R.style.LoadingDialog, tible, R.mipmap.progress_1);
    }

    public LoadingDialog(Context context, int theme, String tible, int imageId) {
        super(context, theme);
        isFrameAnim = false;
        this.context = context;
        this.tible = tible;
        mImageId = imageId;
        initView();
    }

    /**
     *
     * 帧动画
     * @param context
     * @param theme
     * @param id   R.drawable.frame_anim
     */
    public LoadingDialog(Context context, int theme, @DrawableRes int id) {
        super(context, theme);
        this.context = context;
        this.id = id;
        isFrameAnim = true;

    }

    public boolean isCancelable() {
        return mCancelable;
    }

    public void setCancelable(boolean mCancelable) {
        this.mCancelable = mCancelable;

    }

    public void setScale(int scale) {
        this.scale = scale;

    }

    public void setBackgroundTransparency(float dialogBackground) {
        this.dialogBackground = dialogBackground;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setContentView(R.layout.dialog_loading);
        WindowManager windowManager = getWindow().getWindowManager();    // 设置窗口大小
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.alpha = dialogBackground;
        attributes.width = screenWidth / scale;
        attributes.height = attributes.width;
        getWindow().getAttributes().gravity = Gravity.CENTER;
        setCancelable(mCancelable);

        TextView tv_loading = findViewById(R.id.id_tv_loadingmsg);
        ImageView iv_loading = findViewById(R.id.loadingImageView);
        tv_loading.setText(tible);
        if (isFrameAnim) {
            setXml2FrameAnim2(id, iv_loading);
        } else {
            setRotateAnim(iv_loading);
        }

    }

    /**
     * 添加 旋转动画
     * @param iv_loading
     */
    public void setRotateAnim(ImageView iv_loading) {
        iv_loading.setImageResource(mImageId);
        iv_loading.measure(0, 0);
        mRotateAnimation = new RotateAnimation(0, 360, iv_loading.getMeasuredWidth() / 2, iv_loading.getMeasuredHeight() / 2);
        mRotateAnimation.setInterpolator(new LinearInterpolator());
        mRotateAnimation.setDuration(1000);
        mRotateAnimation.setRepeatCount(-1);
        iv_loading.startAnimation(mRotateAnimation);
    }

    /**
     * 通过XML添加帧动画方法二
     *
     * @param id         R.drawable.frame_anim
     * @param iv_loading
     */
    private void setXml2FrameAnim2(@DrawableRes int id, ImageView iv_loading) {

        // 通过逐帧动画的资源文件获得AnimationDrawable示例
        animationDrawable = (AnimationDrawable) context.getResources().getDrawable(id);
        iv_loading.setBackground(animationDrawable);
    }


}
