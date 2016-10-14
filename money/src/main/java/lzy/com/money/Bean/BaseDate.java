package lzy.com.money.Bean;// @author: lzy  time: 2016/09/02.

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import lzy.com.money.DateBase.DbHelper;

public abstract class BaseDate {

    public abstract String get(int num);

    public abstract void set(int num, String msg);
    public abstract String getInsertSql();
    public abstract String getUpdateSql();
    public abstract String getDeleteSql();
    public abstract String getSelectSql();
    public abstract String getTableSql();

    public abstract Object[] get();

    public abstract String[] getStrings();

    public  void insert(DbHelper helper){
        if (hasEmpty())
            return;
        SQLiteDatabase db=helper.getWritableDatabase();
        db.execSQL(getInsertSql(),get());
        db.close();
//        getWritableDatabase().execSQL("INSERT INTO bill_table VALUES (NULL, ?, ?, ?)", get());
    }

    public int update(DbHelper helper,ContentValues cv){
        if (isEmpty())
            return 0;
        SQLiteDatabase db=helper.getWritableDatabase();
        int result=db.update(getTableSql(), cv, getUpdateSql(), getStrings());
        db.close();
        return result;
    }
    public  int delete(DbHelper helper){
        if (isEmpty())
            return 0;
        SQLiteDatabase db=helper.getWritableDatabase();
        int result=db.delete(getTableSql(), getDeleteSql(), getStrings());
        db.close();
        return result;
    }

    public abstract <T extends BaseDate>List<T>  select(DbHelper helper);



    public abstract Boolean isEmpty();

    public Boolean hasEmpty() {
        for (String filedName : getFiledName()) {
            Object value = getFieldValueByName(filedName);
            if (!filedName.equals("id")&&(value == null || value.equals("")))
                return true;
        }
        return false;
    }

    protected abstract String[] getFiledName();

    protected abstract Object getFieldValueByName(String fieldName) ;

}
