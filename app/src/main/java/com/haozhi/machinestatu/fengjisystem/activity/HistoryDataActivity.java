package com.haozhi.machinestatu.fengjisystem.activity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.haozhi.machinestatu.fengjisystem.R;
import com.haozhi.machinestatu.fengjisystem.base.base_activity.Base_TitleBar_Activity;
import com.haozhi.machinestatu.fengjisystem.titlebar.TitleBar;
import com.haozhi.machinestatu.fengjisystem.utils.ColorUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by LSZ on 2018/1/12.
 */
public class HistoryDataActivity extends Base_TitleBar_Activity {

    @Bind(R.id.chart_line)
    LineChart chartLine;
    @Bind(R.id.chart_bar)
    BarChart chartBar;
    @Bind(R.id.chart_pie)
    PieChart chartPie;

    @Override
    protected String setTitleName() {
        return "历史数据分析";
    }

    @Override
    protected TitleBar.TextAction rightButton() {
        return null;
    }

    @Override
    protected boolean leftButton() {
        return true;
    }

    @Override
    public int getLayout() {
        return R.layout.hitory_data_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    Random random=new Random();
    private void initData() {
        initLine();
        initBar();
        initPie();
    }

    /**
     * 饼状图
     */
    private void initPie() {
        PieData pieData=new PieData();
        List<Entry> entryList=new ArrayList<>();
        PieDataSet dataSet=new PieDataSet(entryList,"饼状图");
       pieData.addDataSet(dataSet);
        List<Integer> color=new ArrayList<>();
        for (int i=0;i<6;i++){
            Entry entry=new Entry(random.nextFloat()*5+15,i);
            pieData.addEntry(entry,0);
            color.add(Color.parseColor(ColorUtil.getRandomColor()));
        }
        dataSet.setColors(color);
        chartPie.setData(pieData);
    }

    /**
     * 柱状图
     */
    private void initBar() {
        BarData barData=new BarData();
        List<BarEntry> entryList=new ArrayList<>();
        List<Integer> color=new ArrayList<>();
        for (int i=0;i<6;i++){
            BarEntry entry=new BarEntry(10,i);
            entryList.add(entry);
            color.add(Color.parseColor(ColorUtil.getRandomColor()));
        }
        BarDataSet barSet=new BarDataSet(entryList,"柱状图");
        barSet.setColors(color);
        barData.addDataSet(barSet);
        chartBar.setData(barData);
        chartBar.setDragEnabled(true);
        chartBar.getAxisRight().setEnabled(false);//右侧不显示Y轴
        XAxis xAxis = chartBar.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinValue(1f);
        xAxis.setAxisMaxValue(20f);//关键：差值为x轴数据个数，也是y轴数据的组数
        chartBar.setScaleEnabled(true);
        chartBar.setTouchEnabled(true);
    }

    /**
     * 折线图
     */
    private void initLine() {
       /* LineData lineData=new LineData();
        List<Entry> entryList=new ArrayList<>();
        LineDataSet lineDataSet=new LineDataSet(entryList,"测试");
        lineDataSet.addColor(Color.GREEN);
        for (int i=0;i<15;i++){
            Entry entry=new Entry(random.nextFloat()*5+20,i);
            lineData.addEntry(entry, 0);
        }
        lineData.addDataSet(lineDataSet);
        chartLine.setData(lineData);*/
    }


}
