package lzy.com.money.Bean;// @author: lzy  time: 2016/09/02.

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import lzy.com.money.DateBase.DbHelper;

public class Bill extends BaseDate {

    int id;
    String function;
    String price;
    String time;

    public Bill() {
    }

    public Bill(String function, String price, String time) {
        this.function = function;
        this.price = price;
        this.time = time;
    }

    public Object[] get() {
        return new Object[]{function, price, time};
    }

    public String get(int num) {
        String getString;
        switch (num) {
            case 0:
                getString = "" + id;
                break;
            case 1:
                getString = function;
                break;
            case 2:
                getString = price;
                break;
            case 3:
                getString = time;
                break;
            default:
                getString = "ERROR";
                break;
        }

        return getString;
    }

    public void set(String function, String price, String time) {
        this.function = function;
        this.price = price;
        this.time = time;
    }

    public void set(int num, String msg) {
        switch (num) {
            case 0:
                id = Integer.parseInt(msg);
                break;
            case 1:
                function = msg;
                break;
            case 2:
                price = msg;
                break;
            case 3:
                time = msg;
                break;
            default:
                break;
        }

    }

    public static String getCreateSql() {
        return "create table bill_table(id int PRIMARY KEY AUTOINCREMENT,function varchar(20),price varchar(20),stime varchar(20))";
    }

    @Override
    public String getInsertSql() {
        return "INSERT INTO bill_table VALUES (NULL, ?, ?, ?)";
    }

    @Override
    public String getUpdateSql() {
        String sql = "";
        if (!isEmpty()) {
            if (id != 0)
                sql += " id = ?,";
            if (function != null && !function.isEmpty())
                sql += " function = ?,";
            if (price != null && !price.isEmpty())
                sql += " price = ?,";
            if (time != null && !time.isEmpty())
                sql += " stime = ?,";
        }
        if (sql.isEmpty())
            return null;
        return sql.substring(0, sql.length() - 1);
    }

    @Override
    public String getDeleteSql() {
        String sql = "";
        if (!isEmpty()) {
            if (id != 0)
                sql += " id = ?,";
            if (function != null && !function.isEmpty())
                sql += " function = ?,";
            if (price != null && !price.isEmpty())
                sql += " price = ?,";
            if (time != null && !time.isEmpty())
                sql += " stime = ?,";
        }
        if (sql.isEmpty())
            return null;
        return sql.substring(0, sql.length() - 1);
    }

    @Override
    public String getSelectSql() {
        String sql = "SELECT * FROM bill_table";
        if (!isEmpty()) {
            sql += " where ";
            if (id != 0)
                sql += " id = ?,";
            if (function != null && !function.isEmpty())
                sql += " function = ?,";
            if (price != null && !price.isEmpty())
                sql += " price = ?,";
            if (time != null && !time.isEmpty())
                sql += " stime = ?,";
        }
        if (sql.equals("SELECT * FROM bill_table"))
            return sql;
        return sql.substring(0, sql.length() - 1);
    }

    @Override
    public String getTableSql() {
        return "bill_table";
    }


    public String[] getStrings() {
        int length = 0;
        List<String> string = new ArrayList<>();
        if (id != 0)
            string.add("" + id);
        if (function != null && !function.isEmpty())
            string.add(function);
        if (price != null && !price.isEmpty())
            string.add(price);
        if (time != null && !time.isEmpty())
            string.add(time);
        if (string.isEmpty())
            return null;
        String[] s = new String[string.size()];
        for (int i = 0; i < string.size(); i++) {
            s[i] = string.get(i);
        }
        return s;
    }

    @Override
    public List<Bill> select(DbHelper helper) {
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor result = db.rawQuery(getSelectSql(), getStrings());
        List<Bill> billList = new ArrayList<>();
        Bill f = new Bill();
        if (result.getCount() > 0)
            if (result.moveToFirst()) {
                Bill bill = new Bill();
                for (int i = 0; i < 4; i++)
                    bill.set(i, result.getString(i));
                billList.add(bill);
                while (result.moveToNext()) {
                    Bill bill1 = new Bill();
                    for (int i = 0; i < 4; i++)
                        bill1.set(i, result.getString(i));
                    billList.add(bill1);
                }
            }
        db.close();
        return billList;
    }

    public float getFloat() {
        if (price == null || price.isEmpty()) {
            return 0;
        }
        String s = price.substring(0, price.length() - 2);
        return Float.parseFloat(s);
    }

    protected String[] getFiledName() {
        Field[] fields = getClass().getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            fieldNames[i] = fields[i].getName();
        }
        return fieldNames;
    }

    protected Object getFieldValueByName(String fieldName) {
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    @Override
    public Boolean isEmpty() {
        if (id != 0)
            return false;
        if (!TextUtils.isEmpty(function))
            return false;
        if (!TextUtils.isEmpty(price))
            return false;
        if (!TextUtils.isEmpty(time))
            return false;
        return true;
    }
}

