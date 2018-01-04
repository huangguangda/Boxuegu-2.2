package cn.edu.gdmec.android.boxuegu.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.edu.gdmec.android.boxuegu.R;
import cn.edu.gdmec.android.boxuegu.bean.UserBean;
import cn.edu.gdmec.android.boxuegu.utils.AnalysisUtils;
import cn.edu.gdmec.android.boxuegu.utils.DBUtils;

/**
 * Created by Jack on 2017/12/27.
 */

public class UserInfoActivity extends AppCompatActivity implements View.OnClickListener{

    private String spUserName;

    private TextView tv_back;
    private TextView tv_main_title;
    private RelativeLayout rl_title_bar;
    private RelativeLayout rl_nickName;
    private RelativeLayout rl_sex;
    private RelativeLayout rl_signature;
//添加
    private RelativeLayout rl_head;

    private TextView tv_nickName;
    private TextView tv_user_name;
    private TextView tv_sex;
    private TextView tv_signature;
    private static final int CHANGE_NICKNAME = 1;//修改昵称的自定义常量
    private static final int CHANGE_SIGNATURE = 2;//修改个性签名的自定义常理

  /*  private ImageView mImageView;
    private String mPhotoPath;
    private File mPhotoFile;*/
    //private String new_info;
    private ImageView iv_head_icon;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        spUserName = AnalysisUtils.readLoginUserName(this);

      /*  StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builer.detectFileUriExposure();*/
        
        init();
        initData();
        setListener();
    }
//初始化控件
    private void init() {
        rl_head = (RelativeLayout) findViewById(R.id.rl_head);
        iv_head_icon = (ImageView) findViewById(R.id.iv_head_icon);

        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_main_title = (TextView) findViewById(R.id.tv_main_title);
        tv_main_title.setText("个人资料");
        rl_title_bar = (RelativeLayout) findViewById(R.id.title_bar);
        rl_title_bar.setBackgroundColor(Color.parseColor("#30B4FF"));
        rl_nickName = (RelativeLayout) findViewById(R.id.rl_nickName);
        rl_sex = (RelativeLayout) findViewById(R.id.rl_sex);
        rl_signature = (RelativeLayout) findViewById(R.id.rl_singature);

        tv_nickName = (TextView) findViewById(R.id.tv_nickName);
        tv_user_name = (TextView) findViewById(R.id.tv_user_name);
        tv_sex = (TextView) findViewById(R.id.tv_sex);
        tv_signature = (TextView) findViewById(R.id.tv_signature);
    }
//获取数据
    private void initData() {
        UserBean bean = null;
        bean = DBUtils.getInstance(this).getUserInfo(spUserName);
        if (bean == null) {
            bean = new UserBean();
            bean.userName = spUserName;
            bean.nickName = "问答精灵";
            bean.sex = "男";
            bean.signature = "问答精灵";
            DBUtils.getInstance(this).saveUserInfo(bean);
        }
        setValue(bean);
    }
    //为假面控件设置值
    private void setValue(UserBean bean) {
        tv_nickName.setText(bean.nickName);
        tv_user_name.setText(bean.userName);
        tv_sex.setText(bean.sex);
        tv_signature.setText(bean.signature);
    }
    //设置控件的点击监听事件
    private void setListener() {
        rl_head.setOnClickListener ( this );

        tv_back.setOnClickListener(this);
        rl_nickName.setOnClickListener(this);
        rl_sex.setOnClickListener(this);
        rl_signature.setOnClickListener(this);
    }


