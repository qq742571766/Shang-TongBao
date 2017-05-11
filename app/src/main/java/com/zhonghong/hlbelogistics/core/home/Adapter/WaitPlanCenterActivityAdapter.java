package com.zhonghong.hlbelogistics.core.home.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhonghong.hlbelogistics.R;
import com.zhonghong.hlbelogistics.bean.PlanToPlanOrder;
import com.zhonghong.hlbelogistics.core.home.Activitys.BaidumapActivty;
import com.zhonghong.hlbelogistics.core.home.Activitys.OrderDetailsActivity;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * 待接取计划订单列表
 * Created by Administrator on 2017/3/15.
 */

public class WaitPlanCenterActivityAdapter extends BaseAdapter {
    private List<PlanToPlanOrder> list;
    private Context context;
    // 用来导入布局
    private LayoutInflater inflater = null;

    public WaitPlanCenterActivityAdapter(Context context){
        this.context=context;
        inflater=LayoutInflater.from(context);

    }


    @Override
    public int getCount() {
        if(list!=null){
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
    public void setItems(List<PlanToPlanOrder> orderList) {
        this.list=orderList;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.plan_center_activity_listview_item,null);
            viewHolder=new ViewHolder();
            viewHolder.order_tv= (TextView) convertView.findViewById(R.id.order_tv);
            viewHolder.pick_up_place_tv= (TextView) convertView.findViewById(R.id.pick_up_place_tv);
            viewHolder.arrive_tv= (TextView) convertView.findViewById(R.id.arrive_tv);
            viewHolder.order_name= (TextView) convertView.findViewById(R.id.order_name);
//            viewHolder.pick_up_place_iv= (ImageView) convertView.findViewById(R.id.pick_up_place_iv);
//            viewHolder.arrive_iv= (ImageView) convertView.findViewById(R.id.arrive_iv);
            viewHolder.details= (LinearLayout) convertView.findViewById(R.id.details);
            convertView.setTag(viewHolder);
            //对于listview，注意添加这一行，即可在item上使用高度
            AutoUtils.autoSize(convertView);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        PlanToPlanOrder toPlanOrder=list.get(position);
        String INVENTORY=toPlanOrder.getINVENTORY_PROVINCE()+toPlanOrder.getINVENTORY_CITY()+toPlanOrder.getINVENTORY_COUNTY()+toPlanOrder.getINVENTORY_ADDRESS();
        String DELIVERY=toPlanOrder.getDELIVERY_PROVINCE()+toPlanOrder.getDELIVERY_CITY()+toPlanOrder.getDELIVERY_COUNTY()+toPlanOrder.getDELIVERY_ADDRESS();
        viewHolder.order_tv.setText(toPlanOrder.getOrderNum());
        viewHolder.pick_up_place_tv.setText(INVENTORY);
        viewHolder.arrive_tv.setText(DELIVERY);
        viewHolder.order_name.setText(toPlanOrder.getGoodsName());
        viewHolder.details.setOnClickListener(new MyOnClickListener(toPlanOrder.getOrderNum()));
//        viewHolder.pick_up_place_iv.setOnClickListener(new MyOnClickListener(toPlanOrder.getOrderNum()));
//        viewHolder.arrive_iv.setOnClickListener(new MyOnClickListener(toPlanOrder.getOrderNum()));
        return convertView;
    }
    class ViewHolder {
        TextView order_tv;
        TextView pick_up_place_tv;
        TextView arrive_tv;
        TextView order_name;
//        ImageView pick_up_place_iv;
//        ImageView arrive_iv;
        LinearLayout details;

    }
    class MyOnClickListener implements View.OnClickListener{
        String orderNum;
        public MyOnClickListener(String orderNum){
            this.orderNum=orderNum;

        }

        @Override
        public void onClick(View v) {
            Intent intent=new Intent(context,BaidumapActivty.class);
            intent.putExtra("orderNum",orderNum);
            switch (v.getId()) {
//                case R.id.pick_up_place_iv:
//                    context.startActivity(intent);
//                    break;
//                case R.id.arrive_iv:
//                    context.startActivity(intent);
//                    break;
                case R.id.details:
                    Intent inte=new Intent(context, OrderDetailsActivity.class);
                    inte.putExtra("orderNum",orderNum);
                    context.startActivity(inte);
                    break;
            }

        }
    }
}
