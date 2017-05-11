package com.zhonghong.hlbelogistics.respone;

import java.util.List;

/**
 * Created by Administrator on 2017/3/8.
 */

public class BuyersInfoRespone {
    private String id;
    private String info;
    private List buyersInfoList;


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

    public List getBuyersInfoList() {
        return buyersInfoList;
    }

    public void setBuyersInfoList(List buyersInfoList) {
        this.buyersInfoList = buyersInfoList;
    }


}
