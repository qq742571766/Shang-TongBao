package com.zhonghong.hlbelogistics.core.home.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhonghong.hlbelogistics.R;
import com.zhonghong.hlbelogistics.bean.IngPlanOrder;
import com.zhonghong.hlbelogistics.core.home.Activitys.IngPlanOrderActivity;

import java.util.List;

/**
 * 进行中计划
 */
public class IngPlanrAdapter extends BaseAdapter {
    private Context context;
    private List<IngPlanOrder> list;
    private LayoutInflater inflater;

    public IngPlanrAdapter(Context context) {
        super();
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
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

    public void setItems(List<IngPlanOrder> orderList) {
        this.list = orderList;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView) {
            convertView = LayoutInflater.from(context).inflate(R.layout.ing_plan_fragment_item, parent, false);
            holder = new ViewHolder();
            holder.details_lt = (LinearLayout) convertView.findViewById(R.id.details_lt);
            holder.plan_order_tv = (TextView) convertView.findViewById(R.id.plan_order_tv);
            holder.planOrder_Num_tv = (TextView) convertView.findViewById(R.id.planOrder_Num_tv);
            holder.start_time_tv = (TextView) convertView.findViewById(R.id.start_time_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.plan_order_tv.setText(list.get(position).getPlanId());
        holder.planOrder_Num_tv.setText(list.get(position).getOrderNum() + "个");
        if (list.get(position).getOrderNum() == 0) {
            list.remove(position);
            notifyDataSetChanged();
        }
        holder.start_time_tv.setText(list.get(position).getStart_time());
        holder.details_lt.setOnClickListener(new MyOnClickListener(list.get(position).getPlanId(), list.get(position).getCarId(), list.get(position).getDeliveryPhone()));
        return convertView;
    }

    private class ViewHolder {
        private LinearLayout details_lt;
        private TextView plan_order_tv;
        private TextView planOrder_Num_tv;
        private TextView start_time_tv;
    }

    class MyOnClickListener implements View.OnClickListener {
        private String planId;
        private String carId;
        private String deliveryPhone;

        public MyOnClickListener(String planId, String carId, String deliveryPhone) {
            this.planId = planId;
            this.carId = carId;
            this.deliveryPhone = deliveryPhone;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.details_lt:
                    Intent intent = new Intent(context, IngPlanOrderActivity.class);
                    intent.putExtra("title", planId);
                    intent.putExtra("carId", carId);
                    intent.putExtra("deliveryPhone", deliveryPhone);
                    context.startActivity(intent);
                    break;
            }
        }
    }
}
