package com.haozhi.machinestatu.fengjisystem.utils;

import java.text.DecimalFormat;

/**
 * Created by LSZ on 2018/1/10.
 */
public class DataUtil {

    /**
     * 非字符串型
     * @return
     */
    public static String getPointDataByFloat(float data){
        //保留两位小数
        DecimalFormat df= new DecimalFormat("######0.00");
        return df.format(data);
    }

    /* 非字符串型
     * @return
     */
    public static String getPointDataByDouble(double data){
        //保留两位小数
        DecimalFormat df= new DecimalFormat("######0.00");
        return df.format(data);
    }

    /* 非字符串型
     * @return
     */
    public static String getPointDataByDouble(String data){
        //保留两位小数
        DecimalFormat df= new DecimalFormat("######0.00");
        return df.format(data);
    }

}
