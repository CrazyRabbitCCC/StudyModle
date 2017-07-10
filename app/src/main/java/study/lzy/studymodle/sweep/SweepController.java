package study.lzy.studymodle.sweep;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import study.lzy.studymodle.Game2048.CardView;
import study.lzy.studymodle.Utils.BaseUtil;

/**
 * @author Gavin
 * @date 2017/06/15.
 */

public class SweepController implements View.OnClickListener {
    private GridLayout sweepLayout;
    private Context mContext;
    private int cardSide;
    private Set<Integer> mines;
    private int cloms = 0 ;
    private int mineNum = 0 ;
    private SweepCard[] sweepCards;
    private int showNum = 0;
    private OnResultDeal onResultDeal;
    private int step;

    public SweepController(GridLayout sweepLayout, Context context) {
        this.sweepLayout = sweepLayout;
        mContext = context;
        mines = new HashSet<>();
        DisplayMetrics screenMetrics = BaseUtil.getScreenMetrics(mContext);
        ViewGroup.LayoutParams params = sweepLayout.getLayoutParams();
        if (params==null)
            params = new ViewGroup.LayoutParams(screenMetrics.widthPixels,screenMetrics.widthPixels);
        else {
            params.width = screenMetrics.widthPixels;
            params.height = screenMetrics.widthPixels;
        }
        sweepLayout.setLayoutParams(params);
    }

    public void reload(int colums, int mineNum){
        showNum=0;
        step=0;
        gameOver=false;
        sweepLayout.setBackgroundColor(Color.RED);
        this.mineNum = mineNum;
        cloms=colums;
        sweepLayout.removeAllViews();
        sweepLayout.setColumnCount(colums);
        DisplayMetrics screenMetrics = BaseUtil.getScreenMetrics(mContext);
        cardSide = screenMetrics.widthPixels / colums ;
        sweepCards = new SweepCard[colums*colums];
        loadCards(cardSide,cloms);
        loadMine();
    }

    private void loadCards(int cardSide,int colums) {
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(cardSide, cardSide);
        for (int i = 0; i < colums*colums; i++) {
                SweepCard card = new SweepCard(mContext,i);
                sweepLayout.addView(card, lp);
                card.setNum(0);
            card.setOnClickListener(this);
                sweepCards[i] = card;
        }
    }

    private void loadMine(){
        mines.clear();
        while (mines.size()<mineNum){
            mines.add((int) (Math.random()*cloms*cloms));
        }
        for (Integer num : mines) {
            sweepCards[num].setMine(true);
            sweepCards[num].setNum(-1);
        }
        for (int i = 0; i < cloms * cloms; i++) {
            getCardNum(i);
        }
    }

    @Override
    public void onClick(View v) {
        if (v instanceof SweepCard){
            if (gameOver||sweepCards[((SweepCard) v).getSweepId()].isClicked())
                return;
            step++;
            if (onResultDeal!=null)
                onResultDeal.onStep(step);
            else
                Toast.makeText(mContext, ""+step, Toast.LENGTH_SHORT).show();
            showCardNum(((SweepCard) v).getSweepId());
        }
    }

    boolean gameOver =false;
    private void showCardNum(int sweepId){
        SweepCard card = sweepCards[sweepId];
        if (card.isClicked())
            return;
        int num = card.getNum()!=-2?card.getNum():getCardNum(sweepId);
        card.showNum();
        showNum++;
        if (card.isMine()){
            gameOver =true;
            if (onResultDeal!=null)
                onResultDeal.onFailed();
            else
                Toast.makeText(mContext, "FAILED", Toast.LENGTH_LONG).show();
        }
        else if (checkResult()){
            gameOver =true;
            if (onResultDeal!=null)
                onResultDeal.onSuccess();
            else
                Toast.makeText(mContext, "SUCCESS", Toast.LENGTH_LONG).show();
        }

        if (num ==0){
            int i= sweepId/cloms;
            int j= sweepId%cloms;
            int amin=i==0?0:-1;
            int bmin=j==0?0:-1;
            int am= i==(cloms-1)?0:1;
            int bm= j==(cloms-1)?0:1;
            for (int a= amin; a <=am; a++) {
                for (int b=bmin; b <= bm; b++) {
                    showCardNum((i+a)*cloms+j+b);
                }
            }
        }
    }

    private boolean checkResult() {
        return cloms * cloms - showNum == mineNum;
    }

    private int getCardNum(int sweepId){
        if (sweepCards[sweepId].isMine())
            return -1;
        int num = 0;
        int i= sweepId/cloms;
        int j= sweepId%cloms;
        int amin= i==0?0:-1;
        int bmin= j==0?0:-1;
        int am= i==(cloms-1)?0:1;
        int bm= j==(cloms-1)?0:1;
        for (int a= amin; a <=am; a++) {
            for (int b = bmin; b <=bm; b++) {
                int sweepNum=  (i+a)*cloms+j+b;
                if (sweepCards[sweepNum].isMine())
                    num++;
            }
        }
        sweepCards[sweepId].setNum(num);
        return num;
    }

    public void setOnResultDeal(OnResultDeal onResultDeal) {
        this.onResultDeal = onResultDeal;
    }

    interface OnResultDeal{
        void onSuccess();
        void onFailed();
        void onStep(int step);
    }
}
