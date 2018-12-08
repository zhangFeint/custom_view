package com.library.custom_view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.library.depending.baseview.BaseActivity;
import com.library.depending.view.CameraUtils;
import com.library.depending.view.ImageActivity;
import com.library.depending.webview.PermissionUtils;
import com.library.depending.webview.WebActivity;

import java.io.File;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private Button button1, button2, button3;
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
        ivImage = findViewById(R.id.iv_image);
        initData();
    }

    @Override
    public void initData() {
        if( PermissionUtils.checkMorePermissions(this,PermissionUtils.CAMERA_PERMISSIONS).size()==0){
            Toast.makeText(this, "权限已获取", Toast.LENGTH_SHORT).show();
        }else {
            PermissionUtils. requestMorePermissions (this,PermissionUtils.checkMorePermissions(this,PermissionUtils.CAMERA_PERMISSIONS),102);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

                if( PermissionUtils.isPermissionRequestSuccess(grantResults)){
                    Toast.makeText(this, "申请成功", Toast.LENGTH_SHORT).show();
                }
    }

    @Override
    public void initListener() {
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
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
                CameraUtils.getInstance().getInstance().showCameraDialog(this, CameraUtils.REQUEST_CODE_CAMERA, CameraUtils.REQUEST_CODE_PHOTOS);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {//操作失败
            return;
        }
        switch (requestCode) {
            case CameraUtils.REQUEST_CODE_CAMERA:
                resetCameraResult(CameraUtils.getInstance().getCameraUri());
                break;
            case CameraUtils.REQUEST_CODE_PHOTOS:
                resetCameraResult(data.getData());
                break;
            default:
                break;
        }
    }

    private void resetCameraResult(Uri url) {
        File file =  CameraUtils.getInstance(). compressImage(this,ivImage,url,400,400);
        CameraUtils.getInstance().deleteFile(file.getAbsolutePath());
    }


}
