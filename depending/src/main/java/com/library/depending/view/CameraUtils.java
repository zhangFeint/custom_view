package com.library.depending.view;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

import com.donkingliang.imageselector.utils.ImageSelectorUtils;
import com.library.depending.customview.ActionSheetDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 功能：
 * implementation 'com.github.donkingliang:ImageSelector:1.5.0'//相册多图片选择
 *
 * @author：zhangerpeng
 * @create：2018\10\10 0010 13:36
 * @version：2018 1.0
 * Created with IntelliJ IDEA
 */
public class CameraUtils {
    private Uri cameraUri;//拍照后的路径
    private String cropUri;//裁剪后的路径
    private String cropSavePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/XiaoCao" + File.separator;
    public static final int REQUEST_CODE_CAMERA = 110;//相机选择结果码
    public static final int REQUEST_CODE_PHOTOS = 111;//相册选择结果码
    public static final int RESULT_IMG_TAILOR = 114; //裁剪
    public static final int REQUEST_CODE_FILE = 112;//文件选择结果码
    public static final int REQUEST_CODE = 113; //相册多选
    private String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath(); // /storage/emulated/0/DCIM
    private static CameraUtils cameraUtils;

    public static CameraUtils getInstance() {
        if (cameraUtils == null) {
            cameraUtils = new CameraUtils();
        }
        return cameraUtils;
    }

    /**
     * 底部弹出选择相册，相机
     * getFile(getImagePath(this, uri, null));
     *
     * @param activity
     * @param cameraRequestCode     相机请求码
     * @param PhotoAlbumRequestCode 相册请求码
     */
    public void showCameraDialog(final Activity activity, final int cameraRequestCode, final int PhotoAlbumRequestCode) {
        ActionSheetDialog dialog = new ActionSheetDialog(activity).builder()
                .addSheetItem("拍照", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        openCamera(activity, cameraRequestCode);
                    }
                }).addSheetItem("相册", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        openPhotoAlbum(activity, PhotoAlbumRequestCode);
                    }
                }).setCancelable(false).setCanceledOnTouchOutside(false);

        dialog.show();

    }

    /**
     * 相册多选
     *
     * @param activity
     * @param cameraRequestCode     相机请求码
     * @param PhotoAlbumRequestCode 相册请求码
     * @param maxSelectCount        多选(最多9张)
     */
    public void showCameraDialog(final Activity activity, final int cameraRequestCode, final int PhotoAlbumRequestCode, final int maxSelectCount) {
        ActionSheetDialog dialog = new ActionSheetDialog(activity).builder()
                .addSheetItem("拍照", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        openCamera(activity, cameraRequestCode);
                    }
                }).addSheetItem("相册", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        ImageSelectorUtils.openPhoto(activity, PhotoAlbumRequestCode, false, maxSelectCount);  //多选(最多9张)
                    }
                }).setCancelable(false).setCanceledOnTouchOutside(false);
        dialog.show();

    }


    /**
     * 调用相册
     */
    public void openPhotoAlbum(Activity activity, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 调用相机
     *
     * @param activity
     * @param requestCode 请求码
     */
    public void openCamera(Activity activity, int requestCode) {
        openCamera(activity, filePath, "Camera", requestCode);
    }

    /**
     * 调用相机
     *
     * @param activity
     * @param savePath    保存路径
     * @param subcatalog  子目录
     * @param requestCode 请求码
     */
    public void openCamera(final Activity activity, final String savePath, final String subcatalog, final int requestCode) {
        File file = new File(filePath + getPicName());
        ContentValues contentValues = new ContentValues(1);
        contentValues.put(MediaStore.Images.Media.DATA, file.getAbsolutePath()); //保存到默认相机目录
        cameraUri = activity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues); // 其次把文件插入到系统图库
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri); //添加到文件里
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * Name  IMG_+时间==图片地址
     */
    private String getPicName() {
        return "IMG_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
    }

    /**
     * 裁剪(150x150)
     *
     * @param activity
     * @param uri
     * @param requestCode
     */
    public void cropPic(Activity activity, Uri uri, int requestCode) {
        cropPic(activity, uri, 1, 1, 450, 450, requestCode);
    }

    /**
     * 进行裁剪
     *
     * @param activity
     * @param uri         裁剪图片uri
     * @param aspectX     比例
     * @param aspectY     比例
     * @param outputX     宽
     * @param outputY     高
     * @param requestCode 请求码
     */
    public void cropPic(Activity activity, Uri uri, int aspectX, int aspectY, int outputX, int outputY, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
//        intent.putExtra("scale", true);
        //设置需要裁剪的图片数据
        intent.putExtra("return-data", true);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, saveUri);
