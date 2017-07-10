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

public class 星星小格子 extends FrameLayout{
    protected TextView 文字视图;
    protected ImageView 图片视图;
    protected int 数据=0;
    protected  Context mContext;
    protected int 格子序列;
    public 星星小格子(Context context) {
        this(context, null);
    }

    public static final SparseIntArray 颜色列表 = new SparseIntArray();
    static {
        颜色列表.put(0,Color.WHITE);
        颜色列表.put(1,Color.rgb(0xFF,0xD7,0x00));//FFD700
        颜色列表.put(2,Color.rgb(0xFF,0x00,0xFF));//FF00FF
        颜色列表.put(3,Color.rgb(0xFF,0x00,0x00));//FF0000
        颜色列表.put(4,Color.rgb(0xF4,0xA4,0x60));//F4A460
        颜色列表.put(5,Color.rgb(0x00,0x00,0xAA));//0000AA
        颜色列表.put(6,Color.rgb(0x00,0xBF,0xff));//00BFFF
        颜色列表.put(7,Color.rgb(0x00,0xFF,0x00));//00FF00
        颜色列表.put(8,Color.rgb(0x00,0x8b,0x45));//008B45
        颜色列表.put(9,Color.rgb(0x8b,0x1a,0x1a));//8B1A1A
        颜色列表.put(10,Color.rgb(0x00,0x00,0x00));
        颜色列表.put(11,Color.rgb(0x8a,0x8a,0x8a));
    }

    public 星星小格子(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public 星星小格子(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
        初始化();
    }

    private void 初始化() {
        文字视图=new TextView(mContext);
        文字视图.setText("");
        文字视图.setTextSize(20);
        LayoutParams 布局参数=new LayoutParams(-1,-1);
        布局参数.setMargins(2,2,2,2);
        文字视图.setGravity(Gravity.CENTER);
        文字视图.setBackgroundColor(Color.WHITE);
        文字视图.setTextColor(Color.BLACK);
        addView(文字视图, 布局参数);
        图片视图 = new ImageView(mContext);
        图片视图.setBackgroundColor(Color.argb(0x77,0x00,0xff,0xff));
        addView(图片视图,布局参数);
    }

    public int 获取数据() {
        return 数据;
    }

    public void 设置数据(int 数据) {
        this.数据 = 数据;
        文字视图.setText(数据==0?"":""+数据);
        图片视图.setBackgroundColor(颜色列表.get(数据));

    }

    public TextView 获取文字视图() {
        return 文字视图;
    }

    public ImageView 获取图片视图() {
        return 图片视图;
    }

    public int 获取格子序列() {
        return 格子序列;
    }

    public void 设置格子序列(int 格子序列) {
        this.格子序列 = 格子序列;
    }
}
