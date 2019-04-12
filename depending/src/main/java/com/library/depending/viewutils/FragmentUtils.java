package com.library.depending.viewutils;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.List;

public class FragmentUtils {
    private static FragmentUtils fragmentUtils;

    /**
     * 单例模式
     */
    public static FragmentUtils getInstance() {
        if (fragmentUtils == null) {
            fragmentUtils = new FragmentUtils();
        }
        return fragmentUtils;
    }


    /**
     * 显示当前 Fragment
     * 1、 实例化  Fragment 显示  processView
     * 2、 移除实例 Fragment    removeFragment  不保存状态
     *
     * @param manager  getSupportFragmentManager();
     * @param var1     FrameLayout ID
     * @param fragment new Fragment();
     */
    public void processView(FragmentManager manager, @IdRes int var1, Fragment fragment) {
        FragmentTransaction transaction = manager.beginTransaction();
        removeFragment(manager, transaction);
        transaction.replace(var1, fragment);
        transaction.commit();
    }

    /**
     * 去除（隐藏）所有的Fragment
     * 与processView
     */
    private void removeFragment(FragmentManager manager, FragmentTransaction transaction) {
        List<Fragment> frmlist = manager.getFragments();
        for (int i = 0; i < frmlist.size(); i++) {
            transaction.remove(frmlist.get(i));
        }
    }


    /**
     * 添加fragment   用 showFragment显示
     * 1。先添加 addFragment
     * 2、显示某一个  showFragment  其他隐藏掉   保存状态
     *
     * @param var1     FrameLayout ID
     * @param fragment new Fragment();
     */
    public void addFragment(FragmentManager manager, @IdRes int var1, Fragment fragment) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(var1, fragment);
        transaction.hide(fragment);
        transaction.commit();
    }

    /**
     * 显示fragment
     *
     * @param fragment
     */
    public void showFragment(FragmentManager manager,Fragment fragment) {
        FragmentTransaction  transaction = manager.beginTransaction();
        hideFragment(manager,transaction);
        transaction.show(fragment);
        transaction.commit();
    }

    /*
     * 去除（隐藏）所有的Fragment
     * */
    private void hideFragment(FragmentManager manager, FragmentTransaction transaction) {
        List<Fragment> frmlist = manager.getFragments();
        for (int i = 0; i < frmlist.size(); i++) {
            transaction.hide(frmlist.get(i));
        }
    }
}
