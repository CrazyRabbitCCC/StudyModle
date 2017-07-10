package czrbt.lzy.mylibrary.mvp;
// @author: lzy  time: 2016/10/28.


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;

/**
 * activity 基类
 * @param <H> 继承自ViewHelper的扩展接口
 * @param <T> 继承自BasePerson类
 */
public abstract class BaseActivity<H extends ViewHelper,T extends BasePerson<H>> extends AppCompatActivity implements ViewHelper {
    protected T person;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        person=getPerson();
        person.onCreate();
        setContentView(getLayoutId());
        initView();
    }

    protected abstract int getLayoutId();


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

    public abstract SwipeRefreshLayout getRefreshLayout();
}
