package study.lzy.studymodle.Animation;// @author: lzy  time: 2016/09/20.


import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import study.lzy.studymodle.R;

public class AnimationActivity3 extends AppCompatActivity {
    private Button rotateButton = null;
    private Button scaleButton = null;
    private Button alphaButton = null;
    private Button translateButton = null;
    private ImageView image = null;
    private Button doubleButton;
    private int width;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.annima_activity);

        rotateButton = (Button) findViewById(R.id.rotateButton);
        scaleButton = (Button) findViewById(R.id.scaleButton);
        alphaButton = (Button) findViewById(R.id.alphaButton);
        translateButton = (Button) findViewById(R.id.translateButton);
        doubleButton = (Button) findViewById(R.id.doubleButton);
        doubleButton.setText("自定义");
        image = (ImageView) findViewById(R.id.image);

        rotateButton.setOnClickListener(new RotateButtonListener());
        scaleButton.setOnClickListener(new ScaleButtonListener());
        alphaButton.setOnClickListener(new AlphaButtonListener());
        translateButton.setOnClickListener(new TranslateButtonListener());
        doubleButton.setOnClickListener(new DoubleButtonListener());
        WindowManager windowManager = getWindowManager();
        DisplayMetrics outMetrics=new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetrics);

        AnimationDrawable animation = (AnimationDrawable) getResources().getDrawable(R.drawable.animation_drawable);

        image.setImageDrawable(animation);
        animation.setOneShot(false);
        animation.start();


        width = outMetrics.widthPixels;

    }

    class AlphaButtonListener implements OnClickListener {
        public void onClick(View v) {
            // 使用AnimationUtils装载动画配置文件
            ObjectAnimator alpha = ObjectAnimator.ofFloat(image, "alpha", 1.0f, 0, 0.5f);
            alpha.setDuration(5000);
            alpha.start();
        }
    }

    class RotateButtonListener implements OnClickListener {
        public void onClick(View v) {
            ObjectAnimator rotation = ObjectAnimator.ofFloat(image, "rotation", 0, 360f, 120, 240,0);
            rotation.setDuration(5000);
            rotation.start();
        }
    }

    class ScaleButtonListener implements OnClickListener {
        public void onClick(View v) {

            PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 1.0f, 0, 0.5f);
            PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 1.0f, 0, 0.5f);
            ObjectAnimator scale = ObjectAnimator.ofPropertyValuesHolder(image,scaleX,scaleY);
            scale.setDuration(5000);
            scale.start();
        }
    }

    class TranslateButtonListener implements OnClickListener {
        public void onClick(View v) {
            PropertyValuesHolder translationX = PropertyValuesHolder.ofFloat("translationX", 0, 100f, 50f);
            PropertyValuesHolder translationY = PropertyValuesHolder.ofFloat("translationY", 0, 100f, 50f);
            ObjectAnimator translation = ObjectAnimator.ofPropertyValuesHolder(image, translationX, translationY);
            translation.setDuration(5000);
            translation.start();
        }
    }

    class DoubleButtonListener implements OnClickListener {
        public void onClick(View v) {
            ValueAnimator valueAnimator = new ValueAnimator();
            valueAnimator.setObjectValues(new PointF());
            valueAnimator.setDuration(10000);
            valueAnimator.setEvaluator(new TypeEvaluator<PointF>() {
                @Override
                public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
                    PointF pointF = new PointF();
                    pointF.x = fraction * (width - image.getWidth());
                    pointF.y = (float) Math.sqrt(pointF.x * (width - image.getWidth()));
                    return pointF;
                }
            });
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    PointF value = (PointF) animation.getAnimatedValue();
                    image.setTranslationX(value.x);
                    image.setTranslationY(value.y);
                }
            });
            valueAnimator.start();


//            PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 1.0f, 0, 0.5f);
//            PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 1.0f, 0, 0.5f);
//            PropertyValuesHolder translationX = PropertyValuesHolder.ofFloat("translationX", 0, 100f, 50f);
//            PropertyValuesHolder translationY = PropertyValuesHolder.ofFloat("translationY", 0, 100f, 50f);
//            ObjectAnimator Double =ObjectAnimator.ofPropertyValuesHolder(image,scaleX,scaleY, translationX, translationY);
//            Double.setDuration(5000);
//            Double.start();
        }
    }
}
