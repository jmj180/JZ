package cn.edu.sicnu.jz.frag_record;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cn.edu.sicnu.jz.R;
import cn.edu.sicnu.jz.db.DBManager;
import cn.edu.sicnu.jz.db.TypeBean;


public class OutcomeFragment extends BaseRecordFragment {



//重写
    @Override
    public void loadDataToGV() {
        super.loadDataToGV();
        List<TypeBean>outlist = DBManager.getTypeList(0);
        typeList.addAll(outlist);
        adapter.notifyDataSetChanged();
        typeTV.setText("餐饮");

    }

    @Override
    public void saveAccountToDB() {
        accountBean.setKind(0);
        DBManager.insertToAccounttb(accountBean);

    }
}