package com.library.depending.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.CountDownTimer;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.library.depending.R;
import com.library.depending.baseview.BaseActivity;
import com.library.depending.weight.IndexChangeView;
import com.lljjcoder.style.citypickerview.CityPickerView;


public class GuideActivity extends BaseActivity {
    private ViewPager viewpager;
    private IndexChangeView indexChangeView;
    private TextView tvTime;

    private CountDownTimer countDownTimer;
    private boolean isScrolled;
    private static int[] images;
    private static Class<?> clss;
    private static int time;

    /**
     * @param context
     * @param time1   4000  倒计时
     * @param value   图片集合  R.mipmap.splash, R.mipmap.splash1, R.mipmap.splash2}
     * @param cls     要跳转的地方
     */
    public static void show(Context context, int time1, int[] value, Class<?> cls) {
        clss = cls;
        time = time1;
        images = value;
        Intent intent = new Intent(context, GuideActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//全屏
        setContentView(R.layout.guide_activity);
        initViews();
    }

    @Override
    public void initViews() {
        viewpager = findViewById(R.id.viewpager);
        indexChangeView = findViewById(R.id.indexChangeView);
        tvTime = findViewById(R.id.tv_time);
        initData();
        initListener();

    }

    @Override
    public void initData() {
        indexChangeView.setNumber(images.length);
        setCountdown(tvTime, time);
        initAdapter();
    }


    /**
     * 第二步  加入适配器
     * true 不带滑动跳转   false  带滑动跳转
     */
    public void initAdapter() {
        ViewPagerAdapter vpAdapter = new ViewPagerAdapter(images);
        viewpager.setAdapter(vpAdapter);
        viewpager.setOffscreenPageLimit(images.length);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    indexChangeView.setViewColorChange(position);//XIANSGHI
                } else {
                    indexChangeView.setViewColorChange(position);
                }
                onPageListener(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (false) {
                    return;
                }
                switch (state) {
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        isScrolled = false;
                        break;
                    case ViewPager.SCROLL_STATE_SETTLING:
                        isScrolled = true;
                        break;
                    case ViewPager.SCROLL_STATE_IDLE:
                        if (viewpager.getCurrentItem() == viewpager.getAdapter().getCount() - 1 && !isScrolled) {
                            closeCountdown();
                        }
                        isScrolled = true;
                        break;
                }
            }
        });
    }

    /**
     * 设置倒计时
     *
     * @param textView
     * @param time     1000 = 1秒;
     */
    public void setCountdown(final TextView textView, int time) {
        //实现倒计时
        countDownTimer = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                textView.setText(millisUntilFinished / 1000 + "秒");
            }

            @Override
            public void onFinish() {
                textView.setText(0 + "秒");
                closeCountdown();

            }
        }.start();
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setEnabled(false);
                closeCountdown();
            }
        });
    }

    public void closeCountdown() {
        if (countDownTimer != null)
            countDownTimer.cancel();
        goHomePage();

    }


    public void goHomePage() {
        Intent intent = new Intent(this, clss);
        startActivity(intent);
        finish();
    }

    private void onPageListener(int position) {
    }

    /**
     * 引导页的page适配器
     */
    private class ViewPagerAdapter extends PagerAdapter {

        private int[] images;

        public ViewPagerAdapter(int[] images) {
            this.images = images;
        }

        // 获取要滑动的控件的数量，在这里我们以滑动的广告栏为例，那么这里就应该是展示的广告图片的ImageView数量
        @Override
        public int getCount() {
            return images.length;
        }

        // 来判断显示的是否是同一张图片，这里我们将两个参数相比较返回即可
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        /**
         * 销毁条目
         * PagerAdapter只缓存三张要显示的图片，如果滑动的图片超出了缓存的范围，就会调用这个方法，将图片销毁
         */
        @Override
        public void destroyItem(ViewGroup view, int position, Object object) {
            view.removeView((View) object);
        }

        /**
         * 返回要显示的view,即要显示的视图
         */
        @Override
        public Object instantiateItem(ViewGroup viewGroup, int position) {
            View view = LayoutInflater.from(GuideActivity.this).inflate(R.layout.guide_image, null);
            ImageView imageView = view.findViewById(R.id.image);
            Glide.with(GuideActivity.this).load(images[position]).into(imageView);
            viewGroup.addView(view);
            return view;
        }


    }
}
