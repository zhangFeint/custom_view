package com.library.depending.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

import com.library.depending.R;


@SuppressLint("RestrictedApi")
public class ListViewLoadMore extends ListView implements OnScrollListener {

    View footView;
    int totalItemCount, firstItemCount = 0; // 此刻一共有多少项
    boolean isLoading = false, isRefresh = false;
    //  是否开启上拉，是否开启下拉
    boolean iLoading = true, iRefresh = false;

    //设置开启上拉
    public void setiLoading(boolean iLoading) {
        this.iLoading = iLoading;
    }

    //设置开启下拉
    public void setiRefresh(boolean iRefresh) {
        this.iRefresh = iRefresh;
    }



    public ListViewLoadMore(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    public ListViewLoadMore(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ListViewLoadMore(Context context) {
        super(context);
        initView(context);
    }

    /**
     * 初始化footView
     *
     * @param context
     */
    void initView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        footView = inflater.inflate(R.layout.loader_data_layout, null);
        setOnScrollListener(this);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (iRefresh) {
            if (view.getLastVisiblePosition() == view.getCount() - 1 && scrollState == SCROLL_STATE_IDLE) {
                if (!isLoading) {
                    isLoading = true;
                    addFooterView(footView);
                    isLoadingListener.onLoad();
                }
            }
        }
        if (iLoading) {
            if (view.getFirstVisiblePosition() == firstItemCount && scrollState == SCROLL_STATE_IDLE) {
                if (!isRefresh) {
                    isRefresh = true;
                    addHeaderView(footView);
                    isLoadingListener.onRefresh();
                }
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.totalItemCount = totalItemCount;
    }

    /**
     * 设置接口
     * @param isLoadingListener
     */
    public void setOnLoadingListener(IsLoadingListener isLoadingListener) {
        this.isLoadingListener = isLoadingListener;
    }
    IsLoadingListener isLoadingListener;

    /**
     * 监听
     */
    public interface IsLoadingListener {

        void onRefresh();

        void onLoad();
    }

    /**
     * 关闭动效
     */
    public void removeFooterView() {
        isLoading = false;
        removeFooterView(footView);
    }

    /**
     * 关闭动效
     */
    public void removeHeaderView() {
        isRefresh = false;
        removeHeaderView(footView);
    }
}
