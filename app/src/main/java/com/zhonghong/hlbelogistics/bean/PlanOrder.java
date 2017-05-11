package com.zhonghong.hlbelogistics.bean;

/**
 * 待接取计划列表实体类
 * Created by Administrator on 2017/3/13.
 */

public class PlanOrder {
    private String keyId;
    private String orderNum;//订单号
    private int orderNumList;//订单号list
    private String status;//订单状态
    private String proId;//提示id
    private String info;//提示语
    private String planId;//待接取计划号
    private String planStartTime;//计划开始时间
    private String planEndTime;//计划结束时间



    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }


    public int getOrderNumList() {
        return orderNumList;
    }

    public void setOrderNumList(int orderNumList) {
        this.orderNumList = orderNumList;
    }


    public String getPlanStartTime() {
        return planStartTime;
    }

    public void setPlanStartTime(String planStartTime) {
        this.planStartTime = planStartTime;
    }

    public String getPlanEndTime() {
        return planEndTime;
    }

    public void setPlanEndTime(String planEndTime) {
        this.planEndTime = planEndTime;
    }
}
