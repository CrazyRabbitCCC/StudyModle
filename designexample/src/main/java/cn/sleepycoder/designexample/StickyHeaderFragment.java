package cn.sleepycoder.designexample;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import java.util.ArrayList;
import cn.sleepycoder.designexample.stickyheader.DividerDecoration;
import cn.sleepycoder.designexample.stickyheader.StickyRecyclerAdapter;

/**
 * Created by Mark on 16/5/7.
 */
public class StickyHeaderFragment extends Fragment {
    private StickyRecyclerAdapter adapter;
    private ArrayList<String> dataList;
    private View view;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Handler mHandler;
    boolean isLoadMore=false;
    private SwipeRefreshLayout.OnRefreshListener refreshListener;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataList = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.content_main,null);
        initView();
        setRecyclerView();

        getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                refreshListener.onRefresh();
            }
        }, 100);
        return view;
    }

    private Handler getHandler(){
        if(mHandler==null){
            mHandler = new Handler(getActivity().getMainLooper());
        }
        return mHandler;
    }
    private void initView(){
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeColors(Color.RED,Color.BLUE,Color.GREEN);
        refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                addData();
            }
        };
        swipeRefreshLayout.setOnRefreshListener(refreshListener);

    }

    private void addData(){
        if(isLoadMore){
           return;
        }
        isLoadMore = true;
        getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int size = dataList.size();
                for(int i=size;i<size+10;i++){
                    dataList.add("String"+i);
                }
                swipeRefreshLayout.setRefreshing(false);
                adapter.notifyDataSetChanged();
                isLoadMore = false;
            }
        },1000);

    }

    private void setRecyclerView(){
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);

        adapter = new StickyRecyclerAdapter(dataList);
        recyclerView.setAdapter(adapter);

        recyclerView.addItemDecoration(new StickyRecyclerHeadersDecoration(adapter));
        recyclerView.addItemDecoration(new DividerDecoration(getActivity()));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager mLayoutManager = recyclerView.getLayoutManager();
                int lastVisibleItem = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
                int totalItemCount = mLayoutManager.getItemCount();
                //最后一项显示且是下滑的时候调用加载
                if (lastVisibleItem >= totalItemCount-1 && dy > 0) {
                    addData();
                }
            }
        });
    }

}
