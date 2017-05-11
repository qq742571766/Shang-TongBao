package com.zhonghong.hlbelogistics.core.home.Activitys;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.zhonghong.hlbelogistics.R;
import com.zhonghong.hlbelogistics.base.Application.MyApplication;
import com.zhonghong.hlbelogistics.base.activitys.BaseActivity;
import com.zhonghong.hlbelogistics.bean.IngPlanOrder;
import com.zhonghong.hlbelogistics.bean.PlanToPlanOrder;
import com.zhonghong.hlbelogistics.callback.ExceptionReportCallBack;
import com.zhonghong.hlbelogistics.callback.IngPlanFragmentCallBack;
import com.zhonghong.hlbelogistics.callback.ModifyPlanStatusCallBack;
import com.zhonghong.hlbelogistics.callback.getPlanToPlanOrderCallBack;
import com.zhonghong.hlbelogistics.core.home.Fragments.IngPlanFragment;
import com.zhonghong.hlbelogistics.respone.AllRespone;
import com.zhonghong.hlbelogistics.utils.NetWorkStatte;
import com.zhonghong.hlbelogistics.utils.OkHttpUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.List;

import okhttp3.Call;

/**
 * 问题单上报
 */
public class Exception_report_Activity extends BaseActivity implements View.OnClickListener {
    private ImageView imageView;
    private Button exception_report_ectivity_btn;
    private EditText add_content;
    private String deliveryPhone;
    private String carId;
    private String planId;
    private String orderId;
    private String phone;
    private String contacts;
    private Boolean success = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exception_report_);
        Intent intent = getIntent();
        deliveryPhone = MyApplication.getInstance().getDeliveryPhone();
        carId = intent.getStringExtra("carId");
        planId = intent.getStringExtra("planId");
        orderId = intent.getStringExtra("orderNum");
        phone = intent.getStringExtra("phone");
        contacts = intent.getStringExtra("contacts");
        initView();
    }

    //初始化view
    private void initView() {
        imageView = (ImageView) findViewById(R.id.exception_report_activity_iv);
        exception_report_ectivity_btn = (Button) findViewById(R.id.exception_report_ectivity_btn);
        add_content = (EditText) findViewById(R.id.add_content);
        imageView.setOnClickListener(this);
        exception_report_ectivity_btn.setOnClickListener(this);
    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exception_report_activity_iv:
                finish();
                break;
            case R.id.exception_report_ectivity_btn:
                if(success){
                    success = false;
                    troubleOrder();
                }
                break;
        }
    }

    //问题单上报到服务器
    private void troubleOrder() {
        String aId = MyApplication.getInstance().getUid();
        String deliveryId = MyApplication.getInstance().getDeliveryId();
        String description = add_content.getText().toString();
        if (NetWorkStatte.networkConnected(Exception_report_Activity.this)) {
            if (deliveryPhone == null) {
                Toast.makeText(Exception_report_Activity.this, "配送电话为空", Toast.LENGTH_SHORT).show();
            } else if (carId == null) {
                Toast.makeText(Exception_report_Activity.this, "车牌号为空", Toast.LENGTH_SHORT).show();
            } else if (planId == null) {
                Toast.makeText(Exception_report_Activity.this, "计划单号为空", Toast.LENGTH_SHORT).show();
            } else if (orderId == null) {
                Toast.makeText(Exception_report_Activity.this, "订单号为空", Toast.LENGTH_SHORT).show();
            } else if (phone == null) {
                Toast.makeText(Exception_report_Activity.this, "电话号为空", Toast.LENGTH_SHORT).show();
            } else if (contacts == null) {
                Toast.makeText(Exception_report_Activity.this, "contacts为空", Toast.LENGTH_SHORT).show();
            }else if(description==null){
                Toast.makeText(Exception_report_Activity.this, "请输入异常情况", Toast.LENGTH_SHORT).show();
            } else {
                OkHttpUtils.get()
                        .url(OkHttpUtil.URL)
                        .addParams("config", "troubleOrder")
                        .addParams("orderId", orderId)
                        .addParams("deliveryId", deliveryId)
                        .addParams("deliveryPhone", deliveryPhone)
                        .addParams("contacts", contacts)
                        .addParams("phone", phone)
                        .addParams("carId", carId)
                        .addParams("planId", planId)
                        .addParams("description", description)
                        .addParams("accountId", aId)
                        .build()
                        .execute(new ExceptionReportCallBack() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Toast.makeText(Exception_report_Activity.this, "异常" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(AllRespone response, int id) {
                                if (response != null) {
                                    AllRespone ar = response;
                                    if (ar.getId() == "0") {
                                        String str="OSAT001007";
                                        modify_order_status(str);

                                    } else if (ar.getId() == "-52") {
                                        Toast.makeText(Exception_report_Activity.this, ar.getInfo(), Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(Exception_report_Activity.this,"空的响应",Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

            }

        } else {
            Toast.makeText(Exception_report_Activity.this, "请连接网络", Toast.LENGTH_SHORT).show();
        }

    }


    //修改订单状态
    public void modify_order_status(String status){
        if (NetWorkStatte.networkConnected(Exception_report_Activity.this)){
                OkHttpUtils.get()
                        .url(OkHttpUtil.URL)
                        .addParams("config","order")
                        .addParams("type","1")
                        .addParams("orderNum", orderId)
                        .addParams("status",status)
                        .build()
                        .execute(new ModifyPlanStatusCallBack() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Toast.makeText(Exception_report_Activity.this,"异常"+e.getMessage(),Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(AllRespone response, int id) {
                                if(response!=null){
                                    AllRespone ar=new AllRespone();
                                    ar=response;
                                    if(ar.getId()=="0"){
                                        Intent intent = new Intent(Exception_report_Activity.this, IngPlanOrderActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        intent.putExtra("title",planId);
                                        startActivity(intent);
                                    }else if(ar.getId()=="-52"){
//                                        Toast.makeText(Exception_report_Activity.this,ar.getInfo(),Toast.LENGTH_SHORT).show();
                                    }
                                }else {
//                                    Toast.makeText(Exception_report_Activity.this,"空的响应",Toast.LENGTH_SHORT).show();
                                }


                            }
                        });

        }else {
            Toast.makeText(Exception_report_Activity.this,"请连接网络",Toast.LENGTH_SHORT).show();
        }

    }

//    //获取进行中计划
//    public void getAllOrder(){
//        if (NetWorkStatte.networkConnected(Exception_report_Activity.this)){
//            String deliveryId= MyApplication.getInstance().getDeliveryId();
//            OkHttpUtils.get()
//                    .url(OkHttpUtil.URL)
//                    .addParams("config","planOrder")
//                    .addParams("type","1")
//                    .addParams("deliveryId", deliveryId)
//                    .addParams("status","PSAT001003")
//                    .build()
//                    .execute(new IngPlanFragmentCallBack() {
//                        @Override
//                        public void onError(Call call, Exception e, int id) {
////                            Toast.makeText(getActivity(),"异常"+e.getMessage(),Toast.LENGTH_SHORT).show();
//                        }
//
//                        @Override
//                        public void onResponse(List<IngPlanOrder> response, int id) {
//
//                            if(response!=null){
//                                list=response;
//                                for(int i = 0;i<list.size();i++){
//                                    if(list.get(i).getPlanId().equals(planId)){
//                                        getPlanOrderList();
//                                        if(list.get(i).getOrderNum()==a){
//                                            Log.d("TAG", "计划状态已修改 ");
//                                            String sta="PSAT001005";
//                                            modify_plan_status(sta);
//                                        }
//
//                                    }else {
//                                        Intent intent = new Intent(Exception_report_Activity.this, IngPlanOrderActivity.class);
//                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                        intent.putExtra("title",planId);
//                                        startActivity(intent);
//                                    }
//
//                                }
//                            }
//
//                        }
//                    });
//
//        }else {
//            Toast.makeText(Exception_report_Activity.this,"请连接网络",Toast.LENGTH_SHORT).show();
//        }
//    }

//    //修改计划状态
//    public void modify_plan_status(String status){
//        if (NetWorkStatte.networkConnected(Exception_report_Activity.this)){
//            OkHttpUtils.get()
//                    .url(OkHttpUtil.URL)
//                    .addParams("config","planOrder")
//                    .addParams("type","3")
//                    .addParams("planId", planId)
//                    .addParams("status",status)
//                    .build()
//                    .execute(new ModifyPlanStatusCallBack() {
//                        @Override
//                        public void onError(Call call, Exception e, int id) {
////                                Toast.makeText(IngPlanOrderActivity.this,"异常"+e.getMessage(),Toast.LENGTH_SHORT).show();
//                        }
//
//                        @Override
//                        public void onResponse(AllRespone response, int id) {
//                            if(response!=null){
//                                AllRespone ar=new AllRespone();
//                                ar=response;
//                                if(ar.getId()=="0"){
////                                    Intent intent=new Intent(Exception_report_Activity.this,MainActivity.class);
////                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////                                    startActivity(intent);
//                                    changFragment(new IngPlanFragment());
//                                }else if(ar.getId()=="-52"){
////                                        Toast.makeText(IngPlanOrderActivity.this,ar.getInfo(),Toast.LENGTH_SHORT).show();
//                                }
//                            }else {
////                                    Toast.makeText(IngPlanOrderActivity.this,"空的响应",Toast.LENGTH_SHORT).show();
//                            }
//
//
//                        }
//                    });
//
//        }else {
//            Toast.makeText(Exception_report_Activity.this,"请连接网络",Toast.LENGTH_SHORT).show();
//        }
//
//    }

//    //获取当前计划单中的订单list
//    private void getPlanOrderList(){
//        if (NetWorkStatte.networkConnected(Exception_report_Activity.this)){
//            if(planId==null){
//                Toast.makeText(Exception_report_Activity.this,"订单号为空，无法显示列表",Toast.LENGTH_SHORT).show();
//            }else {
//                OkHttpUtils.get()
//                        .url(OkHttpUtil.URL)
//                        .addParams("config","planOrder")
//                        .addParams("type","2")
//                        .addParams("planId", planId)
//                        .build()
//                        .execute(new getPlanToPlanOrderCallBack() {
//                            @Override
//                            public void onError(Call call, Exception e, int id) {
//                                Toast.makeText(Exception_report_Activity.this,"异常"+e.getMessage(),Toast.LENGTH_SHORT).show();
//                            }
//
//                            @Override
//                            public void onResponse(List<PlanToPlanOrder> response, int id) {
//                                if(response!=null){
//                                    orderList=response;
//                                    for (int i = 0;i<orderList.size();i++){
//                                        if(orderList.get(i).getStatus().equals("OSAT001007")){
//                                            a++;
//                                        }
//
//                                    }
//                                }else {
//                                    Toast.makeText(Exception_report_Activity.this,"空的响应",Toast.LENGTH_SHORT).show();
//                                }
//
//                            }
//                        });
//
//
//            }
//        }else {
//            Toast.makeText(Exception_report_Activity.this,"请连接网络",Toast.LENGTH_SHORT).show();
//        }
//    }
//    public void changFragment(Fragment fragment) {
//        FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction transaction = fm.beginTransaction();
//        //加入返回栈
//        transaction.add(R.id.vp_mainactivity, fragment);
//        transaction.commit();
//    }
//    public interface ModifyListener {
//        void success();
//    }
}
