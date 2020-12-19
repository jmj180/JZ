package cn.edu.sicnu.jz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.edu.sicnu.jz.adapter.AccountAdapter;
import cn.edu.sicnu.jz.db.AccountBean;
import cn.edu.sicnu.jz.db.DBManager;
import cn.edu.sicnu.jz.utils.CalendarDialog;

public class HistoryInActivity extends AppCompatActivity {
    ListView historyin;
    TextView timeTv;
    List<AccountBean> mDatas;
    AccountAdapter adapter;
    int year,month;

    int dialogSelPos = -1;
    int dialogSelMonth = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_in);
        historyin=findViewById(R.id.history_lv_in);
        timeTv=findViewById(R.id.history_tv_time2);
        mDatas=new ArrayList<>();
        adapter=new AccountAdapter(this,mDatas);
        historyin.setAdapter(adapter);
        initTime();
        timeTv.setText(year+"年"+month+"月");
        loadData(year,month);
    }
    private void loadData(int year,int month) {
        List<AccountBean>list= DBManager.getAccountListOneMonthFromAccounttb(year,month,1);
        mDatas.clear();
        mDatas.addAll(list);
        adapter.notifyDataSetChanged();
    }

    private void initTime() {
        Calendar calendar=Calendar.getInstance();
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH)+1;
    }
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.history_iv_back2:
                finish();
                break;
            case R.id.history_iv_rili2:
                CalendarDialog dialog=new CalendarDialog(this,dialogSelPos,dialogSelMonth);
                dialog.show();
                dialog.setDialogSize();
                dialog.setOnRefreshListener(new CalendarDialog.OnRefreshListener() {
                    @Override
                    public void onRefresh(int selPos, int year, int month) {
                        timeTv.setText(year+"年"+month+"月");
                        loadData(year,month);

                    }
                });

                break;
        }
    }

}