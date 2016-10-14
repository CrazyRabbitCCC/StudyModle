package lzy.com.money.FloatWindows;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Field;

import lzy.com.money.R;

public class FloatWindowSmallView extends LinearLayout {

	public static int viewWidth;//记录小悬浮窗的宽度

	public static int viewHeight;//记录小悬浮窗的高度
    public static int type;//记录小悬浮窗的类型 0，正常显示， 1左侧收缩， 2右侧收缩

	private static int statusBarHeight;//记录系统状态栏的高度

	private WindowManager windowManager;//用于更新小悬浮窗的位置

	private WindowManager.LayoutParams mParams;//小悬浮窗的参数

	private float xInScreen;//记录当前手指位置在屏幕上的横坐标值

	private float yInScreen;//记录当前手指位置在屏幕上的纵坐标值

	private float xDownInScreen;//记录手指按下时在屏幕上的横坐标的值

	private float yDownInScreen;//记录手指按下时在屏幕上的纵坐标的值

	private float xInView;//记录手指按下时在小悬浮窗的View上的横坐标的值

	private float yInView;// 记录手指按下时在小悬浮窗的View上的纵坐标的值
    private View view;
    private static int time;
	public FloatWindowSmallView(Context context) {
		super(context);
		windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		LayoutInflater.from(context).inflate(R.layout.float_window_small, this);
		view = findViewById(R.id.small_window_layout);
		viewWidth = view.getLayoutParams().width;
		viewHeight = 25;
		TextView percentView = (TextView) findViewById(R.id.percent);
		percentView.setText(MyWindowManager.getUsedPercentValue(context));


	}
    public void updateView() {
        if (type==1||type==2)
            return;
        int newType=0;
        if (!(mParams.x>0)) {
            newType = 1;
            time++;
        }
        int width = windowManager.getDefaultDisplay().getWidth();
        if (!(mParams.x<width-viewWidth)){
            newType=2;
            time++;
        }
        if (time==10){
            updateViewType(newType);
            windowManager.updateViewLayout(FloatWindowSmallView.this,mParams);
        }
    }

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// 手指按下时记录必要数据,纵坐标的值都需要减去状态栏高度
			xInView = event.getX();
			yInView = event.getY();
			xDownInScreen = event.getRawX();
			yDownInScreen = event.getRawY() - getStatusBarHeight();
			xInScreen = event.getRawX();
			yInScreen = event.getRawY() - getStatusBarHeight();
			break;
		case MotionEvent.ACTION_MOVE:
			xInScreen = event.getRawX();
			yInScreen = event.getRawY() - getStatusBarHeight();
			// 手指移动的时候更新小悬浮窗的位置
			updateViewPosition();
			break;
		case MotionEvent.ACTION_UP:
            time=0;
			// 如果手指离开屏幕时，xDownInScreen和xInScreen相等，且yDownInScreen和yInScreen相等，则视为触发了单击事件。
			if (xDownInScreen == xInScreen && yDownInScreen == yInScreen) {
                if (type==0)
				    openBigWindow();
                else
                    updateViewPosition();
			}
			break;
		default:
			break;
		}
		return true;
	}

	/**
	 * 将小悬浮窗的参数传入，用于更新小悬浮窗的位置。
	 * 
	 * @param params
	 *            小悬浮窗的参数
	 */
	public void setParams(WindowManager.LayoutParams params) {
		mParams = params;
	}

	/**
	 * 更新小悬浮窗在屏幕中的位置。
	 */
	private void updateViewPosition() {
		mParams.x = (int) (xInScreen - xInView);
		mParams.y = (int) (yInScreen - yInView);
        updateViewType(0);
		windowManager.updateViewLayout(this, mParams);
	}



    private void updateViewType(int newType){
        if (type==newType)
            return;
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if (newType==0&&type==1){
            mParams.width=60;
            mParams.height=25;
            layoutParams.width=60;
            layoutParams.height=25;
            setGravity(Gravity.CENTER_HORIZONTAL);
            setLayoutParams(layoutParams);
        }
        else if (newType==0&&type==2){

            int width = windowManager.getDefaultDisplay().getWidth();
            if (viewWidth+mParams.x>width||viewWidth+mParams.x==width)
                mParams.x=width-60;

            mParams.width=60;
            mParams.height=25;
            layoutParams.width=60;
            layoutParams.height=25;
            setGravity(Gravity.CENTER_HORIZONTAL);
            setLayoutParams(layoutParams);
        }
        else if (newType==1&&type==0){
            mParams.width=20;
            mParams.height=60;
            layoutParams.width=20;
            layoutParams.height=60;
            setLayoutParams(layoutParams);
            setGravity(Gravity.CENTER_VERTICAL);
            int height = windowManager.getDefaultDisplay().getHeight();
            if (mParams.y+60>height)
                mParams.y=height-60;
            mParams.x=0;
        }
        else if (newType==2&&type==0){
            mParams.width=20;
            mParams.height=60;
            layoutParams.width=20;
            layoutParams.height=60;
            setLayoutParams(layoutParams);
            setGravity(Gravity.CENTER_VERTICAL);
            int height = windowManager.getDefaultDisplay().getHeight();
            if (mParams.y+60>height)
                mParams.y=height-60;
            int width = windowManager.getDefaultDisplay().getWidth();
            mParams.x=width-20;
        }
        else {

        }

        type=newType;
    }

	/**
	 * 打开大悬浮窗，同时关闭小悬浮窗。
	 */
	private void openBigWindow() {
		MyWindowManager.createBigWindow(getContext());
		MyWindowManager.removeSmallWindow(getContext());
	}

	/**
	 * 用于获取状态栏的高度。
	 * 
	 * @return 返回状态栏高度的像素值。
	 */
	private int getStatusBarHeight() {
		if (statusBarHeight == 0) {
			try {
				Class<?> c = Class.forName("com.android.internal.R$dimen");
				Object o = c.newInstance();
				Field field = c.getField("status_bar_height");
				int x = (Integer) field.get(o);
				statusBarHeight = getResources().getDimensionPixelSize(x);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return statusBarHeight;
	}

}
