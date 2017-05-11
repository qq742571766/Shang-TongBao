package com.zhonghong.hlbelogistics.base.activitys;


import android.os.Bundle;
import com.zhonghong.hlbelogistics.utils.ActivityCtrl;
import com.zhy.autolayout.AutoLayoutActivity;
/**
 *baseActivity
 * 继承AutoLayoutActivity
 * 适配类
 * 适配方法有待改进
 */
public class BaseActivity extends AutoLayoutActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCtrl.addActivity(this); // 新开启就添加
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        ActivityCtrl.removeActivity(this); // 销毁了便删除
    }
    @Override public void onBackPressed() {
        super.onBackPressed();
        ActivityCtrl.finishAll(); // 直接退出
    }
}

