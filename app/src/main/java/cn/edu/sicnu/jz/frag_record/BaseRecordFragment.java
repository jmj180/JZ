package cn.edu.sicnu.jz.frag_record;

import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.KeyboardShortcutGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.edu.sicnu.jz.R;
import cn.edu.sicnu.jz.db.AccountBean;
import cn.edu.sicnu.jz.db.DBManager;
import cn.edu.sicnu.jz.db.TypeBean;
import cn.edu.sicnu.jz.utils.BeiZhuDialog;
import cn.edu.sicnu.jz.utils.KeyBoardUtils;
import cn.edu.sicnu.jz.utils.SelectTimeDialog;


public abstract class BaseRecordFragment extends Fragment implements View.OnClickListener {
    KeyboardView keyboardView;
    EditText moneyEt;
    TextView typeTV,beizhuTv,timeTv,typelv;
    GridView typeGv;
    List<TypeBean>typeList;
    TypeBaseAdapter adapter;
    AccountBean accountBean;//将需要插入到记账本中的数据保存成对象形式


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountBean = new AccountBean();
        accountBean.setTypename("餐饮");
        accountBean.setsImageId(R.mipmap.ic_canyin_fs);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_outcome, container, false);
        initView(view);
        //给gridview填充数据的方法
        loadDataToGV();
        setGVListener();//GV点击事件
        setInitTime();
        
        return view;
    }
 //获取当前时间
    private void setInitTime() {
        Date date = new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("MM月dd日");
        String time=sdf.format(date);
        timeTv.setText(time);
        accountBean.setTime(time);

        Calendar calendar=Calendar.getInstance();
        int year =calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        accountBean.setYear(year);
        accountBean.setMonth(month);
        accountBean.setDay(day);
    }

    private void setGVListener() {
        typeGv.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.selectPos = position;
                adapter.notifyDataSetChanged();
                TypeBean typeBean = typeList.get(position);
                String typename=typeBean.getTypename();
                typeTV.setText(typename);
                accountBean.setTypename(typename);
                int simageId = typeBean.getsImageId();
                accountBean.setsImageId(simageId);


            }
        });
    }

   public void loadDataToGV() {
        typeList=new ArrayList<>();
        adapter = new TypeBaseAdapter(getContext(), typeList);
        typeGv.setAdapter(adapter);

    }

    private void initView(View view) {
        keyboardView = view.findViewById(R.id.frag_record_keyboard);
        moneyEt = view.findViewById(R.id.frag_record_et_money);
        typeGv =  view.findViewById(R.id.frag_record_gv);
        typeTV = view.findViewById(R.id.frag_record_tv_type);
        beizhuTv= view.findViewById(R.id.frag_record_tv_beizhu);
        timeTv = view.findViewById(R.id.frag_record_tv_time);
        beizhuTv.setOnClickListener(this);
        timeTv.setOnClickListener(this);

        //显示键盘
        KeyBoardUtils boardUtils= new KeyBoardUtils(keyboardView,moneyEt);
        boardUtils.showKeyboard();

        //设置接口，监听确定按钮被监听
        boardUtils.setOnEnsureListener(new KeyBoardUtils.OnEnsureListener(){
            @Override
            public void OnEnsure() {

                //获取money
                String moneystr=moneyEt.getText().toString();
                if(TextUtils.isEmpty(moneystr)||moneystr.equals("0.00")){
                    getActivity().finish();
                    return;
                }
                float money = Float.parseFloat(moneystr);
                accountBean.setMoney(money);

                //获取记录的信息，保存在数据库中
                saveAccountToDB();
                //返回上一级
                getActivity().finish();

            }
        });


    }
    //让子类一定要重写这个方法
    public abstract void saveAccountToDB();

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.frag_record_tv_time:
                showTimeDialog();
                break;
            case R.id.frag_record_tv_beizhu:
                showBZDialog();
                break;

        }
    }

    private void showTimeDialog(){
        SelectTimeDialog dialog=new SelectTimeDialog(getContext());
        dialog.show();


        //确定按钮被点击了
        dialog.setOnEnsureListener(new SelectTimeDialog.OnEnsureListener(){
            @Override
            public void onEnsure(String time, int year, int month, int day) {
                timeTv.setText(time);
                accountBean.setTime(time);
                accountBean.setYear(year);
                accountBean.setMonth(month);
                accountBean.setDay(day);
            }
        });
    }


    //弹出备注对话框
    public void showBZDialog(){
        final BeiZhuDialog dialog=new BeiZhuDialog(getContext());
        dialog.show();
        dialog.setDialogSize();
        dialog.setOnEnsureListener(new BeiZhuDialog.OnEnsureListener() {
            @Override
            public void onEnsure() {
               String msg= dialog.getEditext();
                if(!TextUtils.isEmpty(msg)){
                    beizhuTv.setText(msg);
                    accountBean.setBeizhu(msg);

                }
                dialog.cancel();
            }
        });
    }
}