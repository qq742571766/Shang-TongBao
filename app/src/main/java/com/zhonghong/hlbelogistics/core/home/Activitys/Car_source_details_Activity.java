package com.zhonghong.hlbelogistics.core.home.Activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.zhonghong.hlbelogistics.R;
import com.zhonghong.hlbelogistics.base.activitys.BaseActivity;

public class Car_source_details_Activity extends BaseActivity implements View.OnClickListener {
    private ImageView carSource_details_Activity_iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_source_details_);
        initView();
    }

    private void initView() {
        carSource_details_Activity_iv= (ImageView) findViewById(R.id.carSource_details_Activity_iv);
        carSource_details_Activity_iv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.carSource_details_Activity_iv:
                finish();
                break;
        }
    }
}
