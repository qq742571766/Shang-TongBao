package com.zhonghong.hlbelogistics.callback;

import com.zhonghong.hlbelogistics.bean.User;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/21.
 */

public abstract class UserInfoCallBack extends Callback<User>{
    @Override
    public User parseNetworkResponse(Response response, int id) throws Exception {
        User user =new User();
        String responeStr=response.body().string();
        JSONObject jsonObject=new JSONObject(responeStr);
        if(jsonObject.has("0")&&!jsonObject.isNull("0")){
            String string=jsonObject.getString("0");
            user.setProId("0");
            user.setInfo(jsonObject.getString("0"));
            JSONObject object=jsonObject.getJSONObject("deliveryInfo") ;
           user.setUser_name(object.getString("name"));
            user.setE_mail(object.getString("email"));
            user.setAddress(object.getString("address"));
            user.setP_address(object.getString("pAddressName"));
            user.setD_address(object.getString("dAddressName"));
            user.setC_address(object.getString("cAddressName"));
            user.setPhone(object.getString("phone"));
            user.setID_Card_Num(object.getString("idCardNum"));
            return user;
        }else if(jsonObject.has("-52")&&!jsonObject.isNull("-52")){
            String string=jsonObject.getString("-52");
            user.setProId("-52");
            user.setInfo(string);
            return user;
        }
        return null;
    }
}
