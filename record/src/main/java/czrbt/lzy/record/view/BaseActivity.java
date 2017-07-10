package czrbt.lzy.record.view;
// @author: lzy  time: 2016/10/28.


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import czrbt.lzy.record.person.BasePerson;
import czrbt.lzy.record.view.viewHelper.BaseHelper;

public abstract class BaseActivity<H extends BaseHelper,T extends BasePerson<H>> extends AppCompatActivity implements BaseHelper {

    private Toast toast;
    protected Snackbar snackbar;
    private View snackView;
    protected ProgressDialog progress;
    protected T person;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        person=getPerson();
        person.onCreate();
        setContentView(getLayoutId());
        initView();
    }




    @Override
    protected void onDestroy() {
        person.onDestroy();
        super.onDestroy();

    }


    @Override
    protected void onStop() {
        person.onStop();
        super.onStop();
    }

    @Override
    protected void onPause() {
        person.onPause();
        super.onPause();

    }

    @Override
    protected void onResume() {
        person.onResume();
        super.onResume();
    }
    protected abstract T getPerson();
    protected abstract void initView();

    public  void setRefresh(boolean flag){
        if (getRefreshLayout()!=null)
            getRefreshLayout().setRefreshing(flag);
    }
}
