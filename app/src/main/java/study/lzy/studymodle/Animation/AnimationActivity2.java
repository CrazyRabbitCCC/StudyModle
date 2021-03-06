package study.lzy.studymodle.Animation;// @author: lzy  time: 2016/09/18.

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
        import android.view.View.OnClickListener;
        import android.view.animation.Animation;
        import android.view.animation.AnimationUtils;
        import android.widget.Button;
        import android.widget.ImageView;

import study.lzy.studymodle.R;

public class AnimationActivity2 extends AppCompatActivity {
    private Button rotateButton = null;
    private Button scaleButton = null;
    private Button alphaButton = null;
    private Button translateButton = null;
    private ImageView image = null;
    private Button doubleButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.annima_activity);

        rotateButton = (Button) findViewById(R.id.rotateButton);
        scaleButton = (Button) findViewById(R.id.scaleButton);
        alphaButton = (Button) findViewById(R.id.alphaButton);
        translateButton = (Button) findViewById(R.id.translateButton);
        doubleButton = (Button) findViewById(R.id.doubleButton);
        image = (ImageView) findViewById(R.id.image);

        rotateButton.setOnClickListener(new RotateButtonListener());
        scaleButton.setOnClickListener(new ScaleButtonListener());
        alphaButton.setOnClickListener(new AlphaButtonListener());
        translateButton.setOnClickListener(new TranslateButtonListener());
        doubleButton.setOnClickListener(new DoubleButtonListener());

        AnimationDrawable animation = (AnimationDrawable) getResources().getDrawable(R.drawable.animation_drawable);

        image.setImageDrawable(animation);
        animation.setOneShot(false);
        animation.start();
    }

    class AlphaButtonListener implements OnClickListener {
        public void onClick(View v) {
            // 使用AnimationUtils装载动画配置文件
            Animation animation = AnimationUtils.loadAnimation(
                    AnimationActivity2.this, R.anim.alpha);
            // 启动动画
            image.startAnimation(animation);
        }
    }

    class RotateButtonListener implements OnClickListener {
        public void onClick(View v) {
            Animation animation = AnimationUtils.loadAnimation(
                    AnimationActivity2.this, R.anim.rotate);
            image.startAnimation(animation);
        }
    }

    class ScaleButtonListener implements OnClickListener {
        public void onClick(View v) {
            Animation animation = AnimationUtils.loadAnimation(
                    AnimationActivity2.this, R.anim.scale);
            image.startAnimation(animation);
        }
    }

    class TranslateButtonListener implements OnClickListener {
        public void onClick(View v) {
            Animation animation = AnimationUtils.loadAnimation(AnimationActivity2.this, R.anim.translate);
            image.startAnimation(animation);
        }
    }
    class DoubleButtonListener implements OnClickListener {
        public void onClick(View v) {
            // 使用AnimationUtils装载动画配置文件
            Animation animation = AnimationUtils.loadAnimation(AnimationActivity2.this, R.anim. doubleani);
            // 启动动画
            image.startAnimation(animation);
        }
    }
}
