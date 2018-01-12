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
 */
public class FengJiYePianData extends TableData {

    String[] ceDian=new String[]{"叶片1","叶片2","叶片3","轮毂（振幅）"};

    @Override
    public String[] getRowTitle() {
        return new String[]{"测点","日期","振动值"};
    }

    @Override
    public List<Map<String, String>> getListData() {
        List<Map<String, String>> listData = new ArrayList<>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
        Random random = new Random();
        int itemCount = getRowTitle().length;
        for (int i = 0; i <ceDian.length; i++) {//多少行
            Map<String, String> map = new HashMap<>();
            for (int j = 0; j < itemCount; j++) {//多少列
                if (j == 1) {
                    map.put((j) + "", ceDian[i]);
                } else if (j == 2) {
                    map.put((j) + "", date);
                } else {
                    map.put((j) + "", DataUtil.getPointDataByFloat(random.nextFloat() * 5 + 24));
                }
            }
            listData.add(map);
        }
        return listData;
    }

    @Override
    public List<Integer> getColumnWidth() {
        List<Integer> list = new ArrayList<>();
        list.add(200);
        list.add(500);
        list.add(150);
        return list;
    }

    @Override
    public String[] getChangeColumn() {
        return new String[]{"3"};
    }
}
