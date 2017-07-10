package czrbt.lzy.record.view;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import czrbt.lzy.record.R;
import czrbt.lzy.record.person.BasePerson;
import czrbt.lzy.record.view.viewHelper.BaseHelper;

public class SaveActivity extends BaseActivity<BaseHelper,BasePerson<BaseHelper>>  {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_save;
    }

    @Override
    public SwipeRefreshLayout getRefreshLayout() {
        return null;
    }

    @Override
    public View getViewForSnack() {
        return null;
    }

    @Override
    protected BasePerson<BaseHelper> getPerson() {
        return null;
    }

    @Override
    protected void initView(){

    }

    @Override
    public void refreshView() {

    }

    @Override
    public void addData(Object o) {

    }

    @Override
    public void postData(Object o) {

    }

    @Override
    public void afterLoading() {

    }
}
