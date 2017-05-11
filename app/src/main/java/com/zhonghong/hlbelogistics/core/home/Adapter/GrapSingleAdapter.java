package com.zhonghong.hlbelogistics.core.home.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.zhonghong.hlbelogistics.R;
import com.zhonghong.hlbelogistics.base.Application.MyApplication;
import com.zhonghong.hlbelogistics.bean.GrapSingleOrder;
import com.zhonghong.hlbelogistics.callback.ModifyPlanStatusCallBack;
import com.zhonghong.hlbelogistics.core.home.Activitys.BaidumapActivty;
import com.zhonghong.hlbelogistics.core.home.Activitys.MainActivity;
import com.zhonghong.hlbelogistics.core.home.Activitys.OrderDetailsActivity;
import com.zhonghong.hlbelogistics.respone.AllRespone;
import com.zhonghong.hlbelogistics.utils.NetWorkStatte;
import com.zhonghong.hlbelogistics.utils.OkHttpUtil;
import com.zhonghong.hlbelogistics.utils.SelfDialog;
import com.zhy.autolayout.utils.AutoUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.List;

import okhttp3.Call;

/**
 * 抢单
 * Created by Administrator on 2017/3/17.
 */

public class GrapSingleAdapter extends BaseAdapter {
    private List<GrapSingleOrder> list;
    private Context context;
    // 用来导入布局
    private LayoutInflater inflater = null;
    private SelfDialog dialog;

    public GrapSingleAdapter(Context context) {
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

    public void setItems(List<GrapSingleOrder> orderList) {
        this.list = orderList;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.grab_single_item, null);
            viewHolder = new ViewHolder();
            viewHolder.plan_order_tv = (TextView) convertView.findViewById(R.id.plan_order_tv);
            viewHolder.picplace_tv = (TextView) convertView.findViewById(R.id.picplace_tv);
            viewHolder.arrive_tv = (TextView) convertView.findViewById(R.id.arrive_tv);
            viewHolder.grap_tv = (TextView) convertView.findViewById(R.id.grap_tv);
            viewHolder.details_tv = (TextView) convertView.findViewById(R.id.details_tv);
            viewHolder.order_name = (TextView) convertView.findViewById(R.id.order_name);
            convertView.setTag(viewHolder);
            //对于listview，注意添加这一行，即可在item上使用高度
            AutoUtils.autoSize(convertView);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        GrapSingleOrder go = list.get(position);
        String INVENTORY = go.getINVENTORY_PROVINCE() + go.getINVENTORY_CITY() + go.getINVENTORY_COUNTY() + go.getINVENTORY_ADDRESS();
        String DELIVERY = go.getDELIVERY_PROVINCE() + go.getDELIVERY_CITY() + go.getDELIVERY_COUNTY() + go.getDELIVERY_ADDRESS();
        viewHolder.plan_order_tv.setText(go.getOrderNum());
        viewHolder.picplace_tv.setText(INVENTORY);
        viewHolder.arrive_tv.setText(DELIVERY);
        viewHolder.order_name.setText(go.getGoodsName());
        viewHolder.details_tv.setOnClickListener(new MyOnClickListener(go.getOrderNum(), position));
        viewHolder.grap_tv.setOnClickListener(new MyOnClickListener(go.getOrderNum(), position));
        return convertView;
    }

    class ViewHolder {
        TextView plan_order_tv;
        TextView picplace_tv;
        TextView arrive_tv;
        TextView grap_tv;
        TextView details_tv;
        TextView order_name;

    }

    class MyOnClickListener implements View.OnClickListener {
        private String orderNum;
        private int postion;

        public MyOnClickListener(String orderNum, int postion) {
            this.orderNum = orderNum;
            this.postion = postion;
        }

        @Override
        public void onClick(View v) {
//            Intent in=new Intent(context,BaidumapActivty.class);
//            in.putExtra("orderNum",orderNum);
            switch (v.getId()) {
                case R.id.details_tv:
                    Intent intent = new Intent(context, OrderDetailsActivity.class);
                    intent.putExtra("orderNum", orderNum);
                    context.startActivity(intent);
                    break;
                case R.id.grap_tv:
                    String st = "是否确认抢单";
                    String status = "PSAT001003";
                    setDialog(st, status, orderNum, postion);
                    break;
            }
        }
    }

    //设置dialog
    private void setDialog(String st, final String status, final String orderNum, final int postion) {
        dialog = new SelfDialog(context);
        dialog.setTitle("提示");
        dialog.setMessage(st);
        dialog.setYesOnclickListener("确定", new SelfDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                modify_plan_status(status, orderNum, postion);
                dialog.dismiss();
            }
        });
        dialog.setNoOnclickListener("取消", new SelfDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    //修改订单状态
    public void modify_plan_status(String status, String orderNum, final int postion) {
        String deliveryId = MyApplication.getInstance().getDeliveryId();
        if (NetWorkStatte.networkConnected(context)) {
            if (orderNum == null) {
                Toast.makeText(context, "订单号为空，不能修改", Toast.LENGTH_SHORT).show();
            } else {
                OkHttpUtils.get()
                        .url(OkHttpUtil.URL)
                        .addParams("config", "planOrder")
                        .addParams("type", "4")
                        .addParams("orderId", orderNum)
                        .addParams("deliveryId", deliveryId)
                        .build()
                        .execute(new ModifyPlanStatusCallBack() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Toast.makeText(context, "异常" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(AllRespone response, int id) {
                                if (response != null) {
                                    AllRespone ar = response;
                                    if (ar.getId() == "0") {
                                        list.remove(postion);
                                        notifyDataSetChanged();
                                        Intent intent = new Intent(context, MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        context.startActivity(intent);
                                    } else if (ar.getId() == "-52") {
                                        Toast.makeText(context, ar.getInfo(), Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(context, "空的响应", Toast.LENGTH_SHORT).show();
                                }


                            }
                        });
            }
        } else {
            Toast.makeText(context, "请连接网络", Toast.LENGTH_SHORT).show();
        }

    }
}
