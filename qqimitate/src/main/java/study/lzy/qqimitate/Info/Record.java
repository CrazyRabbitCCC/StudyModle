package study.lzy.qqimitate.Info;
// @author: lzy  time: 2016/09/21.


import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Record implements Serializable {
    private int id;
    private String time;
    private String message;
    private User chat, chatWith;
    private static final SimpleDateFormat format = new SimpleDateFormat("MM/dd HH:mm");

    public Record(int id, String message, User chat, User chatWith) {
        this(id, message, chat, chatWith, format.format(new Date()));
    }

    public Record(int id, String message, User chat, User chatWith, String time) {
        this.id = id;
        this.time = time;
        this.message = message;
        this.chat = chat;
        this.chatWith = chatWith;
    }

    public User getChat() {
        return chat;
    }

    public void setChat(User chat) {
        this.chat = chat;
    }

    public User getChatWith() {
        return chatWith;
    }

    public void setChatWith(User chatWith) {
        this.chatWith = chatWith;
    }

    public int getId() {
        return id;
    }

    public static List<Record> getRecordTest(int id, String message, List<User> users) {
        List<Record> test = new ArrayList<>();
        for (int i =0; i < users.size() - 1; i++) {
            for (int j = i + 1; j <users.size(); j++) {
                for (int k=0;k<5;k++) {
                    test.add(new Record(i * 10+k*2 + id, "我是" + users.get(i).getUserName() + "，" + message + (i * 10+k*2 + id), users.get(i), users.get(j)));
                    test.add(new Record(i * 10 +k*2+ 1 + id, "我是" + users.get(j).getUserName() + "，" + message + (i * 10+k*2+ 1 + id), users.get(j), users.get(i)));

                }
            }
        }
        return test;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
