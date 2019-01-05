package com.library.depending.viewutils;

import android.view.View;
import android.widget.ScrollView;

public class ScrollViewUtils {
    private static ScrollViewUtils scrollViewUtils;

    /**
     * 单例模式
     */
    public static ScrollViewUtils getInstance() {
        if (scrollViewUtils == null) {
            scrollViewUtils = new ScrollViewUtils();
        }
        return scrollViewUtils;
    }


    /**
     * 解决ScrollView嵌套Gridview，listview显示不是顶部冲突的解决方法，在ScrollView的父布局中添加如下属性：
     * android:focusable="true"
     * android:focusableInTouchMode="true
     *
     * @param scrollView
     */
    public void setScrollView(ScrollView scrollView) {
        scrollView.setFocusable(true);
        scrollView.setFocusableInTouchMode(true);
    }

    /**
     * 控件锚点
     *
     * @param scrollView
     * @param view
     */
    @Deprecated
    public void setViewTop(ScrollView scrollView, View view) {
        int y = view.getTop();  //顶部距离父容器的Y轴距离
        scrollView.smoothScrollTo(0, y);
    }

}
