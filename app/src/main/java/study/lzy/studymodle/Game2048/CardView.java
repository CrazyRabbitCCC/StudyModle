package study.lzy.studymodle.Game2048;// @author: lzy  time: 2016/09/19.

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

public class CardView  extends FrameLayout{
    TextView text;
    int num=0;
    Context mContext;
    public CardView(Context context) {
        this(context, null);
    }

    public CardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
        init();

    }

    private void init() {
        text=new TextView(mContext);
        text.setText("");
        text.setTextSize(25);
        LayoutParams lp=new LayoutParams(-1,-1);
        lp.setMargins(5,5,5,5);
        text.setGravity(Gravity.CENTER);
        text.setBackgroundColor(Color.WHITE);
        text.setTextColor(Color.BLACK);
        addView(text, lp);

    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
        if (num!=0)
        text.setText(""+num);
        else
            text.setText("");
        setColor();
    }

    private void setColor() {
        int i=0;
        int R=0xff,G=0xff,B=0xff;
        int num=this.num;
        while (num/2!=0){
            num=num/2;

            switch (i/3){
                case 2:
                    R-=70;
                case 1:
                    G-=70;
                case 0:
                    B-=70;
                    if (B<128){
                        B+=128;
                        G-=10;
                    }
                    if (G<128){
                        G+=128;
                        R-=10;
                    }
                    if (R<128){
                        R+=128;
                    }
                    break;
            }
            i++;
        }
        text.setBackgroundColor(Color.rgb(R,G,B));

    }


    public boolean equals(CardView o) {
        return getNum()==o.getNum();
    }
}
