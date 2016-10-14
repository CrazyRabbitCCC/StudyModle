package study.lzy.studymodle.Animation;// @author: lzy  time: 2016/09/20.

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

public class AnimationActivity4 extends AppCompatActivity {
    private Button rotateButton = null;
    private Button scaleButton = null;
    private Button alphaButton = null;
    private Button translateButton = null;
    private ImageView image = null;
    private Button doubleButton;
    private AnimationDrawable animation;

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
        rotateButton.setText("开始");
        scaleButton.setOnClickListener(new ScaleButtonListener());
        scaleButton.setText("暂停");
        alphaButton.setVisibility(View.GONE);
        translateButton.setVisibility(View.GONE);
        doubleButton.setVisibility(View.GONE);
//        animation = new AnimationDrawable();
//        animation.addFrame(getResources().getDrawable(R.drawable.horse_a),100);
//        animation.addFrame(getResources().getDrawable(R.drawable.horse_b),100);
//        animation.addFrame(getResources().getDrawable(R.drawable.horse_c),100);
//        animation.addFrame(getResources().getDrawable(R.drawable.horse_d),100);
//        animation.addFrame(getResources().getDrawable(R.drawable.horse_e),100);
//        animation.addFrame(getResources().getDrawable(R.drawable.horse_f),100);
//        animation.addFrame(getResources().getDrawable(R.drawable.horse_g),100);
//        animation.addFrame(getResources().getDrawable(R.drawable.horse_h),100);
        /*
        animation.addFrame(getResources().getDrawable(R.drawable.horse_i),50);
        animation.addFrame(getResources().getDrawable(R.drawable.horse_j),50);
        animation.addFrame(getResources().getDrawable(R.drawable.horse_k),50);
        animation.addFrame(getResources().getDrawable(R.drawable.horse_l),50);
        animation.addFrame(getResources().getDrawable(R.drawable.horse_m),50);
        animation.addFrame(getResources().getDrawable(R.drawable.horse_n),50);
        animation.addFrame(getResources().getDrawable(R.drawable.horse_o),50);
        animation.addFrame(getResources().getDrawable(R.drawable.horse_p),50);
        */
        animation= (AnimationDrawable) getResources().getDrawable(R.drawable.animation_drawable);

        image.setImageDrawable(animation);




    }

    class RotateButtonListener implements OnClickListener {
        public void onClick(View v) {
            animation.setOneShot(false);
            animation.start();

        }
    }

    class ScaleButtonListener implements OnClickListener {
        public void onClick(View v) {
            animation.stop();
        }
    }
}
