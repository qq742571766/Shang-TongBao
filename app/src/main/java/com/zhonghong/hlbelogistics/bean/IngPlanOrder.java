package com.zhonghong.hlbelogistics.bean;

/**
 * 进行中计划实体类
 * Created by Administrator on 2017/3/18.
 */

public class IngPlanOrder {
    //进行中计划号
    private String planId;
    //订单号 （没用上）
    private int orderNum;
    //实际开始时间
    private String start_time;
    //服务器返回提示信息
    private String Info;
    //服务器返回键值
    private String ProId;
    //车id
    private String carId;
    //配送员电话
    private String deliveryPhone;

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getInfo() {
        return Info;
    }

    public void setInfo(String info) {
        Info = info;
    }

    public String getProId() {
        return ProId;
    }

    public void setProId(String proId) {
        ProId = proId;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getDeliveryPhone() {
        return deliveryPhone;
    }

    public void setDeliveryPhone(String deliveryPhone) {
        this.deliveryPhone = deliveryPhone;
    }
}
