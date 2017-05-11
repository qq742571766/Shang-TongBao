package com.zhonghong.hlbelogistics.core.home.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.zhonghong.hlbelogistics.core.home.Adapter.WaitPlanCenterActivityAdapter;
import com.zhonghong.hlbelogistics.respone.AllRespone;
import com.zhonghong.hlbelogistics.utils.NetWorkStatte;
import com.zhonghong.hlbelogistics.utils.OkHttpUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.List;

import okhttp3.Call;

/**
 * 待接取订单列表页
 */
public class WaitPlanOnderActivity extends AppCompatActivity implements View.OnClickListener {
    private List<PlanToPlanOrder> list;
    private String planId;
    private ListView plan_order_activity_listview;
    private WaitPlanCenterActivityAdapter adapter;
    private TextView wait_planOrder_details_tb_tv;
    private ImageView wait_planOrder_details_iv;
    private LinearLayout pick_up_linearlayout;
    private LinearLayout refuse_linearlayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_onder);
        Intent intent=getIntent();
        planId=intent.getStringExtra("title");
        getPlanOrderList();
        initView();

    }

    private void initView() {
        wait_planOrder_details_tb_tv= (TextView) findViewById(R.id.wait_planOrder_details_tb_tv);
        wait_planOrder_details_iv= (ImageView) findViewById(R.id.wait_planOrder_details_iv);
        pick_up_linearlayout= (LinearLayout) findViewById(R.id.pick_up_linearlayout);
        refuse_linearlayout= (LinearLayout) findViewById(R.id.refuse_linearlayout);
        plan_order_activity_listview= (ListView) findViewById(R.id.plan_order_activity_listview);
        wait_planOrder_details_tb_tv.setText("计划编号"+planId);
        wait_planOrder_details_iv.setOnClickListener(this);
        pick_up_linearlayout.setOnClickListener(this);
        refuse_linearlayout.setOnClickListener(this);
    }


    //获取当前计划单中的订单list
    private void getPlanOrderList(){
        if (NetWorkStatte.networkConnected(WaitPlanOnderActivity.this)){
            if(planId==null){
                Toast.makeText(WaitPlanOnderActivity.this,"订单号为空，无法显示列表",Toast.LENGTH_SHORT).show();
            }else {
                OkHttpUtils.get()
                        .url(OkHttpUtil.URL)
                        .addParams("config","planOrder")
                        .addParams("type","2")
                        .addParams("planId", planId)
                        .build()
                        .execute(new getPlanToPlanOrderCallBack() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
//                                Toast.makeText(WaitPlanOnderActivity.this,"异常"+e.getMessage(),Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(List<PlanToPlanOrder> response, int id) {
                                if(response!=null){
                                    list=response;
                                    adapter=new WaitPlanCenterActivityAdapter(WaitPlanOnderActivity.this);
                                    adapter.setItems(list);
                                    plan_order_activity_listview.setAdapter(adapter);
                                    if(list.size()==0){
//                                        Toast.makeText(WaitPlanOnderActivity.this,"未查询到数据！",Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    if(list.get(0).getProId()=="-52"){
//                                        Toast.makeText(WaitPlanOnderActivity.this,list.get(0).getInfo(),Toast.LENGTH_SHORT).show();
                                    }
                                }else {
//                                    Toast.makeText(WaitPlanOnderActivity.this,"空的响应",Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

            }
        }else {
            Toast.makeText(WaitPlanOnderActivity.this,"请连接网络",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        String status;

        switch (v.getId()) {
            case R.id.wait_planOrder_details_iv:
                finish();
            break;
            case R.id.pick_up_linearlayout:
                status="PSAT001003";
                modify_plan_status(status);

                break;
            case R.id.refuse_linearlayout:
                status="PSAT001004";
                modify_plan_status(status);

                break;
        }
    }


    //修改订单状态
    public void modify_plan_status(String status){
        if (NetWorkStatte.networkConnected(WaitPlanOnderActivity.this)){
            if(planId==null){
                Toast.makeText(WaitPlanOnderActivity.this,"计划单号为空，不能修改",Toast.LENGTH_SHORT).show();
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
//                                Toast.makeText(WaitPlanOnderActivity.this,"异常"+e.getMessage(),Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(AllRespone response, int id) {
                                if(response!=null){
                                    AllRespone ar=new AllRespone();
                                    ar=response;
                                    if(ar.getId()=="0"){
                                        Intent intent=new Intent(WaitPlanOnderActivity.this,MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    }else if(ar.getId()=="-52"){
//                                        Toast.makeText(WaitPlanOnderActivity.this,ar.getInfo(),Toast.LENGTH_SHORT).show();
                                    }
                                }else {
//                                    Toast.makeText(WaitPlanOnderActivity.this,"空的响应",Toast.LENGTH_SHORT).show();
                                }


                            }
                        });
            }
        }else {
            Toast.makeText(WaitPlanOnderActivity.this,"请连接网络",Toast.LENGTH_SHORT).show();
        }

    }
}

