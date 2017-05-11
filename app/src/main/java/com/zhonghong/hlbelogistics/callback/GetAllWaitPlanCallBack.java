package com.zhonghong.hlbelogistics.callback;

import com.zhonghong.hlbelogistics.bean.PlanOrder;
import com.zhonghong.hlbelogistics.respone.AllRespone;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import okhttp3.Response;

/**
 * 待接取计划  列表
 * Created by Administrator on 2017/3/13.
 */

public abstract class GetAllWaitPlanCallBack extends Callback<List<PlanOrder>>{
    @Override
    public List<PlanOrder> parseNetworkResponse(Response response, int id) throws Exception {
        List<PlanOrder> list=new ArrayList();
        PlanOrder ar =new PlanOrder();
        String responeStr=response.body().string();
        JSONObject jsonObject=new JSONObject(responeStr);
        if(jsonObject.has("0")&&!jsonObject.isNull("0")){
            String string=jsonObject.getString("0");
            JSONArray jsonArray=new JSONObject(responeStr).getJSONArray("planList");
            JSONObject object;
           for (int i =0;i<jsonArray.length();i++){
               PlanOrder po =new PlanOrder();
               object= (JSONObject) jsonArray.get(i);
               po.setProId("0");
               po.setInfo(string);
               po.setPlanId(object.getString("planId"));
               po.setOrderNumList(object.get("orderId").toString().split(",").length);
               po.setPlanStartTime(object.getString("planStartTime"));
               po.setPlanEndTime(object.getString("planEndTime"));
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
