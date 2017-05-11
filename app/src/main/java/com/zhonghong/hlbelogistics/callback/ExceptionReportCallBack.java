package com.zhonghong.hlbelogistics.callback;

import com.zhonghong.hlbelogistics.respone.AllRespone;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONObject;

import okhttp3.Response;

/**
 * 问题上报  CallBack
 * Created by Administrator on 2017/3/22.
 */

public abstract class ExceptionReportCallBack extends Callback<AllRespone>{
    @Override
    public AllRespone parseNetworkResponse(Response response, int id) throws Exception {

        if(response.isSuccessful())
        {
            AllRespone rr = new AllRespone();
            String responseStr = response.body().string();
            JSONObject jsonObj = new JSONObject(responseStr);
            if (jsonObj!=null)
            {
                if (jsonObj.has("0")&&!jsonObj.isNull("0"))
                {
                    String  string = jsonObj.getString("0");
                    rr.setId("0");
                    rr.setInfo(string);
                    return rr;
                }else
                if (jsonObj.has("-52")&&!jsonObj.isNull("-52"))
                {
                    String  string = jsonObj.getString("-52");
                    rr.setId("-52");
                    rr.setInfo(string);
                    return rr;
                }

            }
        }
        return null;
    }
}
