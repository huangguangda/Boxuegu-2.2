package cn.edu.gdmec.android.boxuegu.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.edu.gdmec.android.boxuegu.R;

/**
 * Created by Jack on 2017/12/26.
 */

public class SettingActivity extends AppCompatActivity {
    public static  SettingActivity instance = null;
    private TextView tv_main_title;
    private TextView tv_back;
    private RelativeLayout rl_title_bar;
    private RelativeLayout rl_modify_psw;
    private RelativeLayout rl_security_setting;
    private RelativeLayout rl_exit_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        instance = this;
        init();
    }

    private void init() {
        tv_main_title = (TextView) findViewById(R.id.tv_main_title);
        tv_main_title.setText("设置");

        tv_back = (TextView) findViewById(R.id.tv_back);

        rl_title_bar = (RelativeLayout) findViewById(R.id.title_bar);
        rl_title_bar.setBackgroundColor(Color.parseColor("#30B4FF"));

        rl_modify_psw = (RelativeLayout) findViewById(R.id.rl_modiy_psw);

        rl_security_setting = (RelativeLayout) findViewById(R.id.rl_security_setting);

        rl_exit_login = (RelativeLayout) findViewById(R.id.rl_exit_login);

        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingActivity.this.finish();
            }
        });
        rl_modify_psw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this,ModifyPswActivity.class);
                startActivity(intent);
            }
        });
        rl_security_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this,FindPswActivity.class);
                intent.putExtra("from","security");
                startActivity(intent);
            }
        });
        rl_exit_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SettingActivity.this,"退出登录成功",Toast.LENGTH_SHORT).show();
                clearLoginStatus();
                Intent data = new Intent();
                data.putExtra("isLogin",false);
                setResult(RESULT_OK,data);
                SettingActivity.this.finish();
            }
        });
    }

    private void clearLoginStatus(){
        SharedPreferences sp = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("isLogin",false);
        editor.putString("loginUserName","");
        editor.commit();
    }
}