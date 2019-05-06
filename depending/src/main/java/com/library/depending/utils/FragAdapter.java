package com.library.depending.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;
/**
 * @author：zhangerpeng
 * 版本：
 * 日期：2019/5/6 22:45
 * 描述：
 * Fragment 适配器
 */
public class FragAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> list;

    public FragAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

}
