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
import com.zhonghong.hlbelogistics.core.home.Activitys.Car_source_details_Activity;
import com.zhonghong.hlbelogistics.core.home.Activitys.Sellers_details_Activity;
import com.zhonghong.hlbelogistics.core.home.Adapter.CarSourceInfoAdapter;

/**
 * Created by Administrator on 2017/3/1.
 */

public class CarSourceInfoFragment extends Fragment {
private ListView carsource_info_listview;
    private CarSourceInfoAdapter CSIAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

      View view = inflater.inflate(R.layout.fragment_car_source,container,false);
       init(view);
        return view;
    }

    private void init( View view ) {
        carsource_info_listview = (ListView) view.findViewById(R.id.carsource_info_listview);
        CSIAdapter = new CarSourceInfoAdapter();
        carsource_info_listview.setAdapter(CSIAdapter);
        carsource_info_listview.setOnItemClickListener(new MyOnItemClickListener());
    }

    private class MyOnItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent=new Intent(getActivity(), Car_source_details_Activity.class);
            startActivity(intent);
        }
    }
}
