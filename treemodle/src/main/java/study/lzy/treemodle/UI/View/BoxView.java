package study.lzy.treemodle.UI.View;
// @author: lzy  time: 2016/09/30.


import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.text.BreakIterator;

public class BoxView extends FrameLayout{
    private LayoutParams params;
    private Context mContext;
    private int type=0;//0-9 :方块
    //，10-19：金币，20-29，人,30-39 怪物，
    private boolean stone=false,black=false;
    private boolean export=false;
    private boolean snackHere=false,HeroHero=false,moneyHere=false;
    private boolean snackMoveTo=false,HeroMoveTo=false;
    private PersonView personView;
    public BoxView(Context context,int type) {
        super(context);
        mContext=context;this.type=type;
        init();
    }

    public BoxView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        this.type=type;
        init();
    }

    public BoxView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
        this.type=type;
        init();
    }

    private void init() {
        params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setBox();


    }



    private void setBox() {
        switch (type%10){
            case 0://black
                setBackgroundColor(Color.rgb(256,256,256));
                setBlack(true);
                break;
            case 1://success
                setBackgroundColor(Color.RED);
                setExport(true);
            break;

            case 2://normal
                setBackgroundColor(Color.GREEN);
            break;
            case 3://normal
                setBackgroundColor(Color.BLUE);
            break;
            case 4://normal
                setBackgroundColor(Color.argb(0x7f,0,0xff,0xff));
            break;
            case 5://normal
                setBackgroundColor(Color.argb(0x7f,0xff,0,0xff));
            break;
            case 6://normal
                setBackgroundColor(Color.argb(0x7f,0xff,0xff,0));
            break;

            case 7://success
                setBackgroundColor(Color.rgb(0,0,0));
                setStone(true);
            break;
            case 8://success
                setBackgroundColor(Color.rgb(30,30,30));
                setStone(true);
            break;
            case 9://success
                setBackgroundColor(Color.rgb(60,20,80));
                setStone(true);
            break;

        }
    }



    public void addView(View child) {
        check(child);
    }

    private void check(View child) {
        if (child instanceof PersonView) {
            PersonView person = (PersonView) child;
            if (isBlack()||isStone()){
                person.die();
                return;
            }
            if (person.type==1)
                setMoneyHere(true);
            if (person.type==2)
                setSnackHere(true);
            if (person.type==3)
                setHeroHero(true);
            if (person.type==3){
                if (isSnackHere())
                    person.die();
                if (isMoneyHere()&&personView!=null){

                }
            }
            personView=person;
            if (personView.getParent()!=null){
             ViewGroup parent = (ViewGroup) personView.getParent();
                parent.removeView(personView);
            }
            super.addView(personView,params);
        }else
        if (child.getParent()==null)
        super.addView(child,params);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isBlack() {
        return black;
    }

    public void setBlack(boolean black) {
        this.black = black;
    }

    public boolean isHeroHero() {
        return HeroHero;
    }

    public void setHeroHero(boolean heroHero) {
        HeroHero = heroHero;
    }

    public boolean isHeroMoveTo() {
        return HeroMoveTo;
    }

    public void setHeroMoveTo(boolean heroMoveTo) {
        HeroMoveTo = heroMoveTo;
    }

    public boolean isMoneyHere() {
        return moneyHere;
    }

    public void setMoneyHere(boolean moneyHere) {
        this.moneyHere = moneyHere;
    }

    public LayoutParams getParams() {
        return params;
    }

    public void setParams(LayoutParams params) {
        this.params = params;
    }

    public boolean isSnackHere() {
        return snackHere;
    }

    public void setSnackHere(boolean snackHere) {
        this.snackHere = snackHere;
    }

    public boolean isSnackMoveTo() {
        return snackMoveTo;
    }

    public void setSnackMoveTo(boolean snackMoveTo) {
        this.snackMoveTo = snackMoveTo;
    }

    public boolean isStone() {
        return stone;
    }

    public void setStone(boolean stone) {
        this.stone = stone;
    }

    public boolean isExport() {
        return export;
    }

    public void setExport(boolean export) {
        this.export = export;
    }

    public PersonView getPersonView() {
        return personView;
    }

    public void setPersonView(PersonView personView) {
        this.personView = personView;
    }
}
