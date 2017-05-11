package com.zhonghong.hlbelogistics.callback;

import com.zhonghong.hlbelogistics.bean.BaiDuMap;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * 获取轨迹坐标list CallBack
 * Created by Administrator on 2017/3/21.
 */

public abstract class BaiDuMapCallBack extends Callback<List<BaiDuMap>>{
    @Override
    public List<BaiDuMap> parseNetworkResponse(Response response, int id) throws Exception {
        List<BaiDuMap> list=new ArrayList();
        BaiDuMap bm =new BaiDuMap();
        String responeStr=response.body().string();
        JSONObject jsonObject=new JSONObject(responeStr);
        if(jsonObject.has("0")&&!jsonObject.isNull("0")){
            String string=jsonObject.getString("0");
            JSONArray jsonArray=new JSONObject(responeStr).getJSONArray("list");
            JSONObject object;
            for (int i =0;i<jsonArray.length();i++){
                BaiDuMap bdm =new BaiDuMap();
                object= (JSONObject) jsonArray.get(i);
                bdm.setProId("0");
                bdm.setInfo(string);
                bdm.setLon(Double.valueOf(object.getString("lon")));
                bdm.setLat(Double.valueOf(object.getString("lat")));
                list.add(bdm);
            }
            return list;
        }else if(jsonObject.has("-52")&&!jsonObject.isNull("-52")){
            String string=jsonObject.getString("-52");
            bm.setProId("-52");
            bm.setInfo(string);
            list.add(bm);
            return list;
        }
        return null;
    }

}
