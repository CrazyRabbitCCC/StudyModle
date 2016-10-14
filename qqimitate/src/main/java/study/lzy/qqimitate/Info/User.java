package study.lzy.qqimitate.Info;
// @author: lzy  time: 2016/09/21.


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    private String UserId;
    private String UserName;
    private String Other;

    public User() {
    }

    public User(String userId, String userName) {
        UserId = userId;
        UserName = userName;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getOther() {
        return Other;
    }

    public void setOther(String other) {
        Other = other;
    }

    public  static User get(String userId, List<User> users){
        for (User user:users)
            if (user.UserId.equals(userId))
                return user;

        return null;
    }
    public static List<User> getUserTest(int id,String name){
        List<User> test=new ArrayList<>();
        for (int i=id;i<10+id;i++){
            test.add(new User(String.valueOf(i),name+i));
        }
        return test;
    }
}
