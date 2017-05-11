package com.zhonghong.hlbelogistics.callback;

import com.zhonghong.hlbelogistics.respone.AllRespone;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONObject;

import okhttp3.Response;

/**
 * 修改计划状态
 * Created by Administrator on 2017/3/18.
 */

public abstract class ModifyPlanStatusCallBack extends Callback<AllRespone> {
    @Override
    public AllRespone parseNetworkResponse(Response response, int id) throws Exception {

        if(response.isSuccessful())
        {
            AllRespone ar = new AllRespone();
            String responseStr = response.body().string();
            JSONObject jsonObj = new JSONObject(responseStr);
            if (jsonObj!=null)
            {
                if (jsonObj.has("0")&&!jsonObj.isNull("0"))
                {
                    String  string = jsonObj.getString("0");
                    ar.setId("0");
                    ar.setInfo(string);
                    return ar;
                }else
                if (jsonObj.has("-52")&&!jsonObj.isNull("-52"))
                {
                    String  string = jsonObj.getString("-52");
                    ar.setId("-52");
                    ar.setInfo(string);
                    return ar;
                }

            }
        }
        return null;
    }
}
