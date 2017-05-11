package com.zhonghong.hlbelogistics.core.home.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhonghong.hlbelogistics.R;
import com.zhonghong.hlbelogistics.base.Application.MyApplication;
import com.zhonghong.hlbelogistics.bean.User;
import com.zhonghong.hlbelogistics.callback.UserInfoCallBack;
import com.zhonghong.hlbelogistics.utils.NetWorkStatte;
import com.zhonghong.hlbelogistics.utils.OkHttpUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.Call;

/**
 * 账户管理Activity
 */
public class AccountManagementActivity extends AppCompatActivity implements View.OnClickListener{
    //返回
    private ImageView account_Activity_iv;
    //修改按钮
    private Button modify_btn;
    /**
     *用户名
     */
    private TextView name;
    /**
     * 手机号
     */
    private TextView phone;
    /**
     * email
     */
    private TextView email;
    /**
     * 身份证号
     */
    private TextView id_card;
    /**
     * 所在地区
     */
    private TextView adress;
    /**
     * 详细地址
     */
    private TextView deatils_adress;

    private User user=null;


    String str="修改";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_management);
        initView();
        getUserInfo();
    }
     //初始化个控件
    private void initView() {
        name= (TextView) findViewById(R.id.name);
        phone= (TextView) findViewById(R.id.phone);
        email= (TextView) findViewById(R.id.email);
        id_card= (TextView) findViewById(R.id.id_card);
        adress= (TextView) findViewById(R.id.adress);
        deatils_adress= (TextView) findViewById(R.id.deatils_adress);
        account_Activity_iv= (ImageView) findViewById(R.id.account_Activity_iv);
        account_Activity_iv.setOnClickListener(this);
        modify_btn= (Button) findViewById(R.id.modify_btn);
        modify_btn.setOnClickListener(this);
        modify_btn.setText(str);
        //标记按钮   表示没有被点击过
        modify_btn.setTag(false);
        modify_btn.setOnClickListener(this);

    }
    //获取用户个人信息
    private void getUserInfo(){
        String id= MyApplication.getInstance().getDeliveryId();
        if (NetWorkStatte.networkConnected(AccountManagementActivity.this)){
            OkHttpUtils.get()
                    .url(OkHttpUtil.URL)
                    .addParams("config","userCenter")
                    .addParams("deliveryId",id)
                    .addParams("infoType","1" )
                    .build()
                    .execute(new UserInfoCallBack() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                        }

                        @Override
                        public void onResponse(User response, int id) {
                            if(response!=null){
                                user=new User();
                                user=response;
                                String details_adress=user.getP_address()+user.getC_address()+user.getD_address();
                                name.setText(user.getUser_name());
                                phone.setText(user.getPhone());
                                email.setText(user.getE_mail());
                                id_card.setText(user.getID_Card_Num());
                                adress.setText(details_adress);
                                deatils_adress.setText(user.getAddress());
                            }

                        }
                    });

        }else {
            Toast.makeText(AccountManagementActivity.this,"请连接网络",Toast.LENGTH_SHORT).show();
        }
    }
     //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.account_Activity_iv:
               this.finish();
                break;
            case R.id.modify_btn:
                Intent intent=new Intent(AccountManagementActivity.this,ModifyAccountActivity.class);
                intent.putExtra("user",user);
                startActivity(intent);
                break;
        }
    }
}
