package com.zhonghong.hlbelogistics.core.home.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.zhonghong.hlbelogistics.R;
import com.zhonghong.hlbelogistics.core.home.Activitys.Buyers_details_Activity;
import com.zhonghong.hlbelogistics.core.home.Activitys.Sellers_details_Activity;
import com.zhonghong.hlbelogistics.core.home.Adapter.BuyerInfoAdapter;

/**
 * Created by Administrator on 2017/3/1.
 */

public class SellerInfoFragment extends Fragment {
    private ListView sellinfo_listview;
    private BuyerInfoAdapter SIAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

      View view = inflater.inflate(R.layout.fragment_sellerinfo,container,false);
        init(view);
        return view;
    }

    private void init(View view) {

        sellinfo_listview = (ListView) view.findViewById(R.id.sellinfo_listview);
        //SIAdapter = new BuyerInfoAdapter();
        sellinfo_listview.setAdapter(SIAdapter);
        sellinfo_listview.setOnItemClickListener(new MyOnItemClickListener());
    }

    private class MyOnItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent=new Intent(getActivity(), Sellers_details_Activity.class);
            startActivity(intent);
        }
    }
}
