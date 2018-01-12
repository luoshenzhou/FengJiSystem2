package com.haozhi.machinestatu.fengjisystem.vidio;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.haozhi.machinestatu.fengjisystem.R;
import com.haozhi.machinestatu.fengjisystem.base.base_activity.Base_TitleBar_Activity;
import com.haozhi.machinestatu.fengjisystem.titlebar.TitleBar;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by LSZ on 2018/1/12.
 */
public class NowVidioActivity extends Base_TitleBar_Activity {


    int[] img = new int[]{R.drawable.gv_item_vidio, R.drawable.gv_item_vidio, R.drawable.gv_item_vidio, R.drawable.gv_item_vidio, R.drawable.gv_item_vidio, R.drawable.gv_item_vidio, R.drawable.gv_item_vidio, R.drawable.gv_item_vidio};
    @Bind(R.id.gv_vidio)
    GridView gvVidio;
    private String[] title=new String[]{"风机周围环境及其他风机运行状况探头1",
            "风机周围环境及其他风机运行状况探头2",
            "风机周围环境及其他风机运行状况探头3",
            "风机周围环境及其他风机运行状况探头4",
            "塔筒内的主控柜变流器柜",
            "机舱内的设备探头1",
            "机舱内的设备探头2",
            "塔筒开门处"};

    @Override
    protected String setTitleName() {
        return "实时监控视频";
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
        return R.layout.now_vidio_activity;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData() {
        gvVidio.setAdapter(new MyGirdAdapter());
    }


    class MyGirdAdapter extends BaseAdapter {




        @Override
        public int getCount() {
            return img.length;
        }

        @Override
        public Object getItem(int position) {
            return img[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(NowVidioActivity.this, R.layout.vidio_item_layout, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.imImg.setImageResource(img[position]);
            viewHolder.tvTitle.setText(title[position]);
            return convertView;
        }

        class ViewHolder {
            @Bind(R.id.im_img)
            ImageView imImg;
            @Bind(R.id.tv_title)
            TextView tvTitle;
            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
