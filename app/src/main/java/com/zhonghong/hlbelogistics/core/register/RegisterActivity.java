package com.zhonghong.hlbelogistics.core.register;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhonghong.hlbelogistics.R;
import com.zhonghong.hlbelogistics.base.activitys.BaseActivity;
import com.zhonghong.hlbelogistics.respone.RegistRespone;
import com.zhonghong.hlbelogistics.callback.ToRegistCallback;
import com.zhonghong.hlbelogistics.callback.getCodeCallBacl;
import com.zhonghong.hlbelogistics.core.login.LoginActivity;
import com.zhonghong.hlbelogistics.respone.YZMRespone;
import com.zhonghong.hlbelogistics.utils.CountDownTimerUtils;
import com.zhonghong.hlbelogistics.utils.NetWorkStatte;
import com.zhonghong.hlbelogistics.utils.OkHttpUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.HashMap;

import okhttp3.Call;

public class RegisterActivity extends BaseActivity implements View.OnClickListener{
    private EditText register_phonenumber_editText;//手机号
    private EditText register_yanzheng_editText;
    private EditText register_password_editText;
    private TextView yanzheng_textView;
    private Button register_button;//注册按钮
    private CheckBox register_checkBox,person_check,enterprise_check;
    private ImageView back_img;
    CountDownTimerUtils cdtu = null;//倒计时
    private String USER_TYPE = "2";//0 带表个人用户 ，1 代表企业用户 2 代表未选择
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initview();
    }
    /**
     * 初始化控件
     */
    private void initview(){
        back_img = (ImageView) findViewById(R.id.registerActivity_iv);
        register_phonenumber_editText= (EditText) findViewById(R.id.register_phonenumber_editText);
        register_yanzheng_editText= (EditText) findViewById(R.id.register_yanzheng_editText);
        register_password_editText= (EditText) findViewById(R.id.register_password_editText);
        yanzheng_textView= (TextView) findViewById(R.id.yanzheng_button);
        register_checkBox= (CheckBox) findViewById(R.id.register_checkBox);
        register_button = (Button) findViewById(R.id.register_button);
        person_check = (CheckBox) findViewById(R.id.person_check);//个人
        enterprise_check = (CheckBox) findViewById(R.id.enterprise_check);//企业
        register_button.setOnClickListener(this);
        back_img.setOnClickListener(this);
        yanzheng_textView.setOnClickListener(this);
        person_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    enterprise_check.setChecked(false);
                    USER_TYPE = "0";
                }else if (!enterprise_check.isChecked())
                {
                    USER_TYPE = "2";
                }

                System.out.println("USER_TYPE---"+USER_TYPE);
            }
        });
        enterprise_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    person_check.setChecked(false);
                    USER_TYPE = "1";
                }else if (!person_check.isChecked())
                {
                    USER_TYPE = "2";
                }
                System.out.println("USER_TYPE---"+USER_TYPE);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.registerActivity_iv:
            {
                finish();
                break;
            }
            case R.id.yanzheng_button://获取验证
            {
                getYZM();
                break;
            }
            case R.id.register_button:
            {
                toRegist();
            }
        }
    }

//注册
    private void toRegist() {
        if (NetWorkStatte.networkConnected(getApplicationContext()))
        {
            String userPhone = register_phonenumber_editText.getText().toString();
            String yzCode =  register_yanzheng_editText.getText().toString();
            String pwd =  register_password_editText.getText().toString();
            if (userPhone == null||userPhone.length()!=11)
            {
                Toast.makeText(this,"请输入正确手机号",Toast.LENGTH_SHORT).show();
            }else if (yzCode == null|| yzCode.length()!=6)
            {
                Toast.makeText(this,"请输入正确验证码",Toast.LENGTH_SHORT).show();
            }else if (pwd == null|| pwd.length()>14||pwd.length()<6)
            {
                Toast.makeText(this,"请输入正确密码，密码为6到14位",Toast.LENGTH_SHORT).show();
            }else if (USER_TYPE=="2")
            {
                Toast.makeText(this,"请选择用户类型",Toast.LENGTH_SHORT).show();
            }else if (!register_checkBox.isChecked())
            {
                Toast.makeText(this,"请勾选同意相关条款",Toast.LENGTH_SHORT).show();
            }
            else {
                //请求网络
                OkHttpUtils.get()
                            .addParams("config","registerUser")
                            .addParams("phone",userPhone)
                            .addParams("password",pwd)
                            .addParams("code",yzCode)
                            .addParams("accountType",USER_TYPE)
                            .addParams("username",userPhone)
                            .url(OkHttpUtil.URL)
                            .build()
                            .execute(new ToRegistCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {

                                    Toast.makeText(RegisterActivity.this,"异常"+e.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                                @Override
                                public void onResponse(RegistRespone response, int id) {

                                    if (response!=null)
                                    {
                                        RegistRespone rr = response;

                                        if (rr.getId()=="0")
                                        {
                                            Toast.makeText(RegisterActivity.this,rr.getInfo(),Toast.LENGTH_SHORT).show();
                                            Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                                            loginIntent.putExtra("FROM_URL","REGISTE_ACTIVITY");
                                            startActivity(loginIntent);
                                        }else if (rr.getId()=="-52")
                                        {
                                            Toast.makeText(RegisterActivity.this,rr.getInfo(),Toast.LENGTH_SHORT).show();
                                        }


                                    }else
                                    {
                                        Toast.makeText(RegisterActivity.this,"空的响应",Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
            }
        }else
        {
            Toast.makeText(RegisterActivity.this,"请连接网络",Toast.LENGTH_SHORT).show();
        }

    }

    //获取验证码
    private void getYZM() {

        if (NetWorkStatte.networkConnected(getApplicationContext()))
        {
            String userPhone = register_phonenumber_editText.getText().toString();
            if (userPhone == null||userPhone.length()!=11)
            {
                Toast.makeText(this,"请输入正确手机号",Toast.LENGTH_SHORT).show();
            }else
            {
                System.out.println("--------------"+userPhone);
                OkHttpUtils.get()
                        .url("http://192.168.1.200:8080/DistributionAPI")
                        .addParams("config","codeNum")
                        .addParams("phone",userPhone)
                        .build()
                        .execute(new getCodeCallBacl() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                System.out.println(e.getMessage());
                                Toast.makeText(RegisterActivity.this,"获取验证码失败"+e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onResponse(YZMRespone response, int id) {
                                if (response!=null)
                                {
                                    String secuu = response.getId();
                                    if (secuu.equals("0"))
                                    {
//                                        Toast.makeText(RegisterActivity.this,"获取验证码成功",Toast.LENGTH_SHORT).show();
                                        if (cdtu == null)
                                        {
                                            cdtu  = new CountDownTimerUtils(yanzheng_textView,50000,1000,RegisterActivity.this);
                                        }
                                        cdtu.start();
                                    }else if(secuu.equals("-52"))
                                    {
                                        Toast.makeText(RegisterActivity.this,"获取验证码失败",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
        }
        }else
        {
            Toast.makeText(RegisterActivity.this,"请连接网络",Toast.LENGTH_SHORT).show();
        }

    }

}
