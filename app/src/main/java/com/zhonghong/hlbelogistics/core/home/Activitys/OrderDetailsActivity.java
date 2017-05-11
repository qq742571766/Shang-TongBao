package com.zhonghong.hlbelogistics.core.home.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhonghong.hlbelogistics.R;
import com.zhonghong.hlbelogistics.bean.OrderDetails;
import com.zhonghong.hlbelogistics.callback.OrderDetailsCallBack;
import com.zhonghong.hlbelogistics.utils.NetWorkStatte;
import com.zhonghong.hlbelogistics.utils.OkHttpUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.List;

import okhttp3.Call;

/**
 * 订单详情
 */
public class OrderDetailsActivity extends AppCompatActivity implements View.OnClickListener{
    private String orderNum;

    private TextView order_num_textview;
    private TextView goods_name;
    private TextView pick_up_place_textview;
    private TextView arrive_textview;
    private TextView goods_weight_tv;
    private TextView goods_volume_tv;
    private TextView start_time_tv;
    private TextView end_time_tv;
    private LinearLayout start;
    private LinearLayout end;
    private ImageView goods_details_Activity_iv;
    private TextView status;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        Intent intent=getIntent();
        orderNum=intent.getStringExtra("orderNum");
        initView();
        getOrderDetail();

    }

    private void initView() {
        goods_details_Activity_iv= (ImageView) findViewById(R.id.goods_details_Activity_iv);
        goods_details_Activity_iv.setOnClickListener(this);
        order_num_textview= (TextView) findViewById(R.id.order_num_textview);
        goods_name= (TextView) findViewById(R.id.goods_name);
        pick_up_place_textview= (TextView) findViewById(R.id.pick_up_place_textview);
        arrive_textview= (TextView) findViewById(R.id.arrive_textview);
        goods_weight_tv= (TextView) findViewById(R.id.goods_weight_tv);
        goods_volume_tv= (TextView) findViewById(R.id.goods_volume_tv);
        start_time_tv= (TextView) findViewById(R.id.start_time_tv);
        end_time_tv= (TextView) findViewById(R.id.end_time_tv);
        start= (LinearLayout) findViewById(R.id.start);
        end= (LinearLayout) findViewById(R.id.end);
        status= (TextView) findViewById(R.id.status);
    }


    //请求订单详情
    private void getOrderDetail(){
        if (NetWorkStatte.networkConnected(OrderDetailsActivity.this)){
            OkHttpUtils.get()
                    .url(OkHttpUtil.URL)
                    .addParams("config","order")
                    .addParams("type","2")
                    .addParams("orderNum",orderNum )
                    .build()
                    .execute(new OrderDetailsCallBack() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
//                            Toast.makeText(OrderDetailsActivity.this,"异常"+e.getMessage(),Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(OrderDetails response, int id) {
                            if(response!=null){
                                OrderDetails orderDetails =new OrderDetails();
                                orderDetails=response;
                                if(orderDetails.getProId()=="0"){
                                    String INVENTORY_place=orderDetails.getINVENTORY_PROVINCE()+orderDetails.getINVENTORY_CITY()+orderDetails.getINVENTORY_COUNTY()+orderDetails.getINVENTORY_ADDRESS();
                                    String DELIVERY_place=orderDetails.getDELIVERY_PROVINCE()+orderDetails.getDELIVERY_CITY()+orderDetails.getDELIVERY_COUNTY()+orderDetails.getDELIVERY_ADDRESS();
                                    String star=orderDetails.getSTART_TIME();
                                    String en=orderDetails.getEND_TIME();
                                    order_num_textview.setText("订单编号"+orderNum);
                                    goods_name.setText(orderDetails.getGoodsName());
                                    pick_up_place_textview.setText(INVENTORY_place);
                                    arrive_textview.setText(DELIVERY_place);
                                    goods_weight_tv.setText(orderDetails.getGoodsWeight());
                                    goods_volume_tv.setText(orderDetails.getGoodsVolume());
                                    if(!star.equals("")){
                                        start.setVisibility(View.VISIBLE);
                                        start_time_tv.setText(star);
                                    }
                                    if(!en.equals("")){
                                        end.setVisibility(View.VISIBLE);
                                        end_time_tv.setText(en);
                                    }
                                    if(orderDetails.getStatus().equals("OSAT001001")){
                                        status.setText("待分配");
                                    }else if(orderDetails.getStatus().equals("OSAT001002")){
                                        status.setText("已分配");
                                    }else if(orderDetails.getStatus().equals("OSAT001003")){
                                        status.setText("出库中");
                                    }else if(orderDetails.getStatus().equals("OSAT001004")){
                                        status.setText("配送中");
                                    }else if(orderDetails.getStatus().equals("OSAT001005")){
                                        status.setText("已配送");
                                    }else if(orderDetails.getStatus().equals("OSAT001006")){
                                        status.setText("已确认");
                                    }else if(orderDetails.getStatus().equals("OSAT001007")){
                                        status.setText("问题单");
                                    }else {
                                        status.setVisibility(View.GONE);
                                    }




                                }else if(orderDetails.getProId()=="-52"){
//                                    Toast.makeText(OrderDetailsActivity.this,orderDetails.getInfo(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });

        }else {
            Toast.makeText(OrderDetailsActivity.this,"请连接网络",Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onClick(View v) {
        Intent intent=new Intent(OrderDetailsActivity.this,BaidumapActivty.class);
        intent.putExtra("orderNum",orderNum);
        switch (v.getId()) {
            case R.id.goods_details_Activity_iv:
                finish();
                break;
//            case R.id.pick_up_place_map_iv:
//                startActivity(intent);
//                break;
//            case R.id.arrive_map_iv:
//                startActivity(intent);
//                break;
        }
    }
}
