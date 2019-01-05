package com.library.depending.viewutils;




public class ButtonUtils {
    private static ButtonUtils buttonUtils;

    /**
     * 单例模式
     */
    public static ButtonUtils getInstance() {
        if (buttonUtils == null) {
            buttonUtils = new ButtonUtils();
        }
        return buttonUtils;
    }



    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private static final int MIN_CLICK_DELAY_TIME = 2000;
    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }
}
