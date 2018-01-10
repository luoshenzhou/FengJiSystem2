package com.haozhi.machinestatu.fengjisystem.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.haozhi.machinestatu.fengjisystem.R;
import com.haozhi.machinestatu.fengjisystem.base.base_activity.Base_TitleBar_Activity;
import com.haozhi.machinestatu.fengjisystem.lineChart.LineChartAxis_leGendManager;
import com.haozhi.machinestatu.fengjisystem.titlebar.TitleBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by LSZ on 2018/1/9.
 * 实时数据折线图
 */
public class NowLineDataActivity extends Base_TitleBar_Activity {
    @Bind(R.id.chart)
    LineChart chart;
    @Bind(R.id.tv_title_group)
    TextView tvTitleGroup;
    @Bind(R.id.tv_title_child)
    TextView tvTitleChild;
    @Bind(R.id.tv_maxValue)
    TextView tvMaxValue;
    @Bind(R.id.tv_minValue)
    TextView tvMinValue;

    @Override
    protected String setTitleName() {
        return "数据实时折线";
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
        return R.layout.now_data_line_layout;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            addData();
        }
    };


    private void addData() {
        addEntry(chart);
    }

    Random random = new Random();







    float maxValue = 0.0f;
    float minValue = 0.0f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initData();
        startReadData();
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

    private void initData() {
        String title_group = getIntent().getStringExtra("title_group");
        String title_child = getIntent().getStringExtra("title_child");
        if (!TextUtils.isEmpty(title_group)) {
            tvTitleGroup.setText(title_group);
        }
        if (!TextUtils.isEmpty(title_child)) {
            tvTitleChild.setText(title_child);
        }
        chart.setDrawGridBackground(false);
        LineData lineData = new LineData();
        List<Entry> list = new ArrayList<>();
        LineDataSet lineDataSet = new LineDataSet(list, "实时监测数据");
        lineDataSet.setDrawFilled(false);//是否填充折线下方
        lineDataSet.setFillColor(Color.RED);//填充的颜色
        lineDataSet.setColor(Color.GREEN);
        lineDataSet.setValueTextColor(Color.GREEN);
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);//设置为平滑的折线
        lineData.addDataSet(lineDataSet);
        chart.setBackgroundColor(getResources().getColor(R.color.chart_bg));
        chart.setData(lineData);
        chart.setScaleEnabled(true);//设置是否可以缩放
        chart.setPinchZoom(true);
        chart.setAutoScaleMinMaxEnabled(false);
        LineChartAxis_leGendManager axisLeGendManager=new LineChartAxis_leGendManager(chart);
        axisLeGendManager.changeXAxis();
        axisLeGendManager.changeRightYAxis();
        axisLeGendManager.changeLeftYAxis();
        axisLeGendManager.changeLeGend();
    }


    XAxis xAxis;
    private void addEntry(LineChart mChart) {
        LineData data = mChart.getData();
        if (data == null) {
            data = new LineData();
            data.addXValue(0 + "");
        } else {
            data.addXValue((data.getXValCount()) + "");
        }
        // 增加高温
        float fh = (float) (random.nextFloat() * 3 + 26);
        if (minValue == 0.0) {
            minValue = fh;
            maxValue = fh;
            tvMinValue.setText(minValue + "");
            tvMaxValue.setText(maxValue + "");
        }
        Entry entryh = new Entry(fh, data.getXValCount());
        data.addEntry(entryh, 0);
        //============心电图关键代码======================
        if (data.getXValCount()>20){
            //超过20个的时候，只保留15个点
            data.removeEntry(data.getXValCount() - 20, 0);
            //每次都去修改x轴的最小值
            if (xAxis==null){
                xAxis = chart.getXAxis();
            }
            xAxis.setAxisMinValue(data.getXValCount() - 20);
        }
        //============心电图关键代码======================
        mChart.notifyDataSetChanged();
        mChart.moveViewTo(data.getXValCount() / 2,data.getYMax(), YAxis.AxisDependency.RIGHT);

        mChart.invalidate();
        if (fh < minValue) {
            minValue = fh;
            tvMinValue.setText(minValue + "");
        }
        if (fh > maxValue) {
            maxValue = fh;
            tvMaxValue.setText(maxValue + "");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopReadData();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        startReadData();
    }
}
