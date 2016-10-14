package FlagView.sample;
// @author: lzy  time: 2016/09/28.


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TabWidget;
import android.widget.TextView;

import java.util.Arrays;

import FlagView.DraggableFlagView;

public class BadgeView extends View {
    private static final String TAG = BadgeView.class.getSimpleName();

    public interface OnDraggableFlagViewListener {
        /*拖拽销毁圆点后的回调*/
        void onFlagDismiss(BadgeView view);
    }

    private OnDraggableFlagViewListener onDraggableFlagViewListener;

    public void setOnDraggableFlagViewListener(OnDraggableFlagViewListener onDraggableFlagViewListener) {
        this.onDraggableFlagViewListener = onDraggableFlagViewListener;
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
    private int badgeColor;

    private boolean isShown;//是否显示
    private ShapeDrawable badgeBg;//背景
    private int targetTabIndex;//在tab的位置 fixme（可以去掉）

    private int patientColor = DEFAULT_BADGE_COLOR;

    private int originRadius; // 初始的圆的半径
    private int originWidth;
    private int originHeight;

    private int maxMoveLength; // 最大的移动拉长距离
    private boolean isArrivedMaxMoved; // 达到了最大的拉长距离（松手可以触发事件）

    private int curRadius; // 当前点的半径
    private int touchedPointRadius; // touch的圆的半径
    private Point startPoint = new Point();
    private Point endPoint = new Point();

    private Paint paint; // 绘制圆形图形
    private TextPaint textPaint; // 绘制圆形图形
    private Paint.FontMetrics textFontMetrics;

    private int[] location;

    private boolean isTouched; // 是否是触摸状态

    private Triangle triangle = new Triangle();

    private String text = ""; // 正常状态下显示的文字

    LinearLayout.LayoutParams originLp; // 实际的layoutparams
    LinearLayout.LayoutParams newLp; // 触摸时候的LayoutParams

    private boolean isFirst = true;

    public BadgeView(Context context) {
        this(context, (AttributeSet) null, android.R.attr.textViewStyle);
    }

    public BadgeView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public BadgeView(Context context, View target) {
        this(context, null, android.R.attr.textViewStyle, target, 0);
    }

    public BadgeView(Context context, TabWidget target, int index) {
        this(context, null, android.R.attr.textViewStyle, target, index);
    }

    public BadgeView(Context context, AttributeSet attrs, int defStyle) {
        this(context, attrs, defStyle, null, 0);
    }

    public BadgeView(Context context, AttributeSet attrs, int defStyle, View target, int tabIndex) {
        super(context, attrs, defStyle);
        init(context, target, tabIndex);
    }

    private void init(Context context, View target, int tabIndex) {
        this.context = context;
        this.target = target;
        this.targetTabIndex = tabIndex;

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

        badgePosition = DEFAULT_POSITION;
        badgeMarginH = dipToPixels(DEFAULT_MARGIN_DIP);
        badgeMarginV = badgeMarginH;
        badgeColor = DEFAULT_BADGE_COLOR;


        int paddingPixels = dipToPixels(DEFAULT_LR_PADDING_DIP);
        setPadding(paddingPixels, 0, paddingPixels, 0);

        fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(200);

        fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setDuration(200);

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
        LinearLayout container = new LinearLayout(context);

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
        if (getBackground() == null) {
            if (badgeBg == null) {
                badgeBg = getDefaultBackground();
            }
            setBackground(badgeBg);
        }
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
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d(TAG, String.format("onSizeChanged, w: %s, h: %s, oldw: %s, oldh: %s", w, h, oldw, oldh));
        if (isFirst && w > 0 && h > 0) {
            isFirst = false;

            originWidth = w;
            originHeight = h;

            originRadius = Math.min(originWidth, originHeight) / 2;
            curRadius = originRadius;
            touchedPointRadius = originRadius;

            maxMoveLength = dipToPixels(200);

            refreshStartPoint();

            ViewGroup.LayoutParams lp = this.getLayoutParams();
            if (LinearLayout.LayoutParams.class.isAssignableFrom(lp.getClass())) {
                originLp = (LinearLayout.LayoutParams) lp;
            }
            newLp = new LinearLayout.LayoutParams(lp.width, lp.height);
        }

    }

    private ShapeDrawable getDefaultBackground() {

        int r = dipToPixels(DEFAULT_CORNER_RADIUS_DIP);
        float[] outerR = new float[] {r, r, r, r, r, r, r, r};

        RoundRectShape rr = new RoundRectShape(outerR, null, null);
        ShapeDrawable drawable = new ShapeDrawable(rr);
        drawable.getPaint().setColor(badgeColor);

        return drawable;

    }

    private void applyLayoutParams() {

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

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

    @Override
    public void setLayoutParams(ViewGroup.LayoutParams params) {
        super.setLayoutParams(params);
        refreshStartPoint();
    }

    /**
     * 修改layoutParams后，需要重新设置startPoint
     */
    private void refreshStartPoint() {
        location = new int[2];
        this.getLocationInWindow(location);
        Log.d(TAG, "location on screen: " + Arrays.toString(location));
//           startPoint.set(location[0], location[1] );
        try {
            location[1] = location[1];// -outMetrics. ABAppUtil.getTopBarHeight((Activity) context);
        } catch (Exception ex) {
        }

        startPoint.set(location[0], location[1] + getMeasuredHeight());
//        Logger.d(TAG, "startPoint: " + startPoint);
    }

    Path path = new Path();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.TRANSPARENT);

        int startCircleX = 0, startCircleY = 0;
        if (isTouched) { // 触摸状态

            startCircleX = startPoint.x + curRadius;
            startCircleY = startPoint.y - curRadius;
            // 绘制原来的圆形（触摸移动的时候半径会不断变化）
            canvas.drawCircle(startCircleX, startCircleY, curRadius, paint);
            // 绘制手指跟踪的圆形
            int endCircleX = endPoint.x;
            int endCircleY = endPoint.y;
            canvas.drawCircle(endCircleX, endCircleY, originRadius, paint);

            if (!isArrivedMaxMoved) { // 没有达到拉伸最大值
                path.reset();
                double sin = triangle.deltaY / triangle.hypotenuse;
                double cos = triangle.deltaX / triangle.hypotenuse;

                // A点
                path.moveTo(
                        (float) (startCircleX - curRadius * sin),
                        (float) (startCircleY - curRadius * cos)
                );
                // B点
                path.lineTo(
                        (float) (startCircleX + curRadius * sin),
                        (float) (startCircleY + curRadius * cos)
                );
                // C点
                path.quadTo(
                        (startCircleX + endCircleX) / 2, (startCircleY + endCircleY) / 2,
                        (float) (endCircleX + originRadius * sin), (float) (endCircleY + originRadius * cos)
                );
                // D点
                path.lineTo(
                        (float) (endCircleX - originRadius * sin),
                        (float) (endCircleY - originRadius * cos)
                );
                // A点
                path.quadTo(
                        (startCircleX + endCircleX) / 2, (startCircleY + endCircleY) / 2,
                        (float) (startCircleX - curRadius * sin), (float) (startCircleY - curRadius * cos)
                );
                canvas.drawPath(path, paint);
            }

            // 绘制文字
            float textH = -textFontMetrics.ascent - textFontMetrics.descent;
            canvas.drawText(text, endCircleX, endCircleY + textH / 2, textPaint);


        } else { // 非触摸状态
            if (curRadius > 0) {
                startCircleX = curRadius;
                startCircleY = originHeight - curRadius;
                canvas.drawCircle(startCircleX, startCircleY, curRadius, paint);
                if (curRadius == originRadius) { // 只有在恢复正常的情况下才显示文字
                    // 绘制文字
                    float textH = -textFontMetrics.ascent - textFontMetrics.descent;
                    canvas.drawText(text, startCircleX, startCircleY + textH / 2, textPaint);
                }
            }
        }
        Log.d(TAG, "circleX: " + startCircleX + ", circleY: " + startCircleY + ", curRadius: " + curRadius);
    }

