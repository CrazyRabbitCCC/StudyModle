package study.lzy.studymodle.touch;
// @author: lzy  time: 2016/11/11.


import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class Parent extends LinearLayout{

    public Parent(Context context) {
        super(context);
    }

    public Parent(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Parent(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("Parent "," dispatchTouchEvent "+"start");

        boolean event = super.dispatchTouchEvent(ev);
        Log.e("Parent "," dispatchTouchEvent "+event);
        return event;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e("Parent "," onInterceptTouchEvent "+ "start");
        boolean event = super.onInterceptTouchEvent(ev);
        Log.e("Parent "," onInterceptTouchEvent "+ event);
        return event;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("Parent "," onTouchEvent "+ "start");

        boolean ev = super.onTouchEvent(event);
        Log.e("Parent "," onTouchEvent "+ ev);
        return ev;
    }
}
