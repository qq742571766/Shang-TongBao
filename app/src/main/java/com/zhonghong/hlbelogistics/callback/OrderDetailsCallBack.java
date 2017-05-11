package com.zhonghong.hlbelogistics.callback;

import com.zhonghong.hlbelogistics.bean.OrderDetails;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * 订单详细信息
 * Created by Administrator on 2017/3/16.
 */

public abstract class OrderDetailsCallBack extends Callback<OrderDetails> {
    @Override
    public OrderDetails parseNetworkResponse(Response response, int id) throws Exception {
        OrderDetails ods =new OrderDetails();
        String responeStr=response.body().string();
        JSONObject jsonObject=new JSONObject(responeStr);
        if(jsonObject.has("0")&&!jsonObject.isNull("0")){
            String string=jsonObject.getString("0");
            JSONObject object=jsonObject.getJSONObject("order");
            ods.setProId("0");
            ods.setInfo(string);
            ods.setGoodsName(object.getString("goodsName"));
            ods.setINVENTORY_PROVINCE(object.getString("INVENTORY_PROVINCE"));
            ods.setINVENTORY_CITY(object.getString("INVENTORY_CITY"));
            ods.setINVENTORY_COUNTY(object.getString("INVENTORY_COUNTY"));
            ods.setINVENTORY_ADDRESS(object.getString("INVENTORY_ADDRESS"));
            ods.setDELIVERY_PROVINCE(object.getString("DELIVERY_PROVINCE"));
            ods.setDELIVERY_CITY(object.getString("DELIVERY_CITY"));
            ods.setDELIVERY_COUNTY(object.getString("DELIVERY_COUNTY"));
            ods.setDELIVERY_ADDRESS(object.getString("DELIVERY_ADDRESS"));
            ods.setGoodsWeight(object.getString("goodsWeight"));
            ods.setGoodsVolume(object.getString("goodsVolume"));
            ods.setSTART_TIME(object.getString("START_TIME"));
            ods.setEND_TIME(object.getString("END_TIME"));
            ods.setStatus(object.getString("status"));
            return ods;
        }else if(jsonObject.has("-52")&&!jsonObject.isNull("-52")){
            String string=jsonObject.getString("-52");
            ods.setProId("-52");
            ods.setInfo(string);
            return ods;
        }
        return null;
    }
}
