package com.zhonghong.hlbelogistics.callback;

import com.google.gson.JsonArray;
import com.zhonghong.hlbelogistics.respone.YZMRespone;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/6.
 */

public abstract class getCodeCallBacl extends Callback<YZMRespone> {
    @Override
    public YZMRespone parseNetworkResponse(Response response, int id) throws Exception {

        if(response.isSuccessful())
        {
            YZMRespone rr = new YZMRespone();
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
