package com.library.depending.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.library.depending.bean.City;
import com.library.depending.bean.District;
import com.library.depending.bean.Province;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citypickerview.CityPickerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PickerUtils {
    private static PickerUtils pickerUtils;

    /**
     * 单例模式
     */
    public static PickerUtils getInstance() {
        if (pickerUtils == null) {
            pickerUtils = new PickerUtils();
        }
        return pickerUtils;
    }

    /**
     * @param context
     * @param province 江苏省
     * @param city     常州市
     * @param district 天宁区
     * @param listener
     */
    public void showCityPicker(Context context, String province, String city, String district, final OnCityClickListener listener) {
        //添加默认的配置，不需要自己定义，当然也可以自定义相关熟悉，详细属性请看demo
        CityPickerView mPicker = new CityPickerView();
        mPicker.init(context);
        CityConfig cityConfig = new CityConfig.Builder().titleTextSize(14)
                .title("地址选择")
                .titleBackgroundColor("#FFFFFF")
                .titleTextColor("#696969")
                .confirTextColor("#696969")
                .cancelTextColor("#696969")
                .province(province)
                .setCityWheelType(CityConfig.WheelType.PRO_CITY_DIS)//显示类，只显示省份一级，显示省市两级还是显示省市区三级
                .city(city)
                .district(district)
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
                listener.onSelected(new Province(province.getId(), province.getName()), new City(city.getId(), city.getName()), new District(district.getId(), district.getName()));
            }
        });
    }

    /**
     * @param context
     * @param type     new boolean[]{true, true, true, true, true, true}
     * @param listener
     */
    public void showTimePicker(Context context, boolean[] type, final OnTimeListener listener) {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        //startDate.set(2013,1,1);
        Calendar endDate = Calendar.getInstance();
        //endDate.set(2020,1,1);
        //正确设置方式 原因：注意事项有说明
        startDate.set(1970, 0, 1);
        endDate.set(2200, 11, 31);
        TimePickerView pvTime = new TimePickerBuilder(context, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                listener.onTimeSelect(date, v);
            }
        })
                .setType(type)// 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确认")//确认按钮文字
                .setContentTextSize(18)//滚轮文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleText("时间选择")//标题文字
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                .setCancelColor(Color.BLUE)//取消按钮文字颜色
                .setTitleBgColor(0xFFFFFFFF)//标题背景颜色 Night mode
                .setBgColor(0xFFFFFFFF)//滚轮背景颜色 Night mode
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setRangDate(startDate, endDate)//起始终止年月日设定
//                .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒
//                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                .isDialog(true)//是否显示为对话框样式
                .build();
        pvTime.show();
    }

    /**
     * 日期转字符串
     */
    public String getDate(Date data, String format) {
        return new SimpleDateFormat(format, Locale.getDefault()).format(data);
    }

    public interface OnTimeListener {
        void onTimeSelect(Date date, View v);
    }

    public interface OnCityClickListener {
        void onSelected(Province province, City city, District district);
    }

}
