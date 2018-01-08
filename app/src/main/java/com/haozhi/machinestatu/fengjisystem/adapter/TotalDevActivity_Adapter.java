package com.haozhi.machinestatu.fengjisystem.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import sysu.zyb.panellistlibrary.PanelListAdapter;
import sysu.zyb.panellistlibrary.PanelListLayout;

/**
 * Created by LSZ on 2017/9/11.
 */
public class TotalDevActivity_Adapter extends PanelListAdapter {

    //数字的格式化
    private DecimalFormat df = new DecimalFormat("0.00");
    //listView的数据
    private List<Map<String,String>> listData;
    private ListView idLvContent;
    private Context context;

    private boolean isMakeLever=false;
    private int normalColor;
    private int pre_AlarmColor;
    private int alarmColor;

    /**
     * constructor
     *
     * @param context
     * @param pl_root
     * @param lv_content 内容的ListView
     */
    public TotalDevActivity_Adapter(Context context, PanelListLayout pl_root, ListView lv_content) {
        super(context, pl_root, lv_content);
        this.context=context;
        this.idLvContent=lv_content;
    }

    @Override
    protected int getCount() {
        return listData.size();
    }


    public void setData(List<Map<String,String>> listData){
        this.listData=listData;
        if(idLvContent.getAdapter()!=null){
            ((ContentAdapter)idLvContent.getAdapter()).setContentList(listData);
        }
    }

    /*
    * isMakeLever 是否划分不同的等级,默认是false
    * */
    public void setLeverColor(boolean isMakeLever,int normalColor,int pre_AlarmColor,int alarmColor){
        this.isMakeLever=isMakeLever;
        this.normalColor=normalColor;
        this.pre_AlarmColor=pre_AlarmColor;
        this.alarmColor=alarmColor;

    }

    public void setBackGroundTextColor(){
        setTitleBackGroundColor("#292A3E", "#292A3E", "#292A3E");//设置各个标题的背景颜色
        setTitleTextColor("#ffffff","#ffffff","#ffffff");//设置各个标题的字体颜色
        idLvContent.setBackgroundColor(Color.parseColor("#292A3E"));//设置内容的背景颜色
        ((ContentAdapter)idLvContent.getAdapter()).setItemTextColor("#FFffff");//设置内容的字体颜色

    }

    private int rowTitleHeight=0;
    private int rowTitleWidth=0;
    private int column=0;
   //设置一些初始化的配置
    public void initListViewAdapter(int rowTitleHeight,int rowTitleWidth,List<String> rowTitle,List<String> columnTitle){
        this.rowTitleHeight=rowTitleHeight;
        this.rowTitleWidth=rowTitleWidth;
        setTitle("序号");//设置表的标题
        setTitleHeight(rowTitleHeight);//设置表标题的高
        setTitleWidth(rowTitleWidth);//设置表标题的宽
        setRowDataList(rowTitle);//设置横向表头的内容
        setColumnDataList(columnTitle);//设置纵向标题的内容
        column=rowTitle.size();
        setSwipeRefreshEnabled(false);//设置不能进行下拉刷新
        // set自己写的contentAdapter
        ContentAdapter contentAdapter = new ContentAdapter(context,listData);
        idLvContent.setAdapter(contentAdapter);
        setOnRefreshListener(new CustomRefreshListener());//需要在调用父类的方法之前设置监听
        setBackGroundTextColor();
        super.initAdapter();//一定要在设置完后再调用父类的方法

    }

    List<Integer> columnWidthList;
    public void setcolumnWidth(List<Integer> list) {
        columnWidthList=list;
        //传递给标题
        setTitleWidth(columnWidthList);
    }


    /**
     * 也可以自定义OnRefreshListener，然后再上面的{@link #initAdapter()}中调用setOnRefreshListener()
     */
    private class CustomRefreshListener implements SwipeRefreshLayout.OnRefreshListener{
        @Override
        public void onRefresh() {
            getSwipeRefreshLayout().setRefreshing(false);
        }
    }


    public List<Map<String, String>> getContentList() {
        return listData;
    }

    private class ContentAdapter extends ArrayAdapter {

        public void setItemTextColor(String itemTextColor) {
            this.itemTextColor = itemTextColor;
        }

        private String itemTextColor="#ffffff";//内容字体默认颜色

        public void setContentList(List<Map<String, String>> contentList) {
            this.contentList = contentList;
        }

        private List<Map<String, String>> contentList;

        ContentAdapter(Context context, List<Map<String, String>> contentList) {
            super(context, 0);
            this.contentList = contentList;
        }

        @Override
        public int getCount() {
            return contentList.size();
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            Map<String, String> data = contentList.get(position);
            ViewHolder viewHolder;
            LinearLayout linearLayout;
            if (convertView == null) {
                linearLayout=new LinearLayout(context);
                LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                linearLayout.setLayoutParams(layoutParams);
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                for (int i = 0;i<column; i++){//设置列的标题
                    TextView rowItem = new TextView(context);
                    rowItem.setId(i);
                    rowItem.getPaint().setFakeBoldText(true);
                    rowItem.setWidth(columnWidthList.get(i));//设置宽度
                    rowItem.setHeight(rowTitleHeight);//设置高度
                    rowItem.setGravity(Gravity.CENTER);
                    rowItem.setTextColor(Color.parseColor(itemTextColor));//设置内容文本的颜色
                    linearLayout.addView(rowItem);
                }
                viewHolder = new ViewHolder(linearLayout);
                linearLayout.setTag(viewHolder);
            } else {
                linearLayout = (LinearLayout) convertView;
                viewHolder = (ViewHolder) linearLayout.getTag();
            }
            for(int i=0;i<column;i++){
                viewHolder.list.get(i).setText(data.get((i+1)+""));
            }
            return linearLayout;
        }

        //isMakeLever 是否需要分颜色
        public void setTextColor(TextView textView,double data,boolean isMakeLever){
            //因为布局重用的话，这个textview上一次设置的颜色会保持残留，如果不对其进行重置
            //上次的颜色就会保留，而滑动的时候只会更改里面的值。所以要在改变值的时候，还要将其颜色重置为默认色彩
            //然后再对比值的大小，再进行颜色的选择
            if(isMakeLever){
                textView.setTextColor(normalColor);
                selectColor(textView, 60.0, 80.5, data);
            }
            textView.setBackgroundColor(Color.GRAY);
        }

        //设置颜色
        private void selectColor(TextView textView,double pre_alarm,double alarm,double data){
                if(data>=pre_alarm&&data<=alarm){
                    //预报警
                    textView.setTextColor(pre_AlarmColor);
                }else if(data>alarm){
                    //报警
                    textView.setTextColor(alarmColor);
                }
        }



        private class ViewHolder {
            List<TextView> list=new ArrayList<>();

            ViewHolder(View itemView) {
                for(int i=0;i<column;i++){
                    TextView textView = (TextView) itemView.findViewById(i);
                    list.add(textView);
                }
            }
        }
    }
}
