package study.lzy.studymodle.touch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;

import study.lzy.studymodle.R;

public class TouchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("activity ","onTouchEvent "+ "start");
        boolean b = super.onTouchEvent(event);
        Log.e("activity ","onTouchEvent "+ b);
        return b;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("activity "," dispatchTouchEvent "+ "start");
        boolean b = super.dispatchTouchEvent(ev);
        Log.e("activity "," dispatchTouchEvent "+ b);
        return b;
    }
}
