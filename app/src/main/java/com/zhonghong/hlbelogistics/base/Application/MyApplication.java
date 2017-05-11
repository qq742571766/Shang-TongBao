package com.zhonghong.hlbelogistics.base.Application;

import android.app.Application;

import android.content.SharedPreferences;

import com.baidu.mapapi.SDKInitializer;
import com.zhy.autolayout.config.AutoLayoutConifg;

/**
 * 自定义Application
 * 保存了 uid（用户id）
 * token（用于自动登录的token）
 * 配送员id （deliveryId）
 * 配送员电话 （deliveryPhone）
 * 车id （carId）
 * Created by Administrator on 2017/3/6.
 */

public class MyApplication extends Application {

    public static final String USER_INFO = "USER_INFO";
    private static MyApplication app;
    private SharedPreferences sharedPreferences;
    private SharedPreferences sharedPreferences_info;
    private String uid; //用户id
    private String uname;//用户名
    private String pwd;//密码

    public MyApplication() {
        app = this;
    }

    private String token;//token
    private String deliveryId;//配送员id
    private String deliveryPhone;//配送员电话
    private String carId;//车牌号

    @Override
    public void onCreate() {
        super.onCreate();
        AutoLayoutConifg.getInstance().useDeviceSize();
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        //导入百度地图sdk必不可少
        SDKInitializer.initialize(getApplicationContext());
    }

    public static synchronized MyApplication getInstance() {
        if (app == null) {
            app = new MyApplication();
        }
        return app;
    }

    /**
     * SharedPreferences 用法：
     * 1、得到SharedPreferences对象
     * <p>
     * 2、调用SharedPreferences对象的edit()方法来获取一个SharedPreferences.Editor对象。
     * <p>
     * 3、向SharedPreferences.Editor对象中添加数据。
     * <p>
     * 4、调用commit方法将添加的数据提交。
     */
    //移除token
    public void removeToken() {
        if (sharedPreferences_info == null) {
            sharedPreferences_info = getSharedPreferences(USER_INFO, MODE_PRIVATE);
        }
        sharedPreferences_info.edit().remove("token").commit();
        token = null;
    }

    //保存DeliveryId
    public void saveDeliveryId(String deliveryId) {
        this.deliveryId = deliveryId;
        if (sharedPreferences_info == null) {
            sharedPreferences_info = getSharedPreferences(USER_INFO, MODE_PRIVATE);
        }
        sharedPreferences_info.edit().putString("deliveryId", deliveryId).commit();

    }

    //获取DeliveryId
    public String getDeliveryId() {
        if (this.deliveryId == null) {
            if (sharedPreferences_info == null) {
                sharedPreferences_info = getSharedPreferences(USER_INFO, MODE_PRIVATE);
            }
            this.deliveryId = sharedPreferences_info.getString("deliveryId", null);
            return deliveryId;
        }
        return deliveryId;
    }

    //移除DeliveryId
    public void removeDeliveryId() {
        if (sharedPreferences_info == null) {
            sharedPreferences_info = getSharedPreferences(USER_INFO, MODE_PRIVATE);
        }
        sharedPreferences_info.edit().remove("deliveryId").commit();
        deliveryId = null;
    }

    //保存deliveryPhone
    public void saveDeliveryPhone(String deliveryPhone) {
        this.deliveryPhone = deliveryPhone;
        if (sharedPreferences_info == null) {
            sharedPreferences_info = getSharedPreferences(USER_INFO, MODE_PRIVATE);
        }
        sharedPreferences_info.edit().putString("deliveryPhone", deliveryPhone).commit();

    }

    //获取deliveryPhone
    public String getDeliveryPhone() {
        if (this.deliveryPhone == null) {
            if (sharedPreferences_info == null) {
                sharedPreferences_info = getSharedPreferences(USER_INFO, MODE_PRIVATE);
            }
            this.deliveryPhone = sharedPreferences_info.getString("deliveryPhone", null);
            return deliveryPhone;
        }
        return deliveryPhone;
    }

    //移除deliveryPhone
    public void removeDeliveryPhone() {
        if (sharedPreferences_info == null) {
            sharedPreferences_info = getSharedPreferences(USER_INFO, MODE_PRIVATE);
        }
        sharedPreferences_info.edit().remove("deliveryPhone").commit();
        deliveryPhone = null;
    }

