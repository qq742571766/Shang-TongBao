package com.zhonghong.hlbelogistics.callback;

import com.zhonghong.hlbelogistics.bean.Area;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * Created by Administrator on 2017/4/5.
 */

public abstract class AreaCallBack extends Callback<List<Area>> {

    @Override
    public List<Area> parseNetworkResponse(Response response, int id) throws Exception {
        List<Area> list=new ArrayList();
        Area area =new Area();
        String responeStr=response.body().string();
        JSONObject jsonObject=new JSONObject(responeStr);
        if(jsonObject.has("0")&&!jsonObject.isNull("0")){
            String string=jsonObject.getString("0");
            JSONArray jsonArray=new JSONObject(responeStr).getJSONArray("list");
            JSONObject object;
            for (int i =0;i<jsonArray.length();i++){
                Area aa =new Area();
                object= (JSONObject) jsonArray.get(i);
                aa.setProId("0");
                aa.setInfo(string);
                aa.setAreaName(object.getString("adminNameCn"));
                aa.setAreaCode(object.getString("adminCode"));
                list.add(aa);
            }
            return list;
        }else if(jsonObject.has("-52")&&!jsonObject.isNull("-52")){
            String string=jsonObject.getString("-52");
            area.setProId("-52");
            area.setInfo(string);
            list.add(area);
            return list;
        }
        return null;
    }


}
