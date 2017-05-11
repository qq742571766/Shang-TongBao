package com.zhonghong.hlbelogistics.core.home.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhonghong.hlbelogistics.R;
import com.zhonghong.hlbelogistics.base.Application.MyApplication;
import com.zhonghong.hlbelogistics.base.activitys.BaseActivity;
import com.zhonghong.hlbelogistics.core.login.LoginActivity;
import com.zhonghong.hlbelogistics.respone.ModifyPasswordRespone;
import com.zhonghong.hlbelogistics.callback.ModifyPwdCallBack;
import com.zhonghong.hlbelogistics.callback.getCodeCallBacl;
import com.zhonghong.hlbelogistics.respone.YZMRespone;
import com.zhonghong.hlbelogistics.utils.CountDownTimerUtils;
import com.zhonghong.hlbelogistics.utils.NetWorkStatte;
import com.zhonghong.hlbelogistics.utils.OkHttpUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.HashMap;

import okhttp3.Call;


public class Modify_password_Activity extends BaseActivity implements View.OnClickListener {
    private ImageView modify_password_activity_iv;
    //修改按钮
    private Button modify_password_activity_btn;
    //当前密码
    private EditText currebt_pwd;
    //新密码
    private EditText new_pwd;
    //确认密码
    private EditText confirm_pwd;
    //显示当前手机号
    private TextView phoneNum;
    private EditText yanzheng_editText;
    private TextView yanzheng_button;
    CountDownTimerUtils cdtu = null;//倒计时
    private String phoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password_);
        iniView();
    }
    private void iniView() {
        modify_password_activity_iv= (ImageView) findViewById(R.id.modify_password_activity_iv);
        currebt_pwd= (EditText) findViewById(R.id.currebt_pwd);
        new_pwd= (EditText) findViewById(R.id.new_pwd);
        confirm_pwd= (EditText) findViewById(R.id.confirm_pwd);
        yanzheng_editText= (EditText) findViewById(R.id.modify_yanzheng_editText);
        yanzheng_button= (TextView) findViewById(R.id.yanzheng_button);
        phoneNum = (TextView) findViewById(R.id.phoneNum);
        String aa = MyApplication.getInstance().getDeliveryPhone();
        aa = aa.substring(0, 3) + "****" + aa.substring(7, 11);
        phoneNum.setText(aa);
        modify_password_activity_btn= (Button) findViewById(R.id.modify_password_activity_btn);
        modify_password_activity_iv.setOnClickListener(this);
        yanzheng_button.setOnClickListener(this);
        modify_password_activity_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.modify_password_activity_iv:
                finish();
                break;
            case R.id.yanzheng_button:
                cdtu  = new CountDownTimerUtils(yanzheng_button,120000,1000,this);
                cdtu.start();
                getYZM();
                break;
            case R.id.modify_password_activity_btn:
                modifyPassword();
                break;
        }
    }

    //获取验证码
    private void getYZM() {

        if (NetWorkStatte.networkConnected(getApplicationContext()))
        {
            phoneNumber = MyApplication.getInstance().getDeliveryPhone();
                OkHttpUtils.get()
                        .url(OkHttpUtil.URL)
                        .addParams("config","codeNum")
                        .addParams("phone",phoneNumber )
                        .build()
                        .execute(new getCodeCallBacl() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Toast.makeText(Modify_password_Activity.this,"获取验证码失败"+e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onResponse(YZMRespone response, int id) {
                                if (response!=null)
                                {
                                    YZMRespone respone=response;
                                    if (respone.getId()=="0") {
                                    }else if(respone.getId()=="-52")
                                    {
                                        Toast.makeText(Modify_password_Activity.this,response.getInfo(),Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });

        }else
        {
            Toast.makeText(Modify_password_Activity.this,"请连接网络",Toast.LENGTH_SHORT).show();
        }

    }
    //修改密码
    private void modifyPassword(){

         if(NetWorkStatte.networkConnected(getApplicationContext())){
             String useName=MyApplication.getInstance().getUsername();
             String phone=MyApplication.getInstance().getDeliveryPhone();
             String now_password=MyApplication.getInstance().getPassword();
             //当前密码
             String old_password=currebt_pwd.getText().toString();
             //新密码
             String new_password=new_pwd.getText().toString();
             //确认密码
             String second_new_password=confirm_pwd.getText().toString();
             String code=yanzheng_editText.getText().toString();
             if(!now_password.equals(old_password)){
                 Toast.makeText(Modify_password_Activity.this,"当前密码输入错误！",Toast.LENGTH_SHORT).show();
             }else if(!new_password.equals(second_new_password)){
               Toast.makeText(Modify_password_Activity.this,"两次输入密码不一致！",Toast.LENGTH_SHORT).show();
           }else if(new_password.length()<6||new_password.length()>14){
               Toast.makeText(Modify_password_Activity.this,"密码格式错误，密码长度应为6到14位",Toast.LENGTH_SHORT).show();
           }else {
               OkHttpUtils.get()
                       .url(OkHttpUtil.URL)
                       .addParams("config","changePwd")
                       .addParams("username",useName)
                       .addParams("oldPassword",old_password)
                       .addParams("newPassword",new_password)
                       .addParams("code",code)
                       .addParams("phone",phone)
                       .addParams("type","changePwd")
                       .build()
                       .execute(new ModifyPwdCallBack() {
                           @Override
                           public void onError(Call call, Exception e, int id) {
                               Toast.makeText(Modify_password_Activity.this,"异常"+e.getMessage(),Toast.LENGTH_SHORT).show();
                           }

                           @Override
                           public void onResponse(ModifyPasswordRespone response, int id) {
                               if(response!=null){
                                   ModifyPasswordRespone mr=response;
                                   if (mr.getId()=="0")
                                   {
                                       Intent intent=new Intent(Modify_password_Activity.this, LoginActivity.class);
                                       intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                       startActivity(intent);
                                   }else if (mr.getId()=="-52")
                                   {
//                                       Toast.makeText(Modify_password_Activity.this,mpr.getInfo(),Toast.LENGTH_SHORT).show();
                                   }

                               }else {
//                                   Toast.makeText(Modify_password_Activity.this,"空的响应",Toast.LENGTH_SHORT).show();
                               }

                           }
                       });
           }
         }else {
             Toast.makeText(Modify_password_Activity.this,"请连接网络",Toast.LENGTH_SHORT).show();
         }
    }

}
