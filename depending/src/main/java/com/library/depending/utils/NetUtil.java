package com.library.depending.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

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

    /**
     * 单例模式
     */
    public static NetUtil getInstance() {
        if (netUtil == null) {
            netUtil = new NetUtil();
        }
        return netUtil;
    }

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
