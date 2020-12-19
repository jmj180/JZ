package cn.edu.sicnu.jz.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import cn.edu.sicnu.jz.R;
import cn.edu.sicnu.jz.db.AccountBean;

public class AccountAdapter extends BaseAdapter {
    Context context;
    List<AccountBean> mDatas;
    LayoutInflater inflater;
    int year,month,day;
    public AccountAdapter(Context context, List<AccountBean> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
        inflater=LayoutInflater.from(context);
        Calendar calendar=Calendar.getInstance();
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH)+1;
        day=calendar.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView ==null) {
            convertView=inflater.inflate(R.layout.item_mainlv,parent,false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder=(ViewHolder)convertView.getTag();
        }
        AccountBean bean=mDatas.get(position);
        holder.typeIv.setImageResource(bean.getsImageId());
        holder.typeTv.setText(bean.getTypename());
        holder.beizhuTv.setText(bean.getBeizhu());
        if(bean.getMonth()==month&&bean.getDay()==day){
            holder.timeTv.setText("今天");

        }else{
            if(bean.getYear()==year){
                holder.timeTv.setText(bean.getTime());
            }else {
                holder.timeTv.setText(bean.getYear()+"年"+bean.getTime());
            }

        }

        holder.moneyTv.setText(String.valueOf(bean.getMoney()));
        return convertView;
    }
    class ViewHolder{
        ImageView typeIv;
        TextView typeTv,beizhuTv,timeTv,moneyTv;
        public  ViewHolder(View view){
            typeIv=view.findViewById(R.id.item_mainlv_iv);
            typeTv=view.findViewById(R.id.item_mainlv_tv_title);
            beizhuTv=view.findViewById(R.id.item_mainlv_tv_beizhu);
            timeTv=view.findViewById(R.id.item_mainlv_tv_time);
            moneyTv=view.findViewById(R.id.item_mainlv_tv_money);



        }
    }
}
