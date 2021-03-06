package study.lzy.studymodle.chess;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridLayout;

import study.lzy.studymodle.R;

/**
 * @author Gavin
 * @date 2017/06/23.
 */

public class InterChessActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2048);
        GridLayout game= (GridLayout) findViewById(R.id.game);
        InterChessUtil interChess = new InterChessUtil(this,game);
    }
}
