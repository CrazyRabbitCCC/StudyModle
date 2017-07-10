package czrbt.lzy.record.view.viewHelper;
// @author: lzy  time: 2016/10/27.


import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

public interface BaseHelper {
    void refreshView();
    void addData(Object o);
    void postData(Object o);
    void afterLoading();
    int getLayoutId();
    void setRefresh(boolean flag);
    SwipeRefreshLayout getRefreshLayout();
    View getViewForSnack();
}
