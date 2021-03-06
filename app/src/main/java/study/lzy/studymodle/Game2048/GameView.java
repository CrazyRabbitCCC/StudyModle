package study.lzy.studymodle.Game2048;// @author: lzy  time: 2016/09/19.

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

public class GameView extends GridLayout {

    private static final String TAG = "GameView";

    private List<int[][]> lastData = new ArrayList<>();
    List<Integer> data;
    private int cardSide = 0;
    private Pixes downPix, offSetPix;
    private int Score=0;
    private Context mContext;
    private CardView[][] cards = new CardView[4][4];
    private boolean ScoreChanged=false;
    private boolean success=false;

    public GameView(Context context) {
        this(context, null);
    }

    public GameView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        setBackgroundColor(Color.rgb(0xc3, 0xb1, 0xd4));
        setColumnCount(4);

    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        cardSide = (Math.min(widthSpec, heightSpec)) / 4;
        addCard(cardSide);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downPix = new Pixes(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                offSetPix = new Pixes(event.getX() - downPix.x, event.getY() - downPix.y);
                if (Math.abs(offSetPix.x) <= 5 && Math.abs(offSetPix.y) <= 5)
                    break;

                if (!checkResult()){
                    Snackbar.make(this,"游戏结束，你的成绩是"+getScore(),Snackbar.LENGTH_LONG)
                            .setAction("重试", new OnClickListener() {
                                @Override public void onClick(View v) {
                                    restart();
                                }
                            });
                    break;
                }
                lastGameNum = getGameNum();
                if (Math.abs(offSetPix.x) > Math.abs(offSetPix.y)) {
                    //左右
                    if (offSetPix.x < -5) {
                        Log.i(TAG, "left");
                        left();
                    } else {
                        Log.i(TAG, "right");
                        right();
                    }
                } else {
                    //上下
                    if (offSetPix.y < -5) {
                        Log.i(TAG, "top");
                        top();
                    } else {
                        Log.i(TAG, "bottom");
                        bottom();

                    }
                }
                if (!move){
                    Integer[] gameNum= getGameNum();
                    for (int i = 0; i < 16; i++) {
                        if (!lastGameNum[i].equals(gameNum[i])){
                            move=true;
                            break;
                        }
                    }
                }
                if (move) {
                    change =true;
                    addCard();
                    move=false;
                }
                break;
        }


        return true;
    }
