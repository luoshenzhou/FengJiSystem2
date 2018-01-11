package com.haozhi.machinestatu.fengjisystem.chart.tableChart;

import com.haozhi.machinestatu.fengjisystem.utils.DataUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by LSZ on 2018/1/11.
 * 风机塔筒
 */
public class FengJiTaTongData extends TableData{

    public  List<String> getCeDian(){
        List<String> list=new ArrayList<>();
        list.add("主轴承");
        list.add("齿轮箱一级行星级");
        list.add("齿轮箱二级行星级");
        list.add("齿轮箱输出级");
        list.add("发电机前轴承（驱动侧）");
        list.add("发电机后轴承（非驱动侧）");
        list.add("风塔摆动轴向");
        list.add("风塔摆动横向");
        return list;
    }

    public  List<String> getInStallStyle(){
        List<String> list=new ArrayList<>();
        list.add("径向");
        list.add("径向");
        list.add("径向");
        list.add("轴向");
        list.add("径向");
        list.add("径向");
        list.add("轴向");
        list.add("横向");
        return list;
    }

    public  List<String> getChuanGanQi(){
        List<String> list=new ArrayList<>();
        list.add("低频加速度传感器");
        list.add("低频加速度传感器");
        list.add("普通加速度传感器");
        list.add("普通加速度传感器");
        list.add("普通加速度传感器");
        list.add("普通加速度传感器");
        list.add("风塔摆动监测传感器");
        list.add("风塔摆动监测传感器");
        return list;
    }

    @Override
    public List<Integer> getColumnWidth(){
        List<Integer> list=new ArrayList<>();
        list.add(500);
        list.add(300);
        list.add(500);
        list.add(150);
        list.add(150);
        return list;
    }

    @Override
    public String[] getChangeColumn() {
        return new String[]{"4"};
    }


    @Override
    public String[] getRowTitle(){

        return  new String[]{"测点位置", "安装方式","日期", "数据"};
    }

    @Override
    public List<Map<String, String>> getListData() {
        List<Map<String,String>> listData=new ArrayList<>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
        Random random=new Random();
        int  itemCount=getRowTitle().length;
        int nextInt = getCeDian().size();
        for (int i = 0; i < nextInt; i++) {//多少行
            Map<String, String> map = new HashMap<>();
            for (int j = 0; j < itemCount; j++) {//多少列
                if (j==itemCount-1){
                    map.put((j) + "",date);
                }else if(j==1){
                    map.put(j+"",getCeDian().get(i));
                }else if(j==2){
                    map.put(j+"",getInStallStyle().get(i));
                } else {
                    map.put((j) + "", DataUtil.getPointDataByFloat(random.nextFloat() * 5 + 24));
                }
            }
            listData.add(map);
        }
        return listData;
    }
}
