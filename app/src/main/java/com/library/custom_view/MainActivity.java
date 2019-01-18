package com.library.custom_view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.library.depending.baseview.BaseActivity;
import com.library.depending.customview.CustomProgressDialog;
import com.library.depending.utils.GlideApp;
import com.library.depending.view.CameraUtils;
import com.library.depending.view.CityPicker;
import com.library.depending.view.DialogUtils;
import com.library.depending.view.GuideActivity;
import com.library.depending.view.ImageActivity;
import com.library.depending.webview.PermissionUtils;
import com.library.depending.webview.WebActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private Button button1, button2, button3, button4, button5, button6, button7;
    private ImageView ivImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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


        initData();
    }

    @Override
    public void initData() {
        if (PermissionUtils.checkMorePermissions(this, PermissionUtils.CAMERA_PERMISSIONS).size() == 0) {
            Toast.makeText(this, "权限已获取", Toast.LENGTH_SHORT).show();
        } else {
            PermissionUtils.requestMorePermissions(this, PermissionUtils.checkMorePermissions(this, PermissionUtils.CAMERA_PERMISSIONS), 102);
        }
        CityPicker.getInstance().init(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (PermissionUtils.isPermissionRequestSuccess(grantResults)) {
            Toast.makeText(this, "申请成功", Toast.LENGTH_SHORT).show();
        }
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
                ImageActivity.show(this, "http://b-ssl.duitang.com/uploads/item/201407/23/20140723083033_jwNEm.png");
                break;
            case R.id.button2:
                WebActivity.show(MainActivity.this, "https://www.baidu.com/");
                break;
            case R.id.button3:
                CameraUtils.getInstance().showCameraDialog(this);
                break;
            case R.id.button4:
                GuideActivity.show(this, new int[]{R.mipmap.splash, R.mipmap.splash1, R.mipmap.splash2}, MainActivity.class);
                break;
            case R.id.button5:
                CityPicker.getInstance().show(new CityPicker.OnCityClickListener() {
                    @Override
                    public void onSelected(String province, String city, String district) {
                        Toast.makeText(MainActivity.this, "province:" + province + "city:" + city + "district:" + district, Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.button6:
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
                CustomProgressDialog dialog = new CustomProgressDialog(this);
                dialog.setTitle("加载中...");
                dialog.show();
                break;
            case R.id.button7:
                String message1 = "{\"key\":\"valve\",\"key\":\"valve\",\"key\":\"valve\"}";
                estimate(message1);
                String message2 = "[{\"key\":\"value\"},{\"key\":\"value\"},{\"key\":\"value\"}]";
                estimate(message2);
                break;
        }
    }

    private void estimate(String message) {
        try {
            Object json = new JSONTokener(message).nextValue();
            if (json instanceof JSONObject) {
                System.out.println("数据是JSONObject");
            } else if (json instanceof JSONArray) {
                System.out.println("数据是JSONArray");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {//操作失败
            return;
        }
        resetCameraResult(CameraUtils.getInstance().getResult(this, requestCode, data).get(0));
    }

    private void resetCameraResult(Uri url) {
        ivImage.setImageURI(url);
        File file = CameraUtils.getInstance().compressImage(this, url, 400, 400);

    }


}
