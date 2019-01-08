package com.library.depending.weight;

/**
 * 功能：
 * 自定义形状的TextView 圆形 椭圆形
 * @author：zhangerpeng
 * @create：2018\12\14 0014 16:17
 * @version：2018 1.0
 * Created with IntelliJ IDEA
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.TextView;

import com.library.depending.R;


@SuppressLint("AppCompatCustomView")
public class CustomShapTextView extends TextView {
    private Context mContext;
    /**
     * 画笔
     */
    private Paint mPaint;
    /**
     * 画笔颜色 默认灰色
     */
    private int mPaintNormalColor = 0xFFDCDCDC;
    /**
     * 画笔颜色 选中时的颜色,默认灰色
     */
    private int mPaintSelectColor = 0xFFDCDCDC;
    /**
     * 是否填充颜色
     */
    private boolean isChecked = false;

    public CustomShapTextView(Context context) {
        super(context);
    }

    public CustomShapTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint(context,attrs);
    }

    public CustomShapTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initPaint(context,attrs);
    }

    /**
     *  默认灰色
     * @param mPaintNormalColor
     */
    public void setmPaintNormalColor(int mPaintNormalColor) {
        this.mPaintNormalColor = mPaintNormalColor;
    }

    /**
     * 选中时的颜色,默认灰色
     * @param mPaintSelectColor
     */
    public void setmPaintSelectColor(int mPaintSelectColor) {
        this.mPaintSelectColor = mPaintSelectColor;
    }

    /**
     * 初始化画笔和自定义属性
     * @param context
     * @param attrs
     */
    private void initPaint(Context context,AttributeSet attrs){
        mContext = context;
        TypedArray typeArray = context.obtainStyledAttributes(attrs, R.styleable.CustomShapTextView);
        mPaintNormalColor = typeArray.getColor(R.styleable.CustomShapTextView_paintNormalColor,mPaintNormalColor);
        mPaintSelectColor = typeArray.getColor(R.styleable.CustomShapTextView_paintSelectColor,mPaintSelectColor);
        isChecked = typeArray.getBoolean(R.styleable.CustomShapTextView_checked,isChecked);
        mPaint = new Paint();
    }

    /**
     * 调用onDraw绘制边框
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        //抗锯齿
        mPaint.setAntiAlias(true);
        if (isChecked) {
            //画笔颜色
            mPaint.setColor(mPaintSelectColor);
            mPaint.setStyle(Paint.Style.FILL);
        }else{
            //画笔颜色
            mPaint.setColor(mPaintNormalColor);
            //画笔样式:空心
            mPaint.setStyle(Paint.Style.FILL);
        }

        //创建一个区域,限制圆弧范围
        RectF rectF = new RectF();
        //设置半径,比较长宽,取最大值
        int radius = getMeasuredWidth() > getMeasuredHeight() ? getMeasuredWidth() : getMeasuredHeight();
        //设置Padding 不一致,绘制出的是椭圆;一致的是圆形
        rectF.set(getPaddingLeft(),getPaddingTop(),radius-getPaddingRight(),radius-getPaddingBottom());
        //绘制圆弧
        canvas.drawArc(rectF,0,360,false,mPaint);
        //最后调用super方法,解决文本被所绘制的圆圈背景锁覆盖的问题
        super.onDraw(canvas);
    }

    /**
     * 设置是否填充颜色
     * @param isSelect
     */
    public void setChecked(boolean isSelect){
        this.isChecked = isChecked;
        invalidate();
    }

    public boolean isChecked() {
        return isChecked;
    }
}