    //保存carId
    public void saveCarId(String carId) {
        this.carId = carId;
        if (sharedPreferences_info == null) {
            sharedPreferences_info = getSharedPreferences(USER_INFO, MODE_PRIVATE);
        }
        sharedPreferences_info.edit().putString("carId", carId).commit();

    }

    //获取carId
    public String getCarId() {
        if (this.carId == null) {
            if (sharedPreferences_info == null) {
                sharedPreferences_info = getSharedPreferences(USER_INFO, MODE_PRIVATE);
            }
            this.uid = sharedPreferences_info.getString("carId", null);
            return carId;
        }
        return carId;
    }

    //移除carId
    public void removeCarId() {
        if (sharedPreferences_info == null) {
            sharedPreferences_info = getSharedPreferences(USER_INFO, MODE_PRIVATE);
        }
        sharedPreferences_info.edit().remove("carId").commit();
        carId = null;
    }

    //保存UID
    public void saveUid(String uid) {
        this.uid = uid;
        if (sharedPreferences_info == null) {
            sharedPreferences_info = getSharedPreferences(USER_INFO, MODE_PRIVATE);
        }
        sharedPreferences_info.edit().putString("uid", uid).commit();

    }

    //获取UID
    public String getUid() {
        if (this.uid == null) {
            if (sharedPreferences_info == null) {
                sharedPreferences_info = getSharedPreferences(USER_INFO, MODE_PRIVATE);
            }
            this.uid = sharedPreferences_info.getString("uid", null);
            return uid;
        }
        return uid;
    }

    //移除uid
    public void removeUid() {
        if (sharedPreferences_info == null) {
            sharedPreferences_info = getSharedPreferences(USER_INFO, MODE_PRIVATE);
        }
        sharedPreferences_info.edit().remove("uid").commit();
        uid = null;
    }

    //保存Token
    public void saveToken(String token) {
        this.token = token;
        if (sharedPreferences_info == null) {
            sharedPreferences_info = getSharedPreferences(USER_INFO, MODE_PRIVATE);
        }
        sharedPreferences_info.edit().putString("token", token).commit();

    }

    //获取token
    public String getToken() {
        if (this.token == null) {
            if (sharedPreferences_info == null) {
                sharedPreferences_info = getSharedPreferences(USER_INFO, MODE_PRIVATE);
            }
            this.token = sharedPreferences_info.getString("token", null);
            return token;
        }
        return token;
    }

    //保存用户名密码
    public void savaUnameAndPwd(String username, String password) {
        this.uname = username;
        this.pwd = password;
        if (sharedPreferences == null) {
            sharedPreferences = getSharedPreferences(USER_INFO, MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", uname);
        editor.putString("pwd", pwd);
        editor.commit();
    }

    //获取用户名
    public String getUsername() {
        if (sharedPreferences == null) {
            sharedPreferences = getSharedPreferences(USER_INFO, MODE_PRIVATE);
        }
        String name = sharedPreferences.getString("username", null);
        return name;
    }

    //移除username
    public void removeUsername() {
        if (sharedPreferences_info == null) {
            sharedPreferences_info = getSharedPreferences(USER_INFO, MODE_PRIVATE);
        }
        sharedPreferences_info.edit().remove("username").commit();
        uname = null;
    }

    //获取密码
    public String getPassword() {
        if (sharedPreferences == null) {
            sharedPreferences = getSharedPreferences(USER_INFO, MODE_PRIVATE);
        }
        String pwd = sharedPreferences.getString("pwd", null);
        return pwd;
    }

    //移除pwd
    public void removePwd() {
        if (sharedPreferences_info == null) {
            sharedPreferences_info = getSharedPreferences(USER_INFO, MODE_PRIVATE);
        }
        sharedPreferences_info.edit().remove("pwd").commit();
        pwd = null;
    }

    //全移除
    public void removeAll() {
        if (sharedPreferences_info == null) {
            sharedPreferences_info = getSharedPreferences(USER_INFO, MODE_PRIVATE);
        }
        sharedPreferences_info.edit().clear().commit();
    }
}
