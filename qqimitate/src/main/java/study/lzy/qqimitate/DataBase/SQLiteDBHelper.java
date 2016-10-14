package study.lzy.qqimitate.DataBase;
// @author: lzy  time: 2016/09/23.


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import study.lzy.qqimitate.DataBase.Tables.FriendInfo;
import study.lzy.qqimitate.DataBase.Tables.RecordInfo;
import study.lzy.qqimitate.DataBase.Tables.UserInfo;


public class SQLiteDBHelper extends SQLiteOpenHelper {
    public static final int VERSION = 1;

    private static final String DB_NAME = "wei_xin.db";
    public SQLiteDBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       new  UserInfo().CREATE_TABLE(db);
        new FriendInfo().CREATE_TABLE(db);
        new RecordInfo().CREATE_TABLE(db);
        new UserInfo("刘","otherInfo").INSERT(db);
        new UserInfo("李","otherInfo").INSERT(db);
        new UserInfo("张","otherInfo").INSERT(db);
        new UserInfo("王","otherInfo").INSERT(db);
        new UserInfo("赵","otherInfo").INSERT(db);
        new UserInfo("孙","otherInfo").INSERT(db);
//输出创建数据库的日志信息
        Log.i("SQLiteDBHelper", "create Database------------->");
        List<UserInfo> userInfoList=new ArrayList<>();
        Cursor cursor=new UserInfo().SELECT(db);
        while (cursor.moveToNext()){
            UserInfo user=new UserInfo();
            user.setId(cursor.getInt(0));
            user.setName(cursor.getString(1));
            user.setOther(cursor.getString(2));
            userInfoList.add(user);
        }
        for (int i=0;i<userInfoList.size()-1;i++){
            for (int j=i+1;j<userInfoList.size();j++){
                int id = userInfoList.get(i).getId();
                int id2 = userInfoList.get(j).getId();
                String name = userInfoList.get(i).getName();
                String name2 = userInfoList.get(j).getName();
                new FriendInfo(id,id2,"2016/08/08").INSERT(db);
                new RecordInfo(id,id2,"你好"+((i+1)*(j+1)*2)+"，我是"+name,"2016/08/08").INSERT(db);
                new RecordInfo(id2,id,"你好"+((i+1)*(j+1)*2+1)+"，我是"+name2,"2016/08/08").INSERT(db);
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
