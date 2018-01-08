package com.haozhi.machinestatu.fengjisystem.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;


import com.haozhi.machinestatu.fengjisystem.R;
import com.haozhi.machinestatu.fengjisystem.adapter.MyExpandableListViewAdapter;
import com.haozhi.machinestatu.fengjisystem.base.base_activity.Base_TitleBar_Activity;
import com.haozhi.machinestatu.fengjisystem.bean.TitleName;
import com.haozhi.machinestatu.fengjisystem.bottom.BottomPop;
import com.haozhi.machinestatu.fengjisystem.bottom.BottomPopListener;
import com.haozhi.machinestatu.fengjisystem.titlebar.TitleBar;
import com.haozhi.machinestatu.fengjisystem.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lsz on 2017/1/16.
 */
public class SystemCenterActivity extends Base_TitleBar_Activity {

    private ExpandableListView expandableListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initEvent();
    }

    @Override
    public int getLayout() {
        return R.layout.activity_system_center;
    }

    @Override
    protected String setTitleName() {
        return "风机系统中心";
    }

    @Override
    protected TitleBar.TextAction rightButton() {
        return null;
    }

    @Override
    protected boolean leftButton() {
        return false;
    }


    private void initView() {
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);

    }

    private void initData() {
        showData();
    }


    //展示设备的分组和子标题
    private void showData() {

        MyExpandableListViewAdapter myExpandableListViewAdapter = new MyExpandableListViewAdapter(this, makeGroupData());
        expandableListView.setAdapter(myExpandableListViewAdapter);
    }

    //获取通过activity intent传过来的数据
    private void getDataFromOtherActivity() {

    }

    //初始化expandableListView的监听
    private void initEvent() {

        //设置分组的监听
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Log.d(TAG, "groupPosition:" + groupPosition);
                int childrenCount = parent.getExpandableListAdapter().getChildrenCount(groupPosition);
                Log.d(TAG, "childrenCount:" + childrenCount);

                return false;
            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                new BottomPop(SystemCenterActivity.this, new BottomPopListener() {

                    @Override
                    public void chioce(String title, int position) {
                        if (title.equals("数据统计表格")){
                            startActivity(new Intent(SystemCenterActivity.this,TableDataActivity.class));
                        }else if (title.equals("数据历史折线")){
                            startActivity(new Intent(SystemCenterActivity.this,HistoryLineDataActivity.class));
                        }else if (title.equals("数据实时折线")){
                            ToastUtil.toastShow(title,SystemCenterActivity.this);
                        }
                    }
                }).showBottomPopDialog();
                return false;
            }
        });

    }



    public List<TitleName> makeGroupData() {
        List<TitleName> groupTitleNameList = new ArrayList<>();
        List<TitleName.GroupDataBean> groupChildDataList = new ArrayList<>();
        groupChildDataList.add(new TitleName.GroupDataBean("主轴承", 1));
        groupChildDataList.add(new TitleName.GroupDataBean("齿轮箱一级行星级", 1));
        groupChildDataList.add(new TitleName.GroupDataBean("齿轮箱二级行星级", 1));
        groupChildDataList.add(new TitleName.GroupDataBean("齿轮箱输出级", 1));
        groupChildDataList.add(new TitleName.GroupDataBean("发电机前轴承（驱动侧）", 1));
        groupChildDataList.add(new TitleName.GroupDataBean("发电机后轴承（非驱动侧）", 1));
        groupChildDataList.add(new TitleName.GroupDataBean("风塔摆动轴向", 1));
        groupChildDataList.add(new TitleName.GroupDataBean("风塔摆动横向", 1));
        groupTitleNameList.add(new TitleName("风机及塔筒在线状态监测与分析系统" , groupChildDataList));
        groupTitleNameList.add(new TitleName("风机基础监测系统" , groupChildDataList));
        groupTitleNameList.add(new TitleName("风机螺栓载荷在线监测报警系统" , groupChildDataList));
        groupTitleNameList.add(new TitleName("风力发电机组桨叶状态监测系统" , groupChildDataList));
        groupTitleNameList.add(new TitleName("风力发电机绝缘电阻自动监测装置系统" , groupChildDataList));
        groupTitleNameList.add(new TitleName("风机齿轮箱润滑油油质在线监测系统" , groupChildDataList));
        groupTitleNameList.add(new TitleName("风机雷电检测系统" , groupChildDataList));
        groupTitleNameList.add(new TitleName("干式变运行状态监测系统" , groupChildDataList));
        groupTitleNameList.add(new TitleName("风机视频辅控系统" , groupChildDataList));
        groupTitleNameList.add(new TitleName("风机IP电话系统" , groupChildDataList));
        return groupTitleNameList;
    }



}