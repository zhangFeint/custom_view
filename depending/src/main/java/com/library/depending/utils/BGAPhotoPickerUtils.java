package com.library.depending.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
//<cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout
//        android:id="@+id/npl_item_moment_photos"
//        android:layout_width="match_parent"
//        android:layout_height="wrap_content"
//        android:layout_margin="10dp"
//        app:bga_snpl_deleteDrawable="@mipmap/bga_pp_ic_delete"
//        app:bga_snpl_deleteDrawableOverlapQuarter="false"
//        app:bga_snpl_editable="true"
//        app:bga_snpl_itemCornerRadius="0dp"
//        app:bga_snpl_itemSpanCount="3"
//        app:bga_snpl_itemWhiteSpacing="4dp"
//        app:bga_snpl_itemWidth="0dp"
//        app:bga_snpl_maxItemCount="9"
//        app:bga_snpl_otherWhiteSpacing="0dp"
//        app:bga_snpl_placeholderDrawable="@mipmap/bga_pp_ic_holder_light"
//        app:bga_snpl_plusDrawable="@mipmap/bga_pp_ic_plus"
//        app:bga_snpl_plusEnable="true"
//        app:bga_snpl_sortable="true" />

/**
 * 九宫格图片选择
 */
public class BGAPhotoPickerUtils implements EasyPermissions.PermissionCallbacks {
    public static BGAPhotoPickerUtils bgaPhotoPickerUtils;
    public static final int PRC_PHOTO_PICKER = 1;//权限
    public static final int RC_CHOOSE_PHOTO = 101;// 选择
    public static final int RC_PHOTO_PREVIEW = 2;//拍照
    public static final int MULTIPLECHOICE = 102; //单选

    public ArrayList<String> sortarrayList;//排序后的数据
    public ArrayList<String> initialarrayList;//未排序的数据



    private Activity mActivity;


    public static BGAPhotoPickerUtils getInstance() {
        if (bgaPhotoPickerUtils == null) {
            bgaPhotoPickerUtils = new BGAPhotoPickerUtils();
        }
        return bgaPhotoPickerUtils;
    }

    /**
     * 使用九宫格图片展示
     *
     * @param mActivity
     * @param mPhotosSnpl 九宫格控件
     * @param ItemCount   选择的总数
     * @param requestCode 请求码
     */
    public void init(final Activity mActivity, final BGASortableNinePhotoLayout mPhotosSnpl, int ItemCount, final int requestCode) {
        this.mActivity = mActivity;
        mPhotosSnpl.setEditable(true);
        mPhotosSnpl.setPlusEnable(true);
        mPhotosSnpl.setSortable(true);

//            mPhotosSnpl.setData(null);//设置单选
//            mPhotosSnpl.setMaxItemCount(1);//设置单选
        mPhotosSnpl.setMaxItemCount(ItemCount);//设置最多选择图片数量
        mPhotosSnpl.setItemSpanCount(3);//设置显示列数
        mPhotosSnpl.setDelegate(new BGASortableNinePhotoLayout.Delegate() {
            @Override
            public void onClickAddNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, ArrayList<String> models) {
                BGAPhotoPickerUtils.getInstance().choicePhotoWrapper(mActivity, mPhotosSnpl, requestCode);
                initialarrayList = models;

            }

            @Override
            public void onClickDeleteNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
                mPhotosSnpl.removeItem(position);
                initialarrayList = models;

            }

            @Override
            public void onClickNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
                Intent photoPickerPreviewIntent = new BGAPhotoPickerPreviewActivity.IntentBuilder(mActivity)
                        .previewPhotos(models) // 当前预览的图片路径集合
                        .selectedPhotos(models) // 当前已选中的图片路径集合
                        .maxChooseCount(mPhotosSnpl.getMaxItemCount()) // 图片选择张数的最大值
                        .currentPosition(position) // 当前预览图片的索引
                        .isFromTakePhoto(false) // 是否是拍完照后跳转过来
                        .build();
                mActivity.startActivityForResult(photoPickerPreviewIntent, RC_PHOTO_PREVIEW);
            }

