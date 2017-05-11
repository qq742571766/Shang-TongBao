package com.zhonghong.hlbelogistics.bean;

/**
 * 经纬度实体类   没用上！！！！！！！
 * Created by Administrator on 2017/3/24.
 */

public class LatAndLon {
    private Double lat;
    private Double lon;
    public LatAndLon( Double lat,Double lon){
        this.lat=lat;
        this.lon=lon;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }
}
