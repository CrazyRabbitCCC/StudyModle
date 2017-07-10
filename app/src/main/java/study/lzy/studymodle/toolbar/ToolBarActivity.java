package study.lzy.studymodle.toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import study.lzy.studymodle.R;

/**
 * @author Gavin
 * @date 2017/06/19.
 */

public class ToolBarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);
        initView();
        toolbar.setTitleTextColor(Color.WHITE);//
        setSupportActionBar(toolbar);
    }

    private AppBarLayout appbarLayout;
    private Toolbar toolbar;
    private TabLayout tabLayout;

    private void initView(){
//    appbarLayout = (AppBarLayout) findViewById(R.id.appbar_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
//    tabLayout = (TabLayout) findViewById(R.id.tabLayout);
    }

}
