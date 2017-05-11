package com.zhonghong.hlbelogistics.callback;

import com.zhonghong.hlbelogistics.respone.LoginRespone;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONObject;

import okhttp3.Response;


/**
 * 登陆
 * Created by Administrator on 2017/3/7.
 */

public abstract class ToLoginCallBack extends Callback<LoginRespone>{
    @Override
    public LoginRespone parseNetworkResponse(Response response, int id) throws Exception {

        if(response.isSuccessful())
        {
            LoginRespone lr = new LoginRespone();
            String responseStr = response.body().string();
            JSONObject jsonObj = new JSONObject(responseStr);
            if (jsonObj!=null) {
                if (jsonObj.has("0")&&!jsonObj.isNull("0")) {
                    String  string = jsonObj.getString("0");
                    lr.setId("0");
                    lr.setInfo(string);
                    if (jsonObj.has("token")&&!jsonObj.isNull("token")) {
                        String token = jsonObj.optString("token",null);
                        lr.setTokenInfo(token);
                    }if (jsonObj.has("userId")&&!jsonObj.isNull("userId")) {
                        String uid = jsonObj.optString("userId",null);
                        lr.setUid(uid);
                }if (jsonObj.has("deliveryId")&&!jsonObj.isNull("deliveryId")) {
                        String deliveryId=jsonObj.optString("deliveryId",null);
                        lr.setDeliveryId(deliveryId);
                    }if (jsonObj.has("phone")&&!jsonObj.isNull("phone")) {
                        String deliveryPhone=jsonObj.optString("phone",null);
                        lr.setDeliveryPhone(deliveryPhone);
                    }if (jsonObj.has("carId")&&!jsonObj.isNull("carId")) {
                        String carId=jsonObj.optString("carId",null);
                        lr.setCarId(carId);
                    }if (jsonObj.has("password")&&!jsonObj.isNull("password")) {
                        String password=jsonObj.optString("password",null);
                        lr.setPassword(password);
                    }if (jsonObj.has("userName")&&!jsonObj.isNull("userName")) {
                        String userName=jsonObj.optString("userName",null);
                        lr.setUserName(userName);
                    }
                    return lr;
                }else if (jsonObj.has("-52")&&!jsonObj.isNull("-52"))
                {
                    String  string = jsonObj.getString("-52");
                    lr.setId("-52");
                    lr.setInfo(string);
                    return lr;
                }

            }
        }
        return null;
    }
}
