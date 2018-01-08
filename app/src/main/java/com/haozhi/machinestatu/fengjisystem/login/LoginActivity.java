package com.haozhi.machinestatu.fengjisystem.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.haozhi.machinestatu.fengjisystem.R;
import com.haozhi.machinestatu.fengjisystem.activity.MainActivity;
import com.haozhi.machinestatu.fengjisystem.activity.MenuActivity;
import com.haozhi.machinestatu.fengjisystem.activity.SystemCenterActivity;
import com.haozhi.machinestatu.fengjisystem.base.base_activity.BaseActivity;
import com.haozhi.machinestatu.fengjisystem.utils.SharedPreferencesUtil;
import com.haozhi.machinestatu.fengjisystem.utils.ToastUtil;

/**
 * 登录页面，点击登录按钮进入主页
 * Created by SmileXie on 2017/6/9.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {


    private static final String TAG = LoginActivity.class.getSimpleName();
    private EditText userName, pwd;
    private TextView forget_pass, register;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_login;
    }

    /**
     * 自动登录
     * 如果用户已经登录过，那么就去找保存到密码token自动在后台进行登录
     */
    private void autoToLogin() {
        //获取用户的token
        String token = (String) SharedPreferencesUtil.getData(LoginActivity.this, "token", "defalt_token");
        String user = (String) SharedPreferencesUtil.getData(LoginActivity.this, "userName", "defalt_user");
        if (token.equals("defalt_token")||user.equals("defalt_token")){

        }else {
            //提交进行登录
            //postData(token, user);
        }
    }






    public void login(View view) {
        startActivity(new Intent(LoginActivity.this,SystemCenterActivity.class));
        finish();
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==10){
            if(resultCode==10){
                String userName = data.getStringExtra("userName");
                this.userName.setText(userName);
                ToastUtil.toastShow("请输入密码进行登录", LoginActivity.this);
            }
        }
    }

    @Override
    public void onClick(View v) {

    }
}
