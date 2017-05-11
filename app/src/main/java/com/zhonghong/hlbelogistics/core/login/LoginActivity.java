package com.zhonghong.hlbelogistics.core.login;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zhonghong.hlbelogistics.R;
import com.zhonghong.hlbelogistics.base.Application.MyApplication;
import com.zhonghong.hlbelogistics.base.activitys.BaseActivity;
import com.zhonghong.hlbelogistics.core.home.Activitys.ForgetUserNameActivity;
import com.zhonghong.hlbelogistics.core.home.Activitys.MainActivity;
import com.zhonghong.hlbelogistics.utils.NetWorkStatte;
import com.zhonghong.hlbelogistics.utils.OkHttpUtil;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText phone_number_editText;
    private EditText password_editText;
    private Button login_button;
    private TextView wangji_textView;
    private ProgressDialog progressDialog;//加载Dialog



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        String userName=MyApplication.getInstance().getUsername();
        String pwd=MyApplication.getInstance().getPassword();
        if(userName!=null&&pwd!=null){
            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            this.finish();
        }
        initview();
    }

    /**
     * 初始化控件
     */
    private void initview(){
        phone_number_editText= (EditText) findViewById(R.id.phone_number_editText);
        password_editText= (EditText) findViewById(R.id.password_editText);
        login_button= (Button) findViewById(R.id.login_button);
        wangji_textView= (TextView) findViewById(R.id.wangji_textView);
        login_button.setOnClickListener(this);
        wangji_textView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:
                verificationAndJump();
                break;
            case R.id.wangji_textView:
                Intent forgetIntent = new Intent(LoginActivity.this, ForgetUserNameActivity.class);
                startActivity(forgetIntent);
                break;
        }

    }
    //验证跳转
    private void verificationAndJump(){
        if(NetWorkStatte.networkConnected(getApplicationContext())){
            String userName=phone_number_editText.getText().toString();
            String userPassword=password_editText.getText().toString();
            String token ;
            if (MyApplication.getInstance().getToken()==null)
            {
                token = "";
            }else{
                token =  MyApplication.getInstance().getToken();
            }

            if(userName==null||userName==""){
                Toast.makeText(LoginActivity.this,"请输入正确的用户名",Toast.LENGTH_SHORT).show();
            }else if(userPassword==null||userPassword==""|| userPassword.length()>14||userPassword.length()<6){
                Toast.makeText(LoginActivity.this,"请输入正确密码，密码为6到14位",Toast.LENGTH_SHORT).show();
            }else
            {
                OkHttpUtil okHttpUtil = new OkHttpUtil(LoginActivity.this,progressDialog);
                okHttpUtil.getHttp("login",userName,userPassword,token);
//                     OkHttpUtils.get()
//                                .addParams("config","login")
//                                .addParams("usename",userName)
//                                .addParams("password",userPassword)
//                                .addParams("token","")
//                                .url("http://192.168.1.200:8080/DistributionAPI")
//                                .build()
//                                .connTimeOut(10000)
//                                .execute(new ToLoginCallBack() {
//
//                                    @Override
//                                    public void onBefore(Request request, int id) {
//
//                                        progressDialog = new ProgressDialog(LoginActivity.this, ProgressDialog.THEME_DEVICE_DEFAULT_DARK);
//                                        progressDialog.setMessage("正在登陆中");
//                                        progressDialog.setCanceledOnTouchOutside(false);
//                                        progressDialog.show();
//                                        super.onBefore(request, id);
//                                    }
//
//                                    @Override
//                                    public void onAfter(int id) {
//                                        super.onAfter(id);
//                                        progressDialog.dismiss();
//                                    }
//
//                                    @Override
//                                    public void onError(Call call, Exception e, int id) {
//                                        Toast.makeText(LoginActivity.this,"异常"+e.getMessage(),Toast.LENGTH_SHORT).show();
//                                    }
//                                    @Override
//                                    public void onResponse(LoginRespone response, int id) {
//                                        if(response!=null){
//                                            LoginRespone lr=response;
//                                            if(lr.getId()=="0"){
//                                                Toast.makeText(LoginActivity.this,lr.getInfo(),Toast.LENGTH_SHORT).show();
//                                                String token = lr.getTokenInfo();
//                                                String uid = lr.getUid();
//                                                String deliveryId=lr.getDeliveryId();
//                                                String deliveryPhone=lr.getDeliveryPhone();
//                                                if (token!=null)
//                                                {
//                                                    MyApplication.getInstance().saveDeliveryId(deliveryId);
//                                                    MyApplication.getInstance().saveToken(token);
//                                                    MyApplication.getInstance().saveUid(uid);
//                                                    MyApplication.getInstance().saveDeliveryPhone(deliveryPhone);
//                                                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
//                                                    LoginActivity.this.startActivity(intent);
//
//                                                }else
//                                                {
//                                                   Toast.makeText(LoginActivity.this,"获取token为空,登录失败",Toast.LENGTH_SHORT).show();
//                                                }
//
//
//                                            }else if(lr.getId()=="-52"){
//                                                Toast.makeText(LoginActivity.this,lr.getInfo(),Toast.LENGTH_SHORT).show();
//                                            }
//
//                                        }else {
//                                            Toast.makeText(LoginActivity.this,"空的响应",Toast.LENGTH_SHORT).show();
//                                        }
//
//                                    }
//                                });
            }
        }else {
            Toast.makeText(LoginActivity.this,"请连接网络",Toast.LENGTH_SHORT).show();
        }
    }


//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK)
//        {
//            Intent mainIntent = new Intent(LoginActivity.this,MainActivity.class);
//            startActivity(mainIntent);
//            return false;
//        }else
//        return super.onKeyDown(keyCode, event);
//    }
}



