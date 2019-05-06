package com.library.depending.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * @author：zhangerpeng
 * 版本：
 * 日期：2019/5/6 22:45
 * 描述：
 *自定义ListView避免其在scrollview中单行显示
 */
public class ExpandableListView extends ListView {

    public ExpandableListView(Context context) {
        super(context);
    }

    public ExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExpandableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
