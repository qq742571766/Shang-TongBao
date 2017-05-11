package com.zhonghong.hlbelogistics.core.home.Adapter;

import android.app.AlertDialog;
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
import com.zhonghong.hlbelogistics.bean.PlanOrder;
import com.zhonghong.hlbelogistics.callback.ModifyPlanStatusCallBack;
import com.zhonghong.hlbelogistics.core.home.Activitys.WaitPlanOnderActivity;
import com.zhonghong.hlbelogistics.respone.AllRespone;
import com.zhonghong.hlbelogistics.utils.NetWorkStatte;
import com.zhonghong.hlbelogistics.utils.OkHttpUtil;
import com.zhonghong.hlbelogistics.utils.SelfDialog;
import com.zhy.autolayout.utils.AutoUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import java.util.List;
import okhttp3.Call;




/**
 * 待接取计划
 * Created by Administrator on 2017/3/13.
 */

public class WaitPlanCenterAdapter extends BaseAdapter{
    private List<PlanOrder> orderList;
    // 上下文
    private Context context;
    // 用来导入布局
    private LayoutInflater inflater = null;
    private String title;
    private SelfDialog dialog;

    private MotifyPlanListener modifyPlanListener;// 监听

    public WaitPlanCenterAdapter(Context context){
        this.context=context;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        if(orderList!=null){
            return orderList.size();
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
    /**
     * <p>Discription: [设置列表项]</p >
     */
    public void setItems(List<PlanOrder> orderList) {
        this.orderList=orderList;
        notifyDataSetChanged();
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.plan_order_item, null);
            vHolder = new ViewHolder();
            vHolder.linearLayout= (LinearLayout) convertView.findViewById(R.id.details);
            vHolder.tv= (TextView) convertView.findViewById(R.id.plan_order_tv);
            vHolder.access_btn= (TextView) convertView.findViewById(R.id.access_btn);
            vHolder.refuse_btn= (TextView) convertView.findViewById(R.id.refuse_btn);
            vHolder.planOrder_Num_tv= (TextView) convertView.findViewById(R.id.planOrder_Num_tv);
            vHolder.plan_start_time= (TextView) convertView.findViewById(R.id.plan_start_time);
            vHolder.plan_end_time= (TextView) convertView.findViewById(R.id.plan_end_time);
            convertView.setTag(vHolder);
            //对于listview，注意添加这一行，即可在item上使用高度
            AutoUtils.autoSize(convertView);
        } else {
            vHolder = (ViewHolder) convertView.getTag();
        }
        PlanOrder po=orderList.get(position);
        vHolder.tv.setText(po.getPlanId());
        if(po.getPlanStartTime().equals("")){
            vHolder.plan_start_time.setVisibility(View.GONE);
            vHolder.plan_end_time.setVisibility(View.GONE);
        }else if(po.getPlanEndTime().equals("")){
            vHolder.plan_end_time.setVisibility(View.GONE);
        }else {
            vHolder.plan_start_time.setText(po.getPlanStartTime());
            vHolder.plan_end_time.setText(po.getPlanEndTime());
        }
        vHolder.planOrder_Num_tv.setText(po.getOrderNumList()+"个");
        title=vHolder.tv.getText().toString();
        vHolder.linearLayout.setOnClickListener(new MyOnClickListener(title,position));
        vHolder.access_btn.setOnClickListener(new MyOnClickListener(title,position));
        vHolder.refuse_btn.setOnClickListener(new MyOnClickListener(title,position));
        return convertView;
    }




    class ViewHolder {
        LinearLayout linearLayout;
        TextView tv;
        TextView planOrder_Num_tv;
        TextView access_btn;
        TextView refuse_btn;
        TextView plan_start_time;
        TextView plan_end_time;
    }
    class MyOnClickListener implements View.OnClickListener{
        private String planId;
        private int postion;
        public MyOnClickListener(String planId,int postion){
            this.planId=planId;
            this.postion=postion;
        }
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.details:
                    Intent intent=new Intent(context,WaitPlanOnderActivity.class);
                     intent.putExtra("title",planId);
                    context.startActivity(intent);
                break;
                case R.id.access_btn:
                    String str="是否确认接取？";
                    String status="PSAT001003";
                    setDialog(str,status,planId,postion);
                    break;
                case R.id.refuse_btn:
                    String st="是否确认拒绝？";
                    String statu="PSAT001004";
                    setDialog(st,statu,planId,postion);
                    break;
            }
        }

    }
    //设置dialog
    private void setDialog(String st, final String status, final String planId,final int postion){
        dialog = new SelfDialog(context);
        dialog.setTitle("提示");
        dialog.setMessage(st);
        dialog.setYesOnclickListener("确定", new SelfDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                modify_plan_status(status,planId,postion);
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
    public void modify_plan_status(String status,String planId,final int postion){
        if (NetWorkStatte.networkConnected(context)){
            if(planId==null){
                Toast.makeText(context,"计划单号为空，不能修改",Toast.LENGTH_SHORT).show();
            }else {
                OkHttpUtils.get()
                        .url(OkHttpUtil.URL)
                        .addParams("config","planOrder")
                        .addParams("type","3")
                        .addParams("planId", planId)
                        .addParams("status",status)
                        .build()
                        .execute(new ModifyPlanStatusCallBack() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Toast.makeText(context,"异常"+e.getMessage(),Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(AllRespone response, int id) {
                                if(response!=null){
                                    AllRespone ar=response;
                                    if(ar.getId()=="0"){
                                        orderList.remove(postion);
                                        notifyDataSetChanged();
                                        if(modifyPlanListener!=null){
                                            modifyPlanListener.onSucess();
                                        }
                                    }else if(ar.getId()=="-52"){
                                        Toast.makeText(context,ar.getInfo(),Toast.LENGTH_SHORT).show();
                                    }
                                }else {
                                    Toast.makeText(context,"空的响应",Toast.LENGTH_SHORT).show();
                                }


                            }
                        });
            }
        }else {
            Toast.makeText(context,"请连接网络",Toast.LENGTH_SHORT).show();
        }

    }
    public MotifyPlanListener getModifyPlanListener() {
        return modifyPlanListener;
    }

    public void setModifyPlanListener(MotifyPlanListener modifyPlanListener) {
        this.modifyPlanListener = modifyPlanListener;
    }

    public interface MotifyPlanListener {
        void onSucess();
    }
}
