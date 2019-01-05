package com.library.depending.viewutils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.List;

public class TextViewUtils {
    private static TextViewUtils textViewUtils;

    /**
     * 单例模式
     */
    public static TextViewUtils getInstance() {
        if (textViewUtils == null) {
            textViewUtils = new TextViewUtils();
        }
        return textViewUtils;
    }

    /**
     * TextView 多种颜色显示
     * textView.setText(getMulticolorText(str))
     *
     * @param str "我分享了装备[#CC33FF]轩辕剑[#GGGGGG],而且[#FF0000]这[#GGGGGG][#FF7F00]是[#GGGGGG][#FFFF00]七[#GGGGGG][#00FF00]彩[#GGGGGG][#00FFFF]的[#GGGGGG][#0000FF]颜[#GGGGGG][#8B00FF]色[#GGGGGG],你喜欢吗？70";
     */
    public Spanned getHtmlMulticolorText(String str) {
        String REG = "(\\[(\\#[0-9a-fA-F]{6,8})\\])(((?!\\[\\#).)*)(\\[\\#[G]{6,8}\\])"; // 要替换字符串的正则
        String html = str.replaceAll(REG, "<font color=$2>$3</font>");// 替换指定的字符串为html标签
        return Html.fromHtml(html);
    }

    /**
     * TextView 多种颜色显示
     *
     * @param text "备注:签收人(张三)";
     * @param list List<Multicolor>
     *             textView.setText(style)
     */
    public SpannableStringBuilder getMulticolorText(String text, List<Multicolor> list) {
        SpannableStringBuilder style = new SpannableStringBuilder(text);
        for (int i = 0; i < list.size(); i++) {//备注:显示的是蓝色，，第一参数是颜色，2，3是字符窜下标开始——结束位置，模式
            style.setSpan(new ForegroundColorSpan(list.get(i).getColorId()), list.get(i).getStart(), list.get(i).getEnd(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return style;
    }

    public class Multicolor {
        private int colorId;
        private int start;
        private int end;

        public Multicolor(int colorId, int start, int end) {
            this.colorId = colorId;
            this.start = start;
            this.end = end;
        }

        public int getColorId() {
            return colorId;
        }

        public int getStart() {
            return start;
        }

        public int getEnd() {
            return end;
        }
    }


    /**
     * TextView 多种颜色与点击事件
     *
     * @param str
     * @param strt
     * @param end
     * @param clickableSpan
     */
    public void setTextviewSpanColors(String str, int strt, int end, ClickableSpan clickableSpan) {
        SpannableString spanableInfo = new SpannableString(str);
        spanableInfo.setSpan(clickableSpan, strt, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    /**
     * Textview显示Html代码  需要universal-image-loader-1.9.5.jar 依赖包
     * https://blog.csdn.net/qq_36455052/article/details/78734734
     * 下载第三方https://github.com/nostra13/Android-Universal-Image-Loader
     *
     * @param activity
     * @param textView
     * @param string   html代码
     */
    public void setTextViewHTML(Activity activity, TextView textView, String string) {
        ImageLoader imageLoader = ImageLoader.getInstance();//ImageLoader需要实例化
        imageLoader.init(ImageLoaderConfiguration.createDefault(activity));
        URLImageParser imageGetter = new URLImageParser(textView);
        textView.setText(Html.fromHtml(string, imageGetter, null));
    }
    public class URLImageParser implements Html.ImageGetter {
        TextView mTextView;
        public URLImageParser(TextView textView) {
            this.mTextView = textView;
        }

        @Override
        public Drawable getDrawable(String source) {
            final URLDrawable urlDrawable = new URLDrawable();
            ImageLoader.getInstance().loadImage(source,
                    new SimpleImageLoadingListener() {
                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                            urlDrawable.bitmap = loadedImage;
                            urlDrawable.setBounds(0, 0, loadedImage.getWidth(), loadedImage.getHeight());
                            mTextView.invalidate();
                            mTextView.setText(mTextView.getText());
                        }
                    });
            return urlDrawable;
        }
    }

    public class URLDrawable extends BitmapDrawable {
        protected Bitmap bitmap;

        @Override
        public void draw(Canvas canvas) {
            if (bitmap != null) {
                canvas.drawBitmap(bitmap, 0, 0, getPaint());
            }
        }
    }
}
