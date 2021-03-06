package czrbt.lzy.mylibrary.view.little_point;
// @author: lzy  time: 2016/09/28.


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.drawable.ShapeDrawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

/**
 * 显示消息的小红点
 */
public class MessageView extends View {
    private static final String TAG = MessageView.class.getSimpleName();
    private static int statusBarHeight = 0;
    private PointF downPoint;
    private int maxLength = 200;
    private ViewGroup parent;

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public interface OnDraggableFlagViewListener {
        /*拖拽销毁圆点后的回调*/
        void onFlagDismiss(MessageView view);
    }

    private OnLineBrokeListener onLineBrokeListener;

    public void setOnLineBrokeListener(OnLineBrokeListener onLineBrokeListener) {
        this.onLineBrokeListener = onLineBrokeListener;
    }

    /*位置信息*/
    public static final int POSITION_TOP_LEFT = 1;
    public static final int POSITION_TOP_RIGHT = 2;
    public static final int POSITION_BOTTOM_LEFT = 3;
    public static final int POSITION_BOTTOM_RIGHT = 4;
    public static final int POSITION_CENTER = 5;

    /*默认信息*/
    private static final int DEFAULT_MARGIN_DIP = 5; //margin
    private static final int DEFAULT_LR_PADDING_DIP = 5;//padding
    private static final int DEFAULT_CORNER_RADIUS_DIP = 8;
    private static final int DEFAULT_POSITION = POSITION_TOP_RIGHT;
    private static final int DEFAULT_BADGE_COLOR = Color.parseColor("#CCFF0000"); //Color.RED;
    private static final int DEFAULT_TEXT_COLOR = Color.WHITE;

    /*动画*/
    private static Animation fadeIn;
    private static Animation fadeOut;

    private Context context;
    private View target;

    private int badgePosition;//位置
    private int badgeMarginH;//
    private int badgeMarginV;

    private boolean isShown;//是否显示
    private ShapeDrawable badgeBg;//背景

    private int patientColor = DEFAULT_BADGE_COLOR;

    private int originRadius; // 初始的圆的半径

    private int curRadius; // 当前点的半径

    private Paint paint; // 绘制圆形图形
    private TextPaint textPaint; // 绘制圆形图形
    private Paint.FontMetrics textFontMetrics;


    private String text = ""; // 正常状态下显示的文字

    private boolean isFirst = true;

    private PointF start;
    private PointF end;
    private static FrameLayout frame;
    private View view;
    private TouchView touch;

    public MessageView(Context context) {
        this(context, (AttributeSet) null, android.R.attr.textViewStyle);
    }

    public MessageView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public MessageView(Context context, View target, View view) {
        this(context, null, android.R.attr.textViewStyle, target, view);
    }


    public MessageView(Context context, AttributeSet attrs, int defStyle) {
        this(context, attrs, defStyle, null, null);
    }

    public MessageView(Context context, AttributeSet attrs, int defStyle, View target, View view) {
        super(context, attrs, defStyle);
        init(context, target, view);
    }

    private void init(Context context, View target, View view) {
        this.context = context;
        this.target = target;
        this.view = view;

        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            if (frame == null)
                frame = new FrameLayout(context);
            if (frame != parent) {
                int index = parent.indexOfChild(view);
                ViewGroup.LayoutParams lp = view.getLayoutParams();

                parent.removeView(view);
                ViewGroup frameParent = (ViewGroup) frame.getParent();
                if (frameParent != null)
                    frameParent.removeView(frame);
                parent.addView(frame, index, lp);
                frame.addView(view);
            }// FIXME: 2016/09/28
            else {

//                frame.addView(view);
            }
        } else {
            if (frame == null)
                frame = new FrameLayout(context);
            frame.addView(view);
        }

        setBackgroundColor(Color.TRANSPARENT);

        // 设置绘制flag的paint
        paint = new Paint();
        paint.setColor(patientColor);
        paint.setAntiAlias(true);

        // 设置绘制文字的paint
        textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(DEFAULT_TEXT_COLOR);
        textPaint.setTextSize(spToPixels(12));
        textPaint.setTextAlign(Paint.Align.CENTER);
        textFontMetrics = textPaint.getFontMetrics();

        fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(200);

        fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setDuration(200);
        badgePosition = DEFAULT_POSITION;
        badgeMarginH = dipToPixels(DEFAULT_MARGIN_DIP);
        badgeMarginV = badgeMarginH;

        setLayoutParams(new ViewGroup.LayoutParams(dipToPixels(30), dipToPixels(30)));
        originRadius = dipToPixels(20) / 2;
        curRadius = dipToPixels(20) / 2;

        isShown = false;

