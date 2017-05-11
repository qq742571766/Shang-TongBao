package com.zhonghong.hlbelogistics.callback;

import com.zhonghong.hlbelogistics.respone.BuyersInfoRespone;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/8.
 */

public abstract class GetBuyersInfoCallBack extends Callback<BuyersInfoRespone> {
    @Override
    public BuyersInfoRespone parseNetworkResponse(Response response, int id) throws Exception {

        if(response.isSuccessful())
        {
            BuyersInfoRespone bir = new BuyersInfoRespone();
            //提示语
            String responseStr = response.body().string();
            JSONObject jsonObj = new JSONObject(responseStr);
            if (jsonObj!=null)
            {
                if (jsonObj.has("0")&&!jsonObj.isNull("0"))
                {
                    String  string = jsonObj.getString("0");
                    List buyersInfoList=new ArrayList();
                    buyersInfoList= (List) jsonObj.get("infoList");
                    bir.setId("0");
                    bir.setInfo(string);
                    bir.setBuyersInfoList(buyersInfoList);
                    return bir;
                }else
                if (jsonObj.has("-52")&&!jsonObj.isNull("-52"))
                {
                    String  string = jsonObj.getString("-52");
                    bir.setId("-52");
                    bir.setInfo(string);
                    return bir;
                }

            }
        }
        return null;
    }
}