//控件的点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_back://返回键的点击事件
                this.finish();
                break;


            case R.id.rl_nickName://昵称的点击事件
                String name = tv_nickName.getText().toString();
                Bundle bdName = new Bundle();
                bdName.putString("content",name);
                bdName.putString("title","昵称");
                bdName.putInt("flag",1);//flag传递1时表示是修改昵称
                enterActivityForResult(ChangeUserInfoActivity.class,
                        CHANGE_NICKNAME,bdName);//跳转到个人资料修改界面
                break;
            case R.id.rl_sex:
                String sex = tv_sex.getText().toString();
                sexDialog(sex);
                break;
            case R.id.rl_singature://签名的点击事件
                String signature = tv_signature.getText().toString();
                Bundle bdSignature = new Bundle();
                bdSignature.putString("content",signature);
                bdSignature.putString("title","签名");
                bdSignature.putInt("flag",2);
                enterActivityForResult(ChangeUserInfoActivity.class,
                        CHANGE_SIGNATURE,bdSignature);
                break;
            case R.id.rl_head://头像
               /* chooseDialog();*/
                Intent intent = new Intent(UserInfoActivity.this,HeadActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }
   /* //选择头像
    private void chooseDialog() {
        imageUtils = new ImageUtils(this);
        new AlertDialog.Builder(this)//
                .setTitle("选择头像")//
                .setNegativeButton("相册", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                        imageUtils.byAlbum();
                    }
                })

                .setPositiveButton("拍照", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        String status = Environment.getExternalStorageState();
                        if (status.equals(Environment.MEDIA_MOUNTED)) {//判断是否存在SD卡
                            imageUtils.byCamera();
                        }

                    }
                }).show();
    }*/

   /* public void takePhoto() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setPositiveButton("拍照上传", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction("android.media.action.IMAGE_CAPTURE");
                intent.addCategory("android.intent.category.DEFAULT");
                mPhotoPath = getSDPath()+"/"+ getPhotoFileName();//设置图片文件路径，getSDPath()和getPhotoFileName()具体实现在下面

                mPhotoFile = new File(mPhotoPath);
                if (!mPhotoFile.exists()){
                    try {
                        mPhotoFile.createNewFile();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                intent.putExtra( MediaStore.EXTRA_OUTPUT, Uri.fromFile(mPhotoFile));
                startActivity(intent);
            }
        });
        builder.setNegativeButton("本地相册", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //  dialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image*//*");
                startActivity(intent);
            }
        });

        builder.create().show();

    }

    *//*public File getPhotoFile(){
        File externalFilesDir =
    }*//*
    private String getPhotoFileName() {
        //添加文件名获取方法
        //return "IMG_" + getId().toString() + ".jpg";

        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat (
                "'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date)  +".jpg";
    }

    private String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED);   //判断sd卡是否存在
        if(sdCardExist)
        {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir.toString();
    }
*/


    //设置性别的弹出框
    private void sexDialog(String sex){
        int sexFlag = 0;
        if ("男".equals(sex)){
            sexFlag=0;
        }else if("女".equals(sex)){
            sexFlag=1;
        }
        final String items[]={"男","女"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("性别");
        builder.setSingleChoiceItems(items, sexFlag, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
                Toast.makeText(UserInfoActivity.this,items[i],Toast.LENGTH_SHORT).show();
                setSex(items[i]);
            }
        });
        builder.create().show();
    }
//更新界面上的性别数据
    private void setSex(String sex){
        tv_sex.setText(sex);
        //更新数据库中字段
        DBUtils.getInstance(UserInfoActivity.this).updateUserInfo("sex",sex,spUserName);
    }

    private String new_info;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case CHANGE_NICKNAME:
                if (data != null){
                    new_info = data.getStringExtra("nickName");
                    if (TextUtils.isEmpty(new_info)||new_info==null){
                        return;
                    }
                    tv_nickName.setText(new_info);
                    DBUtils.getInstance(UserInfoActivity.this).updateUserInfo(
                            "nickName",new_info,spUserName
                    );
                }
                break;
            case CHANGE_SIGNATURE:
                if (data != null){
                    new_info = data.getStringExtra("signature");
                    if (TextUtils.isEmpty(new_info)||new_info==null){
                        return;
                    }
                    tv_signature.setText(new_info);
                    DBUtils.getInstance(UserInfoActivity.this).updateUserInfo(
                            "signature",new_info,spUserName
                    );
                }
                break;
           /* case ImageUtils.ACTIVITY_RESULT_CAMERA: // 拍照
                try {
                    if (resultCode == -1) {
                        imageUtils.cutImageByCamera();
                    } else {
                        // 因为在无任何操作返回时，系统依然会创建一个文件，这里就是删除那个产生的文件
                        if (imageUtils.picFile != null) {
                            imageUtils.picFile.delete();
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case ImageUtils.ACTIVITY_RESULT_ALBUM:
                try {
                    if (resultCode == -1) {
                        Bitmap bm_icon = imageUtils.decodeBitmap();
                        if (bm_icon != null) {
                            iv_head_icon.setImageBitmap(bm_icon);
                        }
                    } else {
                        // 因为在无任何操作返回时，系统依然会创建一个文件，这里就是删除那个产生的文件
                        if (imageUtils.picFile != null) {
                            imageUtils.picFile.delete();
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;*/
            default:
                break;

        }
    }
    public void enterActivityForResult(Class<?> to,int requestCode,Bundle b){
        Intent i = new Intent(this,to);
        i.putExtras(b);
        startActivityForResult(i,requestCode);
    }
}