package czrbt.lzy.record.person;
// @author: lzy  time: 2016/10/27.


import android.app.ProgressDialog;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import czrbt.lzy.record.view.viewHelper.BaseHelper;

public abstract class BasePerson<H extends BaseHelper> {
    protected H viewHelper;
    protected Context mContext;
    private  Toast toast;
    private  Snackbar snackbar;
    private ProgressDialog progress;

    public BasePerson(Context context,H view) {
        mContext=context;
        this.viewHelper = view;
    }


    public  void showToast(String msg){
        if (toast==null)
            toast= Toast.makeText(mContext,msg,Toast.LENGTH_SHORT);
        else
            toast.setText(msg);
        toast.show();
    }
    public  void showSnack(String msg,String action , View.OnClickListener listener){
        if (snackbar==null)
            snackbar= Snackbar.make(viewHelper.getViewForSnack(),msg,Snackbar.LENGTH_SHORT);
        else
            snackbar.setText(msg);
        if (action!=null&&!action.isEmpty()&&listener!=null)
            snackbar.setAction(action,listener);
        snackbar.show();

    }
    public void showProgress(String msg){
        if (progress==null) {
            progress = new ProgressDialog(mContext);
            progress.setCancelable(false);
        }
        progress.setMessage(msg);
        if (!progress.isShowing())
            progress.show();
    }
    public  void dismissProgress(){
        if (progress!=null&&progress.isShowing())
            progress.dismiss();
    }
    public void setPro(int num,int total){
        if (progress==null) {
            progress = new ProgressDialog(mContext);
            progress.setCancelable(false);
        }
        progress.setMax(total);
        progress.setProgress(num);
        if (!progress.isShowing())
            progress.show();
    }

    public abstract void onCreate();

    public  void onStop(){
        mContext=null;
        viewHelper=null;
        toast=null;
        snackbar=null;
        progress=null;
    }


    public void onDestroy(){
        mContext=null;
        viewHelper=null;
        toast=null;
        snackbar=null;
        progress=null;
    }

    public abstract void onPause();

    public abstract void onResume();
}
