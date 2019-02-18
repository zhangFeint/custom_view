package com.library.depending.webview;

import android.webkit.WebView;

public interface OnOverrideUrlLoadingListener {
    void shouldOverrideUrlLoading(final WebView view, String url);
    void  onReceivedError(final WebView view,int errorCode);
}