    float downX = Float.MAX_VALUE;
    float downY = Float.MAX_VALUE;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
//        Logger.d(TAG, "onTouchEvent: " + event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isTouched = true;
                this.setLayoutParams(newLp);
                endPoint.x = (int) downX;
                endPoint.y = (int) downY;

//                changeViewHeight(this, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                postInvalidate();

                downX = event.getX() + location[0];
                downY = event.getY() + location[1];
//                Logger.d(TAG, String.format("downX: %f, downY: %f", downX, downY));

                break;
            case MotionEvent.ACTION_MOVE:
                // 计算直角边和斜边（用于计算绘制两圆之间的填充去）
                triangle.deltaX = event.getX() - downX;
                triangle.deltaY = -1 * (event.getY() - downY); // y轴方向相反，所有需要取反
                double distance = Math.sqrt(triangle.deltaX * triangle.deltaX + triangle.deltaY * triangle.deltaY);
                triangle.hypotenuse = distance;
//                Logger.d(TAG, "triangle: " + triangle);
                refreshCurRadiusByMoveDistance((int) distance);

                endPoint.x = (int) event.getX();
                endPoint.y = (int) event.getY();

                postInvalidate();

                break;
            case MotionEvent.ACTION_UP:
                isTouched = false;
                this.setLayoutParams(originLp);

