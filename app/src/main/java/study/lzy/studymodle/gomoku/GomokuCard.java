package study.lzy.studymodle.gomoku;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import study.lzy.studymodle.R;
import study.lzy.studymodle.star.GameCard;

/**
 * @author Gavin
 * @date 2017/06/21.
 */

public class GomokuCard extends FrameLayout {

    private TextView text;
    private ImageView image;
    private Context mContext;
    private int num = 0;

    protected Point mPoint  = new Point(0,0);
    public GomokuCard(Context context,int i,int j) {
        this(context);
        mPoint = new Point(j,i);
        text.setText(j+","+i);
    }
    public GomokuCard(Context context) {
        this(context,null);
    }

    public GomokuCard(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public GomokuCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }
    private void init() {
        text=new TextView(mContext);
        text.setText("");
        text.setTextSize(20);
        LayoutParams lp=new LayoutParams(-1,-1);
        lp.setMargins(2,2,2,2);
        text.setGravity(Gravity.CENTER);
        text.setTextColor(Color.BLACK);
        addView(text, lp);
        text.setVisibility(INVISIBLE);

        image = new ImageView(mContext);
        image.setPadding(10,10,10,10);
//        image.setImageResource(R.drawable.circle);
//        image.setBackgroundColor(Color.argb(0x77,0x00,0xff,0xff));
        addView(image,lp);
    }

    public void setNum(int id){
        this.num = id;
//        text.setText(""+num);
        if (num==-1)
            image.setImageResource(R.drawable.white);
        else if (num==1)
            image.setImageResource(R.drawable.circle);
    }

    public void loadText(){
        text.setText(mPoint.x+","+mPoint.y);
    }

    public Point getPoint() {
        return mPoint;
    }

    public void setPoint(Point point) {
        mPoint = point;
        loadText();
    }
    public void setPoint(int i,int j) {
        mPoint.x=j;
        mPoint.y=i;
        loadText();
    }

    public int getI() {
        return mPoint.y;
    }

    public void setI(int i) {
        mPoint.y = i;
        loadText();
    }

    public int getJ() {
        return mPoint.x;
    }

    public void setJ(int j) {
        mPoint.x = j;
        loadText();
    }

    public int getNum() {
        return num;
    }
    public boolean isEmpty(){
        return num==0;
    }
    public boolean equalValue(GomokuCard card){
        return num==card.getNum();
    }
}
