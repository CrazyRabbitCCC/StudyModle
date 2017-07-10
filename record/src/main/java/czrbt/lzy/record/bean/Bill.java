package czrbt.lzy.record.bean;
// @author: lzy  time: 2016/10/28.


import android.database.Cursor;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import czrbt.lzy.record.data.BillColumns;

public class Bill {

    private boolean isSaved;
    private String type;
    private String mode;
    private String date;
    private int id;
    private String money;

    public static Bill getInstance(Cursor cursor) {
        Bill bill = new Bill();
        try {
            if (cursor != null) {
                bill.id = cursor.getInt(cursor.getColumnIndex(BillColumns._ID));
                bill.mode = cursor.getString(cursor.getColumnIndex(BillColumns.MODE));
                bill.money = cursor.getString(cursor.getColumnIndex(BillColumns.MONEY));
                bill.date = cursor.getString(cursor.getColumnIndex(BillColumns.DATE));
                bill.type = cursor.getString(cursor.getColumnIndex(BillColumns.TYPE));
                bill.isSaved = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(BillColumns.IS_SAVED)));
                return bill;
            }
            return null;
        } catch (RuntimeException e) {
            Logger.e("RuntimeException",e.getMessage());
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            Logger.e("Exception",e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static List<Bill> getBills(Cursor cursor) {
        List<Bill> bills = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            Bill bill;
            if ((bill = getInstance(cursor)) != null)
                bills.add(bill);
            while (cursor.moveToNext()) {
                if ((bill = getInstance(cursor)) != null)
                    bills.add(bill);
            }
        }
        return bills;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isSaved() {
        return isSaved;
    }

    public void setSaved(boolean saved) {
        isSaved = saved;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
