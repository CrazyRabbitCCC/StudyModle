package study.lzy.studymodle.sweep;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import study.lzy.studymodle.R;


/**
 * @author Gavin
 * @date 2017/06/15.
 */

public class SweepCard  extends FrameLayout{
    TextView text;
    int num=-2;
    Context mContext;
    final int sweepId;
    private boolean mine;
    public SweepCard(Context context,int sweepId) {
        super(context);
        this.sweepId=sweepId;
        mContext=context;
        init();

    }

    ImageView imageView;

    private void init() {
        text=new TextView(mContext);
        text.setText("");
        text.setTextSize(20);
        FrameLayout.LayoutParams lp=new FrameLayout.LayoutParams(-1,-1);
        lp.setMargins(5,5,5,5);
        text.setGravity(Gravity.CENTER);
        text.setBackgroundColor(Color.WHITE);
        text.setTextColor(Color.BLACK);
        addView(text, lp);
        imageView = new ImageView(mContext);
        imageView.setBackgroundColor(Color.rgb(0x00,0xff,0xff));
        addView(imageView,lp);
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
        text.setText(num==0?"":""+num);
    }

    private boolean clicked;
    public boolean isClicked() {
        return clicked;
    }
    public void setClicked(boolean clicked){
        this.clicked =clicked;
    }

    public int getSweepId() {
        return sweepId;
    }

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public void showNum() {
        clicked=true;
        if (isMine())
            imageView.setImageResource(R.drawable.ic_menu_share);
        else
            imageView.setVisibility(GONE);
    }
}
