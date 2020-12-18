package cn.edu.sicnu.jz.frag_sec;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import cn.edu.sicnu.jz.AboutActivity;
import cn.edu.sicnu.jz.R;
import cn.edu.sicnu.jz.SearchActivity;


public class moreFragment extends Fragment {
    Button aboutBtn,settingBtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_more, container, false);
        initview(view);
        onClick();
        return view;
    }

    private void onClick() {
        aboutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it1 = new Intent(getContext(), AboutActivity.class);
                startActivity(it1);
            }
        });
        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it1 = new Intent(getContext(), AboutActivity.class);
                startActivity(it1);
            }
        });

    }

    private void initview(View view) {
        aboutBtn=view.findViewById(R.id.dialog_more_btn_about);
        settingBtn=view.findViewById(R.id.dialog_more_btn_setting);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}