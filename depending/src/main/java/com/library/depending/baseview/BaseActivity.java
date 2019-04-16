package com.library.depending.baseview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import com.library.depending.broadcast.NetBroadcastReceiver;
import com.library.depending.utils.NetUtil;
import com.library.depending.utils.ScreenUtil;

import java.util.List;


/**
 * 自定义的基本Activity
 */
public abstract class BaseActivity extends AppCompatActivity implements NetBroadcastReceiver.NetChangeListener {
    private static final String TAG = "BaseActivity";
    public static NetBroadcastReceiver.NetChangeListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyActivityManager.getInstance().pushOneActivity(this);
        listener = this;
        NetUtil.getInstance().checkNet(this);

    }

    //别忘了将广播取消掉哦~
    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetUtil.getInstance().unregisterReceiver(this);
    }
    /**
     * 使得在“setContentView()"之前生效，所以配置在此方法中。
     *
     * @param newBase
     */
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        ScreenUtil.resetDensity(this);
    }

    /**
     * 在某种情况下需要Activity的视图初始完毕Application中DisplayMetrics相关参数才能起效果，例如toast.
     *
     * @param
     */
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        ScreenUtil.resetDensity(this.getApplicationContext());
    }


    /**
     * 网络变化之后的类型
     */
    @Override
    public void onChangeListener(int netMobile) {
    }

    /**
     * 初始化控件
     */
    public void initViews() {

    }

    /**
     * 初始化数据
     */
    public void initData() {

    }

    /**
     * 加载数据
     */
    public void loadData() {

    }

    /**
     * 加载适配器
     */
    public void loadAdapter() {

    }

    /**
     * 初始化监听
     */
    public void initListener() {

    }

    /**
     * 初始适配器
     */
    public void initAdapter() {
    }

    /**
     * 解决在Fragment中申请权限时发现Fragment中不会回调onRequestPermissionsResult方法
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    // Activity中
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();  // 获取到Activity下的Fragment
        if (fragments == null) {
            return;
        }
        // 查找在Fragment中onRequestPermissionsResult方法并调用
        for (Fragment fragment : fragments) {
            if (fragment != null) {
                // 这里就会调用我们Fragment中的onRequestPermissionsResult方法
                fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    /**
     * 参数传到 Fragment
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        FragmentManager fragmentManager = getSupportFragmentManager();
        for (int indext = 0; indext < fragmentManager.getFragments().size(); indext++) {
            Fragment fragment = fragmentManager.getFragments().get(indext); //找到第一层Fragment
            if (fragment == null)
                Log.w(TAG, "Activity result no fragment exists for index: 0x"
                        + Integer.toHexString(requestCode));
            else
                handleResult(fragment, requestCode, resultCode, data);
        }
    }

    /**
     * 递归调用，对所有的子Fragment生效
     *
     * @param fragment
     * @param requestCode
     * @param resultCode
     * @param data
     */
    private void handleResult(Fragment fragment, int requestCode, int resultCode, Intent data) {
        fragment.onActivityResult(requestCode, resultCode, data);//调用每个Fragment的onActivityResult
        List<Fragment> childFragment = fragment.getChildFragmentManager().getFragments(); //找到第二层Fragment
        if (childFragment != null)
            for (Fragment f : childFragment)
                if (f != null) {
                    handleResult(f, requestCode, resultCode, data);
                }
        if (childFragment == null)
            Log.e(TAG, TAG);
    }

}
