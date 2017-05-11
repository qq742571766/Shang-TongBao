package com.zhonghong.hlbelogistics.core.home.Fragments;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.zhonghong.hlbelogistics.R;
import com.zhonghong.hlbelogistics.base.Application.MyApplication;
import com.zhonghong.hlbelogistics.core.home.Activitys.AccountManagementActivity;
import com.zhonghong.hlbelogistics.core.home.Activitys.Bind_bank_Activity;
import com.zhonghong.hlbelogistics.core.home.Activitys.Exception_report_Activity;
import com.zhonghong.hlbelogistics.core.home.Activitys.Modify_password_Activity;
import com.zhonghong.hlbelogistics.core.login.LoginActivity;

/**
    Modify by lize by 2017年3月6日
 */
public class SetFragment extends Fragment implements View.OnClickListener {
//    private RelativeLayout bind_bank;
    private RelativeLayout account_management;
    private RelativeLayout modify_password;
    private View view;
    private Button cancelandback_btn;//退出
    private Intent intent;
    private static Boolean ISLOGIN = true;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_set,container,false);
        initview();
//        System.out.println("---------SetFragment-------------");
        return view;
    }

    private void initview(){
//        bind_bank= (RelativeLayout) view.findViewById(R.id.bind_bank);
        account_management= (RelativeLayout) view.findViewById(R.id.zh_admin);
        modify_password = (RelativeLayout) view.findViewById(R.id.modify_pwd);//获取修改密码relativelayout
        modify_password.setOnClickListener(this);//添加监听
        cancelandback_btn = (Button) view.findViewById(R.id.cancelandback_btn);
        cancelandback_btn.setOnClickListener(this);
//        bind_bank.setOnClickListener(new bind_bank_click());
        account_management.setOnClickListener(new account_management_click());
       if (MyApplication.getInstance().getToken()==null)
       {
           ISLOGIN = false;
           cancelandback_btn.setText("登录");
       }else {
           ISLOGIN = true;
           cancelandback_btn.setText("退出");
       }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.modify_pwd:
                Intent modifyIntent = new Intent(getActivity(),Modify_password_Activity.class);
                startActivity(modifyIntent);
                break;

            case R.id.cancelandback_btn:

                if (ISLOGIN)
                {
                    MyApplication.getInstance().removeAll();
                    MyApplication.getInstance().saveToken("");
                }
                Intent back_Intent = new Intent(getActivity(),LoginActivity.class);
                back_Intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(back_Intent);
                break;

        }
    }

    /**
     * 账户管理自定义点击事件
     */
    private class account_management_click implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            intent=new Intent(getActivity(),AccountManagementActivity.class);
            startActivity(intent);

        }
    }
}
