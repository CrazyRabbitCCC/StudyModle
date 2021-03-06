package czrbt.lzy.mylibrary.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import czrbt.lzy.mylibrary.R;


// @author: lzy  time: 2016/10/31.

/**
 * BaseFragment 基类
 * @param <H> 继承自ViewHelper的扩展接口
 * @param <P> 继承自BasePerson类
 * @author lzy  time: 2016/10/31.
 */
public abstract class BaseFragment<H extends ViewHelper,P extends BasePerson<H>> extends Fragment implements ViewHelper{

    protected View view;
    protected P person;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        person=getPerson();
        setHasOptionsMenu(true);
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view==null) {
            if (getLayoutId()==0){
                TextView textView = new TextView(getActivity());
                textView.setText(R.string.fragment_error);
                return textView;
            }
            view = inflater.inflate(getLayoutId(), container, false);
            initView();
        }
        if (view.getParent()!=null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            parent.removeView(view);
        }
        return view;
    }

    protected abstract int getLayoutId();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData(savedInstanceState);
    }

    protected abstract void initView();
    protected abstract void initData(Bundle savedInstanceState);

    public abstract void onSaveInstanceState(Bundle outState);
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (getMenuId()!=0)
            inflater.inflate(getMenuId(),menu);
    }
    public abstract boolean onOptionsItemSelected(MenuItem item) ;
    public abstract int  getMenuId() ;
    protected abstract P getPerson();

    public  void setRefresh(boolean flag){
        if (getRefreshLayout()!=null)
            getRefreshLayout().setRefreshing(flag);
    }
}
