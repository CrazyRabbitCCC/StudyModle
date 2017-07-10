package czrbt.lzy.mylibrary.mvp;
// @author: lzy  time: 2016/10/27.


import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

/**
 * 用于person操作view的接口
 */
public interface ViewHelper {
    void refreshView();
    void addData(Object o);
    void postData(Object o);
    void afterLoading();
    void setRefresh(boolean flag);
    SwipeRefreshLayout getRefreshLayout();
    View getViewForSnack();
}
