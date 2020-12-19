package cn.edu.sicnu.jz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import cn.edu.sicnu.jz.frag_first.BBFragment;
import cn.edu.sicnu.jz.frag_first.JZFragment;
import cn.edu.sicnu.jz.frag_thir.moreFragment;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout1;
    ViewPager viewPager1;
//头布局控件
    View headerView;
    Fragment firstFragment,secondFragment,thirdFragment;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView mBottomNV = (BottomNavigationView) findViewById(R.id.main_menu_bottom);
        mBottomNV.setItemIconTintList(null);

        firstFragment=new JZFragment();
        secondFragment=new BBFragment();
        thirdFragment=new moreFragment();
        setmainFragment();

        mBottomNV.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.write:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout,firstFragment)
                                .commit();
                       return true;
                    case R.id.baobiao:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout,secondFragment)
                                .commit();
                        return true;
                    case R.id.more:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout,thirdFragment)
                                .commit();
                        return true;

                }
                Log.d("fragment",String.valueOf( item.getItemId()));
                return false;
            }
        });


    }




    private void setmainFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout,firstFragment)
                .commit();
    }

}