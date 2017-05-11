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

public class GoodsInfoAdapter extends BaseAdapter {
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.infoshow_goods_item,null);
            viewHolder = new ViewHolder();
            viewHolder.goods_fromwhere_title = (TextView) convertView.findViewById(R.id.goods_fromwhere_title);
            viewHolder.from = (TextView) convertView.findViewById(R.id.from);
            viewHolder.to = (TextView) convertView.findViewById(R.id.to);
            viewHolder.car_type = (TextView) convertView.findViewById(R.id.car_type);
            viewHolder.price = (TextView) convertView.findViewById(R.id.price);
            viewHolder.weight = (TextView) convertView.findViewById(R.id.weight);
            viewHolder.volume = (TextView) convertView.findViewById(R.id.volume);
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
        TextView goods_fromwhere_title;
        TextView from;
        TextView to;
        TextView car_type;
        TextView price;
        TextView weight;
        TextView volume;//体积
        TextView release_time;//发布时间
    }
}
