package com.zhonghong.hlbelogistics.bean;

/**
 * 抢单 实体类
 * Created by Administrator on 2017/3/18.
 */

public class GrapSingleOrder {
    //订单号
    private String orderNum;
    //库存省份
    private String INVENTORY_PROVINCE;
    //库存城市
    private String INVENTORY_CITY;
    //库存区
    private String INVENTORY_COUNTY;
    //库存地址
    private String INVENTORY_ADDRESS;
    //交收省份
    private String DELIVERY_PROVINCE;
    //交收城市
    private String DELIVERY_CITY;
    //交收区
    private String DELIVERY_COUNTY;
    //交收地址
    private String DELIVERY_ADDRESS;
    //服务器返回键值
    private String proId;
    //服务器返回提示信息
    private String Info;
    //goodsName
    private String goodsName;

    public String getINVENTORY_PROVINCE() {
        return INVENTORY_PROVINCE;
    }

    public void setINVENTORY_PROVINCE(String INVENTORY_PROVINCE) {
        this.INVENTORY_PROVINCE = INVENTORY_PROVINCE;
    }

    public String getINVENTORY_COUNTY() {
        return INVENTORY_COUNTY;
    }

    public void setINVENTORY_COUNTY(String INVENTORY_COUNTY) {
        this.INVENTORY_COUNTY = INVENTORY_COUNTY;
    }

    public String getINVENTORY_ADDRESS() {
        return INVENTORY_ADDRESS;
    }

    public void setINVENTORY_ADDRESS(String INVENTORY_ADDRESS) {
        this.INVENTORY_ADDRESS = INVENTORY_ADDRESS;
    }

    public String getDELIVERY_PROVINCE() {
        return DELIVERY_PROVINCE;
    }

    public void setDELIVERY_PROVINCE(String DELIVERY_PROVINCE) {
        this.DELIVERY_PROVINCE = DELIVERY_PROVINCE;
    }

    public String getDELIVERY_CITY() {
        return DELIVERY_CITY;
    }

    public void setDELIVERY_CITY(String DELIVERY_CITY) {
        this.DELIVERY_CITY = DELIVERY_CITY;
    }

    public String getDELIVERY_COUNTY() {
        return DELIVERY_COUNTY;
    }

    public void setDELIVERY_COUNTY(String DELIVERY_COUNTY) {
        this.DELIVERY_COUNTY = DELIVERY_COUNTY;
    }


    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getDELIVERY_ADDRESS() {
        return DELIVERY_ADDRESS;
    }

    public void setDELIVERY_ADDRESS(String DELIVERY_ADDRESS) {
        this.DELIVERY_ADDRESS = DELIVERY_ADDRESS;
    }

    public String getINVENTORY_CITY() {
        return INVENTORY_CITY;
    }

    public void setINVENTORY_CITY(String INVENTORY_CITY) {
        this.INVENTORY_CITY = INVENTORY_CITY;
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

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
}
