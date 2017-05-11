package com.zhonghong.hlbelogistics.callback;

import com.zhonghong.hlbelogistics.bean.City;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * Created by Administrator on 2017/4/5.
 */

public abstract class CityCallBack extends Callback<List<City>> {
    @Override
    public List<City> parseNetworkResponse(Response response, int id) throws Exception {
        List<City> list=new ArrayList();
        City city =new City();
        String responeStr=response.body().string();
        JSONObject jsonObject=new JSONObject(responeStr);
        if(jsonObject.has("0")&&!jsonObject.isNull("0")){
            String string=jsonObject.getString("0");
            JSONArray jsonArray=new JSONObject(responeStr).getJSONArray("list");
            JSONObject object;
            for (int i =0;i<jsonArray.length();i++){
                City ct =new City();
                object= (JSONObject) jsonArray.get(i);
                ct.setProId("0");
                ct.setInfo(string);
                ct.setCityName(object.getString("adminNameCn"));
                ct.setCityCode(object.getString("adminCode"));
                list.add(ct);
            }
            return list;
        }else if(jsonObject.has("-52")&&!jsonObject.isNull("-52")){
            String string=jsonObject.getString("-52");
            city.setProId("-52");
            city.setInfo(string);
            list.add(city);
            return list;
        }
        return null;
    }
}
