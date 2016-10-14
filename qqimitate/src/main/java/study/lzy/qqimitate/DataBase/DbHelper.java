package study.lzy.qqimitate.DataBase;
// @author: lzy  time: 2016/09/26.


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteAbortException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import study.lzy.qqimitate.DataBase.Tables.BaseInfo;
import study.lzy.qqimitate.DataBase.Tables.FriendInfo;
import study.lzy.qqimitate.DataBase.Tables.RecordInfo;
import study.lzy.qqimitate.DataBase.Tables.UserInfo;
import study.lzy.qqimitate.Info.ChatRecord;
import study.lzy.qqimitate.Info.Friend;
import study.lzy.qqimitate.Info.Record;
import study.lzy.qqimitate.Info.User;

import static android.R.attr.id;

public class DbHelper {
    private Context mContext;
    private SQLiteDBHelper dbHelper;
    private Gson gson;

    public DbHelper(Context context) {
        mContext = context;
        dbHelper=new SQLiteDBHelper(context);
        gson=new GsonBuilder().create();
    }

    public synchronized String[] getResult(String[] msg){
        String[] result =new String[2];
        if (msg==null)
            return null;
        if (TextUtils.isEmpty(msg[0]))
            return null;
        Map<String,Object> map=new HashMap<>();
        map=gson.fromJson(msg[0], map.getClass());
        String  transCode = (String) map.get("TransCode");
        if ( transCode ==null)
            return null;
        switch (transCode){
            case "UserInfo":
                return getUserInfo(msg[1]);
            case "ChatRecord":
                return getChatRecord(msg[1]);
            case "Friend":
                return getFriend(msg[1]);


            default:
                return null;
        }
    }



    private String[] getUserInfo(String message){
        Map<String,Object> map=new HashMap<>();
        map=gson.fromJson(message, map.getClass());
        String[] result =new String[2];
        Map<String,Object> header=new HashMap<>();
        Map<String,Object> body=new HashMap<>();

        try {
            Double did = (Double) map.get("UserId");
            int id=did.intValue();
            String name= (String) map.get("UserName");
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            UserInfo userInfo = new UserInfo();
            if (id!=0)
            userInfo.setId(id);
            if (!TextUtils.isEmpty(name))
                userInfo.setName(name);
            Cursor select = userInfo.SELECT(db);
            if (select.moveToFirst()){
                User user=new User();
                user.setUserId(select.getInt(0)+"");
                user.setUserName(select.getString(1));
                user.setOther(select.getString(2));
                body.put("User",user);
                header.put("Message","SUCCESS");
                header.put("ResultCode","0000");
            }
            else {
                header.put("Message","ERROR,没有找到该用户的信息");
                header.put("ResultCode","9999");
            }
            select.close();
            db.close();

        }catch (SQLiteAbortException e1){
            header.put("Message","ERROR");
            header.put("ResultCode","9999");

        }catch (Exception e2){
            header.put("Message","ERROR");
            header.put("ResultCode","9999");
        }
        result[0]=gson.toJson(header);
        result[1]=gson.toJson(body);

        return result;
    }

    private String[] getChatRecord(String message){
        Map<String,Object> map=new HashMap<>();
        map=gson.fromJson(message, map.getClass());
        String[] result =new String[2];
        Map<String,Object> header=new HashMap<>();
        Map<String,Object> body=new HashMap<>();

        try {
            //得到id
            Double did = (Double) map.get("UserId");
            int id=did.intValue();
            //获取数据库
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            //获取用户
            List<Record> recordList=new ArrayList<>();
            if (id!=0){
                Cursor cursor1 = RecordInfo.SELECT(id,db);
                while (cursor1.moveToNext()){
                    RecordInfo recordInfo=new RecordInfo();
                    recordInfo.setId(cursor1.getInt(0));
                    recordInfo.setUserId(cursor1.getInt(1));
                    recordInfo.setUserId2(cursor1.getInt(2));
                    recordInfo.setMessage(cursor1.getString(3));
                    recordInfo.setTime(cursor1.getString(4));

                    User chatWith=getUserById( recordInfo.getUserId2(),db);
                    User user=getUserById( recordInfo.getUserId(),db);
                    Record record=new Record(recordInfo.getId(),recordInfo.getMessage(),user,chatWith,recordInfo.getTime());
                    recordList.add(record);
                    header.put("Message","SUCCESS");
                header.put("ResultCode","0000");
                }
                cursor1.close();
                db.close();
            }
            else {
                header.put("Message","ERROR,参数错误");
                header.put("ResultCode","9999");   
            }
            body.put("RecordList",recordList);
        }catch (SQLiteAbortException e1){
            header.put("Message","ERROR");
            header.put("ResultCode","9999");

        }catch (Exception e2){
            header.put("Message","ERROR");
            header.put("ResultCode","9999");
        }
        result[0]=gson.toJson(header);
        result[1]=gson.toJson(body);

        return result;

    }

    private String[] getFriend(String message) {
        Map<String,Object> map=new HashMap<>();
        map=gson.fromJson(message, map.getClass());
        String[] result =new String[2];
        Map<String,Object> header=new HashMap<>();
        Map<String,Object> body=new HashMap<>();
        
        try {
            Double did = (Double) map.get("UserId");
            int id=did.intValue();
            //获取数据库
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            //获取用户
            List<Friend> friendList=new ArrayList<>();
            if (id!=0){
                Cursor cursor1 = FriendInfo.SELECT(id,db);
                while (cursor1.moveToNext()){
                    FriendInfo friendInfo=new FriendInfo();
                    friendInfo.setId(cursor1.getInt(0));
                    friendInfo.setUserId(cursor1.getInt(1));
                    friendInfo.setUserId2(cursor1.getInt(2));
                    friendInfo.setTime(cursor1.getString(3));

                    User user;
                    if (id==friendInfo.getUserId()){
                        user = getUserById(friendInfo.getUserId2(),db);
                    }else
                    {
                        user=getUserById(friendInfo.getUserId(),db);
                    }
                    Friend friend = new Friend(friendInfo.getId(),user,friendInfo.getTime());

                    friendList.add(friend);
                    header.put("Message","SUCCESS");
                    header.put("ResultCode","0000");
                }
                cursor1.close();
                db.close();
            }
            else {
                header.put("Message","ERROR,参数错误");
                header.put("ResultCode","9999");
            }
            body.put("FriendList",friendList);

        }catch (SQLiteAbortException e1){
            header.put("Message","ERROR");
            header.put("ResultCode","9999");

        }catch (Exception e2){
            header.put("Message","ERROR");
            header.put("ResultCode","9999");
        }
        result[0]=gson.toJson(header);
        result[1]=gson.toJson(body);

        return result;

    }
    
    private User getUserById(int id, SQLiteDatabase db) {
        UserInfo userInfo = new UserInfo();
        if (id!=0)
            userInfo.setId(id);
        Cursor select = userInfo.SELECT(db);
        User user=new User();
        if (select.moveToFirst()){
            user.setUserId(select.getInt(0)+"");
            user.setUserName(select.getString(1));
            user.setOther(select.getString(2));
        }
        select.close();
        return user;
    }
}
