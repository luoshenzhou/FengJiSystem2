package com.haozhi.machinestatu.fengjisystem.bottom;

import android.app.AlertDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;

import com.haozhi.machinestatu.fengjisystem.R;
import com.haozhi.machinestatu.fengjisystem.utils.ToastUtil;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;


/**
 * Created by LSZ on 2017/11/30.
 * 底部弹出框
 */
public class BottomPop {

    private static final String TAG = BottomPop.class.getSimpleName();
    private Context context;
    private BottomPopListener listener;

    public BottomPop(Context context, BottomPopListener listener) {
        this.context = context;
        this.listener = listener;
    }

    AlertDialog albumDialog;

    public void showBottomPopDialog() {
        albumDialog = new AlertDialog.Builder(context).create();
        albumDialog.setCanceledOnTouchOutside(true);
        albumDialog.setCancelable(true);
        View view = View.inflate(context, R.layout.bottom_pop_layout, null);
        setListener(view);
        albumDialog.show();
        albumDialog.setContentView(view);
        albumDialog.getWindow().setGravity(Gravity.BOTTOM);
        albumDialog.getWindow().setDimAmount(0.2f);
        albumDialog.getWindow().setWindowAnimations(R.style.bottom_pop);
        albumDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    String[] title=new String[]{"数据统计表格","数据历史折线","数据实时折线","取消"};
    private void setListener(View view) {
        ListView listView= (ListView) view.findViewById(R.id.lv_bottom);
        listView.setAdapter(new ArrayAdapter<String>(context,R.layout.bottom_spinner_layout, Arrays.asList(title)));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener.chioce(title[position],position);
                albumDialog.dismiss();
            }
        });
    }


}
