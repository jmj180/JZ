package cn.edu.sicnu.jz;

import android.app.Application;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cn.edu.sicnu.jz.db.DBManager;
import cn.edu.sicnu.jz.db.TypeBean;

public class UniteApp  extends Application {
    public  void  onCreate(){
        super.onCreate();
        DBManager.initDB(getApplicationContext());

    }


}
