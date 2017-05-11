package com.zhonghong.hlbelogistics.core.home.Fragments;


import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.zhonghong.hlbelogistics.R;
import com.zhonghong.hlbelogistics.core.home.Adapter.SellerRecycleViewAdapter;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/10.
 */

public class InfoShowFragment extends Fragment {

    private ViewPager infoshow_viewpager;
    private View view;
    private int postion;

    private RadioGroup infoshowo_rgTools;
    private RadioButton infoshow_rbBuyers;
    private RadioButton infoshow_rbSellers;
    private RadioButton infoshow_rbGoods;
    private RadioButton infoshow_rbCarSource;


    private RecyclerView recyclerView;
    private List<String> mDatas;
    private SellerRecycleViewAdapter sellerRecycleViewAdapter;
    //承载Fragment的FrameLayout
    private FrameLayout centent_Fram;
    //声明4个Fragment
    private BuyerInfoFragment BIFragment;
    private SellerInfoFragment SIFragment;
    private GoodsInfoFragment GIFragment;
    private CarSourceInfoFragment CSIFragment;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_infoshow,container,false);
        initData();
        initView();
        return view;
    }

    /**
     * 初始化控件
     */
    private void initView(){
        infoshowo_rgTools= (RadioGroup) view.findViewById(R.id.infoshowo_rgTools);
        //买家信息
        infoshow_rbBuyers= (RadioButton) view.findViewById(R.id.infoshow_rbBuyers);
        //卖家信息
        infoshow_rbSellers= (RadioButton) view.findViewById(R.id.infoshow_rbSellers);
        //货物信息
        infoshow_rbGoods= (RadioButton) view.findViewById(R.id.infoshow_rbGoods);
        //车源信息
        infoshow_rbCarSource= (RadioButton) view.findViewById(R.id.infoshow_rbCarSource);
        infoshowo_rgTools.setOnCheckedChangeListener(new  myCheckChangeListener());
        infoshow_rbBuyers.setChecked(true);

        // 初始化
        centent_Fram = (FrameLayout) view.findViewById(R.id.content_fragment);

        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction  fragmentTransaction = fm.beginTransaction();
        BIFragment = new BuyerInfoFragment();
        fragmentTransaction.replace(R.id.content_fragment,BIFragment);
        fragmentTransaction.commit();
    }
    /**
     * radioButton切换fragment
     */
    private class myCheckChangeListener implements RadioGroup.OnCheckedChangeListener{
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            FragmentManager fm = getChildFragmentManager();
            FragmentTransaction  fragmentTransaction = fm.beginTransaction();
            switch (checkedId) {
                case R.id.infoshow_rbBuyers:
                    postion=R.id.infoshow_rbBuyers;
                    if (BIFragment == null)
                    {
                        BIFragment = new BuyerInfoFragment();
                    }
                    fragmentTransaction.replace(R.id.content_fragment,BIFragment);
                    fragmentTransaction.commit();
                break;
                case R.id.infoshow_rbSellers:
                    postion=R.id.infoshow_rbSellers;
                    if (SIFragment == null)
                    {
                        SIFragment = new SellerInfoFragment();
                    }
                    fragmentTransaction.replace(R.id.content_fragment,SIFragment);
                    fragmentTransaction.commit();
                    break;
                case R.id.infoshow_rbGoods:
                    postion=R.id.infoshow_rbGoods;
                    if (GIFragment == null)
                    {
                        GIFragment = new GoodsInfoFragment();
                    }
                    fragmentTransaction.replace(R.id.content_fragment,GIFragment);
                    fragmentTransaction.commit();
                    break;
                case R.id.infoshow_rbCarSource:
                    postion=R.id.infoshow_rbCarSource;
                    if (CSIFragment == null)
                    {
                        CSIFragment = new CarSourceInfoFragment();
                    }
                    fragmentTransaction.replace(R.id.content_fragment,CSIFragment);
                    fragmentTransaction.commit();
                    break;
            }



        }
    }

    private void initData() {
        mDatas = new ArrayList<String>();
        for ( int i=0; i < 40; i++) {
            mDatas.add( "item"+i);
        }

    }





}
