package com.haozhi.machinestatu.fengjisystem.nowDataFragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.haozhi.machinestatu.fengjisystem.R;
import com.haozhi.machinestatu.fengjisystem.chart.lineChart.LineChartAxis_leGendManager;
import com.haozhi.machinestatu.fengjisystem.log.LogManager;
import com.haozhi.machinestatu.fengjisystem.titlebar.TitleBar;
import com.haozhi.machinestatu.fengjisystem.utils.ColorUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by LSZ on 2018/1/11.
 */
public class LineFragment extends Fragment {

    private static final String TAG = LineFragment.class.getSimpleName();
    @Bind(R.id.chart)
    LineChart chart;

    @Bind(R.id.tv_maxValue)
    TextView tvMaxValue;
    @Bind(R.id.tv_minValue)
    TextView tvMinValue;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.line_fragment_layout, null);
        ButterKnife.bind(this, view);
        initData();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        LogManager.e(TAG, "onDestroyView");
        stopReadData();
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

    LineData lineData;
    private void initData() {
        chart.setDrawGridBackground(false);
        if (lineData==null){
            lineData = new LineData();
        }
        for (String name:cedianList){
        LineDataSet lineDataSet = getLineDataSet(name);
        lineData.addDataSet(lineDataSet);
        }
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

    @NonNull
    private LineDataSet getLineDataSet(String title) {
        List<Entry> list = new ArrayList<>();
        LineDataSet lineDataSet = new LineDataSet(list,title);
        lineDataSet.setDrawFilled(false);//是否填充折线下方
        lineDataSet.setFillColor(Color.RED);//填充的颜色
        lineDataSet.setColor(Color.parseColor(ColorUtil.getRandomColor()));
        lineDataSet.setValueTextColor(Color.GREEN);
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);//设置为平滑的折线
        return lineDataSet;
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
        for (int i=0;i<cedianList.size();i++) {
            // 增加高温
            float fh = (float) (random.nextFloat() * 3 + 26);
            if (minValue == 0.0) {
                minValue = fh;
                maxValue = fh;
                //tvMinValue.setText(minValue + "");
                //tvMaxValue.setText(maxValue + "");
            }
            Entry entryh = new Entry(fh, data.getXValCount());
            data.addEntry(entryh, i);
            //============心电图关键代码======================
            if (data.getXValCount()>10){
                //超过20个的时候，只保留15个点
                data.removeEntry(data.getXValCount() - 10, i);
                //每次都去修改x轴的最小值
                if (xAxis==null){
                    xAxis = chart.getXAxis();
                }
                xAxis.setAxisMinValue(data.getXValCount() - 10);
            }
        }

        //============心电图关键代码======================
        mChart.notifyDataSetChanged();
        mChart.moveViewTo(data.getXValCount() / 2,data.getYMax(), YAxis.AxisDependency.RIGHT);

        mChart.invalidate();
        /*if (fh < minValue) {
            minValue = fh;
            tvMinValue.setText(minValue + "");
        }
        if (fh > maxValue) {
            maxValue = fh;
            tvMaxValue.setText(maxValue + "");
        }*/
    }

    @Override
    public void onResume() {
        super.onResume();
        LogManager.e(TAG, "开始画");
    }

    @Override
    public void onStop() {
        super.onStop();
        LogManager.e(TAG,"onStop");

    }




    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden){
            stopReadData();
        }else {
            startReadData();
        }
    }

    List<String> cedianList;
    public void setData(List<String> cedianList){
        if (this.cedianList==null){
            this.cedianList=cedianList;
            startReadData();
        }
    }
}
