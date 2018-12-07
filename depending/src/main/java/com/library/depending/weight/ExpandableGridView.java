package com.library.depending.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 功能：
 * 自定义GridView避免其在scrollview中单行显示
 *
 * @author：zhangerpeng
 * @create：2018\11\17 0017 10:03
 * @version：2018 1.0
 * Created with IntelliJ IDEA
 */
public class ExpandableGridView extends GridView {

    public ExpandableGridView(Context context) {
        super(context);
    }

    public ExpandableGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExpandableGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}