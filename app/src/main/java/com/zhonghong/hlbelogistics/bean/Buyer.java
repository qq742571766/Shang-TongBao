package com.zhonghong.hlbelogistics.bean;

/**
 *
 * Created by Administrator on 2017/3/8.
 * 用户实体类   没用上
 */

public class Buyer {

    private String id;
    private String gd_title;
    private String price_type;
    private String price;
    private String total;
    private String Dealed_num;//以成交量
    private String releas_time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReleas_time() {
        return releas_time;
    }

    public void setReleas_time(String releas_time) {
        this.releas_time = releas_time;
    }

    public String getDealed_num() {
        return Dealed_num;
    }

    public void setDealed_num(String dealed_num) {
        Dealed_num = dealed_num;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice_type() {
        return price_type;
    }

    public void setPrice_type(String price_type) {
        this.price_type = price_type;
    }

    public String getGd_title() {
        return gd_title;
    }

    public void setGd_title(String gd_title) {
        this.gd_title = gd_title;
    }
}
