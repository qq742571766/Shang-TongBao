package com.zhonghong.hlbelogistics.core.home.Activitys;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhonghong.hlbelogistics.R;
import com.zhonghong.hlbelogistics.base.Application.MyApplication;
import com.zhonghong.hlbelogistics.base.activitys.BaseActivity;
import com.zhonghong.hlbelogistics.bean.ForGetBean;
import com.zhonghong.hlbelogistics.callback.ForgetCallBack;
import com.zhonghong.hlbelogistics.callback.ModifyPwdCallBack;
import com.zhonghong.hlbelogistics.callback.getCodeCallBacl;
import com.zhonghong.hlbelogistics.core.login.LoginActivity;
import com.zhonghong.hlbelogistics.respone.ModifyPasswordRespone;
import com.zhonghong.hlbelogistics.respone.YZMRespone;
import com.zhonghong.hlbelogistics.utils.CountDownTimerUtils;
import com.zhonghong.hlbelogistics.utils.JudgeNum;
import com.zhonghong.hlbelogistics.utils.NetWorkStatte;
import com.zhonghong.hlbelogistics.utils.OkHttpUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.HashMap;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/3/7.
 */

public class ForgetUserNameActivity extends BaseActivity implements View.OnClickListener {
    private ImageView backImg;
    private EditText user_name;//用户名
    private Button sure;
    private String phoneNum = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        initView();
    }

    private void initView() {
        backImg = (ImageView) findViewById(R.id.forget_back_img);
        user_name = (EditText) findViewById(R.id.user_name);
        sure = (Button) findViewById(R.id.sure);
        backImg.setOnClickListener(this);
        sure.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.forget_back_img:
                finish();
                break;
            case R.id.sure:
                Intent intent = new Intent(ForgetUserNameActivity.this, ForgetPhoneNumActivity.class);
                String phone = getPhoneNum();
                intent.putExtra("phone", phone);
                startActivity(intent);
                break;
        }
    }

    private String getPhoneNum() {

        if (NetWorkStatte.networkConnected(ForgetUserNameActivity.this)) {
            String userName = user_name.getText().toString();
            if (userName == null) {
                Toast.makeText(ForgetUserNameActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
            } else {
                OkHttpUtils.get()
                        .url(OkHttpUtil.URL)
                        .addParams("config", "findPhone")
                        .addParams("usename", userName)
                        .build()
                        .execute(new ForgetCallBack() {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }

                            @Override
                            public void onResponse(ForGetBean response, int id) {
                                if (response != null) {
                                    ForGetBean forGetBean = response;
                                    if (forGetBean.getId().equals("0")) {
                                        phoneNum = forGetBean.getPhone();
                                    } else if (forGetBean.getId().equals("-52")) {
                                        Toast.makeText(ForgetUserNameActivity.this, forGetBean.getInfo(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                            }
                        });
            }

        } else {
            Toast.makeText(ForgetUserNameActivity.this, "请连接网络", Toast.LENGTH_SHORT).show();
        }
        return phoneNum;
    }


}
