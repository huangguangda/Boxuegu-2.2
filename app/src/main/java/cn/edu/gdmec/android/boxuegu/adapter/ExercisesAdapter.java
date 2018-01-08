package cn.edu.gdmec.android.boxuegu.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.edu.gdmec.android.boxuegu.R;
import cn.edu.gdmec.android.boxuegu.activity.ExercisesDetailActivity;
import cn.edu.gdmec.android.boxuegu.activity.LoginActivity;
import cn.edu.gdmec.android.boxuegu.bean.ExercisesBean;

/**
 * Created by student on 17/12/25.
 */

public class ExercisesAdapter extends BaseAdapter{
    private Context mContext;
    private List<ExercisesBean> ebl;
    public ExercisesAdapter(Context context){
        this.mContext=context;
    }
    //设置数据更新界面
    public void setData(List<ExercisesBean> ebl){
        this.ebl = ebl;
        notifyDataSetChanged();
    }
    //获取Item的总数
    @Override
    public int getCount() {
        return ebl == null ? 0 : ebl.size();
    }
    //根据position  i 得到的Item的对象
    @Override
    public ExercisesBean getItem(int i) {
        return ebl == null ? null : ebl.get(i);
    }
    //根据
    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder vh;
        //复用convertView
        if (view == null){
            vh = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(
                    R.layout.exercises_list_item,null
            );
            vh.title = (TextView) view.findViewById(R.id.tv_title);
            vh.content = (TextView) view.findViewById(R.id.tv_content);
            vh.order = (TextView) view.findViewById(R.id.tv_order);
            view.setTag(vh);
        }else{
            vh=(ViewHolder) view.getTag();
        }

        //获取 i 对应的Item的数据对象
        final ExercisesBean bean = getItem(i);
        if (bean!=null){
            vh.order.setText(i+1+"");
            vh.title.setText(bean.title);
            vh.content.setText(bean.content);
            vh.order.setBackgroundResource(bean.background);
        }
        //convertView每个Item的点击事件

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bean == null)
                    return;
                //跳转到习题详情页面

                /*Intent intent = new Intent(
                        mContext, ExercisesDetailActivity.class
                );
                intent.putExtra("id",bean.id);
                intent.putExtra("title",bean.title);
                mContext.startActivity(intent);*/

                if(readLoginStatus()){
              Intent intent = new Intent( mContext, ExercisesDetailActivity.class );
                    intent.putExtra("id",bean.id);
                    intent.putExtra("title",bean.title);
                    mContext.startActivity(intent);
                }else{
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(intent);
                }

            }
        });
        return view;
    }

    private boolean readLoginStatus() {
        SharedPreferences sp = mContext.getSharedPreferences("loginInfo",Context.MODE_PRIVATE);
        boolean isLogin = sp.getBoolean("isLogin",false);
        return isLogin;
    }

    class ViewHolder {
        public TextView title,content;
        public TextView order;
    }
}