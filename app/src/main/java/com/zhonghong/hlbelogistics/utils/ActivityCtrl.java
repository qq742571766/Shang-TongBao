package com.zhonghong.hlbelogistics.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * activity管理类
 * Created by Administrator on 2017/2/17.
 */
public class ActivityCtrl {
    public static List<Activity> activities = new ArrayList<>();

    // 添加打开的Activity至管理集合中
    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    // 当Activity销毁，从集合中删除
    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    // 在最后需要退出的地方直接退出
    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
