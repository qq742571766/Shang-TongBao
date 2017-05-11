package com.zhonghong.hlbelogistics.callback;

import com.zhonghong.hlbelogistics.bean.ForGetBean;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONObject;

import okhttp3.Response;

/**
 * Created by Administrator on 2017/4/11.
 */

public abstract class ForgetCallBack extends Callback<ForGetBean>{
    @Override
    public ForGetBean parseNetworkResponse(Response response, int id) throws Exception {

        if(response.isSuccessful())
        {
            ForGetBean forGetBean = new ForGetBean();
            String responseStr = response.body().string();
            JSONObject jsonObj = new JSONObject(responseStr);
            if (jsonObj!=null)
            {
                if (jsonObj.has("0")&&!jsonObj.isNull("0"))
                {
                    String  string = jsonObj.getString("0");
                    forGetBean.setId("0");
                    forGetBean.setInfo(string);
                    forGetBean.setAccountId(jsonObj.getString("accountId"));
                    forGetBean.setPhone(jsonObj.getString("phone"));
                    return forGetBean;
                }else
                if (jsonObj.has("-52")&&!jsonObj.isNull("-52"))
                {
                    String  string = jsonObj.getString("-52");
                    forGetBean.setId("0");
                    forGetBean.setInfo(string);
                    return forGetBean;
                }

            }
        }
        return null;
    }
}
