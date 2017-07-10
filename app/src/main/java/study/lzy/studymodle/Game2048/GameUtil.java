package study.lzy.studymodle.Game2048;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

import study.lzy.studymodle.Utils.BaseUtil;

/**
 * @author Gavin
 * @date 2017/06/14.
 */

public class GameUtil implements View.OnTouchListener {
    private static final String TAG = "GameUtil";

    private List<int[][]> lastData = new ArrayList<>();
    List<Integer> data;
    private int cardSide = 0;
    private Pixes downPix, offSetPix;
    private int Score = 0;
    private Context mContext;
    private CardView[][] cards = new CardView[4][4];
    private boolean ScoreChanged = false;
    private boolean success = false;
    private GridLayout gameLayout;

    private PropertyValuesHolder left[], right[], top[], bottom[];


    public GameUtil(GridLayout gridLayout, Context context) {
        gameLayout = gridLayout;
        mContext = context;
        init();
    }

    List<ObjectAnimator> animatorList = new ArrayList<>();

    private void init() {
        gameLayout.setBackgroundColor(Color.rgb(0xc3, 0xb1, 0xd4));
        gameLayout.setColumnCount(4);
        left = new PropertyValuesHolder[3];
        right = new PropertyValuesHolder[3];
        top = new PropertyValuesHolder[3];
        bottom = new PropertyValuesHolder[3];

        DisplayMetrics screenMetrics = BaseUtil.getScreenMetrics(mContext);
        cardSide = screenMetrics.widthPixels / 4;
        for (int i = 0; i < 3; i++) {
            left[i] = PropertyValuesHolder.ofFloat("translationX", 0, -cardSide * (i + 1));
            right[i] = PropertyValuesHolder.ofFloat("translationX", 0, cardSide * (i + 1));
            top[i] = PropertyValuesHolder.ofFloat("translationY", 0, -cardSide * (i + 1));
            bottom[i] = PropertyValuesHolder.ofFloat("translationY", 0, cardSide * (i + 1));
        }
        addCard(cardSide);
        gameLayout.setOnTouchListener(this);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return onTouchEvent(event);
    }

