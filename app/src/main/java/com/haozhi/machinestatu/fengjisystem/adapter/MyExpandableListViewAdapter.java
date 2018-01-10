package com.haozhi.machinestatu.fengjisystem.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.haozhi.machinestatu.fengjisystem.R;
import com.haozhi.machinestatu.fengjisystem.bean.TitleData;
import com.haozhi.machinestatu.fengjisystem.bean.TitleName;

import java.util.List;

/**
 * Created by lsz on 2017/1/16.
 * 重复列表的适配器
 */
public class MyExpandableListViewAdapter extends BaseExpandableListAdapter {

    private static final String TAG = "MyExpandableList";
    private List<TitleName> groupData;
    private Context context;
    private Handler handler;

    public MyExpandableListViewAdapter(Context context, List<TitleName>  groupData) {
        this.context = context;
        this.groupData = groupData;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }


    @Override
    public int getGroupCount() {
        return groupData.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupData.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
            return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.expandaview_group_title_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.ex_textView_title = (TextView) convertView.findViewById(R.id.ex_group_title_name);
            viewHolder.im_logo = (ImageView) convertView.findViewById(R.id.im_sys_logo);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        TitleName titleName = groupData.get(groupPosition);
        viewHolder.im_logo.setImageResource(titleName.getImg());
        viewHolder.ex_textView_title.setText(titleName.getGroupTitleName());
        return convertView;
    }

    private final int SHOW_EXPANDABLE_GROUP_NOT_DATA_TOAST=0;
    //在这里控制为0的时候，不打开
    @Override
    public int getChildrenCount(int groupPosition) {
        if (groupData.size() > groupPosition) {
            return groupData.get(groupPosition).getGroupData().size();
        } else {
            if (handler != null) {
                handler.sendEmptyMessage(SHOW_EXPANDABLE_GROUP_NOT_DATA_TOAST);
            }
            Log.e(TAG, "childCount=0，this group not contain child");
            return 0;
        }
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (groupData.get(groupPosition).getGroupData().size() > 0) {
            return groupData.get(groupPosition).getGroupData().get(childPosition);
        } else {
            Log.e(TAG, "childCount=0，this group not contain child");
            return null;
        }

    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        Log.d(TAG, "hasStableIds");
        return false;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolder viewHolder ;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.expandaview_group_data_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.ex_textView_data = (TextView) convertView.findViewById(R.id.ex_group_data_name);
            viewHolder.im_statue= (ImageView) convertView.findViewById(R.id.im_statue);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //在这里加上要比较的东西，用来做判断，然后去修改textView的字体的颜色，并且需要设定一个值，在点击的时候不
        //做任何处理，只是提示没有数据
        viewHolder.ex_textView_data.setTextColor(Color.GRAY);
        Log.e(TAG, "iscontain childPosition" + childPosition);
        if (childPosition%3==0){
            viewHolder.im_statue.setImageResource(R.drawable.statue_green);
        }else {
            viewHolder.im_statue.setImageResource(R.drawable.statue_red);
        }
        viewHolder.ex_textView_data.setText(groupData.get(groupPosition).getGroupData().get(childPosition).getDataTitle());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        //这个是控制子列表的监听开关；返回true可以监听，返回false不能监听（ps:此处与组列表的监听无关）
        Log.d(TAG, "isChildSelectable:" + groupPosition + " " + childPosition);
        return true;
    }


    class ViewHolder {
        TextView ex_textView_data;
        TextView ex_textView_title;
        ImageView im_statue;
        ImageView im_logo;
    }
}
