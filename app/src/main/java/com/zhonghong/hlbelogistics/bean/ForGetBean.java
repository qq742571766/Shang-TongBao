package com.zhonghong.hlbelogistics.bean;

/**
 * Created by Administrator on 2017/4/11.
 * 根据用户名查找用户密码用到的实体类（忘记密码）
 */

public class ForGetBean {
    /**
     * 用户id
     */
    private String accountId;
    /**
     * 用户手机号
     */
    private String phone;
    /**
     * 服务器返回key值
     */
    private String id;
    /**
     * 服务器返回提示语
     */
    private String info;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
