package cn.edu.sicnu.jz.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cn.edu.sicnu.jz.adapter.AccountAdapter;
import cn.edu.sicnu.jz.utils.FloatUtils;

public class DBManager {
    private static SQLiteDatabase db;

    public static void initDB(Context context){
        DBOpenHelper helper = new DBOpenHelper(context);
        db=helper.getWritableDatabase();
    }

    //读取数据库中的数据，写入内存集合里
    public static List<TypeBean> getTypeList(int kind){
        List<TypeBean> list = new ArrayList<>();

        //读取数据
        String sql = "select * from typetb where kind = "+kind;
        Cursor cursor = db.rawQuery(sql,null);

        //循环读取游标内容，存储到对象中
        while (cursor.moveToNext()){
           String typename= cursor.getString(cursor.getColumnIndex("typename"));
            int imageId=cursor.getInt(cursor.getColumnIndex("imageId"));
            int sImageId=cursor.getInt(cursor.getColumnIndex("sImageId"));
            int kindl=cursor.getInt(cursor.getColumnIndex("kind"));
            int id=cursor.getInt(cursor.getColumnIndex("id"));
            TypeBean typeBean=new TypeBean(id,typename,imageId,sImageId,kind);
            list.add(typeBean);
        }
        return list;

    }
    //删除所有记录
    public static void deleteAllAccount(){
        String sql = "delete from accounttb";
        db.execSQL(sql);
    }
    //向记账表插入一条元素
    public  static  void insertToAccounttb(AccountBean bean){
        ContentValues values=new ContentValues();
        values.put("typename",bean.getTypename());
        values.put("sImageId",bean.getsImageId());
        values.put("beizhu",bean.getBeizhu());
        values.put("money",bean.getMoney());
        values.put("time",bean.getTime());
        values.put("year",bean.getYear());
        values.put("month",bean.getMonth());
        values.put("day",bean.getDay());
        values.put("kind",bean.getKind());
        db.insert("accounttb",null,values);

    }
    /**
     * 查询指定年份和月份的收入或者支出每一种类型的总钱数
     * */
    public static List<ChartItemBean>getChartListFromAccounttb(int year,int month,int kind){
        List<ChartItemBean>list = new ArrayList<>();
        float sumMoneyOneMonth = getSumMoneyOneMonth(year, month, kind);  //求出支出或者收入总钱数
        String sql = "select typename,sImageId,sum(money)as total from accounttb where year=? and month=? and kind=? group by typename " +
                "order by total desc";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "", kind + ""});
        while (cursor.moveToNext()) {
            int sImageId = cursor.getInt(cursor.getColumnIndex("sImageId"));
            String typename = cursor.getString(cursor.getColumnIndex("typename"));
            float total = cursor.getFloat(cursor.getColumnIndex("total"));
            //计算所占百分比  total /sumMonth
            float ratio = FloatUtils.div(total,sumMoneyOneMonth);
            ChartItemBean bean = new ChartItemBean(sImageId, typename, ratio, total);
            list.add(bean);
        }
        return list;
    }
    /**
     * 获取这个月当中某一天收入支出最大的金额，金额是多少
     * */
    public static float getMaxMoneyOneDayInMonth(int year,int month,int kind){
        String sql = "select sum(money) from accounttb where year=? and month=? and kind=? group by day order by sum(money) desc";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "", kind + ""});
        if (cursor.moveToFirst()) {
            float money = cursor.getFloat(cursor.getColumnIndex("sum(money)"));
            return money;
        }
        return 0;
    }

    /** 根据指定月份每一日收入或者支出的总钱数的集合*/
    public static List<BarChartItemBean>getSumMoneyOneDayInMonth(int year,int month,int kind){
        String sql = "select day,sum(money) from accounttb where year=? and month=? and kind=? group by day";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "", kind + ""});
        List<BarChartItemBean>list = new ArrayList<>();
        while (cursor.moveToNext()) {
            int day = cursor.getInt(cursor.getColumnIndex("day"));
            float smoney = cursor.getFloat(cursor.getColumnIndex("sum(money)"));
            BarChartItemBean itemBean = new BarChartItemBean(year, month, day, smoney);
            list.add(itemBean);
        }
        return list;
    }

    /** 统计某月份支出或者收入情况有多少条  收入-1   支出-0*/
    public static int getCountItemOneMonth(int year,int month,int kind){
        int total = 0;
        String sql = "select count(money) from accounttb where year=? and month=? and kind=?";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "", kind + ""});
        if (cursor.moveToFirst()) {
            int count = cursor.getInt(cursor.getColumnIndex("count(money)"));
            total = count;
        }
        return total;
    }

    //获取记账表中某一天的支出收入情况
    public  static List<AccountBean> getAccountListOneDayFromatAccounttb(int year,int month,int day){
        List<AccountBean>list=new ArrayList<>();
        String sql="select * from accounttb where year = ? and month = ? and day = ? order by id desc";
        Cursor cursor=db.rawQuery(sql,new String[]{year+"",month+"",day+""});
        //遍历符合要求的每一项数据
        while (cursor.moveToNext()){
            int id=cursor.getInt(cursor.getColumnIndex("id"));
            String typename=cursor.getString(cursor.getColumnIndex("typename"));
            String beizhu=cursor.getString(cursor.getColumnIndex("beizhu"));
            String time=cursor.getString(cursor.getColumnIndex("time"));
            int sImageId=cursor.getInt(cursor.getColumnIndex("sImageId"));
            int yearl=cursor.getInt(cursor.getColumnIndex("year"));
            int monthl=cursor.getInt(cursor.getColumnIndex("month"));
            int dayl=cursor.getInt(cursor.getColumnIndex("day"));
            int kind=cursor.getInt(cursor.getColumnIndex("kind"));
            float money=cursor.getFloat(cursor.getColumnIndex("money"));
            AccountBean accountBean=new AccountBean(id,typename,sImageId,beizhu,money,time,yearl,monthl,dayl,kind);
            list.add(accountBean);
        }
        return list;
    }
    //获取记账表中某一月的支出收入情况
    public static List<AccountBean>getAccountListOneMonthFromAccounttb(int year,int month,int kind){
        List<AccountBean>list = new ArrayList<>();
        String sql = "select * from accounttb where year=? and month=?  and kind = ? order by id desc";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "",kind+""});
        //遍历符合要求的每一行数据
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String typename = cursor.getString(cursor.getColumnIndex("typename"));
            String beizhu = cursor.getString(cursor.getColumnIndex("beizhu"));
            String time = cursor.getString(cursor.getColumnIndex("time"));
            int sImageId = cursor.getInt(cursor.getColumnIndex("sImageId"));
            int kindl = cursor.getInt(cursor.getColumnIndex("kind"));
            float money = cursor.getFloat(cursor.getColumnIndex("money"));
            int day = cursor.getInt(cursor.getColumnIndex("day"));
            AccountBean accountBean = new AccountBean(id, typename, sImageId, beizhu, money, time, year, month, day, kindl);
            list.add(accountBean);
        }
        return list;
    }
    //获取记账表所有的支出收入情况
    public  static List<AccountBean> getAccountListAllFromatAccounttb(int year,int month,int day){
        List<AccountBean>list=new ArrayList<>();
        String sql="select * from accounttb  order by id desc";
        Cursor cursor=db.rawQuery(sql,null);
        //遍历符合要求的每一项数据
        while (cursor.moveToNext()){
            int id=cursor.getInt(cursor.getColumnIndex("id"));
            String typename=cursor.getString(cursor.getColumnIndex("typename"));
            String beizhu=cursor.getString(cursor.getColumnIndex("beizhu"));
            String time=cursor.getString(cursor.getColumnIndex("time"));
            int sImageId=cursor.getInt(cursor.getColumnIndex("sImageId"));
            int yearl=cursor.getInt(cursor.getColumnIndex("year"));
            int monthl=cursor.getInt(cursor.getColumnIndex("month"));
            int dayl=cursor.getInt(cursor.getColumnIndex("day"));
            int kind=cursor.getInt(cursor.getColumnIndex("kind"));
            float money=cursor.getFloat(cursor.getColumnIndex("money"));
            AccountBean accountBean=new AccountBean(id,typename,sImageId,beizhu,money,time,yearl,monthl,dayl,kind);
            list.add(accountBean);
        }
        return list;
    }

    //获取一天的支出收入总金额
    public static float getSumMoneyOneDay(int year,int month,int day,int kind){
        float total=0.0f;
        String sql = "select sum(money) from accounttb where year =?and month =? and day = ? and kind="+kind;
        Cursor cursor=db.rawQuery(sql,new String[]{year+"",month+"",day+""});

        if(cursor.moveToFirst()){
            float money= cursor.getFloat(cursor.getColumnIndex("sum(money)"));
            total=money;
        }
        return total;
    }
