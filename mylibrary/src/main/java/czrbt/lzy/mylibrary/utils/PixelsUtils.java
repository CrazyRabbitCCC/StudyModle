package czrbt.lzy.mylibrary.utils;
// @author: lzy  time: 2016/11/07.


import android.content.Context;
import android.util.TypedValue;

public class PixelsUtils {
    public static int dipToPixels(Context context,int dip) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dip,
                context.getResources().getDisplayMetrics());
    }
    public static int sPToPixels(Context context,int sp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, sp,
                context.getResources().getDisplayMetrics());
    }
}
