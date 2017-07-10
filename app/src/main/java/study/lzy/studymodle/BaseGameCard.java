package study.lzy.studymodle;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import study.lzy.studymodle.gomoku.GomokuCard;

/**
 * @author Gavin
 * @date 2017/06/22.
 */

public abstract class BaseGameCard extends FrameLayout {


    protected TextView text;
    protected ImageView image;
    protected Context mContext;
    private int num = 0;
    protected Point mPoint = new Point(0, 0);

    public BaseGameCard(Context context, int x, int y) {
        this(context);
        mPoint = new Point(x, y);
//        text.setText(j + "," + i);
    }

    public BaseGameCard(Context context) {
        this(context, null);
    }

    public BaseGameCard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseGameCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        text = new TextView(mContext);
        text.setText("");
        text.setTextSize(20);
        LayoutParams lp = new LayoutParams(-1, -1);
        lp.setMargins(2, 2, 2, 2);
        text.setGravity(Gravity.CENTER);
        text.setTextColor(Color.BLACK);
        addView(text, lp);
        image = new ImageView(mContext);
        image.setPadding(10, 10, 10, 10);
        addView(image, lp);
    }

    public void setNum(int num) {
        this.num = num;
        loadImageByNum(num);
    }
    protected abstract void loadImageByNum(int num);

    public void loadText() {
        text.setText(mPoint.x + "," + mPoint.y);
    }

    public Point getPoint() {
        return mPoint;
    }

    public void setPoint(Point point) {
        mPoint = point;
        loadText();
    }

    public void setPoint(int x, int y) {
        mPoint.x = x;
        mPoint.y = y;
        loadText();
    }

    public int getPointY() {
        return mPoint.y;
    }

    public void setPointY(int y) {
        mPoint.y =y;
        loadText();
    }

    public int getPointX() {
        return mPoint.x;
    }

    public void setPointX(int X) {
        mPoint.x = X;
        loadText();
    }

    public int getNum() {
        return num;
    }

    public boolean isEmpty() {
        return num == 0;
    }

    public boolean equalValue(BaseGameCard card) {
        return num == card.getNum();
    }
}


