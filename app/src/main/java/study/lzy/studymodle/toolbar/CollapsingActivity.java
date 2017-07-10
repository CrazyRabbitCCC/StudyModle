package study.lzy.studymodle.toolbar;

import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import study.lzy.studymodle.R;

/**
 * @author Gavin
 * @date 2017/06/19.
 */

public class CollapsingActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collapsing);
        initView();
        toolbar.setTitleTextColor(Color.WHITE);//
        setSupportActionBar(toolbar);
        AnimationDrawable animation = (AnimationDrawable) getResources().getDrawable(R.drawable.animation_drawable);
        imageView.setImageDrawable(animation);
        animation.setOneShot(false);
        animation.start();
    }

    private AppBarLayout appBar;
    private CollapsingToolbarLayout collapsingToolbar;
    private Toolbar toolbar;
    private ImageView imageView;

    private void initView() {
        appBar = (AppBarLayout) findViewById(R.id.app_bar);
        collapsingToolbar = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        imageView = (ImageView) findViewById(R.id.imageView);
    }

}
