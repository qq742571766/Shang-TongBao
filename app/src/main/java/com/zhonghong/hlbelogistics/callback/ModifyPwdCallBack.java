package com.zhonghong.hlbelogistics.callback;

import com.zhonghong.hlbelogistics.respone.ModifyPasswordRespone;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONObject;

import okhttp3.Response;

/**
 * 修改密码
 * Created by Administrator on 2017/3/7.
 */

public abstract class ModifyPwdCallBack extends Callback<ModifyPasswordRespone> {
    @Override
    public ModifyPasswordRespone parseNetworkResponse(Response response, int id) throws Exception {
        if(response.isSuccessful()){
            ModifyPasswordRespone mpr=new ModifyPasswordRespone();
            String responseStr = response.body().string();
            JSONObject jsonObj = new JSONObject(responseStr);
            if(jsonObj.has("0")&&!jsonObj.isNull("0")){
                String str=jsonObj.getString("0");
                mpr.setId("0");
                mpr.setInfo(str);
                return mpr;
            }else if (jsonObj.has("-52")&&!jsonObj.isNull("-52")){
                String str=jsonObj.getString("-52");
                mpr.setId("-52");
                mpr.setInfo(str);
                return mpr;
            }

        }
        return null;
    }
}
