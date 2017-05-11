package com.zhonghong.hlbelogistics.core.home.Activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.zhonghong.hlbelogistics.R;
import com.zhonghong.hlbelogistics.base.Application.MyApplication;
import com.zhonghong.hlbelogistics.bean.BaiDuMap;
import com.zhonghong.hlbelogistics.callback.BaiDuMapCallBack;
import com.zhonghong.hlbelogistics.utils.NetWorkStatte;
import com.zhonghong.hlbelogistics.utils.OkHttpUtil;
import com.zhy.http.okhttp.OkHttpUtils;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;

/**
 * 地图 轨迹
 */
public class MapLineActivity extends AppCompatActivity implements View.OnClickListener {
    private List<BaiDuMap> list;
    private List<LatLng> latAndLon = new ArrayList<LatLng>();
    private TextureMapView mapView;
    private BaiduMap mBaiduMap = null;
    private ImageView map_line_Activity_iv;
    //开始时间
    private String startTime;
    //结束时间
    private String endTime;
    private ProgressDialog progressDialog;//加载Dialog

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_line);
        Intent intent = getIntent();
        startTime = intent.getStringExtra("startTime");
        endTime = intent.getStringExtra("endTime");
        getLatAndLon(startTime, endTime);
//        list= (List<BaiDuMap>) getIntent().getSerializableExtra("mapList");
//        if(list!=null){
//            for(int i = 0;i<list.size();i++){
//                BaiDuMap bm=list.get(i);
//                LatLng ll=new LatLng(bm.getLat(),bm.getLon());
//                latAndLon.add(ll);
//            }
//        }
        map_line_Activity_iv = (ImageView) findViewById(R.id.map_line_Activity_iv);
        map_line_Activity_iv.setOnClickListener(this);
        mapView = (TextureMapView) findViewById(R.id.bmap_line_View);
        mBaiduMap = mapView.getMap();
        //设置Baidu的地图类型，具有MAP_TYPE_NONE、MAP_TYPE_NORMAL和MAP_TYPE_SATELLITE
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
//        LatLng point = new LatLng(120.042882,49.279245);
//        // 设置当前位置显示在地图中心
//        float f = mBaiduMap.getMaxZoomLevel();// 19.0 最小比例尺
//        MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(point, f-10);// 设置缩放比例
//        mBaiduMap.animateMapStatus(u);
//            if(list!=null){
//
//            }


    }

    //处理轨迹中的坐标
    public void drawStart() {
        //获取起点和终点以及计算中心点
        double latstart = list.get(0).getLat();
        double lngstart = list.get(0).getLon();
        double latend = list.get(list.size() - 1).getLat();
        double lngend = list.get(list.size() - 1).getLon();

        final double midlat = (latstart + latend) / 2;
        final double midlon = (lngstart + lngend) / 2;
        LatLng point = new LatLng(midlat, midlon);// 中点
        LatLng point1 = new LatLng(latstart, lngstart);// 起点
        LatLng point2 = new LatLng(latend, lngend);// 终点

        //地图缩放等级
        int zoomLevel[] = {2000000, 1000000, 500000, 200000, 100000, 50000,
                25000, 20000, 10000, 5000, 2000, 1000, 500, 100, 50, 20, 0};
        // 计算两点之间的距离，重新设定缩放值，让全部marker显示在屏幕中。
        int jl = (int) DistanceUtil.getDistance(point1, point2);
        int i;
        for (i = 0; i < 17; i++) {
            if (zoomLevel[i] < jl) {
                break;
            }
        }
        //根据起点和终点的坐标点计算出距离来对比缩放等级获取最佳的缩放值，用来得到最佳的显示折线图的缩放等级
        float zoom = i + 2;
        // 设置当前位置显示在地图中心
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(point, zoom);// 设置缩放比例
        mBaiduMap.animateMapStatus(u);

        /**
         * 创建自定义overlay
         */
        // 起点位置
        LatLng geoPoint = new LatLng(latstart, lngstart);
        // 构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.mark);
        // 构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions().position(geoPoint)
                .icon(bitmap).zIndex(8).draggable(true);

        // 终点位置
        LatLng geoPoint1 = new LatLng(latend, lngend);
        // 构建Marker图标
        BitmapDescriptor bitmap1 = BitmapDescriptorFactory
                .fromResource(R.drawable.mark);
        // 构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option1 = new MarkerOptions().position(geoPoint1)
                .icon(bitmap1).zIndex(8).draggable(true);
        // 在地图上添加Marker，并显示

        List<OverlayOptions> overlay = new ArrayList<OverlayOptions>();
        overlay.add(option);
        overlay.add(option1);
        mBaiduMap.addOverlays(overlay);

        //开始绘制
        drawMyRoute(latAndLon);


    }

    /**
     * 根据数据绘制轨迹
     *
     * @param
     */
    protected void drawMyRoute(List<LatLng> latAndLon) {
        OverlayOptions options = new PolylineOptions().width(10).color(0xAAFF0000).points(latAndLon);
        mBaiduMap.addOverlay(options);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mapView.onDestroy();
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.map_line_Activity_iv:
                this.finish();
                break;
        }
    }

    //获取经纬度list
    private void getLatAndLon(String startTime, String endTime) {
        if (NetWorkStatte.networkConnected(MapLineActivity.this)) {
            String deliveryId = MyApplication.getInstance().getDeliveryId();
            OkHttpUtils.get()
                    .url(OkHttpUtil.URL)
                    .addParams("config", "findPointFun")
                    .addParams("deliveryId", deliveryId)
                    .addParams("startTime", startTime)
                    .addParams("endTime", endTime)
                    .build()
                    .connTimeOut(10000)
                    .execute(new BaiDuMapCallBack() {

                        @Override
                        public void onBefore(Request request, int id) {
                            super.onBefore(request, id);
                            progressDialog = new ProgressDialog(MapLineActivity.this, ProgressDialog.BUTTON_POSITIVE);
                            progressDialog.setMessage("数据过多，请耐心等待");
                            progressDialog.setCanceledOnTouchOutside(false);
                            progressDialog.show();
                        }

                        @Override
                        public void onAfter(int id) {
                            super.onAfter(id);
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Toast.makeText(MapLineActivity.this, "异常" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(List<BaiDuMap> response, int id) {
                            list = response;
                            if (list.size() != 0) {
                                if (list.get(0).getProId().equals("0")) {
                                    for (int i = 0; i < list.size(); i++) {
                                        BaiDuMap bm = list.get(i);
                                        LatLng ll = new LatLng(bm.getLat(), bm.getLon());
                                        latAndLon.add(ll);
                                    }
                                    /**
                                     * 绘制轨迹的方法
                                     */
                                    drawStart();
                                } else {

                                }

                            } else {
                                Toast.makeText(MapLineActivity.this, "未生成轨迹", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

        } else {
            Toast.makeText(MapLineActivity.this, "请连接网络", Toast.LENGTH_SHORT).show();
        }
    }
}
