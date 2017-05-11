package com.zhonghong.hlbelogistics.core.home.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhonghong.hlbelogistics.R;
import com.zhonghong.hlbelogistics.bean.PlanToPlanOrder;
import com.zhonghong.hlbelogistics.callback.ModifyPlanStatusCallBack;
import com.zhonghong.hlbelogistics.core.home.Activitys.Exception_report_Activity;
import com.zhonghong.hlbelogistics.core.home.Activitys.MapLineActivity;
import com.zhonghong.hlbelogistics.core.home.Activitys.OrderDetailsActivity;
import com.zhonghong.hlbelogistics.respone.AllRespone;
import com.zhonghong.hlbelogistics.utils.NetWorkStatte;
import com.zhonghong.hlbelogistics.utils.OkHttpUtil;
import com.zhonghong.hlbelogistics.utils.SelfDialog;
import com.zhy.autolayout.utils.AutoUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.Call;

/**
 * 进行中计划
 * Created by Administrator on 2017/3/16.
 */
public class IngPlanOrderAdapter extends BaseAdapter {
    private String deliveryPhone;
    private String carId;
    private String planId;
    private Context context;
    private SelfDialog selfDialog;
    private LayoutInflater inflater = null;
    private List<PlanToPlanOrder> list;
    private String now_status;
    private MotifyPlanListener modifyPlanListener;

