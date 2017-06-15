package study.lzy.studymodle.Game2048;// @author: lzy  time: 2016/09/19.

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import study.lzy.studymodle.R;

public class Game2048Activity extends AppCompatActivity{

    private TextView score;
//    private GameView game;
    private GridLayout mGridLayout;
    private CoordinatorLayout coordinator;
    private GameUtil game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2048);

        score = (TextView) findViewById(R.id.score);
        coordinator = (CoordinatorLayout) findViewById(R.id.coordinator);
//        game = (GameView) findViewById(R.id.game);
        mGridLayout = (GridLayout) findViewById(R.id.game);
        game = new GameUtil(mGridLayout,this);

        score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   game.restart();
                handler.sendEmptyMessage(0);*/
                Snackbar.make(coordinator,"重新开始？",Snackbar.LENGTH_SHORT).setAction("重新开始", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        game.restart();
                        handler.sendEmptyMessage(0);
                    }
                }).show();
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    if (game.isScoreChanged())
                        handler.sendEmptyMessage(1);
                }
            }
        }).start();
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            score.setText("Score:"+game.getScore());
        }
    };
}
