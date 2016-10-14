package study.lzy.qqimitate.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import study.lzy.qqimitate.Adapter.BaseHolder.OnItemClickListener;

import study.lzy.qqimitate.Adapter.BaseAdapter;
import study.lzy.qqimitate.Adapter.DividerDecoration;
import study.lzy.qqimitate.DataBase.DbHelper;
import study.lzy.qqimitate.R;
import study.lzy.qqimitate.WxApplication;

// @author: lzy  time: 2016/09/21.


public abstract class BaseFragment extends Fragment {
    protected WxApplication wxApp;
    private View view;
    private SwipeRefreshLayout refresh;
    protected RecyclerView recycleView;
    protected View parent;
    protected BaseAdapter adapter;
    protected OnItemClickListener onItemClickListener;
    protected Gson gson;
    protected DbHelper dbHelper;
    protected String[] result;
    private boolean first=true;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view==null){
            view=inflater.inflate(R.layout.fragment_weixin,container,false);
            initView();
        }
        if (view.getParent() !=null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            parent.removeView(view);
        }
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);
    }



    private void initView(){
        wxApp= (WxApplication) getActivity().getApplication();
        gson = new GsonBuilder().create();
        dbHelper = new DbHelper(getActivity());
        recycleView = (RecyclerView) view.findViewById(R.id.recycle_view);
        refresh = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        refresh.setColorSchemeColors(Color.RED,Color.BLUE,Color.GREEN);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
                refresh.setRefreshing(false);
            }
        });
        setRecyclerView();
        initData();
    }

    protected  void initData(){
        setOnItemClickListener();
        setAdapter();
        if (first) {
            recycleView.addItemDecoration(new StickyRecyclerHeadersDecoration(adapter));
            first=false;
        }
        recycleView.setAdapter(adapter);
    }

    private void setRecyclerView(){
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recycleView.setLayoutManager(manager);
        recycleView.addItemDecoration(new DividerDecoration(getActivity()));

    }

    public void setParent(View parent) {
        this.parent = parent;
    }

    protected abstract void setOnItemClickListener();
    protected abstract void setAdapter();
    protected abstract void refresh();
}
