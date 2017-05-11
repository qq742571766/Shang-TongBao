package com.zhonghong.hlbelogistics.callback;

import com.zhonghong.hlbelogistics.bean.Province;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * Created by Administrator on 2017/4/1.
 */

public abstract class ProvinceCallBack extends Callback<List<Province>> {
    @Override
    public List<Province> parseNetworkResponse(Response response, int id) throws Exception {
        List<Province> list=new ArrayList();
        Province pro =new Province();
        String responeStr=response.body().string();
        JSONObject jsonObject=new JSONObject(responeStr);
        if(jsonObject.has("0")&&!jsonObject.isNull("0")){
            String string=jsonObject.getString("0");
            JSONArray jsonArray=new JSONObject(responeStr).getJSONArray("list");
            JSONObject object;
            for (int i =0;i<jsonArray.length();i++){
                Province po =new Province();
                object= (JSONObject) jsonArray.get(i);
                po.setProId("0");
                po.setInfo(string);
                po.setProvinceCode(object.getString("adminCode"));
                po.setProvinceName(object.getString("adminNameCn"));
                list.add(po);
            }
            return list;
        }else if(jsonObject.has("-52")&&!jsonObject.isNull("-52")){
            String string=jsonObject.getString("-52");
            pro.setProId("-52");
            pro.setInfo(string);
            list.add(pro);
            return list;
        }
        return null;
    }

}
