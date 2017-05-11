package com.zhonghong.hlbelogistics.bean;

import java.io.Serializable;

/**
 * 百度地图  经纬度实体类
 * Created by Administrator on 2017/3/21.
 */

public class BaiDuMap implements Serializable{
    //服务器返回提示键值
    private String proId;
    //服务器返回提示信息
    private String Info;
    //纬度
    private Double Lat;
    //经度
    private Double Lon;

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public String getInfo() {
        return Info;
    }

    public void setInfo(String info) {
        Info = info;
    }

    public Double getLon() {
        return Lon;
    }

    public void setLon(Double lon) {
        Lon = lon;
    }

    public Double getLat() {
        return Lat;
    }

    public void setLat(Double lat) {
        Lat = lat;
    }
}
