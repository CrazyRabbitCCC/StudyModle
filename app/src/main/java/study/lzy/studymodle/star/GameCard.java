package study.lzy.studymodle.star;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author Gavin
 * @date 2017/06/16.
 */

public class GameCard extends FrameLayout{
    protected TextView text;
    protected ImageView image;
    protected int num=0;
    protected  Context mContext;
    protected int cardId;
    public GameCard(Context context) {
        this(context, null);
    }

    public static final SparseIntArray colors = new SparseIntArray();
    static {
        colors.put(0,Color.WHITE);
        colors.put(1,Color.rgb(0xFF,0xD7,0x00));//FFD700
        colors.put(2,Color.rgb(0xFF,0x00,0xFF));//FF00FF
        colors.put(3,Color.rgb(0xFF,0x00,0x00));//FF0000
        colors.put(4,Color.rgb(0xF4,0xA4,0x60));//F4A460
        colors.put(5,Color.rgb(0x00,0x00,0xAA));//0000AA
        colors.put(6,Color.rgb(0x00,0xBF,0xff));//00BFFF
        colors.put(7,Color.rgb(0x00,0xFF,0x00));//00FF00
        colors.put(8,Color.rgb(0x00,0x8b,0x45));//008B45
        colors.put(9,Color.rgb(0x8b,0x1a,0x1a));//8B1A1A
        colors.put(10,Color.rgb(0x00,0x00,0x00));
        colors.put(11,Color.rgb(0x8a,0x8a,0x8a));
    }

    public GameCard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GameCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
        init();
    }

    private void init() {
        text=new TextView(mContext);
        text.setText("");
        text.setTextSize(20);
        LayoutParams lp=new LayoutParams(-1,-1);
        lp.setMargins(2,2,2,2);
        text.setGravity(Gravity.CENTER);
        text.setBackgroundColor(Color.WHITE);
        text.setTextColor(Color.BLACK);
        addView(text, lp);
        image = new ImageView(mContext);
        image.setBackgroundColor(Color.argb(0x77,0x00,0xff,0xff));
        addView(image,lp);
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
        text.setText(num==0?"":""+num);
        image.setBackgroundColor(colors.get(num));

    }

    public TextView getText() {
        return text;
    }

    public ImageView getImage() {
        return image;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }
}
