package com.library.depending.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.library.depending.R;
import com.library.depending.baseview.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片预览界面
 */
public class PlusImageActivity extends BaseActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {
    public static final String IMG_LIST = "img_list"; //第几张图片
    public static final String POSITION = "position"; //第几张图片
    public static final int REQUEST_CODE_MAIN = 101; //请求码
    private ViewPager viewPager; //展示图片的ViewPager
    private TextView positionTv; //图片的位置，第几张图片
    private ArrayList<String> imgList; //图片的数据源
    private int mPosition; //第几张图片
    private ViewPagerAdapter mAdapter;
    private ImageView back_iv, delete_iv;

    //查看大图
    public static void show(Activity mContext, ArrayList<String> mPicList, int position) {
        Intent intent = new Intent(mContext, PlusImageActivity.class);
        intent.putStringArrayListExtra(IMG_LIST, mPicList);
        intent.putExtra(POSITION, position);
        mContext.startActivityForResult(intent, REQUEST_CODE_MAIN);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plus_image);
        imgList = getIntent().getStringArrayListExtra(IMG_LIST);
        mPosition = getIntent().getIntExtra(POSITION, 0);
        initViews();
    }

    @Override
    public void initViews() {
        viewPager = findViewById(R.id.viewPager);
        positionTv = findViewById(R.id.position_tv);
        back_iv = findViewById(R.id.back_iv);
        delete_iv = findViewById(R.id.delete_iv);
        initListener();
        initAdapter();
    }

    @Override
    public void initListener() {
        viewPager.addOnPageChangeListener(this);
        back_iv.setOnClickListener(this);
        delete_iv.setOnClickListener(this);
    }

    @Override
    public void initAdapter() {
        mAdapter = new ViewPagerAdapter(this, imgList);
        viewPager.setAdapter(mAdapter);
        positionTv.setText(mPosition + 1 + "/" + imgList.size());
        viewPager.setCurrentItem(mPosition);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mPosition = position;
        positionTv.setText(position + 1 + "/" + imgList.size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.back_iv) {//返回
            back();

        } else if (i == R.id.delete_iv) {//删除图片
            deletePic();

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //按下了返回键
            back();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //删除图片
    private void deletePic() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("要删除这张图片吗?");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                imgList.remove(mPosition); //从数据源移除删除的图片
                setPosition();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    //设置当前位置
    private void setPosition() {
        positionTv.setText(mPosition + 1 + "/" + imgList.size());
        viewPager.setCurrentItem(mPosition);
        mAdapter.notifyDataSetChanged();
    }

    //返回上一个页面
    private void back() {
        Intent intent = getIntent();
        intent.putStringArrayListExtra(IMG_LIST, imgList);
        setResult(RESULT_OK, intent);
        finish();
    }

    public class ViewPagerAdapter extends PagerAdapter {

        private Context context;
        private List<String> imgList; //图片的数据源

        public ViewPagerAdapter(Context context, List<String> imgList) {
            this.context = context;
            this.imgList = imgList;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public int getCount() {
            return imgList.size();
        }

        //判断当前的View 和 我们想要的Object(值为View) 是否一样;返回 true/false
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view == object);
        }

        //instantiateItem()：将当前view添加到ViewGroup中，并返回当前View
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = getItemView(R.layout.view_pager_img);
            ImageView imageView = itemView.findViewById(R.id.img_iv);
            Glide.with(context).load(imgList.get(position)).into(imageView);
            container.addView(itemView);
            return itemView;
        }

        //destroyItem()：删除当前的View;
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        private View getItemView(int layoutId) {
            View itemView = LayoutInflater.from(this.context).inflate(layoutId, null, false);
            return itemView;
        }
    }
}
