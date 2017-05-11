package com.zhonghong.hlbelogistics.core.home.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * viewpager 中的fragment的adapter
 * Created by Administrator on 2017/2/10.
 */
public class myFragmentPageAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragmentList;
    public myFragmentPageAdapter(FragmentManager fm,ArrayList<Fragment> fragmentList) {
        super(fm);
        this.fragmentList=fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }
    // 返回这个是强制ViewPager不缓存，每次滑动都刷新视图
    @Override
    public int getItemPosition(Object object) {
        return  POSITION_NONE;
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
