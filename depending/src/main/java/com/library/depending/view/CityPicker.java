package com.library.depending.view;

import android.content.Context;

import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citypickerview.CityPickerView;

/**
 * 功能：
 *
 * @author：zhangerpeng
 * @create：2018\12\11 0011 10:09
 * @version：2018 1.0
 * Created with IntelliJ IDEA
 */
public class CityPicker {
    private static CityPicker cityPicker;
    private CityPickerView mPicker;

    public static CityPicker getInstance() {
        if (cityPicker == null) {
            cityPicker = new CityPicker();
        }
        return cityPicker;
    }

    public void init(Context context) {
        mPicker = new CityPickerView();
        mPicker.init(context);
    }

    public void show(final OnCityClickListener listener) {
        //添加默认的配置，不需要自己定义，当然也可以自定义相关熟悉，详细属性请看demo
        CityConfig cityConfig = new CityConfig.Builder().titleTextSize(14)
                .title("地址选择")
                .titleBackgroundColor("#FFFFFF")
                .titleTextColor("#696969")
                .confirTextColor("#696969")
                .cancelTextColor("#696969")
                .province("江苏省")
                .city("常州市")
                .district("天宁区")
                .provinceCyclic(false)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(5)
                .build();
        mPicker.setConfig(cityConfig);
        //显示
        mPicker.showCityPicker();
//监听选择点击事件及返回结果
        mPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                listener.onSelected(province.getName(),city.getName(),district.getName());
            }
        });

    }
   public interface OnCityClickListener{
        void onSelected(String province, String city, String district) ;
   }

}
