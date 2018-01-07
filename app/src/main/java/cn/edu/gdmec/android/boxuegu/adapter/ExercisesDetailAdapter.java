package cn.edu.gdmec.android.boxuegu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.edu.gdmec.android.boxuegu.R;
import cn.edu.gdmec.android.boxuegu.bean.ExercisesBean;
import cn.edu.gdmec.android.boxuegu.utils.AnalysisUtils;

/**
 * Created by student on 17/12/25.
 */

public class ExercisesDetailAdapter extends BaseAdapter{
    private Context mContext;
    private List<ExercisesBean> ebl;
    private OnSelectListener onSelectListener;
    public ExercisesDetailAdapter(Context context,OnSelectListener onSelectListener){
        this.mContext=context;
        this.onSelectListener = onSelectListener;
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

    private ArrayList<String> selectedPosition = new ArrayList<String>();

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder vh;
        if (view == null){
            vh = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(
                    R.layout.exercises_detail_list_item,null);
            vh.subject=(TextView) view.findViewById(R.id.tv_subject);
            vh.tv_a=(TextView) view.findViewById(R.id.tv_a);
            vh.tv_b=(TextView) view.findViewById(R.id.tv_b);
            vh.tv_c=(TextView) view.findViewById(R.id.tv_c);
            vh.tv_d=(TextView) view.findViewById(R.id.tv_d);
            vh.iv_a=(ImageView) view.findViewById(R.id.iv_a);
            vh.iv_b=(ImageView) view.findViewById(R.id.iv_b);
            vh.iv_c=(ImageView) view.findViewById(R.id.iv_c);
            vh.iv_d=(ImageView) view.findViewById(R.id.iv_d);
            view.setTag(vh);
        }else {
            vh=(ViewHolder) view.getTag();
        }
        final ExercisesBean bean = getItem(i);
        if (bean != null){
            vh.subject.setText(bean.subject);
            vh.tv_a.setText(bean.a);
            vh.tv_b.setText(bean.b);
            vh.tv_c.setText(bean.c);
            vh.tv_d.setText(bean.d);
        }
        if (!selectedPosition.contains(""+i)){
            vh.iv_a.setImageResource(R.drawable.exercises_a);
            vh.iv_b.setImageResource(R.drawable.exercises_b);
            vh.iv_c.setImageResource(R.drawable.exercises_c);
            vh.iv_d.setImageResource(R.drawable.exercises_d);
            AnalysisUtils.setABCDEnable(true,vh.iv_a,vh.iv_b,vh.iv_c,vh.iv_d);
        }else{
            AnalysisUtils.setABCDEnable(false,vh.iv_a,vh.iv_b,vh.iv_c,vh.iv_d);
            switch (bean.select){
                case 0:
                    //用户所选项是正确的
                    if (bean.answer==1){
                        vh.iv_a.setImageResource(R.drawable.exercises_right_icon);
                        vh.iv_b.setImageResource(R.drawable.exercises_b);
                        vh.iv_c.setImageResource(R.drawable.exercises_c);
                        vh.iv_d.setImageResource(R.drawable.exercises_d);
                    }else if (bean.answer==2){
                        vh.iv_a.setImageResource(R.drawable.exercises_a);
                        vh.iv_b.setImageResource(R.drawable.exercises_right_icon);
                        vh.iv_c.setImageResource(R.drawable.exercises_c);
                        vh.iv_d.setImageResource(R.drawable.exercises_d);
                    }else if (bean.answer==3){
                        vh.iv_a.setImageResource(R.drawable.exercises_a);
                        vh.iv_b.setImageResource(R.drawable.exercises_b);
                        vh.iv_c.setImageResource(R.drawable.exercises_right_icon);
                        vh.iv_d.setImageResource(R.drawable.exercises_d);
                    }else if (bean.answer==4){
                        vh.iv_a.setImageResource(R.drawable.exercises_a);
                        vh.iv_b.setImageResource(R.drawable.exercises_b);
                        vh.iv_c.setImageResource(R.drawable.exercises_c);
                        vh.iv_d.setImageResource(R.drawable.exercises_right_icon);
                    }
                    break;
                case 1:
                    //A错了
                    vh.iv_a.setImageResource(R.drawable.exercises_error_icon);
                    if (bean.answer==2){
                        vh.iv_b.setImageResource(R.drawable.exercises_right_icon);
                        vh.iv_c.setImageResource(R.drawable.exercises_c);
                        vh.iv_d.setImageResource(R.drawable.exercises_d);
                    }else if (bean.answer==3){
                        vh.iv_b.setImageResource(R.drawable.exercises_b);
                        vh.iv_c.setImageResource(R.drawable.exercises_right_icon);
                        vh.iv_d.setImageResource(R.drawable.exercises_d);
                    }else if (bean.answer==4){
                        vh.iv_b.setImageResource(R.drawable.exercises_b);
                        vh.iv_c.setImageResource(R.drawable.exercises_c);
                        vh.iv_d.setImageResource(R.drawable.exercises_right_icon);
                    }break;
                case 2:
                    vh.iv_b.setImageResource(R.drawable.exercises_error_icon);
                    if (bean.answer==1){
                        vh.iv_a.setImageResource(R.drawable.exercises_right_icon);
                        vh.iv_c.setImageResource(R.drawable.exercises_c);
                        vh.iv_d.setImageResource(R.drawable.exercises_d);
                    }else if (bean.answer==3){
                        vh.iv_a.setImageResource(R.drawable.exercises_a);
                        vh.iv_c.setImageResource(R.drawable.exercises_right_icon);
                        vh.iv_d.setImageResource(R.drawable.exercises_d);
                    }else if (bean.answer==4){
                        vh.iv_a.setImageResource(R.drawable.exercises_a);
                        vh.iv_c.setImageResource(R.drawable.exercises_c);
                        vh.iv_d.setImageResource(R.drawable.exercises_right_icon);
                    }break;
                case 3:
                    vh.iv_c.setImageResource(R.drawable.exercises_error_icon);
                    if (bean.answer==1){
                        vh.iv_a.setImageResource(R.drawable.exercises_right_icon);
                        vh.iv_b.setImageResource(R.drawable.exercises_b);
                        vh.iv_d.setImageResource(R.drawable.exercises_d);
                    }else if (bean.answer==2){
                        vh.iv_a.setImageResource(R.drawable.exercises_a);
                        vh.iv_b.setImageResource(R.drawable.exercises_right_icon);
                        vh.iv_d.setImageResource(R.drawable.exercises_d);
                    }else if (bean.answer==4){
                        vh.iv_a.setImageResource(R.drawable.exercises_a);
                        vh.iv_b.setImageResource(R.drawable.exercises_b);
                        vh.iv_d.setImageResource(R.drawable.exercises_right_icon);
                    }break;

                case 4:
                    vh.iv_d.setImageResource(R.drawable.exercises_error_icon);
                    if (bean.answer==1){
                        vh.iv_a.setImageResource(R.drawable.exercises_right_icon);
                        vh.iv_b.setImageResource(R.drawable.exercises_b);
                        vh.iv_c.setImageResource(R.drawable.exercises_c);
                    }else if (bean.answer==2){
                        vh.iv_a.setImageResource(R.drawable.exercises_a);
                        vh.iv_b.setImageResource(R.drawable.exercises_right_icon);
                        vh.iv_c.setImageResource(R.drawable.exercises_c);
                    }else if (bean.answer==3){
                        vh.iv_a.setImageResource(R.drawable.exercises_a);
                        vh.iv_b.setImageResource(R.drawable.exercises_b);
                        vh.iv_c.setImageResource(R.drawable.exercises_right_icon);
                    }break;
                default:
                    break;
            }
        }
        //当用户名点击A选项的点击事件
        vh.iv_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //判断selectedPosition中是否包含此时的position
                if (selectedPosition.contains(""+i)){
                    selectedPosition.remove(""+i);
                }else{
                    selectedPosition.add(i+"");
                }
                onSelectListener.onSelectA(i,vh.iv_a,vh.iv_b,vh.iv_c,vh.iv_d);
            }
        });
        vh.iv_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedPosition.contains(""+i)){
                    selectedPosition.remove(""+i);
                }else{
                    selectedPosition.add(i+"");
                }
                onSelectListener.onSelectB(i,vh.iv_a,vh.iv_b,vh.iv_c,vh.iv_d);
            }
        });
        vh.iv_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedPosition.contains(""+i)){
                    selectedPosition.remove(""+i);
                }else{
                    selectedPosition.add(i+"");
                }
                onSelectListener.onSelectC(i,vh.iv_a,vh.iv_b,vh.iv_c,vh.iv_d);
            }
        });
        vh.iv_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedPosition.contains(""+i)){
                    selectedPosition.remove(""+i);
                }else{
                    selectedPosition.add(i+"");
                }
                onSelectListener.onSelectD(i,vh.iv_a,vh.iv_b,vh.iv_c,vh.iv_d);
            }
        });
        return view;
    }

    class ViewHolder {
        public TextView subject, tv_a, tv_b, tv_c, tv_d;
        public ImageView iv_a, iv_b, iv_c, iv_d;
    }
    public interface OnSelectListener{
        void onSelectA(int position, ImageView iv_a, ImageView iv_b, ImageView iv_c, ImageView iv_d);
        void onSelectB(int position, ImageView iv_a, ImageView iv_b, ImageView iv_c, ImageView iv_d);
        void onSelectC(int position, ImageView iv_a, ImageView iv_b, ImageView iv_c, ImageView iv_d);
        void onSelectD(int position, ImageView iv_a, ImageView iv_b, ImageView iv_c, ImageView iv_d);
    }
}

