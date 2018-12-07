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
    boolean iLoading = true, iRefresh = false;

    //设置加载更多动画
    public void setiLoading(boolean iLoading) {
        this.iLoading = iLoading;
    }

    //设置刷新数据动画
    public void setiRefresh(boolean iRefresh) {
        this.iRefresh = iRefresh;
    }

    IsLoadingListener isLoadingListener;


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

    public void setOnLoadingListener(IsLoadingListener isLoadingListener) {
        this.isLoadingListener = isLoadingListener;
    }

    public interface IsLoadingListener {

        void onRefresh();

        void onLoad();
    }

    public void removeFooterView() {
        isLoading = false;
        removeFooterView(footView);
    }

    public void removeHeaderView() {
        isRefresh = false;
        removeHeaderView(footView);
    }
}
