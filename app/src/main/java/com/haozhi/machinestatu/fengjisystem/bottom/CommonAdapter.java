package com.haozhi.machinestatu.fengjisystem.bottom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by LSZ on 2017/12/28.
 * http://blog.csdn.net/u012296101/article/details/45364621
 */
public abstract class CommonAdapter<T> extends BaseAdapter {


    private List<T> list;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    private LayoutInflater inflater;
    private int layoutId;

    public CommonAdapter(List<T> list, Context context,int layoutId) {
        this.list = list;
        this.inflater = LayoutInflater.from(context);
        this.layoutId = layoutId;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommonViewHolder viewHolder = CommonViewHolder.getViewHolder(parent,convertView,inflater,layoutId);
        convert(viewHolder,list.get(position));
        return viewHolder.getView(layoutId);
    }
    public abstract void convert(CommonViewHolder viewHolder,T t);
}
