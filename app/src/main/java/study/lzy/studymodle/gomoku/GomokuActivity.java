package study.lzy.studymodle.gomoku;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridLayout;

import study.lzy.studymodle.R;

/**
 * @author Gavin
 * @date 2017/06/21.
 */

public class GomokuActivity extends AppCompatActivity{
    GridLayout mGridLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2048);
        mGridLayout = (GridLayout) findViewById(R.id.game);
        GomokuUtil gomokuUtil = new GomokuUtil(this);
        gomokuUtil.addToGrid(mGridLayout);

    }
}
