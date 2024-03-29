package cn.edu.gdmec.android.boxuegu.activity;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.edu.gdmec.android.boxuegu.R;
import cn.edu.gdmec.android.boxuegu.utils.AnalysisUtils;
import cn.edu.gdmec.android.boxuegu.utils.MD5Utils;

/**
 * Created by Jack on 2017/12/28.
 */

public class FindPswActivity extends AppCompatActivity {

    private String from;
    private TextView tv_main_title;
    private TextView tv_back;
    private Button btn_validate;
    private EditText et_validate_name;
    private TextView tv_reset_psw;
    private EditText et_user_name;
    private TextView tv_user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_psw);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        from = getIntent().getStringExtra("from");
        init();
    }

    private void init() {
        tv_main_title = (TextView) findViewById(R.id.tv_main_title);
        tv_back = (TextView) findViewById(R.id.tv_back);
        et_validate_name = (EditText) findViewById(R.id.et_validate_name);
        btn_validate = (Button) findViewById(R.id.btn_validate);
        tv_reset_psw = (TextView) findViewById(R.id.tv_reset_psw);
        et_user_name = (EditText) findViewById(R.id.et_user_name);
        tv_user_name = (TextView) findViewById(R.id.tv_user_name);
        if ("security".equals(from)){
            tv_main_title.setText("设置密保");
        }else{
            tv_main_title.setText("找回密码");
            tv_user_name.setVisibility(View.VISIBLE);
            et_user_name.setVisibility(View.VISIBLE);
        }
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FindPswActivity.this.finish();
            }
        });
        btn_validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String validateName = et_validate_name.getText().toString().trim();
                if ("security".equals(from)){
                    if (TextUtils.isEmpty(validateName)){
                        Toast.makeText(FindPswActivity.this,"请输入要验证的姓名",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }else{
                        Toast.makeText(FindPswActivity.this,"密保设置成功",
                                Toast.LENGTH_SHORT).show();
                        saveSecurity(validateName);
                        FindPswActivity.this.finish();
                    }
                }else{
                    String userName = et_user_name.getText().toString().trim();
                    String sp_security = readSecurity(userName);
                    if (TextUtils.isEmpty(userName)){
                        Toast.makeText(FindPswActivity.this,"请输入您的用户名",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }else if (!isExistUserName(userName)){
                        Toast.makeText(FindPswActivity.this,"您输入的用户名不存在",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }else if (TextUtils.isEmpty(validateName)){
                        Toast.makeText(FindPswActivity.this,"请输入要验证的姓名",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }if (!validateName.equals(sp_security)){
                        Toast.makeText(FindPswActivity.this,"输入的密保不正确",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }else{
                        tv_reset_psw.setVisibility(View.VISIBLE);
                        tv_reset_psw.setText("初始密码：123456");
                        savePsw(userName);
                    }
                }
            }
        });
    }

    private void savePsw(String userName){
        String md5Psw = MD5Utils.md5("123456");
        SharedPreferences sp = getSharedPreferences("loginInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(userName,md5Psw);
        editor.commit();
    }
    private void saveSecurity(String validateName){
        SharedPreferences sp = getSharedPreferences("loginInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(AnalysisUtils.readLoginUserName(this)+"_security",validateName);
        editor.commit();
    }

    private String readSecurity(String userName){
        SharedPreferences sp = getSharedPreferences("loginInfo",MODE_PRIVATE);
        String security = sp.getString(userName+"_security","");
        return security;
    }
    private boolean isExistUserName(String userName){
        boolean hasUserName = false;
        SharedPreferences sp = getSharedPreferences("loginInfo",MODE_PRIVATE);
        String spPsw = sp.getString(userName,"");
        if (!TextUtils.isEmpty(spPsw)){
            hasUserName=true;
        }
        return hasUserName;
    }
}
