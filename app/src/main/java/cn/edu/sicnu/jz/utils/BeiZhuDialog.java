package cn.edu.sicnu.jz.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import cn.edu.sicnu.jz.R;

public class BeiZhuDialog extends Dialog implements View.OnClickListener {
    EditText et;
    Button cancelBtn,ensureBtn;
    OnEnsureListener onEnsureListener;
//设定回调接口的方法
    public void setOnEnsureListener(OnEnsureListener onEnsureListener) {
        this.onEnsureListener = onEnsureListener;
    }

    public BeiZhuDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public void onClick(View v) {
    switch (v.getId()){
        case R.id.dialog_beizhu_btn_cancel:
            cancel();
            break;
        case R.id.dialog_beizhu_btn_ensure:
            if(onEnsureListener!=null){
                onEnsureListener.onEnsure();
            }
            break;
    }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_beizhu);
        et=findViewById(R.id.dialog_beizhu_et);
        cancelBtn=findViewById(R.id.dialog_beizhu_btn_cancel);
        ensureBtn=findViewById(R.id.dialog_beizhu_btn_ensure);
        cancelBtn.setOnClickListener(this);
        ensureBtn.setOnClickListener(this);

    }
    public  interface  OnEnsureListener{
        public  void onEnsure();
    }
    //获取输入数据
    public  String getEditext(){
        return et.getText().toString().trim();
    }

    //设置dialog尺寸
    public void setDialogSize(){
        Window window=getWindow();
        WindowManager.LayoutParams wlp=window.getAttributes();
        Display d = window.getWindowManager().getDefaultDisplay();
        wlp.width=(int)(d.getWidth());
        wlp.gravity= Gravity.BOTTOM;
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setAttributes(wlp);
        handler.sendEmptyMessageDelayed(1,100);

    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInput(0,inputMethodManager.HIDE_NOT_ALWAYS);
        }
    };
}
