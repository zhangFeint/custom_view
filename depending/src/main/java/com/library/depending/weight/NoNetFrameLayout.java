package com.library.depending.weight;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.library.depending.R;

/**
 * @author：zhangerpeng
 * 版本：
 * 日期：2019/5/7 23:26
 * 描述：
 *   断网页面
 */
public class NoNetFrameLayout extends FrameLayout {

    private Context context;
    private View view404;
    private Button btnRefresh;

    public NoNetFrameLayout(@NonNull Context context) {
        this(context, null);
    }

    public NoNetFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        view404 = LayoutInflater.from(context).inflate(R.layout.view_404, null, false);
        addView(view404);
    }

    /**
     * 设置404显示状态
     *
     * @param visiable
     */
    public void set404Visiable(boolean visiable) {
        if (visiable) {
            view404.setVisibility(View.VISIBLE);
            bringChildToFront(view404);
        } else {
            view404.setVisibility(View.GONE);
        }
    }

    /**
     * 404刷新数据回调
     *
     * @param listener
     */
    public void setRefreshListener(OnClickListener listener) {
        view404.setOnClickListener(listener);
    }
}
