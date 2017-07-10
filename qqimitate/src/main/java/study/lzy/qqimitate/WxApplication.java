package study.lzy.qqimitate;

import android.app.Application;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.List;

import study.lzy.qqimitate.Info.ChatRecord;
import study.lzy.qqimitate.Info.Friend;
import study.lzy.qqimitate.Info.User;

// @author: lzy  time: 2016/09/21.


public class WxApplication extends Application {
    private User  loginUser;
    private List<ChatRecord> chatRecordList;
    private List<Friend> friendList;
    public static  final  char[] ZiMu=new char[]{
      'A','B','C','D','E' ,'F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','#'
    };
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static char getFirstChar(String chines){
        if (chines.length()>0){
            chines=chines.substring(0,1);
            char c=chines.charAt(0);
            HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
            defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
            defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
            if (c> 128) {
                try {
                    return PinyinHelper.toHanyuPinyinStringArray(c, defaultFormat)[0].charAt(0);
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            }else{
                if (c>96&&c<123)
                    c-=32;
                return c;
            }
        }
        return '#';
    }
    public static String getEnglish(String chines){
        String s="";
        boolean flag=true;
        if (chines.length()>0) {
            for (int i = 0; i < chines.length(); i++) {
                char c  = chines.substring(i, i + 1).charAt(0);

                HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
                defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
                defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

                if (c > 128) {
                    try {
                        s=s+" "+ PinyinHelper.toHanyuPinyinStringArray(c, defaultFormat)[0];
                        flag=true;
                    } catch (BadHanyuPinyinOutputFormatCombination e) {
                        e.printStackTrace();
                    }
                } else {
                    if (flag)
                        s+=" ";
                    flag=false;
                    if (c > 96 && c < 123)
                        c -= 32;
                    s+= c;
                }
            }
        }
        return s;
    }

    public  static int getHeaderId(String chines){
        char c=getFirstChar(chines);
        int i;
        for ( i=0;i<ZiMu.length-1;i++){
            if (c==ZiMu[i])
                break;
        }

        return i;
    }

    public User getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(User loginUser) {
        this.loginUser = loginUser;
    }

    public List<ChatRecord> getChatRecordList() {
        return chatRecordList;
    }

    public void setChatRecordList(List<ChatRecord> chatRecordList) {
        this.chatRecordList = chatRecordList;
    }

    public List<Friend> getFriendList() {
        return friendList;
    }

    public void setFriendList(List<Friend> friendList) {
        this.friendList = friendList;
    }
}
