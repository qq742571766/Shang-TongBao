package com.zhonghong.hlbelogistics.callback;

import com.zhonghong.hlbelogistics.respone.RegistRespone;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONObject;

import okhttp3.Response;

/**
 * 注册
 * Created by Administrator on 2017/3/7.
 */

public abstract class ToRegistCallback extends Callback<RegistRespone> {

    @Override
    public RegistRespone parseNetworkResponse(Response response, int id) throws Exception {

        if(response.isSuccessful())
        {
            RegistRespone rr = new RegistRespone();
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
