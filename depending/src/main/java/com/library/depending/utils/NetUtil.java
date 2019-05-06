package com.library.depending.utils;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

import com.library.depending.baseview.BaseActivity;
import com.library.depending.broadcast.NetBroadcastReceiver;

/**
 *  
 *  *  
 *  * @author cj 判断网络工具类 
 *  *  
 *  
 */

public class NetUtil {
    /**没有连接网络*/
    private static final int NETWORK_NONE = -1;
    /**移动网络*/
    private static final int NETWORK_MOBILE = 0;
    /**无线网络*/
    private static final int NETWORK_WIFI = 1;

    private static NetUtil netUtil;
    private NetBroadcastReceiver netBroadcastReceiver;

    /**
     * 单例模式
     */
    public static NetUtil getInstance() {
        if (netUtil == null) {
            netUtil = new NetUtil();
        }
        return netUtil;
    }

    /**
     * 初始化时判断有没有网络 动态注册
     */
    public void checkNet(Activity context) {
        //Android 7.0以上需要动态注册
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            IntentFilter filter = new IntentFilter();  //实例化IntentFilter对象
            filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            netBroadcastReceiver = new NetBroadcastReceiver();
             context.registerReceiver(netBroadcastReceiver, filter);  //注册广播接收
        }

    }

    /**
     *  将广播取消掉哦~
     * @param context
     */
    public void unregisterReceiver(Activity context) {
        if(netBroadcastReceiver!=null){
            context. unregisterReceiver(netBroadcastReceiver);
        }
    }

    /**
     * @param context
     * @return   -1、没有连接网络 0、移动网络 1、无线网络
     */
    public int getNetWorkState(Context context) {
        // 得到连接管理器对象  
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_WIFI)) {
                return NETWORK_WIFI;
            } else if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_MOBILE)) {
                return NETWORK_MOBILE;
            }
        }
        return NETWORK_NONE;
    }
}
