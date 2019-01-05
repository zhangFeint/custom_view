package com.library.depending.viewutils;

import android.app.Activity;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

public class GridViewUtils {
    private static GridViewUtils gridViewUtils;

    /**
     * 单例模式
     */
    public static GridViewUtils getInstance() {
        if (gridViewUtils == null) {
            gridViewUtils = new GridViewUtils();
        }
        return gridViewUtils;
    }

    /**
     * 解决GridView 在滚动条只显示一行
     *
     * @param gridView
     * @param number
     */
    public void processGridViewHeight(GridView gridView, int number) {
        //获取listview的适配器
        ListAdapter listAdapter = gridView.getAdapter();
        int numCollumn = number;
        //item的高度
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            if (i % numCollumn == 0) {
                View listItem = listAdapter.getView(i, null, gridView);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }
        }
        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight;
        gridView.setLayoutParams(params);
    }

    /**
     * @param activity
     * @param gridView
     * @param size                   数据大小
     * @param itemsHorizontalSpacing 列表项水平间距  10
     */
    //gridview横向布局方法
    public void drawGridViewHorizontalLayout(Activity activity, GridView gridView, int size, int itemwidth, int itemsHorizontalSpacing) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;
        int itemWidth = (int) (itemwidth * density);
        int allWidth = (int) (itemwidth * size * density) + (size * itemsHorizontalSpacing);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(allWidth, LinearLayout.LayoutParams.FILL_PARENT);
        gridView.setLayoutParams(params);// 设置GirdView布局参数
        gridView.setColumnWidth(itemWidth);// 列表项宽
        gridView.setHorizontalSpacing(itemsHorizontalSpacing);// 列表项水平间距
        gridView.setStretchMode(GridView.NO_STRETCH);
        gridView.setNumColumns(size);//总长度
    }
}
