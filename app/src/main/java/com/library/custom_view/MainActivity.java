package com.library.custom_view;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.library.depending.baseview.BaseActivity;
import com.library.depending.customview.AlertDialog;
import com.library.depending.utils.BGAPhotoPickerUtils;
import com.library.depending.utils.City;
import com.library.depending.utils.District;
import com.library.depending.utils.JsonUtils;
import com.library.depending.utils.PickerUtils;
import com.library.depending.utils.Province;
import com.library.depending.utils.RequestCode;
import com.library.depending.view.CameraUtils;
import com.library.depending.view.GuideActivity;
import com.library.depending.view.PlusImageActivity;
import com.library.depending.viewutils.FragmentUtils;
import com.library.depending.webview.PermissionUtils;
import com.library.depending.webview.WebActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout;
public class MainActivity extends BaseActivity implements View.OnClickListener {
    private Button button1, button2, button3, button4, button5, button6, button7;
    private ImageView ivImage;
    private BGASortableNinePhotoLayout mPhotosSnpl;
    private int requestCode;
    private int resultCode;
    private Intent data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initListener();
        setOrientation(false);
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
        mPhotosSnpl = findViewById(R.id.npl_item_moment_photos);
        BGAPhotoPickerUtils.getInstance().init(this, mPhotosSnpl, 6, BGAPhotoPickerUtils.RC_CHOOSE_PHOTO);
        initData();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == BGAPhotoPickerUtils.RC_CHOOSE_PHOTO) {
//          mPhotosSnpl.setData(BGAPhotoPickerActivity.getSelectedPhotos(data));//设置单选数据
            mPhotosSnpl.addMoreData(BGAPhotoPickerActivity.getSelectedPhotos(data));//设置多选数据
//            mPicList = BGAPhotoPickerActivity.getSelectedPhotos(data);
        } else if (requestCode == BGAPhotoPickerUtils.RC_PHOTO_PREVIEW) {
//            mPicList = BGAPhotoPickerActivity.getSelectedPhotos(data);
            mPhotosSnpl.setData(BGAPhotoPickerPreviewActivity.getSelectedPhotos(data));//拍照也是单选
        }
    }

    @Override
    public void onChangeListener(int netMobile) {
        super.onChangeListener(netMobile);
        Log.d("onNetChange1111: ", "" + isNetConnect());
    }

    @Override
    public void initData() {
        PermissionUtils.checkMorePermissions(this, PermissionUtils.CAMERA_PERMISSIONS, new PermissionUtils.PermissionCheckCallBack() {
            @Override
            public void onHasPermission() {
                Log.d("PermissionUtils", "onHasPermission: 权限已获取");
                Toast.makeText(MainActivity.this, "权限已获取", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUserHasAlreadyTurnedDown(String... permission) {
                Log.d("PermissionUtils", "onUserHasAlreadyTurnedDown: 拒绝");
                PermissionUtils.requestPermission(MainActivity.this, permission, 1111);
            }

            @Override
            public void onUserHasAlreadyTurnedDownAndDontAsk(String... permission) {
                Log.d("PermissionUtils", "onUserHasAlreadyTurnedDownAndDontAsk: 已勾选不再询问");
                PermissionUtils.requestPermission(MainActivity.this, permission, 1111);
                Toast.makeText(MainActivity.this, "" + permission, Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtils.onRequestMorePermissionsResult(MainActivity.this, permissions, new PermissionUtils.PermissionCheckCallBack() {
            @Override
            public void onHasPermission() {
                Log.d("PermissionUtils", "onHasPermission: 权限已获取回调");
            }

            @Override
            public void onUserHasAlreadyTurnedDown(String... permission) {
                Log.d("PermissionUtils", "onUserHasAlreadyTurnedDown: 拒绝回调");
            }

            @Override
            public void onUserHasAlreadyTurnedDownAndDontAsk(String... permission) {
                Log.d("PermissionUtils", "onUserHasAlreadyTurnedDownAndDontAsk: 已勾选不再询问回调");
            }
        });
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
                CameraUtils.getInstance().showCameraDialog(this);
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
             showDialog();
                break;
            case R.id.button7:
                String message1 = "{\"key\":\"valve\",\"key\":\"valve\",\"key\":\"valve\"}";
                String message2 = "[{\"key\":\"value\"},{\"key\":\"value\"},{\"key\":\"value\"}]";
                String message3 = "key:value";
                System.out.println(JsonUtils.getInstance().estimate(message1));
                System.out.println(JsonUtils.getInstance().estimate(message2));
                System.out.println(JsonUtils.getInstance().estimate(message3));
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
