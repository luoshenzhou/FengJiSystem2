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
    @Override
    public String[] getRowTitle() {
        return new String[]{"测点","日期","振动值","轮毂（振幅）"};
    }

    @Override
    public List<Map<String, String>> getListData() {
        List<Map<String, String>> listData = new ArrayList<>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
        Random random = new Random();
        int itemCount = getRowTitle().length;
        for (int i = 1; i <= 3; i++) {//多少行
            Map<String, String> map = new HashMap<>();
            for (int j = 0; j < itemCount; j++) {//多少列
                if (j == 1) {
                    map.put((j) + "", "叶片" + i);
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
        list.add(150);
        list.add(500);
        list.add(150);
        list.add(300);
        return list;
    }

    @Override
    public String[] getChangeColumn() {
        return new String[]{"3","4"};
    }
}
