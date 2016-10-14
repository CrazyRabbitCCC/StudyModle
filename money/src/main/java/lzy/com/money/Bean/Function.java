package lzy.com.money.Bean;// @author: lzy  time: 2016/09/02.

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import lzy.com.money.DateBase.DbHelper;

public class Function  extends BaseDate{
    int id;
    String function;

    public Function(){
    }

    public Function(String function) {
        this.function = function;
    }

    public Object[] get(){
        return new Object[]{function};

    }

    public String get(int num){
        String getString;
        switch (num)
        {
            case 0:
                getString=""+id;
                break;
            case 1:
                getString=function;
                break;
            default:
                getString="ERROR";
                break;
        }

        return getString;
    }

    public void set(String function) {
        this.function = function;

    }

    public void set(int num,String msg){
        switch (num)
        {
            case 0:
                id=Integer.parseInt(msg);
                break;
            case 1:
                function=msg;
                break;
            default:
                break;
        }

    }

    public static String getCreateSql() {
        return "create table function_table(id int PRIMARY KEY AUTOINCREMENT,function varchar(20))";
    }

    @Override
    public String getInsertSql() {
        return "INSERT INTO function_table VALUES (NULL, ?)";
    }

    @Override
    public String getUpdateSql() {
        String sql="";
        if (!isEmpty()) {
            if (id != 0)
                sql += " id = ?,";
            if (function != null && !function.isEmpty())
                sql += " function = ?,";
        }
        if (sql.isEmpty())
            return null;
        return sql.substring(0,sql.length()-1);
    }

    @Override
    public String getDeleteSql() {
        String sql="";
        if (!isEmpty()) {
            if (id != 0)
                sql += " id = ?,";
            if (function != null && !function.isEmpty())
                sql += " function = ?,";
        }
        if (sql.isEmpty())
            return null;
        return sql.substring(0,sql.length()-1);
    }

    @Override
    public String getSelectSql() {
        String sql="SELECT * FROM function_table";
        if (!isEmpty())
            sql+=" where ";
        if (id!=0)
            sql+=" id = ?,";
        if (function!=null&&!function.isEmpty())
            sql+=" function = ?,";
        if ("SELECT * FROM function_table".equals(sql))
            return sql;
        return sql.substring(0,sql.length()-1);
    }

    @Override
    public String getTableSql() {
        return "function_table";
    }


    public String[] getStrings(){
        ArrayList<String> string=new ArrayList<>();
        if (id!=0)
            string.add(""+id);
        if (function!=null&&!function.isEmpty())
            string.add(function);
        if (string.isEmpty())
            return null;
        String [] s=new String[string.size()] ;

        for (int i=0;i<string.size();i++)
            s[i]=string.get(i);

        return s;
    }

    @Override
    public  List<Function> select(DbHelper helper) {
        SQLiteDatabase db=helper.getWritableDatabase();
        List<Function> functionList=new ArrayList<>();
        Cursor result=db.rawQuery(getSelectSql(),getStrings());
        if (result.moveToFirst()) {
            Function function = new Function();
            function.set(0, result.getString(0));
            function.set(1, result.getString(1));

            functionList.add(function);
            while (result.moveToNext()) {
                Function function1 = new Function();
                function1.set(0, result.getString(0));
                function1.set(1, result.getString(1));
                functionList.add(function1);
            }
        }
        db.close();
        return functionList;
    }

    protected String[] getFiledName() {
        Field[] fields = getClass().getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            fieldNames[i] = fields[i].getName();
        }
        return fieldNames;
    }

    protected  Object getFieldValueByName(String fieldName) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = getClass().getMethod(getter);
            return method.invoke(this);
        } catch (Exception e) {
            System.out.println("属性不存在");
            return null;
        }
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public Boolean isEmpty() {
        if (id!=0)
            return false;
        if (!TextUtils.isEmpty(function))
            return false;
        return true;
    }

}
