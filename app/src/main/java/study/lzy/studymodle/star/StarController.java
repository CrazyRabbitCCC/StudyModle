package study.lzy.studymodle.star;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import study.lzy.studymodle.Utils.BaseUtil;

/**
 * @author Gavin
 * @date 2017/06/16.
 */

public class StarController implements View.OnClickListener
{
    GameCard[][] mGameCards;
    private int length;
    private Context mContext;
    private int width=15,height=18;
    private int currentW,currentH;
    private GridLayout gameLayout;

    public StarController(Context context, GridLayout gameLayout) {
        mContext = context;
        this.gameLayout = gameLayout;
        init();
    }

    public void init(){
        gameLayout.setColumnCount(width);
        mGameCards = new GameCard[width][height];
        DisplayMetrics screenMetrics = BaseUtil.getScreenMetrics(mContext);
        int cardSide = screenMetrics.widthPixels / width ;
        ViewGroup.LayoutParams params = gameLayout.getLayoutParams();
        int h = cardSide* height;
        if (params==null)
            params = new ViewGroup.LayoutParams(screenMetrics.widthPixels,h);
        else {
            params.width = screenMetrics.widthPixels;
            params.height = h;
        }
        gameLayout.setLayoutParams(params);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(cardSide, cardSide);
        for (int j=0;j<height;j++){
            for (int i=0;i<width;i++){
                mGameCards[i][j]= new GameCard(mContext);
                mGameCards[i][j].setCardId(j*width+i);
                mGameCards[i][j].setOnClickListener(this);
                gameLayout.addView(mGameCards[i][j],lp);
            }
        }
    }

    public void reloadCards(int level){
        currentH= 0;
        currentW= width;
        for (int j=0;j<height;j++){
            for (int i=0;i<width;i++){
                mGameCards[i][j].setNum((int)(Math.random()*level)+1);
            }
        }
    }


    private void loadData(){
        int column = -1;
        for (int i = 0; i < currentW; i++) {
            boolean isColumnEmpty = loadColumn(i);
            if (isColumnEmpty){
                if (column ==-1)
                    column = i ;
            }else if (column !=-1){
                swapColumn(i,column);
                column++;
            }
        }
        if (column!=-1)
            currentW = column;
        else
            currentW = width;
    }

    private void swapColumn(int i,int column){
        for (int x=height-1;x>currentH-1;x--){
            mGameCards[column][x].setNum(mGameCards[i][x].getNum());
            mGameCards[i][x].setNum(0);
        }
    }

    private boolean loadColumn(int i){
        boolean flag =true;
        int row = -1;
        for (int j=height-1;j>currentH-1;j--){
            GameCard card = mGameCards[i][j];
            int num = card.getNum();
            if (num!=0){
                flag= false;
                if (row!=-1){
                    card.setNum(0);
                    mGameCards[i][row].setNum(num);
                    row--;
                }
            }else {
                if (row ==-1)
                    row = j;
            }
        }
        if (row == -1){
            currentH=0;
        }else {
            currentH=Math.min(currentH,row);
        }
        return flag;
    }

    private   boolean isGameOver(){
        for (int i=0;i<currentW-1;i++){
            for (int j=height-1;j>currentH;j--){
                int num = mGameCards[i][j].getNum();
                if (num!=0){
                    if (num == mGameCards[i][j-1].getNum())
                        return false;
                    if (num == mGameCards[i+1][j].getNum())
                        return false;
                }
            }
        }
        return true;
    }

    private  boolean checkCards(int i,int j){
        int num=mGameCards[i][j].getNum();
        if (num==0)
            return false;
        if (i>0 && num== mGameCards[i-1][j].getNum()){
            return true;
        }
        if (i<currentW-1 && num== mGameCards[i+1][j].getNum()){
            return true;
        }
        if (j>currentH && num== mGameCards[i][j-1].getNum()){
            return true;
        }
        if (j<height-1 && num== mGameCards[i][j+1].getNum()){
            return true;
        }
        return false;
    }

    private  int breakCards(int i,int j){
        int result = 1;
        boolean x= false;
        int num=mGameCards[i][j].getNum();
        mGameCards[i][j].setNum(0);
        if (i>0 && num== mGameCards[i-1][j].getNum()){
            result+=breakCards(i-1,j);
        }
        if (i<currentW-1 && num== mGameCards[i+1][j].getNum()){
            result+=breakCards(i+1,j);
        }
        if (j>currentH && num== mGameCards[i][j-1].getNum()){
            result+=breakCards(i,j-1);
        }
        if (j<height-1 && num== mGameCards[i][j+1].getNum()){
            result+=breakCards(i,j+1);
        }
        return result;
    }

    @Override
    public void onClick(View v) {
        if (v instanceof GameCard){
            int cardId = ((GameCard) v).getCardId();
            int j = cardId/width;
            int i = cardId%width;
            if (checkCards(i,j)){
                breakCards(i,j);
                loadData();
            }
        }
    }
}
