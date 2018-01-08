package com.haozhi.machinestatu.fengjisystem.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.haozhi.machinestatu.fengjisystem.R;
import com.haozhi.machinestatu.fengjisystem.base.base_activity.BaseActivity;
import com.haozhi.machinestatu.fengjisystem.login.LoginActivity;

/**
 * Created by LSZ on 2017/11/23.
 * APP欢迎页面
 */
public class WelComeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_welcome_layout;
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(WelComeActivity.this,LoginActivity.class));
                finish();
            }
        },2500);
    }
}
