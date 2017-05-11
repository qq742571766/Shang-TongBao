package com.zhonghong.hlbelogistics.core.home.Activitys;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.TextureMapView;
import com.zhonghong.hlbelogistics.R;
import com.zhonghong.hlbelogistics.base.Application.MyApplication;
import com.zhonghong.hlbelogistics.base.activitys.BaseActivity;
import com.zhonghong.hlbelogistics.callback.ModifyPlanStatusCallBack;
import com.zhonghong.hlbelogistics.core.home.Adapter.myFragmentPageAdapter;
import com.zhonghong.hlbelogistics.core.home.Fragments.PlanCenterFragment;
import com.zhonghong.hlbelogistics.core.home.Fragments.SetFragment;
import com.zhonghong.hlbelogistics.core.home.Fragments.IngPlanFragment;
import com.zhonghong.hlbelogistics.respone.AllRespone;
import com.zhonghong.hlbelogistics.ui.AutoToolbar;
import com.zhonghong.hlbelogistics.utils.NetWorkStatte;
import com.zhonghong.hlbelogistics.utils.OkHttpUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.Call;

/**
 * 主界面
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {
    private AutoToolbar mtoolBar;
    private ViewPager mviewPager;
    private RadioGroup mGroup;
    private TextView main_toobar_textview, main_tb_tv;
    private RadioButton rbPlanCenter, rbTaskCenter, rbSet;
    private ArrayList<Fragment> fragmentList;


    private TextureMapView mapView = null;
    private BaiduMap mBaiduMap = null;
    private LocationClient mLocationClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initViewPager();
        mapView = (TextureMapView) findViewById(R.id.main_map);
        mBaiduMap = mapView.getMap();
        //设置Baidu的地图类型，具有MAP_TYPE_NONE、MAP_TYPE_NORMAL和MAP_TYPE_SATELLITE
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        //必须主动开启定位功能
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocationClient = new LocationClient(this);
        mLocationClient.registerLocationListener(new MyLocationListener());
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(30000);
        mLocationClient.setLocOption(option);
        /**
         * 开启上传经纬度
         */
        startLocation();
    }

    //初始化控件
    private void initView() {
        mviewPager = (ViewPager) findViewById(R.id.vp_mainactivity);
        mGroup = (RadioGroup) findViewById(R.id.main_rgTools);
//        rbInfoShow = (RadioButton) findViewById(R.id.rbInfoShow);
        rbPlanCenter = (RadioButton) findViewById(R.id.rbPlanCenter);
        rbTaskCenter = (RadioButton) findViewById(R.id.rbTaskCenter);
        rbSet = (RadioButton) findViewById(R.id.rbSet);
        main_toobar_textview = (TextView) findViewById(R.id.main_toobar_textview);
        main_tb_tv = (TextView) findViewById(R.id.main_tb_tv_btn);
        main_tb_tv.setOnClickListener(this);
        mGroup.setOnCheckedChangeListener(new myCheckChangeListen());
        rbPlanCenter.setChecked(true);

    }

    //初始化viewPager
    private void initViewPager() {
        PlanCenterFragment planCenterFragment = new PlanCenterFragment();
        SetFragment setFragment = new SetFragment();
        final IngPlanFragment ingPlanFragment = new IngPlanFragment();
        fragmentList = new ArrayList<Fragment>();
        fragmentList.add(planCenterFragment);
        fragmentList.add(ingPlanFragment);
        fragmentList.add(setFragment);
        myFragmentPageAdapter adapter = new myFragmentPageAdapter(getSupportFragmentManager(), fragmentList);
        //viewPager设置适配器
        mviewPager.setAdapter(adapter);
        //viewPager显示第一个fragment
        mviewPager.setCurrentItem(0);
        //viewPager页面切换监听
        mviewPager.setOnPageChangeListener(new myOnPageChangeListener());
        //获取监听方法
        planCenterFragment.setPlanCenterListener(new PlanCenterFragment.PlanCenterListener() {
            @Override
            public void onPlanOrderUnderway() {
//                Toast.makeText(getBaseContext(),"planCenterFragment == onPlanOrderUnderway ==",Toast.LENGTH_SHORT).show();
                ingPlanFragment.getAllOrder();
            }
        });

    }

    //所有点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_tb_tv_btn:
                if (main_tb_tv.getText() == "抢单") {
                    Intent intent = new Intent(this, GragSingleActivity.class);
                    startActivity(intent);
                } else if (main_tb_tv.getText() == "地图") {
                    Intent inte = new Intent(MainActivity.this, BaidumapActivty.class);
                    startActivity(inte);
                } else {

                }
                break;
        }
    }

    //radioButton切换fragment
    private class myCheckChangeListen implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rbPlanCenter:
                    mviewPager.setCurrentItem(0, false);
                    main_toobar_textview.setText("待接取的配送计划");
                    main_tb_tv.setText("抢单");
                    break;
                case R.id.rbTaskCenter:
                    mviewPager.setCurrentItem(1, false);
                    main_toobar_textview.setText("进行中的配送计划");
                    main_tb_tv.setText("地图");
                    break;
                case R.id.rbSet:
                    mviewPager.setCurrentItem(2, false);
                    main_toobar_textview.setText("设置");
                    main_tb_tv.setText("");
                    break;

            }

        }
    }

    //ViewPager切换Fragment,RadioGroup做相应变化
    private class myOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    mGroup.check(R.id.rbPlanCenter);
                    main_toobar_textview.setText("待接取的配送计划");
                    break;
                case 1:
                    mGroup.check(R.id.rbTaskCenter);
                    main_toobar_textview.setText("进行中的配送计划");
                    break;
                case 2:
                    mGroup.check(R.id.rbSet);
                    main_toobar_textview.setText("设置");
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }


    //上传经纬度
    private void postLatAndLon(String lon, String lat, String time) {
        Log.e("linhaos", "postLatAndLon: "+lon+"..."+lat);
        String dId = MyApplication.getInstance().getDeliveryId();
        if (NetWorkStatte.networkConnected(MainActivity.this)) {
            if (dId == null) {
                Toast.makeText(MainActivity.this, "配送员Id为空，无法提交", Toast.LENGTH_SHORT).show();
            } else {
                OkHttpUtils.get()
                        .url(OkHttpUtil.URL)
                        .addParams("config", "positionFun")
                        .addParams("deliveryId", dId)
                        .addParams("orderId", "")
                        .addParams("planId", "")
                        .addParams("lon", lon)
                        .addParams("lat", lat)
                        .addParams("uploadDate", time)
                        .build()
                        .execute(new ModifyPlanStatusCallBack() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
//                                Toast.makeText(MainActivity.this, "异常" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(AllRespone response, int id) {
                                if (response != null) {
                                    AllRespone ar = response;
                                } else {
//                                    Toast.makeText(MainActivity.this, "上传失败!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        } else {
            Toast.makeText(MainActivity.this, "请连接网络", Toast.LENGTH_SHORT).show();
        }
//        Toast.makeText(this, lon + "..." + lat, Toast.LENGTH_SHORT).show();
//        Log.d("TAG", "postLatAndLon: " + lon + "..." + lat);
    }

    private class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            // 要做的事情
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date curDate = new Date(System.currentTimeMillis());//获取当前时间
            String time = formatter.format(curDate);
            //我在这里利用经度、纬度信息构建坐标点
            Double lat = bdLocation.getLatitude();
            Double lon = bdLocation.getLongitude();
            postLatAndLon(lon.toString(), lat.toString(), time);
        }

        //这个接口主要返回连接网络的类型
        @Override
        public void onConnectHotSpotMessage(String s, int i) {
        }
    }

    //动态定位权限
    private void startLocation() {
        if (Build.VERSION.SDK_INT >= 23) {
            int checkPermission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION);
            if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                return;
            } else {
                mLocationClient.start();
            }
        } else {
            mLocationClient.start();
        }
    }

    /**
     * 运行了权限之后立即就可以获取到位置信息
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {

            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationClient.start();
                } else {
                    Toast.makeText(MainActivity.this, "未开启定位权限", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private long clickTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (SystemClock.uptimeMillis() - clickTime <= 1500) {
                //如果两次的时间差＜1s，就不执行操作

            } else {
                //当前系统时间的毫秒值
                clickTime = SystemClock.uptimeMillis();
                Toast.makeText(MainActivity.this, "再次点击退出", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 退出时销毁定位
        mLocationClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mapView.onDestroy();
    }
}

