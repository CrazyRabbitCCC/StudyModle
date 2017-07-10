package udacity.lzy.test.bean;
// @author: lzy  time: 2016/10/28.


import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import udacity.lzy.test.data.SaveColumns;


public class Save implements Parcelable {
    private int id;
    private String path;
    private boolean isSaved;
    private boolean isChange;
    private String type;
    private String date;
    private String title;

    public Save getClone(){
        Save save=new Save();
        save.isSaved=isSaved;
        save.isChange=isChange;
        save.date=date;
        save.title=title;
        save.type=type;
        save.path=path;
        save.id=id;
        return save;
    }

    private Save() {
    }

    public static Save getInstance(){
        Save save= new Save();
        save.isSaved=false;
        save.isChange=false;
        save.date="";
        save.title="";
        save.type="text";
        save.path="null";
        return save;
    }

    public static Save getInstance(Cursor cursor) {
        Save save = new Save();
        try {
            if (cursor != null) {
                save.id = cursor.getInt(cursor.getColumnIndex(SaveColumns._ID));
                save.path = cursor.getString(cursor.getColumnIndex(SaveColumns.PATH));
                save.date = cursor.getString(cursor.getColumnIndex(SaveColumns.DATE));
                save.type = cursor.getString(cursor.getColumnIndex(SaveColumns.TYPE));
                save.title = cursor.getString(cursor.getColumnIndex(SaveColumns.TITLE));
                save.isSaved = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(SaveColumns.IS_SAVED)));
                save.isChange = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(SaveColumns.IS_CHANGED)));
                return save;
            }
            return null;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Save> getSaves(Cursor cursor) {
        List<Save> saves = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            Save save;
            if ((save = getInstance(cursor)) != null)
                saves.add(save);
            while (cursor.moveToNext()) {
                if ((save = getInstance(cursor)) != null)
                    saves.add(save);
            }
        }
        return saves;
    }

    public ContentValues getValues(){
        ContentValues values=new ContentValues();
        values.put(SaveColumns.PATH,path);
        values.put(SaveColumns.IS_SAVED,isSaved);
        values.put(SaveColumns.IS_CHANGED,isChange);
        values.put(SaveColumns.TYPE,type);
        values.put(SaveColumns.DATE,date);
        values.put(SaveColumns.TITLE,title);
        return values;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isChange() {
        return isChange;
    }

    public void setChange(boolean change) {
        isChange = change;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Save) {
            Save save = (Save) obj;
            return save.id == id && save.path.equals(path) &&
                    save.isSaved == isSaved &&
                    save.type.equals(type) &&
                    save.date.equals(date) &&
                    save.isChange==isChange&&
                    save.title.equals(title);
        }
        return super.equals(obj);
    }

    public void update(Save save) {
        path = save.path;
        isSaved = save.isSaved;
        isChange = save.isChange;
        type = save.type;
        date = save.date;
        title = save.title;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(path);
        parcel.writeString(String.valueOf(isSaved));
        parcel.writeString(String.valueOf(isChange));
        parcel.writeString(type);
        parcel.writeString(date);
        parcel.writeString(title);
    }

    public static final Creator<Save> CREATOR = new Creator<Save>() {

        @Override
        public Save createFromParcel(Parcel source) {
            // 必须按成员变量声明的顺序读取数据，不然会出现获取数据出错
            Save s = new Save();
            s.setId(source.readInt());
            s.setPath(source.readString());
            s.setSaved(Boolean.parseBoolean(source.readString()));
            s.setChange(Boolean.parseBoolean(source.readString()));
            s.setType(source.readString());
            s.setDate(source.readString());
            s.setTitle(source.readString());
            return s;
        }

        @Override
        public Save[] newArray(int size) {
            return new Save[size];
        }
    };
}
