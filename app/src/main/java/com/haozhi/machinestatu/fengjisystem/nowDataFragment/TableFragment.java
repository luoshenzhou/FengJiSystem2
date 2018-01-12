package com.haozhi.machinestatu.fengjisystem.nowDataFragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.haozhi.machinestatu.fengjisystem.R;
import com.haozhi.machinestatu.fengjisystem.adapter.TotalDevActivity_Adapter;
import com.haozhi.machinestatu.fengjisystem.chart.tableChart.FengJiBaseData;
import com.haozhi.machinestatu.fengjisystem.chart.tableChart.FengJiDianZuData;
import com.haozhi.machinestatu.fengjisystem.chart.tableChart.FengJiGanShiBianData;
import com.haozhi.machinestatu.fengjisystem.chart.tableChart.FengJiLeiDianData;
import com.haozhi.machinestatu.fengjisystem.chart.tableChart.FengJiLuioXuanData;
import com.haozhi.machinestatu.fengjisystem.chart.tableChart.FengJiRunHuaData;
import com.haozhi.machinestatu.fengjisystem.chart.tableChart.FengJiTaTongData;
import com.haozhi.machinestatu.fengjisystem.chart.tableChart.FengJiYePianData;
import com.haozhi.machinestatu.fengjisystem.chart.tableChart.TableData;
import com.haozhi.machinestatu.fengjisystem.utils.DataUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import sysu.zyb.panellistlibrary.PanelListLayout;

/**
 * Created by LSZ on 2018/1/11.
 */
public class TableFragment extends Fragment {


    private static final String TAG = TableFragment.class.getSimpleName();
    @Bind(R.id.lv_content)
    ListView lv_content;
    @Bind(R.id.id_pl_root)
    PanelListLayout panelListLayout;
    //列标题
    private String[] rowTitle;
    //表格数据
    List<Map<String,String>> listData=new ArrayList<>();
    //每一列的宽度
    List<Integer> columnWidthList=new ArrayList<>();
    TableData tableData = null;
    private TotalDevActivity_Adapter myTotalAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.table_fragment_layout, null);
        ButterKnife.bind(this, view);
        initData();
        startReadData();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        stopReadData();
    }

    String title ;

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 1、表格列的宽度
     * 2、表格列的标题
     * 3、表格的每一列的数据
     */
    private void initData() {
        //组标题
        if (title.equals(TableData.TA_TONG_TAG)){
            tableData=new FengJiTaTongData();
        }else if (title.equals(TableData.BASE_TAG)){
            tableData=new FengJiBaseData();
        }else if (title.equals(TableData.LUO_XUAN_TAG)){
            tableData=new FengJiLuioXuanData();
        }else if (title.equals(TableData.YE_PIAN_TAG)){
            tableData=new FengJiYePianData();
        }else if (title.equals(TableData.DIAN_ZU_TAG)){
            tableData=new FengJiDianZuData();
        }else if (title.equals(TableData.RUN_HUA_TAG)){
            tableData=new FengJiRunHuaData();
        }else if (title.equals(TableData.GAN_SHI_BIAN_TAG)){
            tableData=new FengJiGanShiBianData();
        }else if (title.equals(TableData.LEI_JI_TAG)){
            tableData=new FengJiLeiDianData();
        }else {
            //throw new RuntimeException("没有对应的TAG--"+title);
            Log.e(TAG,"没有对应的TAG--"+title+"，默认显示风机塔筒");
            tableData=new FengJiTaTongData();
        }
        initTable(tableData);
    }

    private void initTable(TableData tableData) {
        listData=tableData.getListData();
        columnWidthList=tableData.getColumnWidth();
        rowTitle=tableData.getRowTitle();
        setAdapter(listData,columnWidthList,rowTitle);
    }

    private void setAdapter(List<Map<String, String>> listData, List<Integer> rowTitleWidthlist, String[] rowTitle) {
        if (myTotalAdapter == null) {
            myTotalAdapter = new TotalDevActivity_Adapter(getActivity(), panelListLayout, lv_content);
            myTotalAdapter.setData(listData);
            myTotalAdapter.initListViewAdapter(80, 180, Arrays.asList(rowTitle), null);
            //设置每一列的宽度
            myTotalAdapter.setcolumnWidth(rowTitleWidthlist);
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

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            changeData(tableData.getChangeColumn());
            updateAdapter();
        }
    };

    Random random=new Random();
    private void changeData(String[] key) {
        if (key!=null){
            int length = key.length;
            for (int i=0;i<listData.size();i++){
                Map<String, String> map = listData.get(i);
                for (int j=0;j<length;j++){
                    String keyName =key[j];//多个ke
                    map.put(keyName, DataUtil.getPointDataByFloat(random.nextFloat() * 5 + 24));
                }
            }
        }else {
            throw new RuntimeException("要修改的字段的数组为空");
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
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden){
            stopReadData();
        }else{
            startReadData();
        }
    }


    public List<String> getCeDianList(){
        List<String> list=new ArrayList<>();
        for (Map<String,String> entry:listData){
            list.add(entry.get("1"));
        }
        return list;
    }
}