private boolean move = false;
    private void bottom() {
        for (int i = 0; i < 4; i++) {
            int x = 3;
            int num = 0;
            for (int j = 3; j > 0; j--) {
                if (cards[i][j].getNum() > 0) {
                    num = (cards[i][j].getNum());
                    for (int k = j - 1; k > -1; k--)
                        if (cards[i][k].getNum() > 0) {
                            if (cards[i][j].equals(cards[i][k])) {
                                num += cards[i][j].getNum();
                                Score+=2* cards[i][j].getNum();
                                ScoreChanged=true;
                                cards[i][k].setNum(0);
                                move=true;
                                change=true;
                            }
                            break;
                        }
                    cards[i][j].setNum(0);
                    cards[i][x].setNum(num);
                    x--;
                }
            }
            num = (cards[i][0].getNum());
            cards[i][0].setNum(0);
            cards[i][x].setNum(num);
        }
    }

    private void top() {
        for (int i = 0; i < 4; i++) {
            int x = 0;
            int num = 0;
            for (int j = 0; j < 3; j++) {
                if (cards[i][j].getNum() > 0) {
                    num = (cards[i][j].getNum());
                    for (int k = j + 1; k < 4; k++)
                        if (cards[i][k].getNum() > 0) {
                            if (cards[i][j].equals(cards[i][k])) {
                                num += cards[i][j].getNum();
                                Score+=2* cards[i][j].getNum();
                                ScoreChanged=true;
                                cards[i][k].setNum(0);
                                move=true;
                                change=true;
                            }
                            break;
                        }
                    cards[i][j].setNum(0);
                    cards[i][x].setNum(num);
                    x++;
                }
            }
            num = (cards[i][3].getNum());
            cards[i][3].setNum(0);
            cards[i][x].setNum(num);
        }
    }

    private void right() {
        for (int i = 0; i < 4; i++) {
            int x = 3;
            int num = 0;
            for (int j = 3; j > 0; j--) {
                if (cards[j][i].getNum() > 0) {
                    num = (cards[j][i].getNum());
                    for (int k = j - 1; k > -1; k--)
                        if (cards[k][i].getNum() > 0) {
                            if (cards[j][i].equals(cards[k][i])) {
                                num += cards[k][i].getNum();
                                Score+=2* cards[i][j].getNum();
                                ScoreChanged=true;
                                cards[k][i].setNum(0);
                                move=true;
                                change=true;
                            }
                            break;
                        }
                    cards[j][i].setNum(0);
                    cards[x][i].setNum(num);
                    x--;
                }
            }
            num = (cards[0][i].getNum());
            cards[0][i].setNum(0);
            cards[x][i].setNum(num);
        }
    }

    private void left() {
        for (int i = 0; i < 4; i++) {
            int x = 0;
            int num = 0;
            for (int j = 0; j < 3; j++) {
                if (cards[j][i].getNum() > 0) {
                    num = cards[j][i].getNum();
                    for (int k = j + 1; k < 4; k++) {
                        if (cards[k][i].getNum() > 0) {
                            if (cards[j][i].equals(cards[k][i])) {
                                num += cards[k][i].getNum();
                                Score+=2* cards[i][j].getNum();
                                ScoreChanged=true;
                                cards[k][i].setNum(0);
                                move=true;
                                change=true;
                            }
                            break;
                        }
                    }
                    cards[j][i].setNum(0);
                    cards[x][i].setNum(num);
                    x++;
                }
            }
            num = (cards[3][i].getNum());
            cards[3][i].setNum(0);
            cards[x][i].setNum(num);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {

        cardSide = (Math.min(w, h)) / 4;
        addCard(cardSide);
        super.onSizeChanged(w, h, oldW, oldH);
    }

    private void addCard(int cardSide) {
        removeAllViews();
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(cardSide, cardSide);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                CardView card = new CardView(mContext);
                addView(card, lp);
                card.setNum(0);
                if ((i*4+j)!=0)
                    card.setNum(DoNum(2, (i*4+j+1)));
                cards[j][i] = card;
                change=true;
//                Log.e(TAG,"" + DoNum(2, (i*4+j)));
            }
        }
        addCard();
    }
    private boolean change;
    private int emptyNum= 16;
    private int getEmptyCardNum(){
        if (!change) {
            return emptyNum;
        }
        int emptyNum = 0;
        for (int i = 0; i < 15; i++){
            if (cards[i/4][i%4].getNum()==0)
                emptyNum++;
            if (!success&&cards[i/4][i%4].getNum()==2048) {
                Snackbar.make(this, "成功", Snackbar.LENGTH_SHORT).show();
                success = true;
            }
        }
        change =false;
        this.emptyNum=emptyNum;
        return emptyNum;
    }
    public boolean checkResult(){
        if(getEmptyCardNum()>0)
            return true;
        for (int i = 0; i < 4; i++) {

            for (int j=0;j<3;j++){
                int num = cards[i][j].getNum();
                if (i<3&&cards[i+1][j].getNum()==num)
                        return true;

                if (cards[i][j+1].getNum()==num)
                    return true;
            }
        }
        return false;
    }

    private Integer[] lastGameNum = new Integer[]{
            0,0,0,0,
            0,0,0,0,
            0,0,0,0,
            0,0,0,0};

    public Integer[] getGameNum(){
        Integer[] gameNum = new Integer[16];
        for (int i = 0; i < 16; i++) {
            gameNum[i] = cards[i/4][i%4].getNum();
        }
        return gameNum;
    }



    private void addCard() {
        int emptyNum = getEmptyCardNum();
        if (!checkResult()){
            Snackbar.make(this,"游戏结束，你的成绩是"+getScore(),Snackbar.LENGTH_LONG)
                    .setAction("重试", new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            restart();
                        }
                    });
            return;
        }

        int a = (int) (Math.random() * 10);
        int b = (int) (Math.random() * (emptyNum-1));
        int c = 0;
        for (int k = 0; k < 16; k++) {
            int i = k % 4;
            int j = k / 4;
            if (cards[j][i].getNum() <= 0)
                if (c == b) {
                    cards[j][i].setNum(a > 4 ? 2 : 4);
                    change=true;
                    break;
                } else {
                    c++;
                }
        }
        if (!checkResult()){
            Snackbar.make(this,"游戏结束，你的成绩是"+getScore(),Snackbar.LENGTH_LONG)
                    .setAction("重试", new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            restart();
                        }
                    });
        }
//        lastData.clear();
//        lastData.addAll(data);
    }

    public int getScore() {
        ScoreChanged=false;
        return Score;

    }

    public void restart(){
        addCard(cardSide);
        Score=0;
        ScoreChanged=false;
        success=false;
    }

    public boolean isScoreChanged() {
        return ScoreChanged;
    }

//x^Y
   private int DoNum(int X,int Y){
       int num=1;
       if (Y>0)
       return X^Y;
//       if (Y>0)
//           for (int i=0;i<Y;i++)
//               num*=X;

       return num;
   }

    class Pixes {
        public Pixes(float x, float y) {
            this.x = x;
            this.y = y;
        }
        float x = 0f, y = 0f;
    }
}
