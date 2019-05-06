package com.library.custom_view;


import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.library.depending.TBSWebView.TbsWebViewActivity;
import com.library.depending.baseview.BaseActivity;
import com.library.depending.utils.ActivityUtils;
import com.library.depending.bean.City;
import com.library.depending.bean.District;
import com.library.depending.utils.NetUtil;
import com.library.depending.utils.PickerUtils;
import com.library.depending.bean.Province;
import com.library.depending.utils.RequestCode;
import com.library.depending.view.CameraUtils;
import com.library.depending.view.GuideActivity;
import com.library.depending.view.PlusImageActivity;
import com.library.depending.webview.WebActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends BaseActivity implements View.OnClickListener {
    private Button button1, button2, button3, button4, button5, button6, button7;
    private ImageView ivImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if( NetUtil.getInstance().getNetWorkState(this) ==1){
            Toast.makeText(this, "wggggg", Toast.LENGTH_SHORT).show();
            return;
        }
        setContentView(R.layout.activity_main);
        initViews();
        initListener();
    }

    @Override
    public void initViews() {
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        button7 = findViewById(R.id.button7);
        ivImage = findViewById(R.id.iv_image);
        Log.d("MainActivity", "initViews: "+ NetUtil.getInstance().getNetWorkState(this));
       ;
        initData();
    }

    @Override
    public void onChangeListener(int netMobile) {
        super.onChangeListener(netMobile);
        Log.d("onNetChange1111: ", "" + netMobile);
    }

    @Override
    public void initData() {
    }


    @Override
    public void initListener() {
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                ArrayList<String> list = new ArrayList();
                list.add("http://b-ssl.duitang.com/uploads/item/201407/23/20140723083033_jwNEm.png");
                list.add("http://b-ssl.duitang.com/uploads/item/201503/20/20150320220039_PBckv.jpeg");
                PlusImageActivity.show(this, list, 0, RequestCode.REQUEST_CODE_MAIN);
                break;
            case R.id.button2:
                WebActivity.show(MainActivity.this, "https://www.baidu.com");
                break;
            case R.id.button3:
//                CameraUtils.getInstance().showCameraDialog(this);
                ActivityUtils.exitBy2Click(MainActivity.this,2);
                break;
            case R.id.button4:
                GuideActivity.show(this, 8000, new int[]{R.mipmap.splash1, R.mipmap.splash2, R.mipmap.splash, R.mipmap.splash1, R.mipmap.splash2}, MainActivity.class);
                break;
            case R.id.button5:
                PickerUtils.getInstance().showCityPicker(MainActivity.this, "河南省", "郑州市", "金水区", new PickerUtils.OnCityClickListener() {
                @Override
                public void onSelected(Province province, City city, District district) {
                    Toast.makeText(MainActivity.this, "province:" + province.getId() + "city:" + city.getId() + "district:" + district.getId(), Toast.LENGTH_SHORT).show();
                }
            });

                break;
            case R.id.button6:
                refresh();
//             showDialog();
                break;
            case R.id.button7:
                TbsWebViewActivity.show(this,"https://www.baidu.com");
                break;
        }
    }


    private void resetCameraResult(Uri url) {
        ivImage.setImageURI(url);
        File file = CameraUtils.getInstance().compressImage(this, url, 400, 400);

    }

    /**
     * 展示Dialog
     */
    public void showDialog() {
//              final String message =  "杨幂，1986年9月12日出生于北京市，中国内地影视女演员、流行乐歌手、影视制片人。2005年，杨幂进入北京电影学院表演系本科班就读。2006年，杨幂因出演金庸武侠剧《神雕侠侣》而崭露头角。2008年，杨幂凭借古装剧《王昭君》获得了第24届中国电视金鹰奖观众喜爱的电视剧女演员奖提名。2009年，杨幂在“80后新生代娱乐大明星”评选活动中与其她三位女演员共同被评为“四小花旦”。";
//                DialogUtils.getInstance().displayDialog(this, new DialogUtils.OnDialogListener() {
//                    @Override
//                    public void onBuilder(AlertDialog.Builder builder) {
//                        builder.setTitle("简介");
//                        builder.setMessage(message);
//                        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                Toast.makeText(MainActivity.this, "确认", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                Toast.makeText(MainActivity.this, "取消", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }
//                });
//                        LoadingDialog dialog = new LoadingDialog(this, "加载中。。。");
//                        dialog.show();


    }


}
