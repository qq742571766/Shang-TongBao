package com.zhonghong.hlbelogistics.core.home.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhonghong.hlbelogistics.R;
import com.zhonghong.hlbelogistics.bean.Buyer;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/1.
 */

public class BuyerInfoAdapter extends BaseAdapter {

    ArrayList<Buyer> buyerList ;
/* modeify 修改*/
    public BuyerInfoAdapter(ArrayList<Buyer> buyerList) {
        this.buyerList = buyerList;
    }
    @Override
    public int getCount() {
        if (buyerList!=null)
        {
            return buyerList.size();
        }
        return 0;
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
        ViewHolder vHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.infoshow_buyers_item, null);
            vHolder = new ViewHolder();
            vHolder.gd_title = (TextView) convertView.findViewById(R.id.gd_title);
            vHolder.price_type = (TextView) convertView.findViewById(R.id.price_type);
            vHolder.price = (TextView) convertView.findViewById(R.id.price);
            vHolder.count = (TextView) convertView.findViewById(R.id.count);
            vHolder.deal_num = (TextView) convertView.findViewById(R.id.deal_num);
            convertView.setTag(vHolder);
            //对于listview，注意添加这一行，即可在item上使用高度
            AutoUtils.autoSize(convertView);
        } else {
            vHolder = (ViewHolder) convertView.getTag();
        }
        Buyer buyer = buyerList.get(position);
        vHolder.gd_title.setText(buyer.getGd_title());
        vHolder.count.setText(buyer.getTotal());
        vHolder.price.setText(buyer.getPrice());
        vHolder.price_type.setText(buyer.getPrice_type());
        vHolder.deal_num.setText(buyer.getDealed_num());

        return convertView;
    }

    class ViewHolder {
        TextView gd_title;
        TextView price_type;
        TextView price;
        TextView count;
        TextView deal_num;
    }


}
