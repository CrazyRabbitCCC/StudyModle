package czrbt.lzy.record.bean;
// @author: lzy  time: 2016/10/28.


import android.database.Cursor;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import czrbt.lzy.record.data.UserColumns;

public class User {
    private String account;
    private   String password;
    private boolean isRemember;
    private String lastImei;
    private String lastDate;

    public static User getInstance(Cursor cursor) {
        User user = new User();
        try {
            if (cursor != null) {
                user.account = cursor.getString(cursor.getColumnIndex(UserColumns.ACCOUNT));
                user.password = cursor.getString(cursor.getColumnIndex(UserColumns.PASSWORD));
                user.lastImei = cursor.getString(cursor.getColumnIndex(UserColumns.LAST_IMEI));
                user.lastDate = cursor.getString(cursor.getColumnIndex(UserColumns.LAST_DATE));
                user.isRemember = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(UserColumns.IS_REMEMBER)));
                return user;
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

    public static List<User> getUsers(Cursor cursor) {
        List<User> users = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            User user;
            if ((user = getInstance(cursor)) != null)
                users.add(user);
            while (cursor.moveToNext()) {
                if ((user = getInstance(cursor)) != null)
                    users.add(user);
            }
        }
        return users;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public boolean isRemember() {
        return isRemember;
    }

    public void setRemember(boolean remember) {
        isRemember = remember;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public String getLastImei() {
        return lastImei;
    }

    public void setLastImei(String lastImei) {
        this.lastImei = lastImei;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
