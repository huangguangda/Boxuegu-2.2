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
    public void setData(List<ExercisesBean> ebl){
        this.ebl = ebl;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return ebl == null ? 0 : ebl.size();
    }

    @Override
    public ExercisesBean getItem(int i) {
        return ebl == null ? null : ebl.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder vh;
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
        final ExercisesBean bean = getItem(i);
        if (bean!=null){
            vh.order.setText(i+1+"");
            vh.title.setText(bean.title);
            vh.content.setText(bean.content);
            vh.order.setBackgroundResource(bean.background);
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bean == null)
                    return;

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