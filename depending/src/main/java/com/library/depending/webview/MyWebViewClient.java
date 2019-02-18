package com.library.depending.webview;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alipay.sdk.app.H5PayCallback;
import com.alipay.sdk.app.PayTask;
import com.alipay.sdk.util.H5PayResultModel;




/**
 * webviewb必须实现的方法 ，解决
 */
public class MyWebViewClient extends WebViewClient {
    private Activity activity;
    private String murl;

    private String qq = "mqqwpa:";
    private String wenxin = "weixin://wap/pay?";
    private String phone = "tel:";

    private  OnOverrideUrlLoadingListener onOverrideUrlLoading;

    public MyWebViewClient(Activity activity, String murl,OnOverrideUrlLoadingListener onOverrideUrlLoading) {
        this.activity = activity;
        this.murl = murl;
        this.onOverrideUrlLoading = onOverrideUrlLoading;
    }


    /**
     * 加载页面的服务器出现错误时（如404）调用。
     * 6.0以下执行
     */

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
        onOverrideUrlLoading.onReceivedError(view,errorCode);
    }
    /**
     * 加载页面的服务器出现错误时（如404）调用。
     * 6.0以上执行
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
        onOverrideUrlLoading.onReceivedError(view,error.getErrorCode());
    }

    /**
     * 显示自定义错误提示页面，用一个View覆盖在WebView
     */
    public void showErrorPage(ViewGroup viewGroup, View mErrorView) {

        viewGroup.removeAllViews(); //移除加载网页错误时，默认的提示信息
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        viewGroup.addView(mErrorView, 0, layoutParams); //添加自定义的错误提示的View
    }
    /**
     * 接受信任所有网站的证书
     */
    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        handler.proceed();
    }

    /**
     * 作用：打开网页时不调用系统浏览器， 而是在本WebView中显示；在网页上的所有加载都经过这个方法,这个函数我们可以做很多操作。
     */
    @Override
    public boolean shouldOverrideUrlLoading(final WebView view, String url) {
        final PayTask task = new PayTask(activity);//跳转到支付宝,推荐采用的新的二合一接口(payInterceptorWithUrl),只需调用一次
        boolean isIntercepted = task.payInterceptorWithUrl(url, true, new H5PayCallback() {
            @Override
            public void onPayResult(final H5PayResultModel result) {
                final String url = result.getReturnUrl();
                if (!TextUtils.isEmpty(url)) {
                    view.loadUrl(url);
                }
            }
        });
        if (!isIntercepted)//判断是否成功拦截,若成功拦截，则无需继续加载该URL；否则继续加载 https://wappaygw.alipay.com
            if (!url.equalsIgnoreCase(murl)) //判断是否与当前url相同，
                if (url.startsWith(qq) || url.startsWith(wenxin) || url.startsWith(phone)) {
                    try {
                        activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                        return true;
                    } catch (Exception e) {
                        Toast.makeText(activity, "启动异常！\n请检查是否安装了该应用.", Toast.LENGTH_SHORT).show();
                    }
                } else if (url.startsWith("http:") || url.startsWith("https:")) {
                    onOverrideUrlLoading.shouldOverrideUrlLoading(view,url);
                    return true;//返回true表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                }
        return super.shouldOverrideUrlLoading(view,url);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return super.shouldOverrideUrlLoading(view, request);
    }

    /**
     * 开始载入页面调用的，我们可以设定一个loading的页面，告诉用户程序在等待网络响应。
     */
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {

    }

    /**
     * 在页面加载结束时调用。我们可以关闭loading 条，切换程序动作。
     */
    @Override
    public void onPageFinished(WebView view, String url) {
        view.setLayerType(View.LAYER_TYPE_HARDWARE, null);

    }
}
