package study.lzy.studymodle;

import android.content.Context;
import android.view.View;
import android.widget.GridLayout;

import java.util.LinkedList;

/**
 * @author Gavin
 * @date 2017/06/22.
 */

public abstract class BaseGameController<T extends BaseGameCard,R> implements View.OnClickListener,View.OnTouchListener{

    protected Context mContext;
    protected GridLayout gameLayout;
    protected int width;
    protected int height;
    protected T[][] cards;
    private LinkedList<R> record= new LinkedList<>();
    public BaseGameController(Context context, GridLayout gameLayout) {
        mContext = context;
        this.gameLayout = gameLayout;
        init();
    }
    protected abstract void init();

    public void initWidthAndHeight(int w,int h){
        width = w;
        height = h;
        cards =initCards(w, h);
        for (int y=0;y<h;y++)
            for (int x=0;x<w;x++){
                cards[y][x] = getPointCard(x,y);
                cards[y][x].setOnClickListener(this);
            }
        reload();
    }

    protected abstract T getPointCard(int x, int y);

    public abstract void reload();
    protected abstract T[][] initCards(int width,int height);

    protected void recordADD(R r){
        record.addLast(r);
    }

    protected R undo(){
        return record.removeLast();
    }

    public abstract void undoOnce();



}
