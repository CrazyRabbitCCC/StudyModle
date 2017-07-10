package study.lzy.qqimitate.Info;
// @author: lzy  time: 2016/09/21.


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChatRecord implements Serializable {
    private User chat;
    private User chatWhit;
    private List<Record> records;

    public ChatRecord() {
        records = new ArrayList<>();
    }

    public ChatRecord(User chat, User chatWhit) {
        this.chat = chat;
        this.chatWhit = chatWhit;
        records=new ArrayList<>();
    }

    public ChatRecord(User chat, User chatWhit, List<Record> records) {
        this.chat = chat;
        this.chatWhit = chatWhit;
        this.records = records;
    }

    public User getChatWhit() {
        return chatWhit;
    }



    public void setChatWhit(User chatWhit) {
        this.chatWhit = chatWhit;
    }



    public User getChat() {
        return chat;
    }

    public void setChat(User chat) {
        this.chat = chat;
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }


    public static ChatRecord get(String userId,String userId2, List<ChatRecord> chatRecords) {
        for (ChatRecord chatRecord : chatRecords) {
            if (userId.equals(chatRecord.getChat().getUserId())&&userId2.equals(chatRecord.getChatWhit().getUserId()))
                return chatRecord;
        }
        return null;

    }

    public static List<ChatRecord> getChatRecord(User user, List<Record> records) {
        List<ChatRecord> test = new ArrayList<>();
        for (Record record : records) {

            int flag = 0;
            if (user.getUserId().equals(record.getChat().getUserId())) {
                flag = 1;

            }
            if (user.getUserId().equals(record.getChatWith().getUserId())) {
                flag = 2;

            }
            if (flag != 0) {
                for (ChatRecord chat : test) {
                    if (flag == 1 && chat.chatWhit.getUserId().equals(record.getChatWith().getUserId())) {
                        flag = 3;
                        chat.records.add(record);
                        break;
                    }
                    if (flag == 2 && chat.chatWhit.getUserId().equals(record.getChat().getUserId())) {
                        flag = 4;
                        chat.records.add(record);
                        break;
                    }
                }
            }
            switch (flag) {
                case 1:
                    ChatRecord chatRecord=new ChatRecord(record.getChat(),record.getChatWith());
                    chatRecord.records.add(record);
                    test.add(chatRecord);
                    break;
                case 2:
                    ChatRecord chatRecord2=new ChatRecord(record.getChatWith(),record.getChat());
                    chatRecord2.records.add(record);
                    test.add(chatRecord2);
                    break;
            }
        }
        return test;
    }
}
