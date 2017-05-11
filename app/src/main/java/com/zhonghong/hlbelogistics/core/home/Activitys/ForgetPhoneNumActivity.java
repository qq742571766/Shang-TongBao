package com.zhonghong.hlbelogistics.core.home.Activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zhonghong.hlbelogistics.R;
import com.zhonghong.hlbelogistics.base.Application.MyApplication;
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

import okhttp3.Call;

public class ForgetPhoneNumActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView phoneNum;//跳转获取的手机号
    private EditText phone_num;//输入的手机号
    private Button next;//确认修改按钮
    private String phoneNumber = null;
    private EditText modify_yanzheng_editText;//输入的验证码
    private TextView yanzheng_button;//获取验证码按钮
    private EditText new_pwd;//新密码


    private CountDownTimerUtils cdtu = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_phone_num);
        Intent intent = getIntent();
        phoneNumber = intent.getStringExtra("phone");
        initView();
    }

    private void initView() {
        phoneNum = (TextView) findViewById(R.id.phoneNum);
        phoneNum.setText(phoneNumber);
        phone_num = (EditText) findViewById(R.id.phone_num);
        next = (Button) findViewById(R.id.next);
        next.setOnClickListener(this);
        modify_yanzheng_editText = (EditText) findViewById(R.id.modify_yanzheng_editText);
        yanzheng_button = (TextView) findViewById(R.id.yanzheng_button);
        yanzheng_button.setOnClickListener(this);
        new_pwd = (EditText) findViewById(R.id.new_pwd);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next:
                modifyPassword();
                break;
            case R.id.yanzheng_button:
                String pn = phone_num.getText().toString();
                if (pn.equals(phoneNumber)) {
                    cdtu = new CountDownTimerUtils(phoneNum, 120000, 1000,this);
                    cdtu.start();
                    getYZM();
                }
                break;
        }
    }

    //获取验证码
    private void getYZM() {

        if (NetWorkStatte.networkConnected(getApplicationContext())) {

                OkHttpUtils.get()
                        .url(OkHttpUtil.URL)
                        .addParams("config", "codeNum")
                        .addParams("phone", phoneNumber)
                        .build()
                        .execute(new getCodeCallBacl() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Toast.makeText(ForgetPhoneNumActivity.this, "获取验证码失败" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(YZMRespone response, int id) {
                                if (response != null) {
                                    YZMRespone yzmRespone = response;
                                    if (yzmRespone.getId() == "-52") {
                                        Toast.makeText(ForgetPhoneNumActivity.this, response.getInfo(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });

        } else {
            Toast.makeText(ForgetPhoneNumActivity.this, "请连接网络", Toast.LENGTH_SHORT).show();
        }

    }

    //修改密码
    private void modifyPassword() {

        if (NetWorkStatte.networkConnected(getApplicationContext())) {
            String useName = MyApplication.getInstance().getUsername();
            String now_password = MyApplication.getInstance().getPassword();
            String new_password = new_pwd.getText().toString();
            String code = modify_yanzheng_editText.getText().toString();
            String phone = phone_num.getText().toString();
            if (new_password.length() < 6 || new_password.length() > 14) {
                Toast.makeText(ForgetPhoneNumActivity.this, "新密码格式错误，密码长度应为6到14位", Toast.LENGTH_SHORT).show();
            } else {
                OkHttpUtils.get()
                        .url(OkHttpUtil.URL)
                        .addParams("config", "changePwd")
                        .addParams("username", useName)
                        .addParams("oldPassword", now_password)
                        .addParams("newPassword", new_password)
                        .addParams("code", code)
                        .addParams("phone", phone)
                        .addParams("type", "changePwd")
                        .build()
                        .execute(new ModifyPwdCallBack() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Toast.makeText(ForgetPhoneNumActivity.this, "异常" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(ModifyPasswordRespone response, int id) {
                                if (response != null) {
                                    ModifyPasswordRespone mpr = response;
                                    if (mpr.getId() == "0") {
                                        Intent intent = new Intent(ForgetPhoneNumActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    } else if (mpr.getId() == "-52") {
                                        Toast.makeText(ForgetPhoneNumActivity.this, mpr.getInfo(), Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    Toast.makeText(ForgetPhoneNumActivity.this, "空的响应", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }
        } else {
            Toast.makeText(ForgetPhoneNumActivity.this, "请连接网络", Toast.LENGTH_SHORT).show();
        }
    }
}
