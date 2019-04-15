package com.library.custom_view;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.library.depending.baseview.BaseFragment;
import com.library.depending.utils.BGAPhotoPickerUtils;

import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends BaseFragment {
    private View view;
    private BGASortableNinePhotoLayout mPhotosSnpl;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_blank, container, false);
        initViews();
        return view;
    }

    @Override
    public void initViews() {
        mPhotosSnpl = view.findViewById(R.id.npl_item_moment_photos);
        initData();
    }

    @Override
    public void initData() {
        BGAPhotoPickerUtils.getInstance().init(mActivity, mPhotosSnpl, 6, BGAPhotoPickerUtils.RC_CHOOSE_PHOTO);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == BGAPhotoPickerUtils.RC_CHOOSE_PHOTO) {
//          mPhotosSnpl.setData(BGAPhotoPickerActivity.getSelectedPhotos(data));//设置单选数据
            mPhotosSnpl.addMoreData(BGAPhotoPickerActivity.getSelectedPhotos(data));//设置多选数据
//            mPicList = BGAPhotoPickerActivity.getSelectedPhotos(data);
        } else if (requestCode == BGAPhotoPickerUtils.RC_PHOTO_PREVIEW) {
//            mPicList = BGAPhotoPickerActivity.getSelectedPhotos(data);
            mPhotosSnpl.setData(BGAPhotoPickerPreviewActivity.getSelectedPhotos(data));//拍照也是单选
        }
    }

}
