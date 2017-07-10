package study.lzy.studymodle.chess;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import study.lzy.studymodle.BaseGameController;
import study.lzy.studymodle.Utils.BaseUtil;

import static study.lzy.studymodle.chess.InterChessCard.*;

/**
 * @author Gavin
 * @date 2017/06/22.
 */

public class InterChessUtil extends BaseGameController<InterChessCard,InterChessUtil.Record>{

    private boolean isWhite = false;
    private boolean isSelected = false;

    public InterChessUtil(Context context, GridLayout gameLayout) {
        super(context, gameLayout);
        initWidthAndHeight(8,8);
    }

    @Override
    protected void init() {


    }

    @Override
    protected InterChessCard getPointCard(int x, int y) {
        return new InterChessCard(mContext,x,y);
    }

    @Override
    public void reload() {
        gameLayout.removeAllViews();
        gameLayout.setColumnCount(width);
        DisplayMetrics screenMetrics = BaseUtil.getScreenMetrics(mContext);
        int w =screenMetrics.widthPixels<screenMetrics.heightPixels?screenMetrics.widthPixels:screenMetrics.heightPixels;
        int cardSide =w/ width ;
        ViewGroup.LayoutParams params = gameLayout.getLayoutParams();
        if (params==null)
            params = new ViewGroup.LayoutParams(cardSide*width,cardSide*height);
        else {
            params.width =cardSide*width;
            params.height = cardSide*height;
        }
        gameLayout.setLayoutParams(params);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(cardSide, cardSide);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                gameLayout.addView(cards[y][x],lp);
                cards[y][x].loadBackground();
            }
        }
        initPieces();
    }

    @Override
    protected InterChessCard[][] initCards(int width, int height) {
        return new InterChessCard[height][width];
    }
    @Override
    public void undoOnce() {
        Record record = undo();
        cards[record.start.x][record.start.y].setNum(record.startPieces);
        cards[record.end.x][record.end.y].setNum(record.endPieces);
    }

    InterChessCard selectCard;
    @Override
    public void onClick(View v) {
        if (! (v instanceof InterChessCard))
            return;
        InterChessCard card = (InterChessCard) v;
        if (isSelected){
            switch (card.type){
                case EATEN:
                    eat(selectCard,card);
                case MOVED:
                    move(selectCard,card);
                    clear();
                    isWhite = !isWhite;
                    break;
                default:
                    break;
            }
        }
        if ((isWhite&&card.getNum()<10)||(!isWhite&&card.getNum()>10)){
            clear();
            isSelected = true;
            selectCard = card;
            card.setType(TYPE.SELECTED);
            setMoveAndEat(card);
        }
    }

    private void eat(InterChessCard start ,InterChessCard end) {
        if (end.getNum()%10==9){
            //TODO WIN;
        }

    }

    private void move(InterChessCard start ,InterChessCard end) {
        recordADD(new Record(start.getPoint(),end.getPoint(),start.getNum(),end.getNum()));
        end.setNum(start.getNum());
        start.setNum(0);
        start.setType(TYPE.NONE);
        end.setType(TYPE.NONE);
    }

    private void clear(){
        if (selectCard!=null){
            selectCard.setType(TYPE.NONE);
            selectCard = null;
        }
        for (Point p:points){
            cards[p.y][p.x].setType(TYPE.NONE);
        }
        points.clear();
    }

   private Set<Point> points = new HashSet<>();
    private void setMoveAndEat(InterChessCard card){
        points = getMovePoint(card.getNum(),card.getPointX(),card.getPointY());
        for (Point p:points){
            if(cards[p.y][p.x].getNum()==0)
                cards[p.y][p.x].setType(TYPE.MOVED);
            else
                cards[p.y][p.x].setType(TYPE.EATEN);
        }
    }

    private Set<Point> getMovePoint(int num, int x, int y){
        Set<Point> points = new HashSet<>();
        if (num ==WV||num==BV)
            return getVehicleMove(x,y);
        if (num==WB||num==BB)
            return getBishopMove(x,y);
        if (num==WQ||num==BQ)
        {
            points.addAll(getVehicleMove(x,y));
            points.addAll(getBishopMove(x,y));
            return points;
        }
        if (num==WP){
           return getWPawnedMove(x,y);
        }
        if (num==BP){
            return getBPawnedMove(x,y);
        }
        if (num==WH||num==BH)
            return getHorseMove(x,y);
        if (num==WK||num==BK)
            return getKingMove(x,y);
        return points;
    }

    private Set<Point> getWPawnedMove(int x, int y) {
        Set<Point> points = new HashSet<>();
        if (y+1<8) {
            if (cards[y + 1][x].getNum() == 0) {
                points.add(new Point(x, y + 1));
                if (y == 1 && cards[3][x].getNum() == 0) {
                    points.add(new Point(x, 3));
                }
            }
            if (x-1>-1 &&cards[y+1][x-1].getNum()>10){
                points.add(new Point(x-1,y+1));
            }
            if (x+1<8 && cards[y+1][x+1].getNum()>10){
                points.add(new Point(x+1,y+1));
            }
        }
        return points;
    }

    private Set<Point> getBPawnedMove(int x, int y) {
        Set<Point> points = new HashSet<>();
        if (y-1>-1) {
            if (cards[y - 1][x].getNum() == 0) {
                points.add(new Point(x, y - 1));
                if (y == 6 && cards[4][x].getNum() == 0) {
                    points.add(new Point(x, 4));
                }
            }
            if (x-1>-1 &&cards[y-1][x-1].getNum()!=0&&cards[y-1][x-1].getNum()<10){
                points.add(new Point(x-1,y-1));
            }
            if (x+1<8 && cards[y-1][x+1].getNum()!=0&& cards[y-1][x+1].getNum()<10){
                points.add(new Point(x+1,y-1));
            }
        }
        return points;
    }
    /**
     * x-2,y-1
     * x-2,y+1
     * x+2,y-1
     * x+2,y+1
     * x-1,y-2
     * x-1,y+2
     * x+1,y+2
     * x+1,y-2
     **/
    private Set<Point> getHorseMove(int x, int y) {
        Set<Point> points = new HashSet<>();
        if (x-2>-1&&y-1>-1 && (cards[y-1][x-2].getNum()==0|cards[y][x].getNum()/10 !=cards[y-1][x-2].getNum()/10)){
            points.add(new Point(x-2,y-1));
        }
        if (x+2<8&&y-1>-1 && (cards[y-1][x+2].getNum()==0|cards[y][x].getNum()/10 !=cards[y-1][x+2].getNum()/10)){
            points.add(new Point(x+2,y-1));
        }
        if (x-2>-1&&y+1<8 && (cards[y+1][x-2].getNum()==0|cards[y][x].getNum()/10 !=cards[y+1][x-2].getNum()/10)){
            points.add(new Point(x-2,y+1));
        }
        if (x+2<8&&y+1<8 && (cards[y+1][x+2].getNum()==0|cards[y][x].getNum()/10 !=cards[y+1][x+2].getNum()/10)){
            points.add(new Point(x+2,y+1));
        }
        if (x-1>-1&&y-2>-1 && (cards[y-2][x-1].getNum()==0|cards[y][x].getNum()/10 !=cards[y-2][x-1].getNum()/10)){
            points.add(new Point(x-1,y-2));
        }
        if (x+1<8&&y-2>-1 && (cards[y-2][x+1].getNum()==0|cards[y][x].getNum()/10 !=cards[y-2][x+1].getNum()/10)){
            points.add(new Point(x+1,y-2));
        }
        if (x-1>-1&&y+2<8 && (cards[y+2][x-1].getNum()==0|cards[y][x].getNum()/10 !=cards[y+2][x-1].getNum()/10)){
            points.add(new Point(x-1,y+2));
        }
        if (x+1<8&&y+2<8 && (cards[y+2][x+1].getNum()==0|cards[y][x].getNum()/10 !=cards[y+2][x+1].getNum()/10)){
            points.add(new Point(x+1,y+2));
        }
        return points;
    }

    private Set<Point> getKingMove(int x, int y) {
        Set<Point> points = new HashSet<>();
        int cardNum  = cards[y][x].getNum();
        for (int i=-1;i<=1;i++){
            for (int j = -1; j < 2; j++) {
                if (x+i>-1&&x+i<8 && y+j>-1 && y+j<8 ){
                    int num = cards[y+j][x+i].getNum();
                    if (num==0||num/10!=cardNum/10){
                        points.add(new Point(x+i,y+j));
                    }
                }
            }
        }
        return points;
    }

    private Set<Point> getVehicleMove(int x,int y){
        Set<Point> points = new HashSet<>();
        for (int i = x; i < width; i++) {
            if (i==x)
                continue;
            if (cards[y][i].getNum()==0)
                points.add(new Point(i,y));
            else {
                if (cards[y][x].getNum()/10 !=cards[y][i].getNum()/10)
                    points.add(new Point(i,y));
                break;
            }
        }
        for (int i = y; i < height; i++) {
            if (i==y)
                continue;
            if (cards[i][x].getNum()==0)
                points.add(new Point(x,i));
            else {
                if (cards[y][x].getNum()/10 !=cards[i][x].getNum()/10)
                    points.add(new Point(x,i));
                break;
            }
        }
        for (int i = x; i>=0; i--) {
            if (i==x)
                continue;
            if (cards[y][i].getNum()==0)
                points.add(new Point(i,y));
            else {
                if (cards[y][x].getNum()/10 !=cards[y][i].getNum()/10)
                    points.add(new Point(i,y));
                break;
            }
        }
        for (int i = y; i >=0 ; i--) {
            if (i==y)
                continue;
            if (cards[i][x].getNum()==0)
                points.add(new Point(x,i));
            else {
                if (cards[y][x].getNum()/10 !=cards[i][x].getNum()/10)
                    points.add(new Point(x,i));
                break;
            }
        }
        return points;
    }

    private Set<Point> getBishopMove(int x, int y){
        Set<Point> points = new HashSet<>();
        for (int i = x,j=y; i < width&& j <width; i++,j++) {
            if (i==x&& j==y)
                continue;
            if (cards[j][i].getNum()==0)
                points.add(new Point(i,j));
            else {
                if (cards[y][x].getNum()/10 !=cards[j][i].getNum()/10)
                    points.add(new Point(i,j));
                break;
            }
        }
        for (int i = x,j=y; i >=0&& j <width; i--,j++) {
            if (i==x&& j==y)
                continue;
            if (cards[j][i].getNum()==0)
                points.add(new Point(i,j));
            else {
                if (cards[y][x].getNum()/10 !=cards[j][i].getNum()/10)
                    points.add(new Point(i,j));
                break;
            }
        }
        for (int i = x,j=y; i < width&& j >=0; i++,j--) {
            if (i==x&& j==y)
                continue;
            if (cards[j][i].getNum()==0)
                points.add(new Point(i,j));
            else {
                if (cards[y][x].getNum()/10 !=cards[j][i].getNum()/10)
                    points.add(new Point(i,j));
                break;
            }
        }
        for (int i = x,j=y; i >=0&& j >=0; i--,j--) {
            if (i==x&& j==y)
                continue;
            if (cards[j][i].getNum()==0)
                points.add(new Point(i,j));
            else {
                if (cards[y][x].getNum()/10 !=cards[j][i].getNum()/10)
                    points.add(new Point(i,j));
                break;
            }
        }
        return points;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    private void initPieces() {
     /*   cards[3][4].setNum(WV);
        cards[4][4].setNum(BV);
        cards[2][4].setNum(BV);
        cards[0][0].setNum(WB);
        cards[3][3].setNum(BB);*/
        cards[0][0].setNum(WV);
        cards[0][7].setNum(WV);
        cards[0][1].setNum(WH);
        cards[0][6].setNum(WH);
        cards[0][2].setNum(WB);
        cards[0][5].setNum(WB);
        cards[0][3].setNum(WQ);
        cards[0][4].setNum(WK);
        cards[7][0].setNum(BV);
        cards[7][7].setNum(BV);
        cards[7][1].setNum(BH);
        cards[7][6].setNum(BH);
        cards[7][2].setNum(BB);
        cards[7][5].setNum(BB);
        cards[7][4].setNum(BQ);
        cards[7][3].setNum(BK);
        for (int x = 0; x < width; x++) {
            cards[1][x].setNum(WP);
            cards[6][x].setNum(BP);
        }


    }
    /**
     * 记录每次移动的起始位置和最终位置
     * 记录他们原来是什么
     * 为了悔棋而生
     */
    class Record{
//        chess pieces
        Point start,end;
        int startPieces,endPieces;
        boolean eaten = false;

        public Record() {
        }

        public Record(Point start, Point end, int startPieces, int endPieces) {
            this.start = start;
            this.end = end;
            this.startPieces = startPieces;
            this.endPieces = endPieces;
        }

        public boolean isEaten() {
            return eaten;
        }

        public void setEaten(boolean eaten) {
            this.eaten = eaten;
        }
    }
}
