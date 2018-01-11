package com.haozhi.machinestatu.fengjisystem.chart.lineChart;

import android.graphics.Color;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;

/**
 * Created by LSZ on 2018/1/10.
 * 修改折线的X、Y轴属性及图例属性
 */
public class LineChartAxis_leGendManager {

    private LineChart chart;
    public LineChartAxis_leGendManager(LineChart chart) {
        this.chart=chart;
    }

    /**
     * 修改折线图的左边Y轴的属性
     */
    public void changeLeftYAxis() {
        YAxis axisLeft = chart.getAxisLeft();
        axisLeft.setEnabled(true);//设置隐藏不可用
        axisLeft.setTextColor(Color.WHITE);
    }


    /**
     * 修改折线图的右边Y轴的属性
     */
    public void changeRightYAxis() {
        YAxis axisRight = chart.getAxisRight();
        axisRight.setEnabled(false);
        axisRight.setDrawGridLines(false);
        axisRight.setDrawLabels(true);
    }

    //修改图例
    public void changeLeGend() {
        Legend legend = chart.getLegend();//图例
        legend.setEnabled(true);
        legend.setTextColor(Color.GREEN);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setPosition(Legend.LegendPosition.ABOVE_CHART_LEFT);//设置图例的位置（一定要设置，不然就会看不到，相当于没有设置）
    }

    //改变X轴的设置
    public void changeXAxis() {
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置X坐标轴的位置
        xAxis.setDrawGridLines(false);//设置X的背景线也不画
    }
}
