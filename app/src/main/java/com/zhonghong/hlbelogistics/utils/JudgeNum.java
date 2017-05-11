package com.zhonghong.hlbelogistics.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lize on 2017/3/7.
 * 判断输入是否符合规则
 */

public class JudgeNum {
    //判断手机号
    public static boolean isMobileNO(String mobiles){
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");

        Matcher m = p.matcher(mobiles);

        return m.matches();

        }
}