        if (this.target != null) {
            applyTo(this.target);
        } else {
            show();
        }
    }

    private void applyTo(View target) {

        ViewGroup.LayoutParams lp = target.getLayoutParams();
        ViewParent parent = target.getParent();
        FrameLayout container = new FrameLayout(context);

        // TODO verify that parent is indeed a ViewGroup
        ViewGroup group = (ViewGroup) parent;
        int index = group.indexOfChild(target);

        group.removeView(target);
        group.addView(container, index, lp);
        container.setClipChildren(false);
        container.addView(target);

        this.setVisibility(View.VISIBLE);
        container.addView(this);

        group.invalidate();
    }


    public void show() {
        show(false, null);
    }

    public void show(boolean animate) {
        show(animate, fadeIn);
    }

    public void show(Animation anim) {
        show(true, anim);
    }

    public void hide() {
        hide(false, null);
    }

    public void hide(boolean animate) {
        hide(animate, fadeOut);
    }

    public void hide(Animation anim) {
        hide(true, anim);
    }

    public void toggle() {
        toggle(false, null, null);
    }

    public void toggle(boolean animate) {
        toggle(animate, fadeIn, fadeOut);
    }

    public void toggle(Animation animIn, Animation animOut) {
        toggle(true, animIn, animOut);
    }

    private void show(boolean animate, Animation anim) {
        applyLayoutParams();

        if (animate) {
            this.startAnimation(anim);
        }
        this.setVisibility(View.VISIBLE);
        isShown = true;
    }

    private void hide(boolean animate, Animation anim) {
        this.setVisibility(View.GONE);
        if (animate) {
            this.startAnimation(anim);
        }
        isShown = false;
    }

    private void toggle(boolean animate, Animation animIn, Animation animOut) {
        if (isShown) {
            hide(animate && (animOut != null), animOut);
        } else {
            show(animate && (animIn != null), animIn);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setVisibility(VISIBLE);
                start = new PointF();
                parent = (ViewGroup) getParent();
                parent.requestDisallowInterceptTouchEvent(true);
                downPoint = new PointF();
                downPoint.x = event.getX();
                downPoint.y = event.getY();
                start.x = event.getRawX() - event.getX();
                start.y = event.getRawY() - event.getY();//-getStatusBarHeight()
                if (touch == null) {
                    touch = new TouchView(context, start, this);
                } else
                    touch.setStart(start);
                touch.setVisibility(GONE);
                ViewGroup.LayoutParams params
                        = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT);
                frame.addView(touch, params);

                break;
            case MotionEvent.ACTION_MOVE:// FIXME: 2016/09/28 可能会有问题
                setVisibility(INVISIBLE);
                if (touch == null)
                    touch = new TouchView(context, start, this);
                end = new PointF();
                end.x = event.getRawX() - downPoint.x;
                end.y = event.getRawY() - downPoint.y;
                touch.setVisibility(VISIBLE);
                touch.move(end);
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                setVisibility(VISIBLE);
                parent.requestDisallowInterceptTouchEvent(false);

                if (touch.isBrok()) {
                    touch.startAnimate();
                    setVisibility(GONE);
                    if (getParent()!=null){
                        ViewGroup parent= (ViewGroup) getParent();
                        parent.removeView(this);
                    }
                    if (onLineBrokeListener != null)
                        onLineBrokeListener.onBroke(this);
                }
                touch.setVisibility(GONE);
                frame.removeView(touch);
                break;

        }

        return true;
    }


    private void applyLayoutParams() {
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(dipToPixels(20), dipToPixels(20));

        switch (badgePosition) {
            case POSITION_TOP_LEFT:
                lp.gravity = Gravity.LEFT | Gravity.TOP;
                lp.setMargins(badgeMarginH, badgeMarginV, 0, 0);
                break;
            case POSITION_TOP_RIGHT:
                lp.gravity = Gravity.RIGHT | Gravity.TOP;
                lp.setMargins(0, badgeMarginV, badgeMarginH, 0);
                break;
            case POSITION_BOTTOM_LEFT:
                lp.gravity = Gravity.LEFT | Gravity.BOTTOM;
                lp.setMargins(badgeMarginH, 0, 0, badgeMarginV);
                break;
            case POSITION_BOTTOM_RIGHT:
                lp.gravity = Gravity.RIGHT | Gravity.BOTTOM;
                lp.setMargins(0, 0, badgeMarginH, badgeMarginV);
                break;
            case POSITION_CENTER:
                lp.gravity = Gravity.CENTER;
                lp.setMargins(0, 0, 0, 0);
                break;
            default:
                break;
        }
        setLayoutParams(lp);
    }

    Path path = new Path();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.TRANSPARENT);

        int startCircleX = 0, startCircleY = 0;
        // 非触摸状态
        if (originRadius > 0) {
            startCircleX = originRadius;
            startCircleY = originRadius;
            canvas.drawCircle(startCircleX, startCircleY, originRadius, paint);

            float textH = -textFontMetrics.ascent - textFontMetrics.descent;
            canvas.drawText(text, startCircleX, startCircleY + textH / 2, textPaint);
        }
        Log.d(TAG, "circleX: " + startCircleX + ", circleY: " + startCircleY + ", curRadius: " + curRadius);
    }


    public Paint getPaint() {
        return paint;
    }

    public TextPaint getTextPaint() {
        return textPaint;
    }

    public Paint.FontMetrics getTextFontMetrics() {
        return textFontMetrics;
    }

    public String getText() {
        return text;
    }

    public int getOriginRadius() {
        return originRadius;
    }

    public void setText(String text) {
        this.text = text;
        this.setVisibility(VISIBLE);
        postInvalidate();
    }

    private int dipToPixels(int dip) {
        Resources r = getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, r.getDisplayMetrics());
        return (int) px;
    }

    private int spToPixels(int dip) {
        Resources r = getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, dip, r.getDisplayMetrics());
        return (int) px;
    }

    public interface OnLineBrokeListener {
        void onBroke(MessageView view);
    }
}
