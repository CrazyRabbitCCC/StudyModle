package czrbt.lzy.mylibrary.mvp;
// @author: lzy  time: 2016/10/27.


import android.content.Context;

/**
 * person基类
 * @param <H> 继承自ViewHelper的扩展接口
 */
public abstract class BasePerson<H extends ViewHelper> {
    protected H viewHelper;
    protected Context mContext;

    public BasePerson(Context context, H view) {
        mContext=context;
        this.viewHelper = view;
    }

    public abstract void onCreate();

    public abstract void onStop();

    public abstract  void onDestroy();

    public abstract void onPause();

    public abstract void onResume();
}
