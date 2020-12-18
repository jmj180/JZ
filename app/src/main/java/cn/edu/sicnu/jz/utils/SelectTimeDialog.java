package cn.edu.sicnu.jz.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.annotation.NonNull;

import java.util.Calendar;

import cn.edu.sicnu.jz.R;

public class SelectTimeDialog extends Dialog implements View.OnClickListener {
    DatePicker datePicker;
    Button ensureBtn,cancelBtn;

    public interface OnEnsureListener{
        public void onEnsure(String time,int year,int month,int day);
    }
    OnEnsureListener onEnsureListener;

    public void setOnEnsureListener(OnEnsureListener onEnsureListener) {
        this.onEnsureListener = onEnsureListener;
    }

    public SelectTimeDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_time);
        datePicker=findViewById(R.id.dialog_time_dp);
        hideDatePickerHeader();
        cancelBtn=findViewById(R.id.dialog_time_btn_cancel);
        ensureBtn=findViewById(R.id.dialog_time_btn_ensure);
        cancelBtn.setOnClickListener(this);
        ensureBtn.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dialog_time_btn_cancel:
                cancel();
                break;
            case R.id.dialog_time_btn_ensure:
                int year=datePicker.getYear();
                int month = datePicker.getMonth()+1;
                int dayOfMonth=datePicker.getDayOfMonth();
                String monthStr = String.valueOf(month);
                if(month<10){
                    monthStr="0"+month;
                }
                String dayStr = String.valueOf(dayOfMonth);
                if (dayOfMonth<10){
                    dayStr="0"+dayOfMonth;
                }
                String timeFormat=monthStr+"月"+dayStr+"日";
                if (onEnsureListener!=null) {
                    onEnsureListener.onEnsure(timeFormat,year,month,dayOfMonth);
                }
                cancel();
                break;
        }

    }
    //隐藏头布局

    private  void hideDatePickerHeader(){
        ViewGroup rootView=(ViewGroup) datePicker.getChildAt(0);
        if(rootView ==null){
            return;
        }
        View headerView =rootView.getChildAt(0);
        if(headerView == null){
            return;
        }

        //5.0+
//        int headerId=getContext().getResources().getIdentifier("day_picker_selector_layout","id","android");
//        Log.d("id", String.valueOf(headerView.getId()));
        int id=16908908;

        if (id==headerView.getId()) {
            headerView.setVisibility(View.GONE);
            ViewGroup.LayoutParams layoutParamsRoot=rootView.getLayoutParams();
            layoutParamsRoot.width=ViewGroup.LayoutParams.WRAP_CONTENT;
            rootView.setLayoutParams(layoutParamsRoot);

            ViewGroup animator=(ViewGroup)rootView.getChildAt(1);
            ViewGroup.LayoutParams layoutParamsAnimator=animator.getLayoutParams();
            layoutParamsAnimator.width=ViewGroup.LayoutParams.WRAP_CONTENT;
            animator.setLayoutParams(layoutParamsAnimator);

            View child = animator.getChildAt(0);
            ViewGroup.LayoutParams layoutParamsChild = child.getLayoutParams();
            layoutParamsChild.width=ViewGroup.LayoutParams.WRAP_CONTENT;
            child.setLayoutParams(layoutParamsChild);
            return;

        }
//        //6.0+
//        headerId = getContext().getResources().getIdentifier("data_picker_header","id","android");
//        if (headerId==headerView.getId()) {
//            headerView.setVisibility(View.GONE);
//        }
    }
}
