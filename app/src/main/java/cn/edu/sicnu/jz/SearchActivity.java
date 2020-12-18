package cn.edu.sicnu.jz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.edu.sicnu.jz.adapter.AccountAdapter;
import cn.edu.sicnu.jz.db.AccountBean;
import cn.edu.sicnu.jz.db.DBManager;

public class SearchActivity extends AppCompatActivity {
    ListView searchLv;
    EditText searchEt;
    TextView emptyTv;
    List<AccountBean> mDatas;
    AccountAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        mDatas=new ArrayList<>();
        adapter= new AccountAdapter(this,mDatas);
        searchLv.setAdapter(adapter);
        searchLv.setEmptyView(emptyTv);

    }

    private void initView() {
        searchEt=findViewById(R.id.search_et);
        emptyTv=findViewById(R.id.search_tv_empty);
        searchLv=findViewById(R.id.search_lv);
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.search_iv_back:
                finish();
                break;
            case R.id.search_iv_sh:
                String msg=searchEt.getText().toString().trim();
                //判断是否为空
                if(TextUtils.isEmpty(msg)){
                    Toast.makeText(this,"输入内容不能为空",Toast.LENGTH_SHORT).show();;
                    return;
                }
                //开始搜索
                mDatas.clear();
                List<AccountBean>list = DBManager.getAccountListByRemarkFromAccounttb(msg);
                mDatas.addAll(list);
                adapter.notifyDataSetChanged();
                break;
        }
    }
}