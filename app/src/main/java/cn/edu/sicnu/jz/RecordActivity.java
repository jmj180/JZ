package cn.edu.sicnu.jz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import cn.edu.sicnu.jz.adapter.RecordPagerAdapter;
import cn.edu.sicnu.jz.frag_record.IncomeFragment;
import cn.edu.sicnu.jz.frag_record.BaseRecordFragment;
import cn.edu.sicnu.jz.frag_record.OutcomeFragment;

public class RecordActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        
        //找控件
        tabLayout = findViewById(R.id.record_tabs);
        viewPager = findViewById(R.id.record_vp);
        
        initPager();
    }

    private void initPager() {
        List<Fragment> fragmentList = new ArrayList<>();
        OutcomeFragment outFrag = new OutcomeFragment();
        IncomeFragment inFrag = new IncomeFragment();
        fragmentList.add(outFrag);
        fragmentList.add(inFrag);

        //适配器
        RecordPagerAdapter pagerAdapter=new RecordPagerAdapter(getSupportFragmentManager(),fragmentList);

        //设置适配器
        viewPager.setAdapter(pagerAdapter);
        //taplayout he viewpager关联
        tabLayout.setupWithViewPager(viewPager);

    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.record_iv_back:
                finish();
                break;
//            case R.id.main_btn_edit:
//                Intent it1=new Intent(this,RecordActivity.class);
//                startActivity(it1);
        }
    }
}