//        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
//        intent.putExtra("noFaceDetection", true); // no face detection
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 删除文件
     *
     * @param filePath
     * @return
     */
    public boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
            return true;// 成功
        }
        return false;
    }


    /**
     * 拍照返回路径 ，相册返回data.getData();
     *
     * @return
     */
    public Uri getCameraUri() {
        return cameraUri;
    }

    /**
     * 裁剪返回路径
     *
     * @return
     */
    public String getCropUri() {
        return cropUri;
    }

    /**
     * 保存裁剪之后的图片数据
     */
    public File getPicToView(Context context, Intent intent) {
        if (intent == null) {
            return null;
        }
        Bundle extras = intent.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            cropUri = saveFile(null, getPicName(), photo);//图片路径
            UpdateGallery(context, cropUri);
            return getFile(cropUri);
        }
        return null;
    }

    /**
     * 通知图库更新
     */
    public void UpdateGallery(Context context, String uri) {
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + uri)));
    }

    /**
     * 通过Uri返回的url  转成真实路径
     * getFile(getImagePath(this, uri, null));
     *
     * @param activity
     * @param uri       图片路径
     * @param selection
     * @return
     */
    public String getRealPath(Activity activity, Uri uri, String selection) {
        String path = null;
        //通过Uri和selection来获取真实的图片路径
        Cursor cursor = activity.getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    /**
     * 转成文件
     *
     * @param path
     * @return
     */
    public File getFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            return file;
        }
        return null;
    }


    /**
     * @param filePath
     * @param fileName
     * @param bitmap
     * @return
     */
    private String saveFile(String filePath, String fileName, Bitmap bitmap) {
        byte[] bytes = bitmapToBytes(bitmap);
        return saveFile(filePath, fileName, bytes);
    }

    /**
     * bitmap 转byte[]
     *
     * @param bm
     * @return
     */
    private byte[] bitmapToBytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * 修剪过的图片所放位置
     */
    private String saveFile(String filePath, String fileName, byte[] bytes) {
        String fileFullName = "";
        FileOutputStream fos = null;
        try {
            String suffix = "";
            if (filePath == null || filePath.trim().length() == 0) {
                filePath = cropSavePath;
            }
            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            File fullFile = new File(filePath, fileName + suffix);
            fileFullName = fullFile.getPath();
            fos = new FileOutputStream(new File(filePath, fileName + suffix));
            fos.write(bytes);
        } catch (Exception e) {
            fileFullName = "";
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    fileFullName = "";
                }
            }
        }
        return fileFullName;
    }

    /**
     * 压缩图片
     *
     * @param context
     * @param imageView
     * @param url
     * @param targetWidth
     * @param targetHeight
     * @return
     */
    public File compressImage(Context context, ImageView imageView, Uri url, int targetWidth, int targetHeight) {
        Bitmap bitmap = CompressImageUtils.compressBoundsBitmap(context, url, targetWidth, targetHeight);
        if (imageView != null) {
            imageView.setImageBitmap(bitmap);
        }
        return CompressImageUtils.compressImage(bitmap);
    }

}
