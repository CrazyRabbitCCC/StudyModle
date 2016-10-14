package study.lzy.treemodle.UI.View;
// @author: lzy  time: 2016/09/30.


import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import java.util.Timer;
import java.util.TimerTask;

public class MapView extends GridLayout {
    private int BoxSide = 0;
    BoxView[][] boxViews = new BoxView[10][10];
    private Context mContext;
    EventListener eventListener;
    private Thread thread;
    private Timer timer;


    public MapView(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public MapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public MapView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        setBackgroundColor(Color.rgb(0xff, 0xff, 0xff));
        setColumnCount(10);
        int z = 0;
        int[] boxType = new int[100];
//        for (int i = 0; i < 100; i++) {
//            int x= (int) (Math.random()*4);
//            int y=0;
//            switch (x){
//                case 0:
//                    y = (int) (Math.random()*9);
//                    break;
//                case 1:
//                    y=(int) (Math.random()*4+2);
//                    break;
//                case 2:
//                    y=(int) (Math.random()*4+2);
//                    z++;
//                    if (z<2)
//                        x+=1;
//                    break;
//                case 3:
//                    y=(int) (Math.random()*4+2);
//                    break;
//            }
//            int zzz=x*10+y;
//            if (zzz>39)
//                zzz-=39;
//            boxType[i]=zzz;
////            if (i < 10) {
////                boxType[i] = i;
////            } else {
////                int x = i;
////                while (x >= 39)
////                    x -= 39;
////                if (x>20)
////                    boxType[i]=x+10;
////                else
////                    boxType[i]=x;
////            }
//        }
        eventListener = new EventListener() {
            @Override
            public boolean moveTo(PersonView person, BoxView from, BoxView to) {
                from.removeView(person);
                to.addView(person);
                return true;
            }
        };
//        setBoxTypes(boxType);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {

        if (w > 0 && h > 0) {
            BoxSide = (Math.min(w, h)) / 10;
            setChildSide(BoxSide);
            super.onSizeChanged(w, h, oldW, oldH);
        }
    }

    public void setChildSide(int childSide) {
        this.BoxSide = childSide;
        reloadView();
    }

    private void reloadView() {
        removeAllViews();
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(BoxSide, BoxSide);

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                BoxView boxView = new BoxView(mContext, (int) (Math.random() * 9));
                boxViews[i][j] = boxView;
                PersonView personView = new PersonView(mContext, (int) (Math.random() * 2 + 1), eventListener);
                boxView.addView(personView);
                addView(boxView, lp);
            }
        }
        boolean added = false;
        int i = 0;
        while (!added && i < 100) {
            if (!boxViews[i / 10][i % 10].isBlack() && !boxViews[i / 10][i % 10].isStone()) {
                PersonView personView = new PersonView(mContext, 3, eventListener);
                boxViews[i / 10][i % 10].addView(personView);
                added = true;
            }
            i++;
        }
        timer=new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    if (!boxViews[i / 10][i % 10].isBlack() && !boxViews[i / 10][i % 10].isStone()) {
                        if (boxViews[i / 10][i % 10].getPersonView() != null && boxViews[i / 10][i % 10].getPersonView().type == 3) {
                            PersonView person = boxViews[i / 10][i % 10].getPersonView();
                            Message msg = new Message();
                            msg.what = 0;
                            msg.arg1 = i;
                            msg.obj = person;
                            handler.sendMessage(msg);
                        }
                    }
                }
            }
        }, 5000, 5000);

