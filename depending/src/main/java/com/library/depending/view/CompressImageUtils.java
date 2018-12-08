package com.library.depending.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static android.content.ContentValues.TAG;
import static android.support.v4.content.FileProvider.getUriForFile;

/**
 * 功能：
 *
 * @author：zhangerpeng
 * @create：2018\12\8 0008 13:41
 * @version：2018 1.0
 * Created with IntelliJ IDEA
 */

public class CompressImageUtils {
    private static String savePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/XiaoCao";

    /**
     * 压缩图片
     *
     * @param context
     * @param uri          图片路径
     * @param targetWidth  目标 宽
     * @param targetHeight 目标 高
     * @return
     */
    public static Bitmap compressBoundsBitmap(Context context, Uri uri, int targetWidth, int targetHeight) {
        InputStream input = null;
        Bitmap bitmap = null;

        try {
            input = context.getContentResolver().openInputStream(uri);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(input, (Rect) null, options);
            input.close();
            int originalWidth = options.outWidth;
            int originalHeight = options.outHeight;
            if (originalWidth != -1 && originalHeight != -1) {
                boolean be1 = true;
                int widthBe = 1;
                if (originalWidth > targetWidth) {
                    widthBe = originalWidth / targetWidth;
                }

                int heightBe = 1;
                if (originalHeight > targetHeight) {
                    heightBe = originalHeight / targetHeight;
                }

                int be2 = widthBe > heightBe ? heightBe : widthBe;
                if (be2 <= 0) {
                    be2 = 1;
                }

                options.inJustDecodeBounds = false;
                options.inSampleSize = be2;
                input = context.getContentResolver().openInputStream(uri);
                bitmap = BitmapFactory.decodeStream(input, (Rect) null, options);
                input.close();
                input = null;
            } else {
                Object be = null;
            }
        } catch (Exception var23) {
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException var22) {
                }
            }

            return bitmap;
        }
    }

    /**
     * 图片文件转为Bitmap对象
     *
     * @param filePath
     * @return
     */
    public static Bitmap getBitmap(String filePath) {
        return BitmapFactory.decodeFile(filePath);
    }

    /**
     * Bitmap转文件 保存
     *
     * @param bitmap
     * @return
     */
    public static File compressImage(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 500) {  //循环判断如果压缩后图片是否大于500kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            options -= 10;//每次都减少10
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            long length = baos.toByteArray().length;
        }

        File file = new File(savePath + File.separator + getPicName("IMG_"));
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d(TAG, "compressImage: " + file);
        // recycleBitmap(bitmap);
        return file;
    }

    /**
     * Name  IMG_+时间==图片地址
     */
    private static String getPicName(String Name) {
        return Name + String.valueOf(System.currentTimeMillis()) + ".jpg";
    }
    /**
     * 适配了android7 的uri文件暴露权限
     *
     * @param context
     * @param picturefile
     * @return
     */
    public static Uri getFileUriPermission(Context context, File picturefile) {
        Uri uri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//7.0及以上
            uri = getUriForFile(context, context.getPackageName() + ".fileprovider", picturefile);
            context.grantUriPermission(context.getPackageName(), uri, Intent.FLAG_GRANT_READ_URI_PERMISSION); //申请url暴露权限
        } else {//7.0以下
            uri = Uri.fromFile(picturefile);
        }
        return uri;
    }
}
