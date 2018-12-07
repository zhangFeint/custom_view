package com.library.depending.weight;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.annotation.DrawableRes;

/**
 * 功能：
 *
 * @author：zhangerpeng
 * @create：2018\12\7 0007 14:02
 * @version：2018 1.0
 * Created with IntelliJ IDEA
 */
public class ProgressNotification {
    //通知栏
    private Notification.Builder builder;
    private NotificationManager notificationManager;
    private int preProgress = 0;
    public int notifyId = 1000;//通知栏id
    private static ProgressNotification progressNotification;

    public static ProgressNotification getInstance() {
        if (progressNotification == null) {
            progressNotification = new ProgressNotification();
        }
        return progressNotification;
    }
    /**
     * 初始化Notification通知
     */
    public void initNotification(Context context, @DrawableRes int icon, String tible, int notifyId) {
        this.notifyId = notifyId;
        builder = new Notification.Builder(context)
                .setSmallIcon(icon)
                .setContentText("0%")
                .setContentTitle(tible)
                .setProgress(100, 0, false);
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notifyId, builder.build());
    }

    /**
     * 更新通知
     */
    public void updateNotification(long progress) {
        int currProgress = (int) progress;
        if (preProgress < currProgress) {
            builder.setContentText(progress + "%");
            builder.setProgress(100, (int) progress, false);
            notificationManager.notify(notifyId, builder.build());
        }
        preProgress = (int) progress;
    }

    /**
     * 取消通知
     */
    public void cancelNotification() {
        notificationManager.cancel(notifyId);
    }
}
