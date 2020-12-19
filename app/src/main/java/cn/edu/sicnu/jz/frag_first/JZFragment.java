package cn.edu.sicnu.jz.frag_first;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Handler;

import cn.edu.sicnu.jz.HistoryInActivity;
import cn.edu.sicnu.jz.HistoryOutActivity;
import cn.edu.sicnu.jz.R;
import cn.edu.sicnu.jz.RecordActivity;
import cn.edu.sicnu.jz.SearchActivity;
import cn.edu.sicnu.jz.adapter.AccountAdapter;
import cn.edu.sicnu.jz.db.AccountBean;
import cn.edu.sicnu.jz.db.DBManager;


public class JZFragment extends Fragment {
    ListView todayLv;
    List<AccountBean>mDatas;
    AccountAdapter adapter;
    int year,month,day;
    View headerView;
    ImageView search;
    //头布局相关控件
    TextView topOutTv,topInTv,topConTv,topout,topin;
    Button jiyibi;
    boolean frag=true;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        //添加头布局
        addLVHeaderView();
        mDatas=new ArrayList<>();
        initTime();
        //设置适配器
        adapter = new AccountAdapter(getActivity(), mDatas);
        todayLv.setAdapter(adapter);



    }

    private void initView(@NonNull View view) {
        todayLv=view.findViewById(R.id.main_lv);
    }

    //给listview添加头布局
    private void addLVHeaderView() {
        //将头布局转换成View对象
        headerView = getLayoutInflater().inflate(R.layout.item_mainlv_top, null);
        todayLv.addHeaderView(headerView);
        //查找控件
        topOutTv = headerView.findViewById(R.id.item_mainlv_top_out);
        topInTv = headerView.findViewById(R.id.item_mainlv_top_in);
        topConTv = headerView.findViewById(R.id.item_mainlv_top_tv_day);
        jiyibi=headerView.findViewById(R.id.main_btn_edit);
        topout=headerView.findViewById(R.id.item_mainlv_top_tv1);
        topin=headerView.findViewById(R.id.item_mainlv_top_tv2);
        search=headerView.findViewById(R.id.main_iv_search);

        onClick();
        setLVLongClickListenner();

    }
    private void onClick() {
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it1 = new Intent(getContext(), SearchActivity.class);
                startActivity(it1);
            }
        });
        topOutTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it2=new Intent(getContext(), HistoryOutActivity.class);
                startActivity(it2);
            }
        });
        topInTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it3=new Intent(getContext(), HistoryInActivity.class);
                startActivity(it3);
            }
        });
        jiyibi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it2=new Intent(getContext(),RecordActivity.class);
                startActivity(it2);

            }
        });
    }



    private void initTime() {
        Calendar calendar=Calendar.getInstance();
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH)+1;
        day=calendar.get(Calendar.DAY_OF_MONTH);
    }
    //设置listview的长按时间
    private void setLVLongClickListenner() {
        todayLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position==0) {
                    frag=false;
                }
                int pos=position-1;
                AccountBean clickBean= mDatas.get(pos);

                //弹出提示用户是否删除的对话框
                showDeleteItemDialog(clickBean);

            }
        });



    }
//弹出删除某一条记录的对话框
    private void showDeleteItemDialog(final AccountBean clickBean) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("提示信息").setMessage("您确定删除这条记录么？")
                .setNegativeButton("取消",null)
                .setPositiveButton("确定",new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //执行删除
                        int click_id=clickBean.getId();
                            DBManager.deleteItemFromAccounttbById(click_id);
                            mDatas.remove(clickBean);//实时刷新
                        adapter.notifyDataSetChanged();
                        setTopTvShow();
                    }
                });
        builder.create().show();//显示对话框

    }

    @Override
    public void onResume() {
        super.onResume();
        loadDBData();
        setTopTvShow();
    }
//设置头布局数据
    private void setTopTvShow() {
        float incomeOneday = DBManager.getSumMoneyOneDay(year,month,day,1);
//        Log.d("setTopTvShow","ok");
        float outcomeOneday = DBManager.getSumMoneyOneDay(year,month,day,0);
        String totallfoOneDay="今日收支："+(incomeOneday-outcomeOneday);
        topConTv.setText(totallfoOneDay);

        float incomeOneMonth=DBManager.getSumMoneyOneMonth(year,month ,1);
        float outcomeOneMonth=DBManager.getSumMoneyOneMonth(year,month ,0);

        topInTv.setText(""+incomeOneMonth);
        topOutTv.setText(""+outcomeOneMonth);
        topout.setText(month+"月支出");
        topin.setText(month+"月收入");

//
    }

    private void loadDBData() {
       List<AccountBean>list= DBManager.getAccountListAllFromatAccounttb(year,month,day);
        mDatas.clear();
        mDatas.addAll(list);
        adapter.notifyDataSetChanged();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =inflater.inflate(R.layout.fragment_j_z, container, false);

        return view;
    }





}