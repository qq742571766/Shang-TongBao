package com.zhonghong.hlbelogistics.core.home.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhonghong.hlbelogistics.R;

/**
 * Created by Administrator on 2017/3/1.
 */

public class CarSourceInfoAdapter extends BaseAdapter {
    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null)
        {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.infoshow_carsource_item,null);
            viewHolder = new ViewHolder();
            viewHolder.infoshow_buyers_item_title = (TextView) convertView.findViewById(R.id.infoshow_buyers_item_title);
            viewHolder.car_from = (TextView) convertView.findViewById(R.id.car_from);
            viewHolder.car_to = (TextView) convertView.findViewById(R.id.car_to);
            viewHolder.car_lenght = (TextView) convertView.findViewById(R.id.car_lenght);
            viewHolder.price = (TextView) convertView.findViewById(R.id.price);
            viewHolder.car_volume = (TextView) convertView.findViewById(R.id.car_volume);
            viewHolder.total = (TextView) convertView.findViewById(R.id.total);
            viewHolder.release_time = (TextView) convertView.findViewById(R.id.release_time);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }
    class ViewHolder{
        TextView infoshow_buyers_item_title;
        TextView car_from;
        TextView car_to;
        TextView car_lenght;
        TextView car_volume;
        TextView price;
        TextView total;//体积
        TextView release_time;//发布时间
    }
}
