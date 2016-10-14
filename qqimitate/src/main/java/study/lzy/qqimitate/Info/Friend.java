package study.lzy.qqimitate.Info;
// @author: lzy  time: 2016/09/21.


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Friend implements Serializable {
    private int id;
    private User user;
    private String time;

    public Friend() {
    }

    public Friend(int id, User user,String time) {
        this.id = id;
        this.user = user;
        this.time=time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Friend)
            return equals((Friend) o);
        return super.equals(o);
    }


    public static List<Friend> getFriendTest(int id, List<User> users) {
        List<Friend> test = new ArrayList<>();
        for (int i = 0; i < users.size() - 1; i++) {
            for (int j = i + 1; j < users.size() ; j++)
                test.add(new Friend(i+id, users.get(i),"2016/08/08"));
        }
        return test;
    }
}
