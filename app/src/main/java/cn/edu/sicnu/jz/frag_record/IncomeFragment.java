package cn.edu.sicnu.jz.frag_record;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import java.util.List;

import cn.edu.sicnu.jz.R;
import cn.edu.sicnu.jz.db.DBManager;
import cn.edu.sicnu.jz.db.TypeBean;


public class IncomeFragment extends BaseRecordFragment {


    @Override
    public void loadDataToGV() {
        super.loadDataToGV();
        List<TypeBean> inlist = DBManager.getTypeList(1);
        typeList.addAll(inlist);
        adapter.notifyDataSetChanged();
        typeTV.setText("餐饮");
    }

    @Override
    public void saveAccountToDB() {
    accountBean.setKind(1);
    DBManager.insertToAccounttb(accountBean);
    }
}