    private boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downPix = new Pixes(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                offSetPix = new Pixes(event.getX() - downPix.x, event.getY() - downPix.y);
                if (Math.abs(offSetPix.x) <= 5 && Math.abs(offSetPix.y) <= 5)
                    break;

                if (!checkResult()) {
                    Snackbar.make(gameLayout, "游戏结束，你的成绩是" + getScore(), Snackbar.LENGTH_LONG)
                            .setAction("重试", v -> restart()).show();
                    break;
                }
                lastGameNum = getGameNum();
                animatorList.clear();
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
                if (!move) {
//                    Integer[] gameNum= getGameNum();
                    for (int i = 0; i < 16; i++) {
                        if (!lastGameNum[i].equals(gameNum[i])) {
                            move = true;
                            break;
                        }
                    }
                }
                if (move) {
                    animatorList.get(0).addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            for (int i = 0; i < gameNum.length; i++) {
                                cards[i / 4][i % 4].setNum(gameNum[i]);
                            }
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                    for (ObjectAnimator a : animatorList) {
                        a.setDuration(1000);
                        a.start();
                    }
                    change = true;
                    addCard();
                    move = false;
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
                int xj = j;
                if (cards[i][j].getNum() > 0) {
                    num = (cards[i][j].getNum());
                    for (int k = j - 1; k > -1; k--) {
                        if (cards[i][k].getNum() > 0) {
                            if (cards[i][j].equals(cards[i][k])) {
                                num += cards[i][j].getNum();
                                Score += 2 * cards[i][j].getNum();
                                ScoreChanged = true;
                                gameNum[i * 4 + k] = 0;
                                xj = k;
                                move = true;
                                change = true;
                            }
                            break;
                        }
                    }
                    gameNum[i * 4 + j] = 0;
                    gameNum[i * 4 + x] = num;
                    if (x != j && num != 0) {
                        if (j != xj) {
                            animatorList.add(ObjectAnimator.ofPropertyValuesHolder(cards[i][j], bottom[Math.abs(x - j) - 1]));
                            animatorList.add(ObjectAnimator.ofPropertyValuesHolder(cards[i][xj], bottom[Math.abs(x - xj) - 1]));
                            j = xj;
                        } else
                            animatorList.add(ObjectAnimator.ofPropertyValuesHolder(cards[i][xj], bottom[Math.abs(x - xj) - 1]));
                    }
                    x--;
                }
            }
            if (x != 0 && gameNum[i * 4] != 0) {
                int num3 = gameNum[i * 4];
                gameNum[i * 4] = 0;
                gameNum[i * 4 + x] = num3;
                animatorList.add(ObjectAnimator.ofPropertyValuesHolder(cards[i][0], bottom[Math.abs(x) - 1]));
            }
        }
    }

    private void top() {
        for (int i = 0; i < 4; i++) {
            int x = 0;
            int num = 0;
            for (int j = 0; j < 3; j++) {
                if (cards[i][j].getNum() > 0) {
                    int xj = j;
                    num = (cards[i][j].getNum());
                    for (int k = j + 1; k < 4; k++) {
                        int num2 = cards[i][k].getNum();
                        if (num2 > 0) {
                            if (cards[i][j].equals(cards[i][k])) {
                                xj = k;
                                num += cards[i][j].getNum();
                                Score += 2 * cards[i][j].getNum();
                                ScoreChanged = true;
                                gameNum[i * 4 + k] = 0;
                                move = true;
                                change = true;
                            }
                            break;
                        }
                    }
                    gameNum[i * 4 + j] = 0;
                    gameNum[i * 4 + x] = num;
                    if (x != j && num != 0) {
                        if (j != xj) {
                            animatorList.add(ObjectAnimator.ofPropertyValuesHolder(cards[i][j], top[Math.abs(x - j) - 1]));
                            animatorList.add(ObjectAnimator.ofPropertyValuesHolder(cards[i][xj], top[Math.abs(x - xj) - 1]));
                            j = xj;
                        } else
                            animatorList.add(ObjectAnimator.ofPropertyValuesHolder(cards[i][xj], top[Math.abs(x - j) - 1]));
                    }
                    x++;
                }
            }
            if (x != 3 && gameNum[i * 4 + 3] != 0) {
                int num3 = gameNum[i * 4 + 3];
                gameNum[i * 4 + 3] = 0;
                gameNum[i * 4 + x] = num3;
                animatorList.add(ObjectAnimator.ofPropertyValuesHolder(cards[i][3], top[Math.abs(x - 3) - 1]));
            }
        }
    }

    private void right() {
        for (int i = 0; i < 4; i++) {
            int x = 3;
            int num = 0;
            for (int j = 3; j > 0; j--) {
                int xj = j;
                if (cards[j][i].getNum() > 0) {
                    num = (cards[j][i].getNum());
                    for (int k = j - 1; k > -1; k--)
                        if (cards[k][i].getNum() > 0) {
                            if (cards[j][i].equals(cards[k][i])) {
                                num += cards[k][i].getNum();
                                Score += 2 * cards[i][j].getNum();
                                ScoreChanged = true;
                                gameNum[k * 4 + i] = (0);
                                xj = k;
                                move = true;
                                change = true;
                            }
                            break;
                        }
                    gameNum[j * 4 + i] = 0;
                    gameNum[x * 4 + i] = num;
                    if (x != j) {
                        if (j != xj) {
                            animatorList.add(ObjectAnimator.ofPropertyValuesHolder(cards[j][i], right[Math.abs(x - j) - 1]));
                            animatorList.add(ObjectAnimator.ofPropertyValuesHolder(cards[xj][i], right[Math.abs(x - xj) - 1]));
                            j = xj;
                        } else
                            animatorList.add(ObjectAnimator.ofPropertyValuesHolder(cards[j][i], right[Math.abs(x - j) - 1]));
                    }
                    x--;
                }
            }
            if (x != 0 && gameNum[i] != 0) {
                int num3 = gameNum[i];
                gameNum[i] = 0;
                gameNum[x * 4 + i] = num3;
                animatorList.add(ObjectAnimator.ofPropertyValuesHolder(cards[0][i], right[Math.abs(x) - 1]));
            }
        }
    }

    private void left() {
        for (int i = 0; i < 4; i++) {
            int x = 0;
            int num = 0;
            for (int j = 0; j < 3; j++) {
                int xj = j;
                if (cards[j][i].getNum() > 0) {
                    num = cards[j][i].getNum();
                    for (int k = j + 1; k < 4; k++) {
                        if (cards[k][i].getNum() > 0) {
                            if (cards[j][i].equals(cards[k][i])) {
                                num += cards[k][i].getNum();
                                xj = k;
                                Score += 2 * cards[i][j].getNum();
                                ScoreChanged = true;
                                gameNum[k * 4 + i] = (0);
                                move = true;
                                change = true;
                            }
                            break;
                        }
                    }
                    gameNum[j * 4 + i] = 0;
                    gameNum[x * 4 + i] = num;
                    if (x != j)
                        if (j != xj) {
                            animatorList.add(ObjectAnimator.ofPropertyValuesHolder(cards[j][i], left[Math.abs(x - j) - 1]));
                            animatorList.add(ObjectAnimator.ofPropertyValuesHolder(cards[xj][i], left[Math.abs(x - xj) - 1]));
                            j = xj;
                        } else
                            animatorList.add(ObjectAnimator.ofPropertyValuesHolder(cards[x][i], left[Math.abs(x - j) - 1]));
                    x++;
                }
            }
            num = (cards[3][i].getNum());
            cards[3][i].setNum(0);
            cards[x][i].setNum(num);
            if (x != 3)
                animatorList.add(ObjectAnimator.ofPropertyValuesHolder(cards[x][i], left[Math.abs(x - 3) - 1]));
        }
    }


    private void addCard(int cardSide) {
        gameLayout.removeAllViews();
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(cardSide, cardSide);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                CardView card = new CardView(mContext);
                gameLayout.addView(card, lp);
                card.setNum(0);
//                if ((i*4+j)!=0)
//                    card.setNum(DoNum(2, (i*4+j+1)));
                cards[j][i] = card;
                change = true;
//                Log.e(TAG,"" + DoNum(2, (i*4+j)));
            }
        }
        addCard();
    }

    private boolean change;
    private int emptyNum = 16;

    private int getEmptyCardNum() {
        if (!change) {
            return emptyNum;
        }
        int emptyNum = 0;
        for (int i = 0; i < 15; i++) {
            if (cards[i / 4][i % 4].getNum() == 0)
                emptyNum++;
            if (!success && cards[i / 4][i % 4].getNum() == 2048) {
                Snackbar.make(gameLayout, "成功", Snackbar.LENGTH_SHORT).show();
                success = true;
            }
        }
        change = false;
        this.emptyNum = emptyNum;
        return emptyNum;
    }

    public boolean checkResult() {
        if (getEmptyCardNum() > 0)
            return true;
        for (int i = 0; i < 4; i++) {

            for (int j = 0; j < 3; j++) {
                int num = cards[i][j].getNum();
                if (i < 3 && cards[i + 1][j].getNum() == num)
                    return true;

                if (cards[i][j + 1].getNum() == num)
                    return true;
            }
        }
        return false;
    }

    private Integer[] lastGameNum = new Integer[]{
            0, 0, 0, 0,
            0, 0, 0, 0,
            0, 0, 0, 0,
            0, 0, 0, 0};
    private Integer[] gameNum = new Integer[]{
            0, 0, 0, 0,
            0, 0, 0, 0,
            0, 0, 0, 0,
            0, 0, 0, 0};

    public Integer[] getGameNum() {
        Integer[] gameNum = new Integer[16];
        for (int i = 0; i < 16; i++) {
            gameNum[i] = cards[i / 4][i % 4].getNum();
        }
        return gameNum;
    }


    private void addCard() {
        int emptyNum = getEmptyCardNum();
        if (!checkResult()) {
            Snackbar.make(gameLayout, "游戏结束，你的成绩是" + getScore(), Snackbar.LENGTH_LONG)
                    .setAction("重试", v -> restart());
            return;
        }

        int a = (int) (Math.random() * 10);
        int b = (int) (Math.random() * (emptyNum - 1));
        int c = 0;
        for (int k = 0; k < 16; k++) {
            int i = k % 4;
            int j = k / 4;
            if (cards[j][i].getNum() <= 0)
                if (c == b) {
                    cards[j][i].setNum(a > 3 ? 2 : 4);
                    change = true;
                    break;
                } else {
                    c++;
                }
        }
        if (!checkResult()) {
            Snackbar.make(gameLayout, "游戏结束，你的成绩是" + getScore(), Snackbar.LENGTH_LONG)
                    .setAction("重试", v -> restart()).show();
        }
//        lastData.clear();
//        lastData.addAll(data);
    }

    public int getScore() {
        ScoreChanged = false;
        return Score;

    }

    public void restart() {
        addCard(cardSide);
        Score = 0;
        ScoreChanged = false;
        success = false;
    }

    public boolean isScoreChanged() {
        return ScoreChanged;
    }

    //x^Y
    private int DoNum(int X, int Y) {
        int num = 1;
//        if (Y>0)
//            return X^Y;
        if (Y > 0)
            for (int i = 0; i < Y; i++)
                num *= X;

        return num;
    }


    class Pixes {
        public Pixes(float x, float y) {
            this.x = x;
            this.y = y;
        }

        float x = 0f, y = 0f;
    }

    //top
    {
        Integer[] lastGameNum = getGameNum();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                int num = cards[i][j].getNum();
                if (num > 0) {
                    for (int k = j + 1; k < 4; k++) {
                        int num2 = cards[i][k].getNum();
                        if (num2 > 0) {
                            if (num2 == num) {
                                lastGameNum[i * 4 + j] = num * 2;
                                lastGameNum[i * 4 + k] = 0;
                                j = k;
                            }
                            break;
                        }
                    }
                }
            }
            int x = 0;
            for (int j = 0; j < 4; j++) {
                int num = cards[i][j].getNum();
                if (num > 0) {
                    gameNum[i * 4 + x] = num;
                    x++;
                }
            }
        }
    }

    enum Direction {
        left, right, top, bottom;
        int distance;
        static Direction getDirection(int num) {
            switch (num) {
                case 0:
                    return left;
                case 1:
                    return right;
                case 2:
                    return top;
                default:
                    return bottom;
            }
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }
    }

    class Move {
        Direction direction;
        Point point;
    }
}

