package com.zhonghong.hlbelogistics.callback;

import com.zhonghong.hlbelogistics.bean.GrapSingleOrder;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * 抢单
 * Created by Administrator on 2017/3/18.
 */

public abstract class GrapSingleOrderCallBack extends Callback<List<GrapSingleOrder>> {
    @Override
    public List<GrapSingleOrder> parseNetworkResponse(Response response, int id) throws Exception {
        List<GrapSingleOrder> list = new ArrayList();
        GrapSingleOrder ar = new GrapSingleOrder();
        String responeStr = response.body().string();
        JSONObject jsonObject = new JSONObject(responeStr);
        if (jsonObject.has("0") && !jsonObject.isNull("0")) {
            String string = jsonObject.getString("0");
            JSONArray jsonArray = new JSONObject(responeStr).getJSONArray("orderList");
            JSONObject object;
            for (int i = 0; i < jsonArray.length(); i++) {
                GrapSingleOrder po = new GrapSingleOrder();
                po.setProId("0");
                po.setInfo(string);
                object = (JSONObject) jsonArray.get(i);
                po.setDELIVERY_PROVINCE(object.getString("DELIVERY_PROVINCE"));
                po.setDELIVERY_CITY(object.getString("DELIVERY_CITY"));
                po.setDELIVERY_COUNTY(object.getString("DELIVERY_COUNTY"));
                po.setDELIVERY_ADDRESS(object.getString("DELIVERY_ADDRESS"));
                po.setINVENTORY_PROVINCE(object.getString("INVENTORY_PROVINCE"));
                po.setINVENTORY_CITY(object.getString("INVENTORY_CITY"));
                po.setINVENTORY_COUNTY(object.getString("INVENTORY_COUNTY"));
                po.setINVENTORY_ADDRESS(object.getString("INVENTORY_ADDRESS"));
                po.setOrderNum(object.getString("orderNum"));
                po.setGoodsName(object.getString("goodsName"));
                list.add(po);
            }
            return list;
        } else if (jsonObject.has("-52") && !jsonObject.isNull("-52")) {
            String string = jsonObject.getString("-52");
            ar.setProId("-52");
            ar.setInfo(string);
            list.add(ar);
            return list;
        }
        return null;
    }
}
