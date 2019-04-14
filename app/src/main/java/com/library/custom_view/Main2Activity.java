package com.library.custom_view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.library.depending.baseview.BaseActivity;
import com.library.depending.viewutils.FragmentUtils;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class Main2Activity extends BaseActivity implements View.OnClickListener , EasyPermissions.PermissionCallbacks{
    private static final int RC_CAMERA_PERM = 104 ;
    private TextView tvFragment1,tvFragment2;
    private Fragment fragment1,fragment2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initViews();
    }

    @Override
    public void initViews() {
        tvFragment1 =  findViewById(R.id.tv_fragment1);
        tvFragment2 =  findViewById(R.id.tv_fragment2);
        initData();
        initListener();
    }

    @Override
    public void initData() {
        fragment1= new BlankFragment();
        fragment2= new BlankFragment2();
        FragmentUtils.getInstance().addFragment(getSupportFragmentManager(),R.id.frameLayout,fragment1);
        FragmentUtils.getInstance().addFragment(getSupportFragmentManager(),R.id.frameLayout,fragment2);
    }

    @Override
    public void loadData() {
        super.loadData();
    }

    @Override
    public void loadAdapter() {
        super.loadAdapter();
    }

    @Override
    public void initListener() {
        tvFragment1 .setOnClickListener(this);
        tvFragment2 .setOnClickListener(this);
    }

    @Override
    public void initAdapter() {
        super.initAdapter();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_fragment1:
                FragmentUtils.getInstance().showFragment(getSupportFragmentManager(),fragment1);
                cameraTask();
                break;
            case R.id.tv_fragment2:
                FragmentUtils.getInstance().showFragment(getSupportFragmentManager(),fragment2);
                break;
        }
    }
    @AfterPermissionGranted(RC_CAMERA_PERM)
    public void cameraTask() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
            // 已经有摄像头权限了，可以使用该权限完成app的相应的操作了
            Toast.makeText(this, "TODO: Camera things", Toast.LENGTH_LONG).show();
        } else {
            // app还没有使用摄像头的权限，调用该方法进行申请，同时给出了相应的说明文案，提高用户同意的可能性
            EasyPermissions.requestPermissions(this, "需要相机权限", RC_CAMERA_PERM, Manifest.permission.CAMERA);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // 调用EasyPermissions的onRequestPermissionsResult方法，参数和系统方法保持一致，然后就不要关心具体的权限申请代码了
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        // 此处表示权限申请已经成功，可以使用该权限完成app的相应的操作了
        // ...
    }
    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        // 此处表示权限申请被用户拒绝了，此处可以通过弹框等方式展示申请该权限的原因，以使用户允许使用该权限
        // ...
    }

}
