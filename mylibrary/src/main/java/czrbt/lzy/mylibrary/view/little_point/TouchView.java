package czrbt.lzy.mylibrary.view.little_point;
// @author: lzy  time: 2016/09/28.


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.text.TextPaint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import czrbt.lzy.mylibrary.R;
import czrbt.lzy.mylibrary.utils.PixelsUtils;
import czrbt.lzy.mylibrary.utils.WindowUtils;

/**
 * 辅助messageView的view
 */
public class TouchView extends View {
    PointF start = new PointF();
    PointF end = new PointF();
    Paint paint;
    TextPaint textPaint;
    private Path path = new Path();
    private int curRadius;
    private String text;
    private Triangle triangle;
    private Paint.FontMetrics textFontMetrics;
    private int maxLength;
    private boolean brok=false;
    private ImageView image;
    private AnimationDrawable animation;
    private static int statusBarHeight=0;
    private Context mContext;

    public TouchView(Context context, PointF start, MessageView view) {
        super(context);
        mContext=context;
        this.start = start;
        end.set(start);
        paint = view.getPaint();
        textPaint = view.getTextPaint();
        curRadius = view.getOriginRadius();
        text = view.getText();
        textFontMetrics = view.getTextFontMetrics();
        maxLength=view.getMaxLength();
        image=new ImageView(context);
        init();
    }

    private void init() {
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        triangle = new Triangle();
        image.setLayoutParams(new ViewGroup.LayoutParams(curRadius*2,curRadius*2));
        animation= (AnimationDrawable) getResources().getDrawable(R.drawable.tip_anim);
        image.setImageDrawable(animation);
    }

    public void move(PointF end) {
        this.end = end;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT);

        float startCircleX = 0f, startCircleY = 0f;
        // 触摸状态

        startCircleX = start.x + curRadius;
        startCircleY = start.y - curRadius+ WindowUtils.getStatusBarHeight(mContext);
        // 绘制手指跟踪的圆形
        float endCircleX = end.x;
        float endCircleY = end.y+WindowUtils.getStatusBarHeight(mContext);
        canvas.drawCircle(endCircleX, endCircleY, curRadius, paint);
        if (!brok) {
        // 没有达到拉伸最大值
        path.reset();
        triangle = new Triangle();
        triangle.deltaX = endCircleX - startCircleX;
        triangle.deltaY = -1 * (endCircleY - startCircleY); // y轴方向相反，所有需要取反
        double distance = Math.sqrt(triangle.deltaX * triangle.deltaX + triangle.deltaY * triangle.deltaY);
        triangle.hypotenuse = distance;
            if (distance < maxLength) {
                // 绘制原来的圆形（触摸移动的时候半径会不断变化）
                double sin = triangle.deltaY / triangle.hypotenuse;
                double cos = triangle.deltaX / triangle.hypotenuse;
                int NewRadius = 0;
                distance = distance - 1;
                float calcRadius;
                calcRadius = (float) (1 - distance / maxLength) * curRadius;

                float maxRadius = PixelsUtils.dipToPixels(mContext,2);
                NewRadius = (int) Math.max(calcRadius, maxRadius);
                canvas.drawCircle(startCircleX, startCircleY, NewRadius, paint);
                // A点
                path.moveTo(
                        (float) (startCircleX - NewRadius * sin),
                        (float) (startCircleY - NewRadius * cos)
                );
                // B点
                path.lineTo(
                        (float) (startCircleX + NewRadius * sin),
                        (float) (startCircleY + NewRadius * cos)
                );
                // C点
                path.quadTo(
                        (startCircleX + endCircleX) / 2, (startCircleY + endCircleY) / 2,
                        (float) (endCircleX + curRadius * sin), (float) (endCircleY + curRadius * cos)
                );
                // D点
                path.lineTo(
                        (float) (endCircleX - curRadius * sin),
                        (float) (endCircleY - curRadius * cos)
                );
                // A点
                path.quadTo(
                        (startCircleX + endCircleX) / 2, (startCircleY + endCircleY) / 2,
                        (float) (startCircleX - NewRadius * sin), (float) (startCircleY - NewRadius * cos)
                );
                canvas.drawPath(path, paint);
                brok = false;
            } else {
                brok = true;
            }
        }

        // 绘制文字
        float textH = -textFontMetrics.ascent - textFontMetrics.descent;
        canvas.drawText(text, endCircleX, endCircleY + textH / 2, textPaint);
    }


    private class Triangle {
        float deltaX;
        float deltaY;
        double hypotenuse;
    }

    public void setStart(PointF start) {
        this.start = start;
    }


    public boolean isBrok() {
        return brok;
    }

    public void startAnimate(){
        if (image.getParent()==null)
            ((ViewGroup)getParent()).addView(image);
        image.setVisibility(VISIBLE);
        image.setTranslationX(end.x);
       image.setTranslationY(end.y);
        animation.start();
        Handler handler=new Handler();
        int duration=0;
        for (int i = 0; i<animation.getNumberOfFrames(); i++)
            duration += animation.getDuration(i);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                image.setVisibility(GONE);
                ViewGroup parent = (ViewGroup) image.getParent();
                if (parent!=null)
                    parent.removeView(image);
            }
        },duration);
    }


}
