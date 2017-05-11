package com.zhonghong.hlbelogistics.core.home.Activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.zhonghong.hlbelogistics.R;
import com.zhonghong.hlbelogistics.base.activitys.BaseActivity;

/**
 * 绑定银行卡
 */
public class Bind_bank_Activity extends BaseActivity implements View.OnClickListener{
    private ImageView bank_Activity_iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_bank_);
        initView();
    }

    private void initView() {
        bank_Activity_iv= (ImageView) findViewById(R.id.bank_Activity_iv);
        bank_Activity_iv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bank_Activity_iv:
                finish();
                break;
        }
    }
}
