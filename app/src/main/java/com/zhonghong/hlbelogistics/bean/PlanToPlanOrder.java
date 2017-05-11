package com.zhonghong.hlbelogistics.bean;

/**
 * 计划中   订单的列表字段
 * Created by Administrator on 2017/3/15.
 */

public class PlanToPlanOrder {
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
    //订单号
    private String orderNum;
    //服务器返回键值
    private String proId;
    //服务器返回信息
    private String Info;
    //问题单中用到的字段
    private String contacts;
    //电话
    private String phone;
    //订单状态
    private String status;
    //开始时间
    private String start_time;
    //结束时间
    private String end_time;
    //货物名称
    private String goodsName;

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

    public String getINVENTORY_PROVINCE() {
        return INVENTORY_PROVINCE;
    }

    public void setINVENTORY_PROVINCE(String INVENTORY_PROVINCE) {
        this.INVENTORY_PROVINCE = INVENTORY_PROVINCE;
    }

    public String getINVENTORY_CITY() {
        return INVENTORY_CITY;
    }

    public void setINVENTORY_CITY(String INVENTORY_CITY) {
        this.INVENTORY_CITY = INVENTORY_CITY;
    }

    public String getINVENTORY_COUNTY() {
        return INVENTORY_COUNTY;
    }

    public void setINVENTORY_COUNTY(String INVENTORY_COUNTY) {
        this.INVENTORY_COUNTY = INVENTORY_COUNTY;
    }



    public PlanToPlanOrder() {
    }

    public String getDELIVERY_ADDRESS() {
        return DELIVERY_ADDRESS;
    }

    public void setDELIVERY_ADDRESS(String DELIVERY_ADDRESS) {
        this.DELIVERY_ADDRESS = DELIVERY_ADDRESS;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }


    public String getINVENTORY_ADDRESS() {
        return INVENTORY_ADDRESS;
    }

    public void setINVENTORY_ADDRESS(String INVENTORY_ADDRESS) {
        this.INVENTORY_ADDRESS = INVENTORY_ADDRESS;
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

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
}