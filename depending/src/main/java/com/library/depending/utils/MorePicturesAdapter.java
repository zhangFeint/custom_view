package com.library.depending.utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.library.depending.R;
import com.library.depending.view.PlusImageActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * 添加上传图片适配器
 * <p>
 * 作者： 周旭 on 2017/6/21/0021.
 * 邮箱：374952705@qq.com
 * 博客：http://www.jianshu.com/u/56db5d78044d
 */
/*     <!--展示上传的图片-->
    <GridView
        android:id="@+id/gridView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="15dp"
        android:padding="10dp"
        android:columnWidth="70dp"
        android:numColumns="auto_fit"
        android:horizontalSpacing="5dp"
        android:verticalSpacing="5dp" />*/

/*   mGridViewAddImgAdapter = new MorePicturesAdapter(mContext, mPicList, 9);
        gridView.setAdapter(mGridViewAddImgAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mGridViewAddImgAdapter.setItem(parent, view, position, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectPic(9 - mPicList.size());  //添加凭证图片
                    }
                });

            }
        });*/
public class MorePicturesAdapter extends android.widget.BaseAdapter {

    private Context mContext;
    private List<String> mList;
    private LayoutInflater inflater;
    private int maximum;

    public MorePicturesAdapter(Context mContext, List<String> mList, int maximum) {
        this.mContext = mContext;
        this.mList = mList;
        this.maximum = maximum;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        //return mList.size() + 1;//因为最后多了一个添加图片的ImageView 
        int count = mList == null ? 1 : mList.size() + 1;
        if (count > maximum) {
            return mList.size();
        } else {
            return count;
        }
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.grid_item, parent, false);
        ImageView pic_iv = convertView.findViewById(R.id.pic_iv);
        if (position < mList.size()) {
            String picUrl = mList.get(position); //图片路径
            Glide.with(mContext).load(picUrl).into(pic_iv);  //代表+号之前的需要正常显示图片
        } else {
            pic_iv.setImageResource(R.drawable.ic_add);//最后一个显示加号图片
        }
        return convertView;
    }

    public void setItem(AdapterView<?> parent, View view, int position, View.OnClickListener listener) {
        if (position == parent.getChildCount() - 1) {
            if (mList.size() == maximum) {   //如果“增加按钮形状的”图片的位置是最后一张，且添加了的图片的数量不超过5张，才能点击
                PlusImageActivity.show((Activity) mContext, (ArrayList<String>) mList, position, PlusImageActivity.REQUEST_CODE_MAIN); //最多添加N张图片
            } else {
                listener.onClick(view);
            }
        } else {
            PlusImageActivity.show((Activity) mContext, (ArrayList<String>) mList, position, PlusImageActivity.REQUEST_CODE_MAIN); //最多添加N张图片
        }
    }


}  
