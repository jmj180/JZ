package cn.edu.sicnu.jz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.edu.sicnu.jz.adapter.AccountAdapter;
import cn.edu.sicnu.jz.db.AccountBean;
import cn.edu.sicnu.jz.db.DBManager;
import cn.edu.sicnu.jz.utils.CalendarDialog;

public class HistoryOutActivity extends AppCompatActivity {
    ListView historyout;
    TextView timeTv;
    List<AccountBean>mDatas;
    AccountAdapter adapter;
    int year,month;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_out);
        historyout=findViewById(R.id.history_lv_out);
        timeTv=findViewById(R.id.history_tv_time);
        mDatas=new ArrayList<>();
        adapter=new AccountAdapter(this,mDatas);
        historyout.setAdapter(adapter);
        initTime();
        timeTv.setText(year+"年"+month+"月");
        loadData();
    }

    private void loadData() {
        List<AccountBean>list= DBManager.getAccountListOneMonthFromatAccounttb(year,month,0);
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
            case R.id.history_iv_back:
                finish();
                break;
            case R.id.history_iv_rili:
                CalendarDialog dialog=new CalendarDialog(this);
                dialog.show();
                dialog.setDialogSize();
                break;
        }
    }


}