package study.lzy.studymodle.chess;

import android.content.Context;
import android.graphics.Color;
import android.util.SparseIntArray;
import android.widget.ImageView;

import java.util.HashMap;
import java.util.Map;

import study.lzy.studymodle.BaseGameCard;
import study.lzy.studymodle.R;

import static study.lzy.studymodle.chess.InterChessCard.TYPE.*;

/**
 * @author Gavin
 * @date 2017/06/22.
 */

public class InterChessCard extends BaseGameCard {
    public static final int WK = 9;
    public static final int WQ = 8;
    public static final int WV = 1;
    public static final int WB = 3;
    public static final int WH = 2;
    public static final int WP = 4;
    public static final int BK = 19;
    public static final int BQ = 18;
    public static final int BV = 11;
    public static final int BB = 13;
    public static final int BH = 12;
    public static final int BP = 14;

    public TYPE type = NONE;

    public  enum TYPE{
        NONE (0),
        SELECTED(R.drawable.selected),
        MOVED(R.drawable.moved),
        EATEN(R.drawable.eaten);
        private int imageId;
        TYPE(int imageId) {
            this.imageId = imageId;
        }


    }
    public InterChessCard(Context context, int x, int y) {
        super(context, x, y);
    }

    public static final SparseIntArray array = new SparseIntArray();

    static {
        array.put(WK, R.drawable.white_king);
        array.put(WQ, R.drawable.white_queen);
        array.put(WV, R.drawable.white_vehicle);
        array.put(WB, R.drawable.white_bishop);
        array.put(WH, R.drawable.white_horse);
        array.put(WP, R.drawable.white_pawned);
        array.put(BK, R.drawable.black_king);
        array.put(BQ, R.drawable.black_queen);
        array.put(BV, R.drawable.black_vehicle);
        array.put(BB, R.drawable.black_bishop);
        array.put(BH, R.drawable.black_horse);
        array.put(BP, R.drawable.black_pawned);
    }

    @Override
    protected void loadImageByNum(int num) {
        image.setVisibility(VISIBLE);
        switch (num) {
            case 0:
                image.setImageDrawable(null);
                break;
            default:
                image.setImageResource(array.get(num));
                break;
        }
    }

    public void loadBackground() {
        setBackgroundColor(getBackgroundColor());
    }

    private int getBackgroundColor(){
        return (mPoint.x + mPoint.y)%2> 0?Color.LTGRAY:Color.WHITE;
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
        setImageBgByType(type);
    }

    public ImageView getImage(){
        return image;
    }

    public void setImageBgByType(TYPE type) {
        if (type == NONE)
            image.setBackground(null);
//            image.setBackgroundColor(getBackgroundColor());
        else
            image.setBackgroundResource(type.imageId);
    }
}
