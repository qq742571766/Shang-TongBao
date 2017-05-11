package com.zhonghong.hlbelogistics.bean;

import java.io.Serializable;

/**
 * 账户信息实体类
 * Created by Administrator on 2017/3/21.
 */

public class User implements Serializable {
    //用户名
    private String user_name;
    //手机号
    private String phone;
    //通讯地址
    private String address;
    //所属市
    private String c_address;
    //所属区
    private String d_address;
    //所属省
    private String p_address;
    //Email
    private String e_mail;
    //身份证号
    private String ID_Card_Num;
    private String proId;
    private String Info;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getC_address() {
        return c_address;
    }

    public void setC_address(String c_address) {
        this.c_address = c_address;
    }

    public String getD_address() {
        return d_address;
    }

    public void setD_address(String d_address) {
        this.d_address = d_address;
    }

    public String getP_address() {
        return p_address;
    }

    public void setP_address(String p_address) {
        this.p_address = p_address;
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

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    public String getID_Card_Num() {
        return ID_Card_Num;
    }

    public void setID_Card_Num(String ID_Card_Num) {
        this.ID_Card_Num = ID_Card_Num;
    }
}
