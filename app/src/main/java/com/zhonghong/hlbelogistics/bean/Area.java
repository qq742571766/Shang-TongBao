package com.zhonghong.hlbelogistics.bean;

/**
 * Created by Administrator on 2017/4/5.
 * 作用：地区是实体类
 */

public class Area {
    /**
     * 地区名称
     */
    private String areaName;
    /**
     * 地区对应编码
     */
    private String areaCode;
    /**
     * 返回key值
     */
    private String proId;
    /**
     * 返回提示语
     */
    private String Info;

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
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
