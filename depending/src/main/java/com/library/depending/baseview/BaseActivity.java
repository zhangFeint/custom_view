package com.library.depending.baseview;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;


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

    /**
     * 实时监听网络变化
     */
    private NetBroadcastReceiver netBroadcastReceiver;
    private int netMobile;// 网络类型
    private FragmentManager manager;
    private FragmentTransaction transaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyActivityManager.getInstance().pushOneActivity(this);

        checkNet();
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
     * 初始化时判断有没有网络
     */
    public boolean checkNet() {
        listener = this;
        //Android 7.0以上需要动态注册
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //实例化IntentFilter对象
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            netBroadcastReceiver = new NetBroadcastReceiver();
            //注册广播接收
            registerReceiver(netBroadcastReceiver, filter);
        }
        this.netMobile = NetUtil.getInstance().getNetWorkState(BaseActivity.this);
        return isNetConnect();
    }

    /**
     * 网络变化之后的类型
     */
    @Override
    public void onChangeListener(int netMobile) {
        this.netMobile = netMobile;
        isNetConnect();
    }

    /**
     * 判断有无网络 。
     *
     * @return true 有网, false 没有网络.
     */
    public boolean isNetConnect() {
        if (netMobile == 1) {
            return true;
        } else if (netMobile == 0) {
            return true;
        } else if (netMobile == -1) {
            return false;
        }
        return false;
    }


    /**
     * 显示当前 Fragment
     * 1、 实例化  Fragment 显示  processView
     * 2、 移除实例 Fragment    removeFragment  不保存状态
     *
     * @param var1     FrameLayout ID
     * @param fragment new Fragment();
     */
    public void processView(@IdRes int var1, Fragment fragment) {
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        removeFragment(transaction);
        transaction.replace(var1, fragment);
        transaction.commit();
    }

    /**
     * 去除（隐藏）所有的Fragment
     * 与processView
     */
    private void removeFragment(FragmentTransaction transaction) {
        List<Fragment> frmlist = manager.getFragments();
        for (int i = 0; i < frmlist.size(); i++) {
            transaction.remove(frmlist.get(i));
        }
    }


    /**
     * 添加fragment   用 showFragment显示
     * 1。先添加 addFragment
     * 2、显示某一个  showFragment  其他隐藏掉   保存状态
     *
     * @param var1     FrameLayout ID
     * @param fragment new Fragment();
     */
    public void addFragment(@IdRes int var1, Fragment fragment) {
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.add(var1, fragment);
        transaction.hide(fragment);
        transaction.commit();
    }

    /**
     * 显示fragment
     *
     * @param fragment
     */
    public void showFragment(Fragment fragment) {
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        hideFragment(transaction);
        transaction.show(fragment);
        transaction.commit();
    }

    /*
     * 去除（隐藏）所有的Fragment
     * */
    private void hideFragment(FragmentTransaction transaction) {
        List<Fragment> frmlist = manager.getFragments();
        for (int i = 0; i < frmlist.size(); i++) {
            transaction.hide(frmlist.get(i));
        }
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
     * 设置横屏 true ：横屏 false ：竖屏
     */
    protected void setOrientation(boolean b) {
        if (b) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);// 横屏
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
        }
    }

    /**
     * 当前 屏幕 true ：横屏 false ：竖屏
     *
     * @return
     */
    protected boolean getOrientation() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return true;
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            return false;
        }
        return true;
    }


    /**
     * 设置全屏  true ：全屏 false ：取消全屏
     * 在    setContentView(R.layout.activity_main);之前
     */
    public void setFullScreen(boolean b) {
        if (b) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); // 取消全屏
        }
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
        Log.e(TAG, "MyBaseFragmentActivity");
        List<Fragment> childFragment = fragment.getChildFragmentManager().getFragments(); //找到第二层Fragment
        if (childFragment != null)
            for (Fragment f : childFragment)
                if (f != null) {
                    handleResult(f, requestCode, resultCode, data);
                }
        if (childFragment == null)
            Log.e(TAG, "MyBaseFragmentActivity1111");
    }

}
