package com.library.depending.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.library.depending.viewutils.ButtonUtils;

import java.util.Timer;
import java.util.TimerTask;

public class ExitProgram {
    private static ExitProgram exitProgram;

    /**
     * 单例模式
     */
    public static ExitProgram getInstance() {
        if (exitProgram == null) {
            exitProgram = new ExitProgram();
        }
        return exitProgram;
    }


    /**
     * 双击退出函数
     *
     * @param activity
     * @param type 1:返回桌面 2:直接退出 3：弹出对话框
     */
    private static Boolean isExit = false;

    public void exitBy2Click(Activity activity, int type) {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            Toast.makeText(activity, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            switch (type) {
                case 1:
                    // 返回桌面操作
                    Intent home = new Intent(Intent.ACTION_MAIN);
                    home.addCategory(Intent.CATEGORY_HOME);
                    activity.startActivity(home);
                    break;
                case 2:

                    System.exit(0);  // 双击直接退出
                    break;
                case 3:
                    // 双击弹出对话框
                    displayDialog(activity);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 双击退出弹出对话框
     *
     * @param activity
     */
    public void displayDialog(Activity activity) {
        new AlertDialog.Builder(activity).setTitle("确认退出程序吗？")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 点击“确认”后的操作
                        System.exit(0);
                    }
                })
                .setNegativeButton("返回", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 点击“返回”后的操作,这里不设置没有任何操作
                    }
                }).show();
    }

}
