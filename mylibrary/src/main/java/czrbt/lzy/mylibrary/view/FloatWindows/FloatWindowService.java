package czrbt.lzy.mylibrary.view.FloatWindows;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.BitmapFactory;
import android.net.TrafficStats;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import czrbt.lzy.mylibrary.R;


public class FloatWindowService extends Service {

    /**
     * 用于在线程中创建或移除悬浮窗。
     */
    private Handler handler = new Handler();

    /**
     * 定时器，定时进行检测当前应该创建还是移除悬浮窗。
     */
    private Timer timer;
    private int time;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 开启定时器，每隔0.5秒刷新一次


            Notification notification = new NotificationCompat.Builder(this)
                    .setContentTitle("今天你记账了吗？")
                    .setContentText("每日记账，天天有喜")
                    .setSmallIcon(R.drawable.tip_anim)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.tip_anim))
                    .setNumber(1)
                    .setAutoCancel(false)// 将AutoCancel设为true后，当你点击通知栏的notification后，它会自动被取消消失
                    .setOngoing(true)// 将Ongoing设为true 那么notification将不能滑动删除
// 从Android4.1开始，可以通过以下方法，设置notification的优先级，优先级越高的，通知排的越靠前，优先级低的，不会在手机最顶部的状态栏显示图标
                    .setPriority(NotificationCompat.PRIORITY_MAX)
                   // notifyBuilder.setPriority(NotificationCompat.PRIORITY_MIN);
                    .setContentIntent(null)
                    .build();
            startForeground(1,notification);


//            startForeground(1, new Notification());
        if (timer == null) {
            timer = new Timer();
            lastTotalRxBytes = getTotalRxBytes();
            lastTimeStamp = System.currentTimeMillis();
            timer.scheduleAtFixedRate(new ShowTask(), 0, 500);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
        // Service被终止的同时也停止定时器继续运行
        timer.cancel();
        timer = null;
    }

    class ShowTask extends TimerTask {
        @Override
        public void run() {
            if (!MyWindowManager.isWindowShowing()) {
                handler.post(() -> MyWindowManager.createSmallWindow(getApplicationContext()));
            } else {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        MyWindowManager.updateView();
                        float f = showNetSpeed();
                        if (!(f < 0.1f)) {
                            DecimalFormat format = new DecimalFormat("#0.0");
                            MyWindowManager.updateUsedPercent(format.format(f) + "kb/s");
                        } else
                            MyWindowManager.updateUsedPercent(getApplicationContext());
                    }
                });
            }
        }

    }

    class RefreshTask extends TimerTask {

        @Override
        public void run() {
            // 当前界面是桌面，且没有悬浮窗显示，则创建悬浮窗。
            if (isHome() && !MyWindowManager.isWindowShowing()) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        MyWindowManager.createSmallWindow(getApplicationContext());
                    }
                });
            }
            // 当前界面不是桌面，且有悬浮窗显示，则移除悬浮窗。
            else if (!isHome() && MyWindowManager.isWindowShowing()) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        MyWindowManager.removeSmallWindow(getApplicationContext());
                        MyWindowManager.removeBigWindow(getApplicationContext());
                    }
                });
            }
            // 当前界面是桌面，且有悬浮窗显示，则更新内存数据。
            else if (isHome() && MyWindowManager.isWindowShowing()) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        MyWindowManager.updateUsedPercent(getApplicationContext());
                    }
                });
            }
        }

    }

    private float showNetSpeed() {

        long nowTotalRxBytes = getTotalRxBytes();
        long nowTimeStamp = System.currentTimeMillis();
        float speed = ((float) (nowTotalRxBytes - lastTotalRxBytes) * 1000 / (nowTimeStamp - lastTimeStamp));//毫秒转换

        lastTimeStamp = nowTimeStamp;
        lastTotalRxBytes = nowTotalRxBytes;
        return speed;

    }

    private long lastTotalRxBytes = 0;
    private long lastTimeStamp = 0;

    private long getTotalRxBytes() {
        return TrafficStats.getUidRxBytes(getApplicationInfo().uid) == TrafficStats.UNSUPPORTED ? 0 : (TrafficStats.getTotalRxBytes() / 1024);//转为KB
    }


    /**
     * 判断当前界面是否是桌面
     */
    private boolean isHome() {
        ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> rti = mActivityManager.getRunningTasks(1);
        if (rti.isEmpty())
            return true;
        return getHomes().contains(rti.get(0).topActivity.getPackageName());
    }

    /**
     * 获得属于桌面的应用的应用包名称
     *
     * @return 返回包含所有包名的字符串列表
     */
    private List<String> getHomes() {
        List<String> names = new ArrayList<String>();
        PackageManager packageManager = this.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        List<ResolveInfo> resolveInfo = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo ri : resolveInfo) {
            names.add(ri.activityInfo.packageName);
        }
        return names;
    }
}
