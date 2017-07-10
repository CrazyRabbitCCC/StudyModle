package study.lzy.studymodle.touch;
// @author: lzy  time: 2016/11/11.


import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class ChildGroup extends LinearLayout{
    public ChildGroup(Context context) {
        super(context);
    }

    public ChildGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChildGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("ChildGroup","dispatchTouchEvent "+"start");
        boolean event = super.dispatchTouchEvent(ev);
        Log.e("ChildGroup","dispatchTouchEvent "+event);
        return event;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e("ChildGroup"," onInterceptTouchEvent "+ "start");
        boolean event = super.onInterceptTouchEvent(ev);
        Log.e("ChildGroup"," onInterceptTouchEvent "+ event);
        return event;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("ChildGroup"," onTouchEvent "+ "start");
        boolean ev = super.onTouchEvent(event);
        Log.e("ChildGroup"," onTouchEvent "+ ev);
        return ev;
    }
}
