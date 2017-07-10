package czrbt.lzy.mylibrary.utils;
// @author: lzy  time: 2016/11/07.


import android.content.Context;

import java.lang.reflect.Field;

public class WindowUtils {

    private  static  int statusBarHeight=0;

    /**
     * 获取状态栏高度
     * @param context getApplicationContext();
     * @return statusBarHeight
     */
    public static int getStatusBarHeight(Context context) {
        if (statusBarHeight == 0) {
            try {
                Class<?> c = Class.forName("com.android.internal.R$dimen");
                Object o = c.newInstance();
                Field field = c.getField("status_bar_height");
                int x = (Integer) field.get(o);
                statusBarHeight = context.getResources().getDimensionPixelSize(x);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statusBarHeight;
    }
}
