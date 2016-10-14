package study.lzy.treemodle.UI.View;
// @author: lzy  time: 2016/09/30.


import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import study.lzy.treemodle.R;

public class PersonView extends ImageView{
    int type=0;
    EventListener event;
    public PersonView(Context context,int type,EventListener event) {
        super(context);
        this.type=type;
        this.event=event;
        init();
    }

    private void init() {
        switch (type%4){
            case 1:
                setImageResource(R.mipmap.money);
                break;
            case 3:
                setImageResource(R.mipmap.hero);
                break;
            case 2:
                setImageResource(R.mipmap.snack);
                break;
        }
    }

    public void moveTo(BoxView from ,BoxView to){
        event.moveTo(this,from,to);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void die() {

    }
}
