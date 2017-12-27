package cn.edu.gdmec.android.boxuegu.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import cn.edu.gdmec.android.boxuegu.R;
import cn.edu.gdmec.android.boxuegu.bean.UserBean;
import cn.edu.gdmec.android.boxuegu.utils.AnalysisUtils;
import cn.edu.gdmec.android.boxuegu.utils.DBUtils;

/**
 * Created by student on 17/12/27.
 */

public class UserInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_back;
    private TextView tv_main_title;
    private TextView tv_user_name;
    private TextView tv_sex;
    private TextView tv_nickName;
    private TextView tv_signature;
    private RelativeLayout rl_sex;
    private RelativeLayout rl_nickName;
    private RelativeLayout rl_signature;
    private RelativeLayout rl_title_bar;
    private static final int CHANGE_NICKNAME = 1;
    private static final int CHANGE_SIGNATURE = 2;
    private String spUserName;
    private String sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        //设置此界面为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //从ShPreference是中获取登录时的用户名
        spUserName = AnalysisUtils.readLoginUserName(this);
        init();
        initData();
        setListener();
    }


//初始化控件
    private void init() {
        TextView tv_back = (TextView) findViewById(R.id.tv_back);
        TextView tv_main_title = (TextView) findViewById(R.id.tv_main_title);
        tv_main_title.setText("个人资料");
        rl_title_bar = (RelativeLayout) findViewById(R.id.title_bar);
        rl_title_bar.setBackgroundColor(Color.parseColor("#30B4FF"));
        rl_nickName = (RelativeLayout) findViewById(R.id.rl_nickName);
        rl_sex = (RelativeLayout) findViewById(R.id.rl_sex);
        rl_signature = (RelativeLayout) findViewById(R.id.rl_singature);
        tv_nickName = (TextView) findViewById(R.id.tv_nickName);
        tv_user_name = (TextView) findViewById(R.id.tv_user_name);
        tv_signature = (TextView) findViewById(R.id.tv_signature);
        tv_sex = (TextView) findViewById(R.id.tv_sex);
    }

    //从数据库中获取数据
    private void initData() {
        UserBean  bean = null;
        bean = DBUtils.getInstance(this).getUserInfo(spUserName);
        //首先判断一下数据库中是否有数据
        if(bean == null){
            bean = new UserBean();
            bean.userName = spUserName;
            bean.nickName = " 问答精灵";
            bean.sex = "男";
            bean.signature = " 问答精灵";
            //保存用户信息到数据库
            DBUtils.getInstance(this).saveUserInfo(bean);
        }
        setValue(bean);
    }

    //界面控件数值值
    public void setValue(UserBean bean) {
        tv_nickName.setText(bean.nickName);
        tv_user_name.setText(bean.userName);
        tv_sex.setText(bean.sex);
        tv_signature.setText(bean.signature);
    }

    private void setListener() {
        tv_back.setOnClickListener(this);
        rl_nickName.setOnClickListener(this);
        rl_sex.setOnClickListener(this);
        tv_signature.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_back:
                this.finish();
                break;
            case R.id.rl_nickName:  //昵称的点击事件
                String name = tv_nickName.getText().toString();
                Bundle bdName = new Bundle();
                bdName.putString("content",name);
                bdName.putString("title","昵称");
                bdName.putInt("flag",1);
                enterActivityForResult(ChangeUserInfoActivity.class,
                        CHANGE_NICKNAME,bdName);//跳转到个人资料修改界面
                break;
            case R.id.rl_sex:   //性别的点击事件
                String sex = tv_sex.getText().toString();
                sexDialog(sex);
                break;
            case R.id.rl_singature:  //签名的点击事件
                String signature = tv_signature.getText().toString();
                Bundle bdSignature = new Bundle();
                bdSignature.putString("content",signature);
                bdSignature.putString("title","签名");
                bdSignature.putInt("flag",2);
                enterActivityForResult(ChangeUserInfoActivity.class,
                        CHANGE_SIGNATURE,bdSignature);
                break;
            default:
                break;
        }
    }

    //设置性别的弹出框
    private void sexDialog(String sex) {
        int sexFlag = 0;
        if ("男".equals(sex)) {
            sexFlag = 0;

        } else if ("女".equals(sex)) {
            sexFlag = 1;
        }

        final String items[] = {"男", "女"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("性别");
        builder.setSingleChoiceItems(items, sexFlag, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(UserInfoActivity.this, items[which], Toast.LENGTH_LONG).show();
                setSex(items[which]);
            }
        });
        builder.show();
    }

    //更新界面上的性别属性
        public void setSex(String sex) {
            tv_sex.setText(sex);
            //更新数据库中的性别字段
            DBUtils.getInstance(this).updateUserInfo("sex",sex,spUserName);
        }

        //回传数据
    private String new_info;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        switch (requestCode){
            case CHANGE_NICKNAME:   //个人资料修改界面回传过来的昵称数据
                if(data != null){
                    new_info = data.getStringExtra("nickName");
                    if(TextUtils.isEmpty( new_info )|| new_info == null){
                        return;
                    }
                    tv_nickName.setText(new_info);
                    DBUtils.getInstance(UserInfoActivity.this).updateUserInfo(
                            "nickName",new_info,spUserName);
                }
                break;
            case CHANGE_SIGNATURE:  //个人资料修改界面回传过来的签名数据
                if(data != null){
                    new_info = data.getStringExtra("signature");
                    if(TextUtils
                            .isEmpty(new_info) || new_info == null){
                        return;
                    }
                    tv_signature.setText(new_info);
                    //更新数据库中的签名字段
                    DBUtils.getInstance(UserInfoActivity.this).updateUserInfo(
                            "signature",new_info,spUserName);
                }
                break;
        }
    }

        private void enterActivityForResult(Class<?> to, int requestCode, Bundle b) {
            Intent i = new Intent(this,to);
            i.putExtras(b);
            startActivityForResult(i,requestCode);
    }
}


