package com.library.depending.webview;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;


/**
 * 作用：辅助 WebView 处理 Javascript 的对话框,网站图标,网站标题等等。
 */
public class MyWebChromeClient extends WebChromeClient {
    private Activity activity;

    public static final String[] CAMERA_PERMISSIONS = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}; //相机权限
    public static final int REQUEST_CODE_PERMISSION = 1010; //权限请求码
    private OnProgressChangedListener onProgressChangedListener;

    public MyWebChromeClient(Activity activity,OnProgressChangedListener onProgressChangedListener) {
        this.activity = activity;
        this.onProgressChangedListener = onProgressChangedListener;
    }



      /**
     * 作用：获得网页的加载进度并显示
     */
    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        if(onProgressChangedListener  == null){
            return;
        }
        if (newProgress == 100) {
            onProgressChangedListener.conceal();
        } else {
            onProgressChangedListener.show(newProgress);
        }
        super.onProgressChanged(view, newProgress);
    }

    //配置权限（同样在WebChromeClient中实现）
    @Override
    public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
        callback.invoke(origin, true, false);
        super.onGeolocationPermissionsShowPrompt(origin, callback);
    }


    /**
     * 上传文件问题，需要下方方法
     */

    //For Android  >= 4.1
    public void openFileChooser(ValueCallback<Uri> valueCallback, String acceptType, String capture) {
        isFile(valueCallback);
    }

    // For Android >= 5.0
    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
        isFile(filePathCallback);
        return true;
    }

    private void isFile(final ValueCallback valueCallback) {
        PermissionUtils.checkAndRequestMorePermissions(activity, CAMERA_PERMISSIONS, REQUEST_CODE_PERMISSION, new PermissionUtils.PermissionRequestSuccessCallBack() {
            @Override
            public void onHasPermission() {

            }
        });
        DialogUtils.getInstance().showCameraDialog(activity, valueCallback);
    }

    /**
     * web弹出框不出问题  分为4种弹框
     */
    @Override
    public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
        showDialog( message, result);
        return true;
    }

    /**
     * 设置响应js 的Confirm()函数
     */
    @Override
    public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
        showDialog( message, result);
        return true;
    }

    /**
     * 设置响应js 的Prompt()函数
     */
    @Override
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, final JsPromptResult result) {
        showDialog( message, result);
        return true;
    }

    /**
     * 显示弹出框
     * @param message
     * @param result
     */
    public void showDialog( String message, final JsResult result) {
        AlertDialog.Builder b = new AlertDialog.Builder(activity);
        b.setMessage(message);
        b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                result.confirm();
            }
        });
        b.setCancelable(false);
        b.create().show();
    }


}