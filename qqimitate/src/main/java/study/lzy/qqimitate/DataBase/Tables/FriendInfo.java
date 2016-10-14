package study.lzy.qqimitate.DataBase.Tables;
// @author: lzy  time: 2016/09/26.


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import study.lzy.qqimitate.Info.Friend;

public class FriendInfo extends BaseInfo {

    private  int id=0;
    private int UserId=0;
    private int UserId2=0;
    private String time;

    public FriendInfo() {
    }

    public FriendInfo( int userId2, int userId,String time) {
        UserId2 = userId2;
        UserId = userId;
        this.time=time;
    }

    @Override
    protected String getDeleteSql() {
        String sql = "";
        if (id != 0)
            sql += " id=? AND";
        if (UserId != 0)
            sql += " UserId=? AND";
        if (UserId2 != 0)
            sql += " UserId2=? AND";
        if (time!=null)
            sql +=" time=? AND";
        if (sql.length() == 0)
            return "";
        sql = sql.substring(0, sql.length() - 3);
        return sql;
    }

    @Override
    protected String getUpdateSql() {
        String sql = "";
        if (id != 0)
            sql += " id=? AND";
        if (UserId != 0)
            sql += " UserId=? AND";
        if (UserId2 != 0)
            sql += " UserId2=? AND";
        if (time!=null)
            sql +=" time=? AND";
        if (sql.length() == 0)
            return "";
        sql = sql.substring(0, sql.length() - 3);
        return sql;
    }

    @Override
    protected String getInsertSql() {
        String sql="";
        sql+="INSERT INTO "+getTableName()+" VALUES (NULL, ? , ?, ?)";
        return sql;
    }


    @Override
    protected String getTableName() {
        return "Friend";
    }

    @Override
    protected String getCreateSql() {
        return " , UserId  integer NOT NULL,UserId2 integer NOT NULL,time varchar(20) ";
    }

    @Override
    protected String[] getSelection() {
        List<String> list = new ArrayList<>();
        if (id != 0)
            list.add(id + "");
        if (UserId != 0)
            list.add(UserId + "");
        if (UserId2 != 0)
            list.add(UserId2 + "");
        if (time != null)
            list.add(time);
        if (list.isEmpty())
            return new String[0];
        return ListToString(list);
    }

    @Override
    protected String getSelectSql() {
        String sql = "where";
        if (id != 0)
            sql += " id=? AND";
        if (UserId != 0)
            sql += " UserId=? AND";
        if (UserId2 != 0)
            sql += " UserId2=? AND";
        if (time!=null)
            sql +=" time=? AND";
        if (sql.length() == 0)
            return "";
        if (sql.length()==5)
            return "";
        return sql.substring(0,sql.length()-3);
    }

    public static Cursor SELECT(int id,SQLiteDatabase db){
        String sql = "SELECT * FROM Friend where UserId=? OR UserId2=?";
        String[] strings=new String[2];
        strings[0]=id+"";
        strings[1]=id+"";
        return db.rawQuery(sql,strings);
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getUserId2() {
        return UserId2;
    }

    public void setUserId2(int userId2) {
        UserId2 = userId2;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }
}
