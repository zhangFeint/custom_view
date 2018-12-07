package com.library.depending.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.library.depending.R;
import com.library.depending.baseview.BaseActivity;

public class ImageActivity extends BaseActivity {
    private ImageView iv_back, iv_img;
    public static final String KEY_IMG = "position"; //第几张图片
    public static void show(Context context,String imgPath) {
        Intent intent = new Intent(context, ImageActivity.class);
        intent.putExtra(KEY_IMG, imgPath);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        initViews();
        initListener();
    }

    @Override
    public void initViews() {
        iv_back = findViewById(R.id.iv_back);
        iv_img = findViewById(R.id.iv_img);
        Glide.with(this).load(getIntent().getStringExtra(KEY_IMG)).into(iv_img);
    }

    @Override
    public void initListener() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
