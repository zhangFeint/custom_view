package com.library.depending.customview;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

import com.library.depending.R;


/**
 * 带有圆形加载进度的对话框
 */
public class CustomProgressDialog extends Dialog {

    public CustomProgressDialog(Context context) {
        super(context);
        setContentView(R.layout.custom_progress_dialog);
        getWindow().getAttributes().gravity = Gravity.CENTER;
        setCancelable(true);
    }

    public CustomProgressDialog(Context context, int theme) {
        super(context, theme);
        setContentView(R.layout.custom_progress_dialog);
        getWindow().getAttributes().gravity = Gravity.CENTER;
        setCancelable(true);
    }


    public void onWindowFocusChanged(boolean hasFocus) {
        ImageView imageView = findViewById(R.id.loadingImageView);
        if(imageView != null){
            AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
            animationDrawable.start();
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        TextView tvMsg = findViewById(R.id.id_tv_loadingmsg);
        if (tvMsg != null) {
            tvMsg.setText(title);
        }
    }

}
