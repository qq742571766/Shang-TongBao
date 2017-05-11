package com.zhonghong.hlbelogistics.bean;

/**
 * Created by Administrator on 2017/4/5.
 * 城市实体类
 */

public class City {
    /**
     * 城市名称
     */
    private String cityName;
    /**
     * 城市id
     */
    private String cityCode;
    /**
     * fanhui
     */
    private String proId;
    private String Info;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

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


}
