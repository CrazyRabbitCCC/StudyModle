package lzy.com.money.Service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import lzy.com.money.Activity.MainActivity;
import lzy.com.money.R;

/**
 * Created by Administrator on 2016/8/4.
 */
public class MessageService extends Service {
    //获取消息线程
    private MessageThread messageThread = null;
    //点击查看
    private Intent messageIntent = null;
    private PendingIntent messagePendingIntent = null;
    //通知栏消息
    private int messageNotificationID = 1000;
    private Notification messageNotification = null;
    private NotificationManager messageNotificationManager = null;

    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
//初始化
        messagePendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);
        // 通过Notification.Builder来创建通知，注意API Level
        // API16之后才支持

        messageNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//点击跳转的activity
//开启线程
        messageThread = new MessageThread();
        messageThread.isRunning = true;
        messageThread.start();
//Toast.makeText(MessageService.this, "", Toast.LENGTH_LONG).show();
        super.onCreate();
    }

    /**
     * 从服务器端获取消息
     */
    class MessageThread extends Thread {
        //运行状态，下一步骤有大用
        public boolean isRunning = true;

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        public void run() {
            while (isRunning) {
                try{
//休息10分钟
                    Thread.sleep(1000);
//获取服务器消息
                    String serverMessage = getServerMessage();
                    if (serverMessage != null && !"".equals(serverMessage)) {

                        messageNotification = new Notification.Builder(MessageService.this)
                                .setSmallIcon(R.drawable.icon)
                                .setTicker("您今天记账了吗？")
                                .setContentTitle("记账")
                                .setContentText(serverMessage)
                                .setContentIntent(messagePendingIntent).build(); // 需要注意build()是在API
                        // level16及之后增加的，API11可以使用getNotificatin()来替代
                        messageNotification.flags |= Notification.FLAG_AUTO_CANCEL; // FLAG_AUTO_CANCEL表明当通知被用户点击时，通知将被清除。
//更新通知栏

//                        messageNotification.setLatestEventInfo(MessageService.this, "", serverMessage, messagePendingIntent);
                        messageNotificationManager.notify(messageNotificationID, messageNotification);
//每次通知完，通知ID递增一下，避免消息覆盖掉
                        messageNotificationID++;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onDestroy() {
// System.exit(0);
//或者，二选一，推荐使用System.exit(0)，这样进程退出的更干净
        messageThread.isRunning = false;
        super.onDestroy();
    }

    /**
     * 这里以此方法为服务器Demo，仅作示例
     *
     * @return 返回服务器要推送的消息，否则如果为空的话，不推送
     */
    public String getServerMessage() {
        SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
        String time = sdf.format(new Date());
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        Boolean x=true;
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        String str="";
        switch (time) {
            case "073000":
                if (dayOfWeek==1||dayOfWeek==7) {
                    str = "";
                    break;
                }
                str="记得早餐记账";
            case "195500":
                str = "记得晚餐记账";
                return str;
            case "223000":
                str = "今天还有什么帐目没有记录的吗？";
                break;
            default:
                str = "";
                break;
        }
        return str;
    }
}