//获取一个月的收入总金额
    public static float getSumMoneyOneMonth(int year,int month,int kind){
    float total = 0.0f;
    String sql = "select sum(money) from accounttb where year=? and month=? and kind=?";
    Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "", kind + ""});
    // 遍历
    if (cursor.moveToFirst()) {
        float money = cursor.getFloat(cursor.getColumnIndex("sum(money)"));
        total = money;
    }
    return total;
    }

    //获取一年的收入总金额
    public static float getSumMoneyOneYear(int year,int kind){
        float total=0.0f;
        String sql = "select sum(money) from accounttb where year = ?   and kind="+kind;
        Cursor cursor=db.rawQuery(sql,new String[]{year+""});
        if(cursor.moveToFirst()){
            float money= cursor.getFloat(cursor.getColumnIndex("sum(money)"));
            total=money;
        }
        return total;
    }


    //根据id删除数据
    public  static  int deleteItemFromAccounttbById(int id){
        int i=db.delete("accounttb","id=?",new String[]{id + ""});
        return i;
    }

    //根据备注模糊查找数据
    public  static  List<AccountBean>getAccountListByRemarkFromAccounttb(String beizhu){
        List<AccountBean>list=new ArrayList<>();
        String sql="select *from accounttb where beizhu like '%"+beizhu+"%'";
        Cursor cursor= db.rawQuery(sql,null);
        while (cursor.moveToNext()){
            int id=cursor.getInt(cursor.getColumnIndex("id"));
            String typename=cursor.getString(cursor.getColumnIndex("typename"));
            String beizhul=cursor.getString(cursor.getColumnIndex("beizhu"));
            String time=cursor.getString(cursor.getColumnIndex("time"));
            int sImageId=cursor.getInt(cursor.getColumnIndex("sImageId"));
            int yearl=cursor.getInt(cursor.getColumnIndex("year"));
            int monthl=cursor.getInt(cursor.getColumnIndex("month"));
            int dayl=cursor.getInt(cursor.getColumnIndex("day"));
            int kind=cursor.getInt(cursor.getColumnIndex("kind"));
            float money=cursor.getFloat(cursor.getColumnIndex("money"));
            AccountBean accountBean=new AccountBean(id,typename,sImageId,beizhul,money,time,yearl,monthl,dayl,kind);
            list.add(accountBean);
        }
        return list;
    }


    //获取年份信息
    public static List<Integer>getYearListFromAccounttb(){
        List<Integer>list = new ArrayList<>();
        String sql="select distinct(year) from Accounttb order by year asc";
        Cursor cursor=db.rawQuery(sql,null);
       while (cursor.moveToNext()){
           int year = cursor.getInt(cursor.getColumnIndex("year"));
           list.add(year);
       }
        return list;
    }


}