                if (isArrivedMaxMoved) { // 触发事件
                    changeViewHeight(this, originWidth, originHeight);
                    postInvalidate();
                    if (null != onDraggableFlagViewListener) {
                        onDraggableFlagViewListener.onFlagDismiss(this);
                    }
                    Log.d(TAG, "触发事件...");
                    resetAfterDismiss();
                } else { // 还原
                    changeViewHeight(this, originWidth, originHeight);
                    startRollBackAnimation(500/*ms*/);
                }

                downX = Float.MAX_VALUE;
                downY = Float.MAX_VALUE;
                break;
        }

        return true;
    }

    /**
     * 触发事件之后重置
     */
    private void resetAfterDismiss() {
        this.setVisibility(GONE);
        text = "";
        isArrivedMaxMoved = false;
        curRadius = originRadius;
        postInvalidate();
    }

    /**
     * 根据移动的距离来刷新原来的圆半径大小
     *
     * @param distance
     */
    private void refreshCurRadiusByMoveDistance(int distance) {
        if (distance > maxMoveLength) {
            isArrivedMaxMoved = true;
            curRadius = 0;
        } else {
            isArrivedMaxMoved = false;
            float calcRadius = (1 - 1f * distance / maxMoveLength) * originRadius;
            float maxRadius = dipToPixels(2);
            curRadius = (int) Math.max(calcRadius, maxRadius);
//            Logger.d(TAG, "[refreshCurRadiusByMoveDistance]curRadius: " + curRadius + ", calcRadius: " + calcRadius + ", maxRadius: " + maxRadius);
        }

    }

    /**
     * 改变某控件的高度
     *
     * @param view
     * @param height
     */
    private void changeViewHeight(View view, int width, int height) {
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if (null == lp) {
            lp = originLp;
        }
        lp.width = width;
        lp.height = height;
        view.setLayoutParams(lp);
    }

    /**
     * 回滚状态动画
     */
    private void startRollBackAnimation(long duration) {
        ValueAnimator rollBackAnim = ValueAnimator.ofFloat(curRadius, originRadius);
        rollBackAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                curRadius = (int) value;
                postInvalidate();
            }
        });
        rollBackAnim.setInterpolator(new BounceInterpolator()); // 反弹效果
        rollBackAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                BadgeView.this.clearAnimation();
            }
        });
        rollBackAnim.setDuration(duration);
        rollBackAnim.start();
    }

    /**
     * 计算四个坐标的三角边关系
     */
    class Triangle {
        double deltaX;
        double deltaY;
        double hypotenuse;

        @Override
        public String toString() {
            return "Triangle{" +
                    "deltaX=" + deltaX +
                    ", deltaY=" + deltaY +
                    ", hypotenuse=" + hypotenuse +
                    '}';
        }
    }

    public String getText() {
        return text;
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
}
