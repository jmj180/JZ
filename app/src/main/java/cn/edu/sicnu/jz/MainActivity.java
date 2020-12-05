package cn.edu.sicnu.jz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
//头布局控件
    View headerView;
    TextView topOutTv,topInTv;
    Button jz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView mBottomNV = (BottomNavigationView) findViewById(R.id.main_menu_bottom);
        mBottomNV.setItemIconTintList(null);
        //头布局
        addLVHeaderView();
    }

    private void addLVHeaderView() {
        headerView = getLayoutInflater().inflate(R.layout.item_mainlv_top, null);

    }

    public void onClick(View view) {
    }
}