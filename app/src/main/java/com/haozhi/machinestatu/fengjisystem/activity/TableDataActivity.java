package com.haozhi.machinestatu.fengjisystem.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.haozhi.machinestatu.fengjisystem.R;
import com.haozhi.machinestatu.fengjisystem.adapter.TotalDevActivity_Adapter;
import com.haozhi.machinestatu.fengjisystem.base.base_activity.Base_TitleBar_Activity;
import com.haozhi.machinestatu.fengjisystem.titlebar.TitleBar;
import com.haozhi.machinestatu.fengjisystem.utils.DataUtil;
import com.haozhi.machinestatu.fengjisystem.utils.DateUtil;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sysu.zyb.panellistlibrary.PanelListLayout;

/**
 * Created by LSZ on 2018/1/8.
 * 展现表格数据
 */
public class TableDataActivity extends Base_TitleBar_Activity {

    @Bind(R.id.lv_content)
    ListView lv_content;
    @Bind(R.id.id_pl_root)
    PanelListLayout panelListLayout;
    //列标题
    private String[] rowTitle = new String[]{"测点位置", "安装方式","日期", "数据"};
    private TotalDevActivity_Adapter myTotalAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initData();
    }

    @Override
    public int getLayout() {
        return R.layout.table_activity_layout;
    }

    @Override
    protected String setTitleName() {
        return "数据表格统计";
    }

    @Override
    protected TitleBar.TextAction rightButton() {
        return null;
    }

    @Override
    protected boolean leftButton() {
        return true;
    }
    List<Map<String,String>> listData=new ArrayList<>();
    private void initData() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
        Random random=new Random();
        int  itemCount=rowTitle.length+1;
        int nextInt = getCeDian().size();
        for (int i = 0; i < nextInt; i++) {//多少行
            Map<String, String> map = new HashMap<>();
            for (int j = 0; j < itemCount; j++) {//多少列
                if (j==itemCount-2){
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

        if (myTotalAdapter == null) {
            myTotalAdapter = new TotalDevActivity_Adapter(this, panelListLayout, lv_content);
            myTotalAdapter.setData(listData);
            myTotalAdapter.initListViewAdapter(80, 180, Arrays.asList(rowTitle), null);
            List<Integer> list=new ArrayList<>();
            list.add(300);
            list.add(150);
            list.add(500);
            list.add(150);
            list.add(150);
            //设置每一列的宽度
            myTotalAdapter.setcolumnWidth(list);
        } else {
            updateAdapter();
        }
    }

    private void updateAdapter() {
        myTotalAdapter.getColumnAdapter().notifyDataSetChanged();
        ArrayAdapter adapter = (ArrayAdapter) lv_content.getAdapter();
        myTotalAdapter.setData(listData);
        adapter.setNotifyOnChange(true);
        adapter.notifyDataSetChanged();
    }

    public List<String> getCeDian(){
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

        public List<String> getInStallStyle(){
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

    public List<String> getChuanGanQi(){
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
    protected void onResume() {
        super.onResume();
        startReadData();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            changeData();
            updateAdapter();
        }
    };

    Random random=new Random();
    private void changeData() {
        int  itemCount=rowTitle.length+1;
        int size = listData.get(0).size();
        //Map<String, String> map = listData.get(size - 1);
        /*Map<String, String> changeMap = new HashMap<>();
        for (Map.Entry<String,String> entry:map.entrySet()){
            changeMap.put(entry.getKey(),(random.nextFloat()*5+24+""));
        }
        listData.set(size-1,changeMap);*/
        for (int i=0;i<listData.size();i++){
            Map<String, String> map = listData.get(i);
            String key = (itemCount - 1) + "";
            map.put(key, DataUtil.getPointDataByFloat(random.nextFloat() * 5 + 24));
        }
    }

    Timer timer;
    public void startReadData() {
        timer = new Timer();
        final TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                timer.scheduleAtFixedRate(timerTask, 1000, 1000);
            }
        }).start();
    }

    public void stopReadData() {
        if (timer!=null){
            timer.purge();
            timer.cancel();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopReadData();
    }
}