            @Override
            public void onNinePhotoItemExchanged(BGASortableNinePhotoLayout sortableNinePhotoLayout, int fromPosition, int toPosition, ArrayList<String> models) {
                Toast.makeText(mActivity, "排序发生变化", Toast.LENGTH_SHORT).show();
                sortarrayList=models;
            }
        });
    }
    public ArrayList<String> getFile() {
        if (sortarrayList == null) {
            sortarrayList = initialarrayList;
        }
        return sortarrayList;
    }


    /**
     * 复制下面到页面里
     */
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK && requestCode == BGAPhotoPickerUtils.RC_CHOOSE_PHOTO) {
////          mPhotosSnpl.setData(BGAPhotoPickerActivity.getSelectedPhotos(data));//设置单选数据
//            mPhotosSnpl.addMoreData(BGAPhotoPickerActivity.getSelectedPhotos(data));//设置多选数据
//            mPicList = BGAPhotoPickerActivity.getSelectedPhotos(data);
//        } else if (requestCode == BGAPhotoPickerUtils.RC_PHOTO_PREVIEW) {
//            mPicList = BGAPhotoPickerActivity.getSelectedPhotos(data);
//            mPhotosSnpl.setData(BGAPhotoPickerPreviewActivity.getSelectedPhotos(data));//拍照也是单选
//        }
//    }

    /**
     * @author：zhangerpeng 版本：
     * 日期：2019\3\6 0006 16:23
     * 描述：
     * 选择图片
     */
    @AfterPermissionGranted(PRC_PHOTO_PICKER)
    public void choicePhotoWrapper(Activity mActivity, BGASortableNinePhotoLayout mPhotosSnpl, int requestCode) {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(mActivity, perms)) {
            // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
            File takePhotoDir = new File(Environment.getExternalStorageDirectory(), "LMYPhotoPickerTakePhoto");
            Intent photoPickerIntent = new BGAPhotoPickerActivity.IntentBuilder(mActivity)
                    .cameraFileDir(takePhotoDir) // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话则不开启图库里的拍照功能
                    .maxChooseCount(mPhotosSnpl.getMaxItemCount() - mPhotosSnpl.getItemCount()) // 图片选择张数的最大值
                    .selectedPhotos(null) // 当前已选中的图片路径集合
                    .pauseOnScroll(false) // 滚动列表时是否暂停加载图片
                    .build();
            mActivity.startActivityForResult(photoPickerIntent, requestCode);
        } else {
            EasyPermissions.requestPermissions(mActivity, "图片选择需要以下权限:\n\n1.访问设备上的照片\n\n2.拍照", PRC_PHOTO_PICKER, perms);
        }
    }

    @AfterPermissionGranted(PRC_PHOTO_PICKER)
    public void MultipleChoice(Activity context) {
        //图片单选时调用  例如选择头像等点击事件调用该方法即可
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(context, perms)) {
            // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
            File takePhotoDir = new File(Environment.getExternalStorageDirectory(), "LMYPhotoPickerTakePhoto");
            Intent photoPickerIntent = new BGAPhotoPickerActivity.IntentBuilder(context)
                    .cameraFileDir(takePhotoDir) // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话则不开启图库里的拍照功能
                    .maxChooseCount(1) // 图片选择张数的最大值
                    .selectedPhotos(null) // 当前已选中的图片路径集合
                    .pauseOnScroll(false) // 滚动列表时是否暂停加载图片
                    .build();
            context.startActivityForResult(photoPickerIntent, MULTIPLECHOICE);
        } else {
            EasyPermissions.requestPermissions(context, "图片选择需要以下权限:\n\n1.访问设备上的照片\n\n2.拍照", PRC_PHOTO_PICKER, perms);
        }
    }

    /**
     * @author：zhangerpeng 日期：2019\3\6 0006
     * 描述：
     * 权限处理
     */

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (requestCode == PRC_PHOTO_PICKER) {
            Toast.makeText(mActivity, "您拒绝了「图片选择」所需要的相关权限!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
