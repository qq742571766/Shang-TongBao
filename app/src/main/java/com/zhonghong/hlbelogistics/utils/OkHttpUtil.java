package com.zhonghong.hlbelogistics.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhonghong.hlbelogistics.base.Application.MyApplication;
import com.zhonghong.hlbelogistics.bean.IngPlanOrder;
import com.zhonghong.hlbelogistics.callback.IngPlanFragmentCallBack;
import com.zhonghong.hlbelogistics.callback.ToLoginCallBack;
import com.zhonghong.hlbelogistics.core.home.Activitys.MainActivity;
import com.zhonghong.hlbelogistics.core.login.LoginActivity;
import com.zhonghong.hlbelogistics.respone.LoginRespone;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;

import java.util.List;

import okhttp3.Call;
import okhttp3.Request;

/**
 * 服务器路径：http://101.53.102.99:18080/DistributionAPI
 * Created by Administrator on 2017/3/20.
 */

public class OkHttpUtil {
    private Context context;
    private ProgressDialog progressDialog;
    public static final String URL = "http://101.53.102.99:18080/DistributionAPI";
//    public static final String URL = "http://192.168.1.200:8080/DistributionAPI";


    public OkHttpUtil(Context context, ProgressDialog progressDialog) {
        this.context = context;
        this.progressDialog = progressDialog;
    }

    public void getHttp(String config, String userName, String userPassword, String token) {
        OkHttpUtils.get()
                .addParams("config", config)
                .addParams("usename", userName)
                .addParams("password", userPassword)
                .addParams("token", token)
                .url(URL)
                .build()
                .connTimeOut(10000)
                .execute(new ToLoginCallBack() {

                    @Override
                    public void onBefore(Request request, int id) {
                        progressDialog = new ProgressDialog(context, ProgressDialog.THEME_DEVICE_DEFAULT_DARK);
                        progressDialog.setMessage("正在登陆中");
                        progressDialog.setCanceledOnTouchOutside(false);
                        progressDialog.show();
                        super.onBefore(request, id);
                    }

                    @Override
                    public void onAfter(int id) {
                        super.onAfter(id);
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(context, "异常" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(LoginRespone response, int id) {
                        if (response != null) {
                            LoginRespone lr = response;
                            if (lr.getId() == "0") {
                                Toast.makeText(context, lr.getInfo(), Toast.LENGTH_SHORT).show();
                                String token = lr.getTokenInfo();
                                String uid = lr.getUid();
                                String deliveryId = lr.getDeliveryId();
                                String deliveryPhone = lr.getDeliveryPhone();
                                String carId = lr.getCarId();
                                String password = lr.getPassword();
                                String userName = lr.getUserName();
                                if (token != null) {
                                    MyApplication.getInstance().savaUnameAndPwd(userName, password);
                                    MyApplication.getInstance().saveDeliveryId(deliveryId);
                                    MyApplication.getInstance().saveToken(token);
                                    MyApplication.getInstance().saveUid(uid);
                                    MyApplication.getInstance().saveDeliveryPhone(deliveryPhone);
                                    MyApplication.getInstance().saveCarId(carId);
                                    Intent intent = new Intent(context, MainActivity.class);
                                    context.startActivity(intent);

                                } else {
                                    Toast.makeText(context, "获取token为空,登录失败", Toast.LENGTH_SHORT).show();
                                }


                            } else if (lr.getId() == "-52") {
                                Toast.makeText(context, lr.getInfo(), Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(context, "空的响应", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }


}
