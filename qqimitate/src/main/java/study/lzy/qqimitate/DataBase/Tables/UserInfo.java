package study.lzy.qqimitate.DataBase.Tables;
// @author: lzy  time: 2016/09/23.


import android.content.ContentValues;

import java.util.ArrayList;
import java.util.List;

public class UserInfo extends BaseInfo {
    int id = 0;
    String name;
    String other;

    public UserInfo() {
    }

    public UserInfo(String name, String other) {
        this.name = name;
        this.other = other;
    }

    @Override
    protected String getDeleteSql() {
        String sql = "";
        if (id != 0)
            sql += " id=? AND";
        if (name != null)
            sql += " name=? AND";
        if (other != null)
            sql += " other=? AND";
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
        if (name != null)
            sql += " name=? AND";
        if (other != null)
            sql += " other=? AND";
        if (sql.length() == 0)
            return "";
        sql = sql.substring(0, sql.length() - 3);
        return sql;
    }

    @Override
    protected String getInsertSql() {
        String sql="";
        sql+="INSERT INTO "+getTableName()+" VALUES (NULL, ? , ?)";
        return sql;
    }


    @Override
    public String getTableName() {
        return "UserTable";
    }

    @Override
    protected String getCreateSql() {
        return " , name varchar(20)  NOT NULL,other varchar (20)";
    }

    @Override
    protected String[] getSelection() {
        List<String> list = new ArrayList<>();
        if (id != 0)
            list.add(id + "");
        if (name != null)
            list.add(name);
        if (other != null)
            list.add(other);
        if (list.isEmpty())
            return new String[0];
        return ListToString(list);
    }

    @Override
    protected String getSelectSql() {
        String sql = "where";
        if (id != 0)
            sql += " id=? AND";
        if (name != null)
            sql += " name=? AND";
        if (other != null)
            sql += " other=? AND";
        if (sql.length()==5)
            return "";
        return sql.substring(0,sql.length()-3);

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}
