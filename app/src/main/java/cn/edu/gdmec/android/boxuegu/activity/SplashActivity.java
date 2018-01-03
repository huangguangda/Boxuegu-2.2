package cn.edu.gdmec.android.boxuegu.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import cn.edu.gdmec.android.boxuegu.R;

/**
 * Created by Jack on 2017/12/26.
 * 1.获取版本号
 * 在SplashActivity中创建init()方法，在方法中获取TextView控件并通过PackageManager
 * (包管理器)，获取层序版本号
 * （版本号是build.gradle文件中的versionName的值显示在TextView控件上。
 * 2.让界面延迟跳转
 * 在init()方法中使用Timer以及TimerTask类设置欢迎界面延迟3秒在跳转到主界面
 *
 */
public class SplashActivity extends AppCompatActivity {
    private TextView tv_version;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
    }

    private void init() {
        tv_version=(TextView) findViewById(R.id.tv_version);
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(),0);
            tv_version.setText("v"+info.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            tv_version.setText("v");
        }
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        };
        timer.schedule(task,3000);

    }
}