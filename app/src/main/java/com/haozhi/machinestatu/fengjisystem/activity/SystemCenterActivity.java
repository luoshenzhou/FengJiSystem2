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
import com.haozhi.machinestatu.fengjisystem.chart.tableChart.TableData;
import com.haozhi.machinestatu.fengjisystem.titlebar.TitleBar;
import com.haozhi.machinestatu.fengjisystem.vidio.HistoryVidioActivity;
import com.haozhi.machinestatu.fengjisystem.vidio.NowVidioActivity;

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
            public boolean onChildClick(ExpandableListView parent, View v, final int groupPosition, final int childPosition, long id) {
                String groupTitleName = makeGroupData().get(groupPosition).getGroupTitleName();
                /**
                 * IP电话是不用弹出选项的，其他的都要弹出显示
                 *
                 */
                if (!groupTitleName.equals(TableData.IP_PHONE_TAG)) {
                    if(groupTitleName.equals(TableData.VIDIO_TAG)){
                        /**
                         * 修改标题
                         * 视频监控那里不用显示数据，只需要显示监控的第一帧画面，每台风机有8个监控，所以要显示8张图片，历史也是
                         */
                        String[] title=new String[]{"实时监控视频","历史监控视频","取消"};
                        new BottomPop(SystemCenterActivity.this, new BottomPopListener() {
                            @Override
                            public void chioce(String title, int position) {
                                if (title.equals("实时监控视频")) {
                                    //动态表格数据+动态折线图
                                    Intent intent = new Intent();
                                    intent.putExtra("title_group", makeGroupData().get(groupPosition).getGroupTitleName());
                                    intent.putExtra("title_child", makeGroupData().get(groupPosition).getGroupData().get(childPosition).getDataTitle());
                                    intent.setClass(SystemCenterActivity.this, NowVidioActivity.class);
                                    intent.putExtra("title", makeGroupData().get(groupPosition).getGroupTitleName());
                                    startActivity(intent);
                                } else if (title.equals("历史监控视频")) {
                                    Intent intent = new Intent();
                                    intent.putExtra("title_group", makeGroupData().get(groupPosition).getGroupTitleName());
                                    intent.putExtra("title_child", makeGroupData().get(groupPosition).getGroupData().get(childPosition).getDataTitle());
                                    intent.setClass(SystemCenterActivity.this, HistoryVidioActivity.class);
                                    intent.putExtra("title", makeGroupData().get(groupPosition).getGroupTitleName());
                                    startActivity(intent);
                                }
                            }
                        },title).showBottomPopDialog();

                    }else {
                        new BottomPop(SystemCenterActivity.this, new BottomPopListener() {
                            @Override
                            public void chioce(String title, int position) {
                                if (title.equals("实时数据监测")) {
                                    //动态表格数据+动态折线图
                                    Intent intent = new Intent();
                                    intent.putExtra("title_group", makeGroupData().get(groupPosition).getGroupTitleName());
                                    intent.putExtra("title_child", makeGroupData().get(groupPosition).getGroupData().get(childPosition).getDataTitle());
                                    intent.setClass(SystemCenterActivity.this, NowDataActivity.class);
                                    intent.putExtra("title", makeGroupData().get(groupPosition).getGroupTitleName());
                                    startActivity(intent);
                                } else if (title.equals("历史数据分析")) {
                                    startActivity(new Intent(SystemCenterActivity.this, HistoryDataActivity.class));
                                } else if (title.equals("数据实时折线")) {
                                    Intent intent = new Intent();
                                    intent.setClass(SystemCenterActivity.this, NowLineDataActivity.class);
                                    startActivity(intent);
                                }
                            }
                        }).showBottomPopDialog();
                    }
                }
                return false;
            }
        });

    }


    public List<TitleName> makeGroupData() {
        List<TitleName> groupTitleNameList = new ArrayList<>();
        List<TitleName.GroupDataBean> groupChildDataList = new ArrayList<>();
        for (int i = 1; i <= 36; i++) {
            groupChildDataList.add(new TitleName.GroupDataBean("风机" + i + "号", 1));
        }
        groupTitleNameList.add(new TitleName("风机及塔筒在线状态监测与分析系统", R.drawable.tatong, groupChildDataList));
        groupTitleNameList.add(new TitleName("风机基础监测系统", R.drawable.jichu, groupChildDataList));
        groupTitleNameList.add(new TitleName("风机螺栓载荷在线监测报警系统", R.drawable.luo_xuan, groupChildDataList));
        groupTitleNameList.add(new TitleName("风机组桨叶状态监测系统", R.drawable.ye_pian, groupChildDataList));
        groupTitleNameList.add(new TitleName("风机绝缘电阻自动监测装置系统", R.drawable.dianzu, groupChildDataList));
        groupTitleNameList.add(new TitleName("风机齿轮箱润滑油油质在线监测系统", R.drawable.chilun, groupChildDataList));
        groupTitleNameList.add(new TitleName("风机雷电检测系统", R.drawable.lei_dian, groupChildDataList));
        groupTitleNameList.add(new TitleName("干式变运行状态监测系统", R.drawable.statue, groupChildDataList));
        groupTitleNameList.add(new TitleName("风机视频辅控系统", R.drawable.vidio, groupChildDataList));
        groupTitleNameList.add(new TitleName("风机IP电话系统", R.drawable.phone, groupChildDataList));
        return groupTitleNameList;
    }


}
