package com.library.custom_view;

import android.app.Application;

import com.library.depending.TBSWebView.utils.TbsUtils;

public class App extends Application {
    @Override
    public void onCreate() {

        super.onCreate();
        TbsUtils.init(getApplicationContext());
    }
}
