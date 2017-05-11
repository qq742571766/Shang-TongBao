package com.zhonghong.hlbelogistics.core.home.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhonghong.hlbelogistics.R;
import com.zhonghong.hlbelogistics.bean.PlanToPlanOrder;
import com.zhonghong.hlbelogistics.callback.ModifyPlanStatusCallBack;
import com.zhonghong.hlbelogistics.callback.getPlanToPlanOrderCallBack;
import com.zhonghong.hlbelogistics.core.home.Adapter.IngPlanOrderAdapter;
import com.zhonghong.hlbelogistics.respone.AllRespone;
import com.zhonghong.hlbelogistics.utils.NetWorkStatte;
import com.zhonghong.hlbelogistics.utils.OkHttpUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.List;

import okhttp3.Call;

public class IngPlanOrderActivity extends AppCompatActivity implements View.OnClickListener {
    private String planId;
    private String carId;
    private String deliveryPhone;
    private ImageView ing_planOrder_details_iv;
    private TextView ing_planOrder_details_tb_tv;
    private IngPlanOrderAdapter adapter;
    private ListView plan_order_activity_listview;
    private LinearLayout ready;


    private List<PlanToPlanOrder> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ing_plan_order);
        Intent intent = getIntent();
        planId = intent.getStringExtra("title");
        carId = intent.getStringExtra("carId");
        deliveryPhone = intent.getStringExtra("deliveryPhone");
        getPlanOrderList();
        initView();
        adapter = new IngPlanOrderAdapter(IngPlanOrderActivity.this, carId, deliveryPhone, planId);
        adapter.setModifyPlanListener(new IngPlanOrderAdapter.MotifyPlanListener() {
            @Override
            public void onSucess() {
                getPlanOrderList();
            }
        });
    }

    @Override
    protected void onDestroy() {
        this.finish();
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initView() {
        ing_planOrder_details_iv = (ImageView) findViewById(R.id.ing_planOrder_details_iv);
        ing_planOrder_details_tb_tv = (TextView) findViewById(R.id.ing_planOrder_details_tb_tv);
        ing_planOrder_details_tb_tv.setText("计划编号" + planId);
        ready = (LinearLayout) findViewById(R.id.ready);
        plan_order_activity_listview = (ListView) findViewById(R.id.plan_order_activity_listview);
        ing_planOrder_details_iv.setOnClickListener(this);
        ready.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ready:
                if (judge() == 0) {
                    String sta = "PSAT001005";
                    modify_plan_status(sta);
                } else if (judge() != 0) {
                    Toast.makeText(IngPlanOrderActivity.this, "还有" + judge() + "条订单未完成！", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.ing_planOrder_details_iv:
                this.finish();
                break;

        }
    }

    //修改计划状态
    public void modify_plan_status(String status) {
        if (NetWorkStatte.networkConnected(IngPlanOrderActivity.this)) {
            OkHttpUtils.get()
                    .url(OkHttpUtil.URL)
                    .addParams("config", "planOrder")
                    .addParams("type", "3")
                    .addParams("planId", planId)
                    .addParams("status", status)
                    .build()
                    .execute(new ModifyPlanStatusCallBack() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
//                                Toast.makeText(IngPlanOrderActivity.this,"异常"+e.getMessage(),Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(AllRespone response, int id) {
                            if (response != null) {
                                AllRespone ar = new AllRespone();
                                ar = response;
                                if (ar.getId() == "0") {
                                    IngPlanOrderActivity.this.finish();
                                } else if (ar.getId() == "-52") {
//                                        Toast.makeText(IngPlanOrderActivity.this,ar.getInfo(),Toast.LENGTH_SHORT).show();
                                }
                            } else {
//                                    Toast.makeText(IngPlanOrderActivity.this,"空的响应",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        } else {
            Toast.makeText(IngPlanOrderActivity.this, "请连接网络", Toast.LENGTH_SHORT).show();
        }

    }

    //获取当前计划单中的订单list
    private void getPlanOrderList() {
        if (NetWorkStatte.networkConnected(IngPlanOrderActivity.this)) {
            if (planId == null) {
                Toast.makeText(IngPlanOrderActivity.this, "订单号为空，无法显示列表", Toast.LENGTH_SHORT).show();
            } else {
                OkHttpUtils.get()
                        .url(OkHttpUtil.URL)
                        .addParams("config", "planOrder")
                        .addParams("type", "2")
                        .addParams("planId", planId)
                        .build()
                        .execute(new getPlanToPlanOrderCallBack() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Toast.makeText(IngPlanOrderActivity.this, "异常" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(List<PlanToPlanOrder> response, int id) {
                                if (response != null) {
                                    list = response;
                                    adapter.setItems(list);
                                    plan_order_activity_listview.setAdapter(adapter);
                                    if (list.size() == 0) {
                                        //Toast.makeText(IngPlanOrderActivity.this,"未查找到数据！",Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    if (list.get(0).getProId() == "-52") {
                                        //Toast.makeText(IngPlanOrderActivity.this,list.get(0).getInfo(),Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(IngPlanOrderActivity.this, "空的响应", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        } else {
            Toast.makeText(IngPlanOrderActivity.this, "请连接网络", Toast.LENGTH_SHORT).show();
        }
    }

    //判断该条计划是否全部完成
    private int judge() {
        int a = 0;
        for (int i = 0; i < list.size(); i++) {
            String str = list.get(i).getStatus();
            if (str.equals("OSAT001005") || str.equals("OSAT001007")) {
                a++;
            }
        }
        if (a == list.size()) {
            return 0;
        } else {
            return list.size() - a;
        }
    }
}