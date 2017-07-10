package study.lzy.studymodle.gomoku;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.Toast;

import study.lzy.studymodle.R;
import study.lzy.studymodle.Utils.BaseUtil;

/**
 * @author Gavin
 * @date 2017/06/21.
 */

public class GomokuUtil implements View.OnClickListener {
    public static final int WIDTH = 15;
    private Context mContext;
    GomokuCard[][] cards= new GomokuCard[WIDTH][WIDTH];
    /**
     * 获胜情况 ， 0 正在进行 1，白棋胜利 2，黑棋胜利 3，平局
     */
    private int type = 0 ;

    /**
     * 判断当前是否白棋
     */
    private boolean isWhite =false;


    public GomokuUtil(Context context) {
        mContext = context;
        initCards();
        ai = new GomokuAI(this,true);
    }

    private void initCards(){
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < WIDTH; j++) {
                cards[i][j] = new GomokuCard(mContext,i,j);
                cards[i][j].setOnClickListener(this);
                if (i==0 && j==0){
                    cards[i][j].setBackgroundResource(R.drawable.start_top);
                }else if (i==0 && j==WIDTH-1){
                    cards[i][j].setBackgroundResource(R.drawable.end_top);
                }else if (i==WIDTH-1 && j==WIDTH-1){
                    cards[i][j].setBackgroundResource(R.drawable.end_bottom);

                }else if (i==WIDTH-1 && j==0){
                    cards[i][j].setBackgroundResource(R.drawable.start_bottom);
                }else if (i==0){
                    cards[i][j].setBackgroundResource(R.drawable.top);
                }else if (j==0){
                    cards[i][j].setBackgroundResource(R.drawable.start);
                }else if (i==WIDTH-1){
                    cards[i][j].setBackgroundResource(R.drawable.bottom);
                }else if (j==WIDTH-1){
                    cards[i][j].setBackgroundResource(R.drawable.end);
                }else {
                    cards[i][j].setBackgroundResource(R.drawable.center);
                }
            }
        }
    }

    public void addToGrid(GridLayout grid){
        DisplayMetrics screenMetrics = BaseUtil.getScreenMetrics(mContext);
        int width =screenMetrics.widthPixels<screenMetrics.heightPixels?screenMetrics.widthPixels:screenMetrics.heightPixels;
        int cardSide =width / WIDTH ;
        grid.setColumnCount(WIDTH);
        ViewGroup.LayoutParams params = grid.getLayoutParams();
        if (params==null)
            params = new ViewGroup.LayoutParams(width,width);
        else {
            params.width =width;
            params.height = width;
        }
        grid.setLayoutParams(params);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(cardSide, cardSide);
        for (int i =0;i<WIDTH;i++){
            for (int j = 0; j < WIDTH; j++) {
                grid.addView(cards[i][j],lp);
            }
        }
    }

    public boolean checkPoint(int i,int j){
        return checkPoint(cards[i][j]);
    }

    public void doOnClick(int i,int j){

    }
    public boolean checkResult(int i,int j){

        return false;
    }
    public boolean checkPoint(GomokuCard card){
        return card.getNum()==0;
    }

    public void doOnClick(GomokuCard card){
        card.setNum(isWhite?-1:1);
        isWhite = ! isWhite;
    }
    Toast mToast;
    public boolean checkResult(GomokuCard card){
        if (getRowNum(card)>4)
            return true;
        if (getLeftNum(card)>4)
            return true;
        if (getLineNum(card)>4)
            return true;
        if (getRightNum(card)>4)
            return true;
        return false;
    }
    int getLineNum(GomokuCard card){
        if (card.isEmpty())
            return 0;
        int lineNum =1 ;
        int i= card.getI();
        int j= card.getJ();
        if (i>0&&cards[i][j].equalValue(cards[i-1][j]))
            lineNum += getLineStartNum(i-1,j);
        if (i<WIDTH-1&&cards[i][j].equalValue(cards[i+1][j]))
            lineNum +=getLineEndNum(i+1,j);
        return lineNum;
    }
    private int getLineStartNum(int i,int j){
        if (i>0&&cards[i][j].equalValue(cards[i-1][j]))
            return 1+getLineStartNum(i-1,j);
        return 1;
    }
    private  int getLineEndNum(int i,int j){
        if (i<WIDTH-1&&cards[i][j].equalValue(cards[i+1][j]))
            return 1+getLineEndNum(i+1,j);
        return 1;
    }

    int getRowNum(GomokuCard card){
        if (card.isEmpty())
            return 0;
        int rowNum=1;
        int i= card.getI();
        int j= card.getJ();
        if (j>0&&card.equalValue(cards[i][j-1]))
            rowNum += getRowStartNum(i,j-1);
        if (j<WIDTH-1&&cards[i][j].equalValue(cards[i][j+1]))
            rowNum +=getRowEndNum(i,j+1);
        return rowNum;
    }
    private int getRowStartNum(int i,int j){
        if (j>0&&cards[i][j].equalValue(cards[i][j-1]))
            return 1+getRowStartNum(i,j-1);
     return 1;
    }
    private  int getRowEndNum(int i,int j){
        if (j<WIDTH-1&&cards[i][j].equalValue(cards[i][j+1]))
            return 1+getRowEndNum(i,j+1);
        return 1;
    }


    int getLeftNum(GomokuCard card){
        if (card.isEmpty())
            return 0;
        int rowNum=1;
        int i= card.getI();
        int j= card.getJ();
        if (j>0&&i>0&&card.equalValue(cards[i-1][j-1]))
            rowNum += getLeftStartNum(i-1,j-1);
        if (j<WIDTH-1&&i<WIDTH-1&&cards[i][j].equalValue(cards[i+1][j+1]))
            rowNum +=getLeftEndNum(i+1,j+1);
        return rowNum;
    }
    private int getLeftStartNum(int i,int j){
        if (j>0&&i>0&&cards[i][j].equalValue(cards[i-1][j-1]))
            return 1+getLeftStartNum(i-1,j-1);
        return 1;
    }
    private  int getLeftEndNum(int i,int j){
        if (j<WIDTH-1&&i<WIDTH-1&&cards[i][j].equalValue(cards[i+1][j+1]))
            return 1+getLeftEndNum(i+1,j+1);
        return 1;
    }
    int getRightNum(GomokuCard card){
        if (card.isEmpty())
            return 0;
        int rowNum=1;
        int i= card.getI();
        int j= card.getJ();
        if (j<WIDTH-1&&i>0&&cards[i][j].equalValue(cards[i-1][j+1]))
            rowNum += getRightStartNum(i-1,j+1);
        if (j>0&&i<WIDTH-1&&cards[i][j].equalValue(cards[i+1][j-1]))
            rowNum +=getRightEndNum(i+1,j-1);
        return rowNum;
    }
    private int getRightStartNum(int i,int j){
        if (j<WIDTH-1&&i>0&&cards[i][j].equalValue(cards[i-1][j+1]))
            return 1+getRightStartNum(i-1,j+1);
        return 1;
    }
    private  int getRightEndNum(int i,int j){
        if (j>0&&i<WIDTH-1&&cards[i][j].equalValue(cards[i+1][j-1]))
            return 1+getRightEndNum(i+1,j-1);
        return 1;
    }

    public Context getContext() {
        return mContext;
    }

    public GomokuCard[][] getCards() {
        return cards;
    }


    public boolean isWhite() {
        return isWhite;
    }

    public void setWhite(boolean white) {
        isWhite = white;
    }

    GomokuAI ai;
    public boolean checkResult(){
        return false;
    }
    @Override
    public void onClick(View v) {
        if (!isWhite&&v instanceof GomokuCard) {
            GomokuCard card = (GomokuCard) v;
            if (checkPoint(card)){
                doOnClick(card);
                if(checkResult(card)) {
                    String msg ="";
                    if (isWhite) {
                        type = 2;
                        msg= "黑棋";
                    }
                    else {
                        type = 1;
                        msg="白棋";
                    }
                    Toast.makeText(mContext, msg+"success", Toast.LENGTH_SHORT).show();
                }else {
                    ai.play();
                }
            }
        }
    }
}
