package com.zhonghong.hlbelogistics.core.home.Activitys;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.zhonghong.hlbelogistics.R;
import com.zhonghong.hlbelogistics.base.activitys.BaseActivity;

/**
 * 地图定位页
 * Created by Administrator on 2017/3/10.
 */
public class BaidumapActivty extends BaseActivity implements View.OnClickListener {
    //订单号
    private String orderNum;
    private TextureMapView mapView = null;
    private BaiduMap mBaiduMap = null;
    private LocationClient mLocationClient;
    private ImageView map_location_iv;
    private ImageButton btn_map;
    public static BDLocation bdLocation;
    boolean map_ones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baidumap);
        Intent intent = getIntent();
        orderNum = intent.getStringExtra("orderNum");
        map_location_iv = (ImageView) findViewById(R.id.map_location_iv);
        map_location_iv.setOnClickListener(this);
        initView();
        startLocation();

    }

    //动态定位权限
    private void startLocation() {
        if (Build.VERSION.SDK_INT >= 23) {
            int checkPermission = ContextCompat.checkSelfPermission(BaidumapActivty.this, Manifest.permission.ACCESS_COARSE_LOCATION);
            if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(BaidumapActivty.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            } else {
                showLocation();
            }
        } else {
            showLocation();
        }
    }

    //初始化地图
    private void initView() {
        btn_map = (ImageButton) findViewById(R.id.btn_map);
        btn_map.setOnClickListener(this);
        mapView = (TextureMapView) findViewById(R.id.bmapView);
        //得到BaiduMap的实例
        mBaiduMap = mapView.getMap();
        //设置Baidu的地图类型，具有MAP_TYPE_NONE、MAP_TYPE_NORMAL和MAP_TYPE_SATELLITE
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        //必须主动开启定位功能
        mBaiduMap.setMyLocationEnabled(true);
//        mCurrentMode = LocationMode.NORMAL;
//        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(mCurrentMode, true, mCurrentMarker));
        LatLng point = new LatLng(41.906030027, 123.40816855430603);
        // 设置当前位置显示在地图中心
        float f = mBaiduMap.getMaxZoomLevel();// 19.0 最小比例尺
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(point, f - 10);// 设置缩放比例
        mBaiduMap.animateMapStatus(u);
        // 定位初始化
        mLocationClient = new LocationClient(this);
        mLocationClient.registerLocationListener(new MyLocationListener());
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");// 可选，默认gcj02，设置返回的定位结果坐标系
//        int span = 10000;
//        option.setScanSpan(span);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);// 可选，默认false,设置是否使用gps
        option.setLocationNotify(false);// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);// 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);// 可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);// 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);// 可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);// 可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    //开启定位
    private void showLocation() {
        //调用LocationClient的start接口，接下来SDK将完成发送定位请求的工作
        mLocationClient.start();

    }

    //清除定位坐标
//    private void clearLocation() {
//        if (mMarker != null) {
//            mLocationClient.stop();
//            mMarker.remove();
//            mMarker = null;
//            mapView.invalidate();
//        }
//    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.map_location_iv:
                this.finish();
                break;
            case R.id.btn_map:
                if (bdLocation != null) {
                    LatLng ll = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
                    MapStatus.Builder builder = new MapStatus.Builder();
                    builder.target(ll).zoom(18.0f);
                    mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                }
                break;
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
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
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    //    private Marker mMarker;
    //定位监听
    private class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            BaidumapActivty.bdLocation = bdLocation;
            // map view 销毁后不在处理新接收的位置
            if (bdLocation == null || mapView == null) {
                return;
            }
            //BDLocation中含有大量的信息，由之前配置的option决定
            //LocType定义了结果码，例如161表示网络定位成功
            //具体参考sdk文档
            MyLocationData locData = new MyLocationData.Builder().accuracy(bdLocation.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(bdLocation.getLatitude()).longitude(bdLocation.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);

            if (!map_ones) {
                map_ones = true;
                LatLng ll = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
//            //我在这里利用经度、纬度信息构建坐标点
//            LatLng point = new LatLng( bdLocation.getLatitude(),bdLocation.getLongitude());
//            //创建一个新的MapStatus
//            MapStatus mapStatus = new MapStatus.Builder()
//                    //定位到定位点
//                    .target(point)
//                    //决定缩放的尺寸
//                    .zoom(12)
//                    .build();
//            //利用MapStatus构建一个MapStatusUpdate对象
//            MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus);
//            //更新BaiduMap，此时BaiduMap的界面就会从初始位置（北京），移动到定位点
//            mBaiduMap.setMapStatus(mapStatusUpdate);
//            //得到定位使用的图标
//            Bitmap origin = BitmapFactory.decodeResource(getResources(), R.drawable.mark);
//            //重新构建定位图标
//            BitmapDescriptor bitmap = BitmapDescriptorFactory.fromBitmap(origin);
//            //利用定位点信息和图标，构建MarkerOption，用于在地图上添加Marker
//            MarkerOptions option = new MarkerOptions().position(point).icon(bitmap);
//            //设置Marker的动画效果，我选的是“生长”
//            option.animateType(MarkerOptions.MarkerAnimateType.grow);
//
//            //因为我选择的是不断更新位置，
//            //因此，如果之前已经叠加过图标，先移除
//            if (mMarker != null) {
//                mMarker.remove();
//            }
//
//            //在地图上添加Marker，并显示
//            mMarker = (Marker)(mBaiduMap.addOverlay(option));
//        }
//
//        //这个接口主要返回连接网络的类型
//        @Override
//        public void onConnectHotSpotMessage(String s, int i) {
//        }
    }

    //销毁
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

    //    @Override
//    protected void onResume() {
//        super.onResume();
//        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
//        mapView.onResume();
//    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mapView.onPause();
    }

}
