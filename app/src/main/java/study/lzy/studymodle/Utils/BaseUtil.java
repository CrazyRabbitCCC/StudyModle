package study.lzy.studymodle.Utils;
// @author: lzy  time: 2016/09/29.


import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.util.List;

public class BaseUtil {


    public static DisplayMetrics getScreenMetrics(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm;
    }

    public static String[] ListTOStrings(List<String > list) {
        if (list==null)
            return new String[0];
        String[] ss=new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            ss[i]=list.get(i);
        }
        return ss;
    }
}
