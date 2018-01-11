package com.haozhi.machinestatu.fengjisystem.chart.tableChart;

import java.util.List;
import java.util.Map;

/**
 * Created by LSZ on 2018/1/11.
 * 表格数据
 */
public abstract class TableData {

    //塔筒监测
    public static final String TA_TONG_TAG="风机及塔筒在线状态监测与分析系统";
    //风机基础
    public static final String BASE_TAG="风机基础监测系统";
    //螺旋
    public static final String LUO_XUAN_TAG="风机螺栓载荷在线监测报警系统";
    //桨叶
    public static final String YE_PIAN_TAG="风机组桨叶状态监测系统";
    public static final String DIAN_ZU_TAG="风机绝缘电阻自动监测装置系统";
    public static final String RUN_HUA_TAG="风机齿轮箱润滑油油质在线监测系统";


    public abstract String[] getRowTitle();

    public abstract List<Map<String,String>> getListData();

    public abstract List<Integer> getColumnWidth();

    public abstract String[] getChangeColumn();
}
