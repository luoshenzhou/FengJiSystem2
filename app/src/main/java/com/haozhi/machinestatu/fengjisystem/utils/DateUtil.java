package com.haozhi.machinestatu.fengjisystem.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by LSZ on 2018/1/9.
 */
public class DateUtil {

    public static String getCurrentDateTime(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        return df.format(new Date());// new Date()为获取当前系统时间
    }
}
