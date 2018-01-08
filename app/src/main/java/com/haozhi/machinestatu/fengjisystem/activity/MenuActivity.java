package com.haozhi.machinestatu.fengjisystem.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import com.haozhi.machinestatu.fengjisystem.R;
import com.haozhi.machinestatu.fengjisystem.base.BaseActionBarActivity;

import butterknife.Bind;

/**
 * Created by LSZ on 2017/11/9.
 * 出入仓统计
 * <p/>
 * 待解决：
 * 筛选条件中有null ---OK
 * 选择某些条件的时候出现异常---不能重现
 * 展示不好看-----OK
 * 没有title 没有headview---OK
 * 选中时，是否需要加其他跳转
 * 默认打钩----OK
 * <p/>
 * 输入的数据：
 * jobstatus
 */
public class MenuActivity extends BaseActionBarActivity {

    private static final String TAG = MenuActivity.class.getSimpleName();

    @Bind(R.id.drawerLayout_mainactivity)
    DrawerLayout mDrawerLayout;

    @Bind(R.id.tl_custom)
    Toolbar tlCustom;
    ActionBarDrawerToggle mDrawerToggle;

    private String tag = "myfragment";
    private MenuFragment myFragment;
    private FragmentManager supportFragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, tlCustom, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                openMenu();
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                mDrawerLayout.closeDrawers();
            }
        };

    }


    @Override
    public int getLayout() {
        return R.layout.menu_main_layout;
    }

    @Override
    public Toolbar setToolBar() {
        return tlCustom;
    }

    @Override
    public String getToolBarTitle() {
        return "首页";
    }


    /**
     * 设置侧滑菜单的高度，使其顶部位置处于状态栏的下面，
     * 不至于影响状态栏的美观
     */
    private void initDrawerHight() {
        int statusBarHeight = getStatusBarHeight(this);//获取状态栏的高度
        //frameLayout.setPadding(0, statusBarHeight, 0, 0);//由于侧滑菜单高度为全屏，会影响状态栏的美观，所以设置侧滑菜单显示与状态栏之下
    }


    private void initFragment() {
        supportFragmentManager = getSupportFragmentManager();
        myFragment = new MenuFragment();

    }

    //打开侧边栏菜单
    public void openMenu() {
        mDrawerLayout.openDrawer(Gravity.RIGHT);
        /*FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        MenuFragment fragmentByTag = (MenuFragment) supportFragmentManager.findFragmentByTag(tag);
        if (fragmentByTag == null) {
            //transaction.add(R.id.frameLayout, myFragment, tag);
        } else {
            transaction.show(fragmentByTag);
        }
        transaction.commit();*/
    }


    private int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
