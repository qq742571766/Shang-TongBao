package com.zhonghong.hlbelogistics.callback;

import com.zhonghong.hlbelogistics.bean.IngPlanOrder;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * 进行中计划列表
 * Created by Administrator on 2017/3/18.
 */

public abstract class IngPlanFragmentCallBack extends Callback<List<IngPlanOrder>> {
    @Override
    public List<IngPlanOrder> parseNetworkResponse(Response response, int id) throws Exception {
        List<IngPlanOrder> list=new ArrayList();
        IngPlanOrder ar =new IngPlanOrder();
        String responeStr=response.body().string();
        JSONObject jsonObject=new JSONObject(responeStr);
        if(jsonObject.has("0")&&!jsonObject.isNull("0")){
            String string=jsonObject.getString("0");
            JSONArray jsonArray=new JSONObject(responeStr).getJSONArray("planList");
            JSONObject object;
            for (int i =0;i<jsonArray.length();i++){
                IngPlanOrder po =new IngPlanOrder();
                object= (JSONObject) jsonArray.get(i);
                po.setProId("0");
                po.setInfo(string);
                po.setStart_time(object.getString("startTime"));
                po.setOrderNum(object.get("orderId").toString().split(",").length);
                po.setPlanId(object.getString("planId"));
                po.setCarId(object.getString("carId"));
                po.setDeliveryPhone(object.getString("deliveryPhone"));
                list.add(po);
            }
            return list;
        }else if(jsonObject.has("-52")&&!jsonObject.isNull("-52")){
            String string=jsonObject.getString("-52");
            ar.setProId("-52");
            ar.setInfo(string);
            list.add(ar);
            return list;
        }
        return null;
    }
}