    public IngPlanOrderAdapter(Context context, String carId, String deliveryPhone, String planId) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.carId = carId;
        this.deliveryPhone = deliveryPhone;
        this.planId = planId;
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
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void setItems(List<PlanToPlanOrder> orderList) {
        this.list = orderList;
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.ing_plan_order_item, null);
            viewHolder = new ViewHolder();
            viewHolder.plan_order_tv = (TextView) convertView.findViewById(R.id.plan_order_tv);
            viewHolder.pick_place_tv = (TextView) convertView.findViewById(R.id.pick_place_tv);
            viewHolder.arrive_place_tv = (TextView) convertView.findViewById(R.id.arrive_place_tv);
            viewHolder.status_tv = (TextView) convertView.findViewById(R.id.status_tv);
            viewHolder.modify_status_tv = (TextView) convertView.findViewById(R.id.modify_status_tv);
            viewHolder.problem_report_tv = (TextView) convertView.findViewById(R.id.problem_report_tv);
            viewHolder.details_tv = (TextView) convertView.findViewById(R.id.details_tv);
            viewHolder.map_tv = (TextView) convertView.findViewById(R.id.map_tv);
            viewHolder.goods_name_tv = (TextView) convertView.findViewById(R.id.goods_name_tv);
            viewHolder.bottom_lt = (LinearLayout) convertView.findViewById(R.id.bottom_lt);
            convertView.setTag(viewHolder);
            //对于listview，注意添加这一行，即可在item上使用高度
            AutoUtils.autoSize(convertView);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        PlanToPlanOrder planToPlanOrder = list.get(position);
        String INVENTORY_place = planToPlanOrder.getINVENTORY_PROVINCE() + planToPlanOrder.getINVENTORY_CITY() + planToPlanOrder.getINVENTORY_COUNTY() + planToPlanOrder.getINVENTORY_ADDRESS();
        String DELIVERY = planToPlanOrder.getDELIVERY_PROVINCE() + planToPlanOrder.getDELIVERY_CITY() + planToPlanOrder.getDELIVERY_COUNTY() + planToPlanOrder.getDELIVERY_ADDRESS();
        viewHolder.plan_order_tv.setText(planToPlanOrder.getOrderNum());
        viewHolder.goods_name_tv.setText(planToPlanOrder.getGoodsName());
        viewHolder.pick_place_tv.setText(INVENTORY_place);
        viewHolder.arrive_place_tv.setText(DELIVERY);
        switch (planToPlanOrder.getStatus()) {
            case "OSAT001001":
                viewHolder.status_tv.setText("待分配");
                break;
            case "OSAT001002":
                viewHolder.status_tv.setText("已分配");
                break;
            case "OSAT001003":
                viewHolder.status_tv.setText("出库中");
                break;
            case "OSAT001004":
                viewHolder.status_tv.setText("配送中");
                break;
            case "OSAT001005":
                viewHolder.status_tv.setText("已配送");
                break;
            case "OSAT001006":
                viewHolder.status_tv.setText("已确认");
                break;
            case "OSAT001007":
                viewHolder.status_tv.setText("问题单");
                break;
        }
        now_status = viewHolder.status_tv.getText().toString();
        switch (planToPlanOrder.getStatus()) {
            case "OSAT001002":
                viewHolder.modify_status_tv.setVisibility(View.VISIBLE);
                viewHolder.problem_report_tv.setVisibility(View.VISIBLE);
                viewHolder.map_tv.setVisibility(View.GONE);
                viewHolder.modify_status_tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selfDialog = new SelfDialog(context);
                        selfDialog.setTitle("提示");
                        selfDialog.setMessage("是否修改该条订单状态？");
                        selfDialog.setYesOnclickListener("出库中", new SelfDialog.onYesOnclickListener() {
                            @Override
                            public void onYesClick() {
                                viewHolder.status_tv.setText("出库中");
                                now_status = "出库中";
                                String st = "OSAT001003";
                                modify_plan_status(st, list.get(position).getOrderNum());
                                selfDialog.dismiss();
                            }
                        });
                        selfDialog.setNoOnclickListener("取消", new SelfDialog.onNoOnclickListener() {
                            @Override
                            public void onNoClick() {
                                selfDialog.dismiss();
                            }
                        });
                        selfDialog.show();
                    }
                });
                break;
            case "OSAT001003":
                viewHolder.modify_status_tv.setVisibility(View.VISIBLE);
                viewHolder.problem_report_tv.setVisibility(View.VISIBLE);
                viewHolder.map_tv.setVisibility(View.GONE);
                viewHolder.modify_status_tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selfDialog = new SelfDialog(context);
                        selfDialog.setTitle("提示");
                        selfDialog.setMessage("是否修改该条订单状态？");
                        selfDialog.setYesOnclickListener("配送中", new SelfDialog.onYesOnclickListener() {
                            @Override
                            public void onYesClick() {
                                viewHolder.status_tv.setText("配送中");
                                now_status = "配送中";
                                String st = "OSAT001004";
                                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                                String time = formatter.format(curDate);
                                change_status(st, list.get(position).getOrderNum(), time);
                                selfDialog.dismiss();
                            }
                        });
                        selfDialog.setNoOnclickListener("取消", new SelfDialog.onNoOnclickListener() {
                            @Override
                            public void onNoClick() {
                                selfDialog.dismiss();
                            }
                        });
                        selfDialog.show();
                    }
                });
                break;
            case "OSAT001004":
                viewHolder.modify_status_tv.setVisibility(View.VISIBLE);
                viewHolder.problem_report_tv.setVisibility(View.VISIBLE);
                viewHolder.map_tv.setVisibility(View.VISIBLE);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                final String time = formatter.format(curDate);
                viewHolder.map_tv.setOnClickListener(new MyOnClickListener(list.get(position).getOrderNum(), list.get(position).getStart_time(), time));
                viewHolder.modify_status_tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selfDialog = new SelfDialog(context);
                        selfDialog.setTitle("提示");
                        selfDialog.setMessage("是否修改该条订单状态？");
                        selfDialog.setYesOnclickListener("已配送", new SelfDialog.onYesOnclickListener() {
                            @Override
                            public void onYesClick() {
                                viewHolder.status_tv.setText("已配送");
                                now_status = "已配送";
                                String st = "OSAT001005";
                                change_status(st, list.get(position).getOrderNum(), time);
                                selfDialog.dismiss();
                            }
                        });
                        selfDialog.setNoOnclickListener("取消", new SelfDialog.onNoOnclickListener() {
                            @Override
                            public void onNoClick() {
                                selfDialog.dismiss();
                            }
                        });
                        selfDialog.show();
                    }
                });
                break;
            case "OSAT001007":
                viewHolder.bottom_lt.setVisibility(View.GONE);
                break;
            case "OSAT001005":

                viewHolder.map_tv.setOnClickListener(new MyOnClickListener(list.get(position).getOrderNum(), list.get(position).getStart_time(), list.get(position).getEnd_time()));
                break;
        }
        viewHolder.details_tv.setOnClickListener(new MyOnClickListener(planToPlanOrder.getOrderNum(), planToPlanOrder.getPhone(), planToPlanOrder.getContacts()));
        viewHolder.problem_report_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Exception_report_Activity.class);
                intent.putExtra("orderNum", list.get(position).getOrderNum());
                intent.putExtra("phone", list.get(position).getPhone());
                intent.putExtra("contacts", list.get(position).getContacts());
                intent.putExtra("deliveryPhone", deliveryPhone);
                intent.putExtra("carId", carId);
                intent.putExtra("planId", planId);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    private class ViewHolder {
        TextView plan_order_tv;
        TextView pick_place_tv;
        TextView arrive_place_tv;
        TextView status_tv;
        TextView modify_status_tv;
        TextView problem_report_tv;
        TextView details_tv;
        TextView map_tv;
        TextView goods_name_tv;
        LinearLayout bottom_lt;
    }

    private class MyOnClickListener implements View.OnClickListener {
        private String orderId;
        private String startTime;
        private String endTime;

        MyOnClickListener(String orderId, String startTime, String endTime) {
            this.orderId = orderId;
            this.startTime = startTime;
            this.endTime = endTime;

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.details_tv:
                    Intent inte = new Intent(context, OrderDetailsActivity.class);
                    inte.putExtra("orderNum", orderId);
                    context.startActivity(inte);
                    break;
                case R.id.map_tv:
                    Intent intent = new Intent(context, MapLineActivity.class);
                    intent.putExtra("startTime", startTime);
                    intent.putExtra("endTime", endTime);
                    context.startActivity(intent);
                    break;
            }
        }
    }

    private void modify_plan_status(final String status, String orderNum) {
        if (NetWorkStatte.networkConnected(context)) {
            if (orderNum == null) {
                Toast.makeText(context, "订单号为空，不能修改", Toast.LENGTH_SHORT).show();
            } else {
                OkHttpUtils.get()
                        .url(OkHttpUtil.URL)
                        .addParams("config", "order")
                        .addParams("type", "1")
                        .addParams("orderNum", orderNum)
                        .addParams("status", status)
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
                                        if (modifyPlanListener != null) {
                                            modifyPlanListener.onSucess();
                                        }

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

    private void change_status(final String status, String orderNum, String time) {
        if (NetWorkStatte.networkConnected(context)) {
            if (orderNum == null) {
                Toast.makeText(context, "订单号为空，不能修改", Toast.LENGTH_SHORT).show();
            } else {
                OkHttpUtils.get()
                        .url(OkHttpUtil.URL)
                        .addParams("config", "order")
                        .addParams("type", "1")
                        .addParams("orderNum", orderNum)
                        .addParams("status", status)
                        .addParams("time", time)
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
                                        if (modifyPlanListener != null) {
                                            modifyPlanListener.onSucess();
                                        }
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

    public void setModifyPlanListener(MotifyPlanListener modifyPlanListener) {
        this.modifyPlanListener = modifyPlanListener;
    }

    public interface MotifyPlanListener {
        void onSucess();
    }
}
