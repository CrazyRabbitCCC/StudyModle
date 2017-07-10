package study.lzy.studymodle.touch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

// @author: lzy  time: 2016/11/11.


public class Child extends View {
    public Child(Context context) {
        super(context);
    }

    public Child(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Child(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("Child"," dispatchTouchEvent start");
        boolean event =super.dispatchTouchEvent(ev);
        Log.e("Child"," dispatchTouchEvent "+event);
        return event;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("Child"," onTouchEvent "+ "start");
        boolean ev = super.onTouchEvent(event);
        Log.e("Child"," onTouchEvent "+ ev);
        return ev;
    }
}
