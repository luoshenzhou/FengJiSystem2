package com.haozhi.machinestatu.fengjisystem.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.haozhi.machinestatu.fengjisystem.R;
import com.haozhi.machinestatu.fengjisystem.base.base_activity.Base_TitleBar_Activity;
import com.haozhi.machinestatu.fengjisystem.nowDataFragment.LineFragment;
import com.haozhi.machinestatu.fengjisystem.nowDataFragment.TableFragment;
import com.haozhi.machinestatu.fengjisystem.titlebar.TitleBar;

import java.util.List;

import butterknife.Bind;

/**
 * Created by LSZ on 2018/1/11.
 * 实时数据监测
 */
public class NowDataActivity extends Base_TitleBar_Activity implements CompoundButton.OnCheckedChangeListener {


    @Bind(R.id.rb_table)
    RadioButton rbTable;
    @Bind(R.id.rb_line)
    RadioButton rbLine;
    @Bind(R.id.rg_title)
    RadioGroup rgTitle;
    @Bind(R.id.fl_fragmetContent)
    FrameLayout flFragmetContent;
    @Bind(R.id.tv_group)
    TextView tvGroup;
    @Bind(R.id.tv_child)
    TextView tvChild;

    private Fragment tableFragment=new TableFragment();
    private Fragment lineFragment=new LineFragment();
    private FragmentManager fragmentManager;

    @Override
    protected String setTitleName() {
        return "实时数据监测";
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
        return R.layout.now_data_layout;
    }


    String title_group;
    String title_child;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title_group = getIntent().getStringExtra("title_group");
        title_child = getIntent().getStringExtra("title_child");
        if (!TextUtils.isEmpty(title_group)) {
            tvGroup.setText("当前系统："+title_group);
        }
        if (!TextUtils.isEmpty(title_child)) {
            tvChild.setText("当前风机："+title_child);
        }
        initData();
        initEvent();
    }

    private void initEvent() {
        rbLine.setOnCheckedChangeListener(this);
        rbTable.setOnCheckedChangeListener(this);
    }

    private void initData() {
        initFrament();
        rgTitle.check(R.id.rb_table);
    }

    private void initFrament() {
        ((TableFragment)tableFragment).setTitle(title_group);
        setFragment(tableFragment, table);
    }

    private String line = "line";
    private String table = "table";

    private void setFragment(Fragment fragment, String tag) {
        if (fragmentManager == null) {
            fragmentManager = getSupportFragmentManager();
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragmentByTag = fragmentManager.findFragmentByTag(tag);
        List<Fragment> fragmentList = fragmentManager.getFragments();
        if (fragmentByTag != null) {
            if (fragmentList != null) {
                for (Fragment mfragment : fragmentList) {
                    if (mfragment == fragmentByTag) {
                        transaction.show(mfragment);
                    } else {
                        transaction.hide(mfragment);
                    }
                }
            }
        } else {
            if (fragmentList != null) {
                for (Fragment mfragment : fragmentList) {
                    transaction.hide(mfragment);
                }
            }

            if (!fragment.isAdded()) {
                transaction.add(R.id.fl_fragmetContent, fragment);
            }
            transaction.show(fragment);
        }
        transaction.commit();
    }


    @Override
    public void onCheckedChanged(CompoundButton view, boolean isChecked) {
        if (isChecked) {
            switch (view.getId()) {
                case R.id.rb_line:
                    setFragment(lineFragment, line);
                    rbLine.setChecked(true);
                    List<String> list = ((TableFragment) tableFragment).getCeDianList();
                    ((LineFragment) lineFragment).setData(list);
                    break;
                case R.id.rb_table:
                    setFragment(tableFragment, table);
                    rbTable.setChecked(true);
                    break;
            }
        }
    }
}