//        thread = new Thread(new Runnable() {
//           @Override
//           public void run() {
//               while (true) {
//
//                   for (int i = 0; i < 100; i++) {
//                       if (!boxViews[i / 10][i % 10].isBlack() && !boxViews[i / 10][i % 10].isStone()) {
//                           if (boxViews[i / 10][i % 10].getPersonView() != null && boxViews[i / 10][i % 10].getPersonView().type == 3) {
//                               PersonView person = boxViews[i / 10][i % 10].getPersonView();
//                               Message msg = new Message();
//                               msg.what = 0;
//                               msg.arg1 = i;
//                               msg.obj = person;
//                               handler.sendMessage(msg);
//                           }
//                       }
//                   }
//
//               }
//           }
//       });
//        thread.start();

    }

    private int oldType=0;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    int i = msg.arg1;
                    PersonView person = (PersonView) msg.obj;
                    if (!moveTo(oldType,i,person)) {
                        if (i / 10 - 1 >= 0) {
                            if (!boxViews[i / 10 - 1][i % 10 ].isBlack() && !boxViews[i / 10 - 1][i % 10 ].isStone() && !boxViews[i / 10 - 1][i % 10].isSnackHere()) {
                                person.moveTo(boxViews[i / 10][i % 10], boxViews[i / 10 - 1][i % 10 ]);
                                oldType = 0;
                                return;
                            }

                        }  if (i % 10 + 1 < 10 ) {
                            if (!boxViews[i / 10][i % 10 + 1].isBlack() && !boxViews[i / 10][i % 10 + 1].isStone()&& !boxViews[i / 10][i % 10 + 1].isSnackHere()) {
                                person.moveTo(boxViews[i / 10][i % 10], boxViews[i / 10][i % 10 + 1]);
                                oldType = 1;
                                return;
                            }
                        }  if (i % 10 - 1 > 0 ) {
                            if (!boxViews[i / 10][i % 10 - 1].isBlack() && !boxViews[i / 10][i % 10 - 1].isStone()&& !boxViews[i / 10 ][i % 10 - 1].isSnackHere()) {
                                person.moveTo(boxViews[i / 10][i % 10], boxViews[i / 10][i % 10 - 1]);
                                oldType = 2;
                                return;
                            }
                        }  if (i / 10 + 1 < 10) {
                            if (!boxViews[i / 10 + 1][i % 10].isBlack() && !boxViews[i / 10+1][i % 10].isStone()&& !boxViews[i / 10 + 1][i % 10].isSnackHere()) {

                                person.moveTo(boxViews[i / 10][i % 10], boxViews[i / 10 + 1][i % 10]);
                                oldType = 3;
                                return;
                            }
                        }
                    }
                    break;

            }
        }
    };
    private Boolean moveTo(int type,int i,PersonView person){
        switch (type){
            case 0:
                if ( i / 10 - 1 >= 0)
                    if (!boxViews[i / 10 - 1][i % 10].isBlack() && !boxViews[i / 10 - 1][i % 10].isStone()&& !boxViews[i / 10 - 1][i % 10].isSnackHere()) {
                        person.moveTo(boxViews[i / 10][i % 10], boxViews[i / 10 - 1][i % 10 ]);
                        return true;
                    }
                break;
            case 1:
                if (i % 10 + 1 < 10 )
                    if (!boxViews[i / 10][i % 10 + 1].isBlack() && !boxViews[i / 10][i % 10 + 1].isStone()&& !boxViews[i / 10][i % 10 + 1].isSnackHere()) {
                        person.moveTo(boxViews[i / 10][i % 10], boxViews[i / 10 ][i % 10 + 1]);
                        return true;
                    }
                break;
            case 2:
                if (i % 10 - 1 > 0 )
                    if (!boxViews[i / 10 ][i % 10 - 1].isBlack() && !boxViews[i / 10 ][i % 10 - 1].isStone()&& !boxViews[i / 10][i % 10 - 1].isSnackHere()) {
                        person.moveTo(boxViews[i / 10][i % 10], boxViews[i / 10 ][i % 10 - 1]);
                        return  true;
                    }

                break;
            case 3:
                if ( i / 10 + 1 < 10)
                    if (!boxViews[i / 10 + 1][i % 10].isBlack() && !boxViews[i / 10 + 1][i % 10 ].isStone()&& !boxViews[i / 10 + 1][i % 10 ].isSnackHere()) {
                        person.moveTo(boxViews[i / 10][i % 10], boxViews[i / 10 + 1][i % 10 ]);
                        return true;
                    }

                break;
        }
        return false;

    }

//    public void setBoxTypes(int... boxTypes) {
//        for (int i = 0; i < boxTypes.length; i++) {
//            this.boxTypes[i / 10][i % 10] = boxTypes[i];
//        }
//    }
}
