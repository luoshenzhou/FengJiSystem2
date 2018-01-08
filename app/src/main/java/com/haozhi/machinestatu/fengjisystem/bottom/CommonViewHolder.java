package com.haozhi.machinestatu.fengjisystem.bottom;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * Created by LSZ on 2017/12/28.
 */
public class CommonViewHolder {
    private SparseArray<View> views;
    private View convertView;

    public CommonViewHolder(ViewGroup parent,LayoutInflater inflater,int layoutId) {
        this.views = new SparseArray<View>();
        this.convertView = inflater.inflate(layoutId,parent,false);
        this.convertView.setTag(this);
    }

    /**
     * 得到viewHolder
     * @param parent
     * @param convertView
     * @param inflater
     * @param layoutId
     * @return
     */
    public static CommonViewHolder getViewHolder(ViewGroup parent,View convertView,LayoutInflater inflater,int layoutId){
        if (convertView==null){
            return new CommonViewHolder(parent,inflater,layoutId);
        }
        return (CommonViewHolder) convertView.getTag();
    }


    /**
     * 根据Id得到view
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View>T getView(int viewId){
        View view = views.get(viewId);
        if (view==null){
            view = convertView.findViewById(viewId);
            views.put(viewId,view);
        }
        return (T) view;
    }

    /**
     * 根据id得到TextView
     * @return
     */
    public TextView getTextView(int viewId){
        return getView(viewId);
    }
    /**
     * 根据id得到ImageView
     * @return
     */
    public ImageView getImageView(int viewId){
        return getView(viewId);
    }
    /**
     * 根据id得到Button
     * @return
     */
    public Button getButton(int viewId){
        return getView(viewId);
    }
    /**
     * 根据id得到RadioButton
     * @return
     */
    public RadioButton getRadioButton(int viewId){
        return getView(viewId);
    }
    /**
     * 根据id得到CheckBox
     * @return
     */
    public CheckBox getCheckBox(int viewId){
        return getView(viewId);
    }
    /**
     * 根据id得到ImageButton
     * @return
     */
    public ImageButton getImageButton(int viewId){
        return getView(viewId);
    }
    /**
     * 根据id得到ImageButton
     * @return
     */
    public EditText getEditText(int viewId){
        return getView(viewId);
    }
}
