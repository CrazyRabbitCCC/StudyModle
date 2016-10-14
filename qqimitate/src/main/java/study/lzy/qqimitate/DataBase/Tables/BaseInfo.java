package study.lzy.qqimitate.DataBase.Tables;
// @author: lzy  time: 2016/09/23.


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Field;
import java.util.List;

public abstract class BaseInfo {

    public  void CREATE_TABLE(SQLiteDatabase db){
        String sql="";
        sql="create table "+getTableName()+"(id INTEGER PRIMARY KEY AUTOINCREMENT " +getCreateSql()+ ")";
        db.execSQL(sql);

    }

    public Cursor SELECT(SQLiteDatabase db){
        String sql = "SELECT * FROM "+getTableName()+" "+getSelectSql();
        return db.rawQuery(sql, getSelection());
    }
//    public void  UPDATE(SQLiteDatabase db,String set,String...sets){
//        ContentValues values=new ContentValues();
//        String  sql="update "+getTableName()+" "+set+getUpdateSql();
////        update t_users set username=?,pass=? where" + " id=?
//        String[] selection = getSelection();
//
//        String update[]=new String[selection.length+sets.length];
//
//        System.arraycopy(sets, 0, update, 0, sets.length);
//        System.arraycopy(selection, 0, update, sets.length, selection.length);
//         db.execSQL(sql,update);
//    }
    public int  UPDATE(SQLiteDatabase db,ContentValues values){
        return db.update(getTableName(),values,getUpdateSql(),getSelection());
    }

    public void INSERT(SQLiteDatabase db){
         db.execSQL(getInsertSql(),getSelection());
    }

    public int  DELETE(SQLiteDatabase db){
        return db.delete(getTableName(),getDeleteSql(),getSelection());
    }

    protected abstract String getDeleteSql();
    protected abstract String getUpdateSql();
    protected abstract String getInsertSql();
    protected abstract String getTableName();
    protected abstract String getCreateSql();
    protected abstract String[] getSelection();
    protected abstract String getSelectSql();

    protected String[] ListToString(List<String> list){
        String[] strings=new String[list.size()];
        for (int i=0;i<list.size();i++)
            strings[i]=list.get(i);
        return strings;

    }
    protected   String[] getFiledName(Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            fieldNames[i] = fields[i].getName();
        }
        return fieldNames;
    }
    interface DBHelper{
        String getDeleteSql();
        String getUpdateSql();
        ContentValues getInsertValues();
        String getTableName();
        String getCreateSql();
        String[] getSelection();
        String getSelectSql();

    }
}
