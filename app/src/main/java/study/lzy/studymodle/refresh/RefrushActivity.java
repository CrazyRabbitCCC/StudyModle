package study.lzy.studymodle.refresh;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import study.lzy.studymodle.AboutHolder.BaseAdapter;
import study.lzy.studymodle.AboutHolder.Holder;
import study.lzy.studymodle.R;

public class RefrushActivity extends AppCompatActivity {

    @Bind(R.id.recycler1)
    RecyclerView recycler1;
    @Bind(R.id.refresh1)
    SwipeRefreshLayout refresh1;
    @Bind(R.id.recycler2)
    RecyclerView recycler2;
    @Bind(R.id.refresh2)
    SwipeRefreshLayout refresh2;
    @Bind(R.id.recycler3)
    RecyclerView recycler3;
    @Bind(R.id.refresh3)
    SwipeRefreshLayout refresh3;
    @Bind(R.id.recycler4)
    RecyclerView recycler4;
    @Bind(R.id.refresh4)
    SwipeRefreshLayout refresh4;
    @Bind(R.id.recycler5)
    RecyclerView recycler5;
    @Bind(R.id.refresh5)
    SwipeRefreshLayout refresh5;
    @Bind(R.id.recycler6)
    RecyclerView recycler6;
    @Bind(R.id.refresh6)
    SwipeRefreshLayout refresh6;

    AAdapter adapter1;
    AAdapter adapter2;
    AAdapter adapter3;
    AAdapter adapter4;
    AAdapter adapter5;
    AAdapter adapter6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refrush);
        ButterKnife.bind(this);
        initRecycler();
        setRefresh();
    }

    private void setRefresh() {
        refresh1.setColorSchemeColors(Color.BLUE);
        refresh1.setOnChildScrollUpCallback((parent, child) -> {
            Logger.d(child.getClass().getSimpleName());
            return child.callOnClick();
        });
        refresh1.setDistanceToTriggerSync(100);
        refresh1.setProgressBackgroundColorSchemeColor(Color.GREEN);

        refresh2.setDistanceToTriggerSync(300);
        refresh2.setSize(0);




    }

    private void initRecycler() {
        List<String> list=new ArrayList<>();
        
        for (int i = 0; i < 20; i++) {
            list.add("String test "+(i+1));
        }
        adapter1=new AAdapter();
        adapter1.add(list);
        LinearLayoutManager manager1= new LinearLayoutManager(this);
        manager1.setOrientation(LinearLayoutManager.VERTICAL);
        adapter2=new AAdapter();
        adapter2.add(list);
        LinearLayoutManager manager2= new LinearLayoutManager(this);
        manager2.setOrientation(LinearLayoutManager.VERTICAL);
        adapter3=new AAdapter();
        adapter3.add(list);
        LinearLayoutManager manager3= new LinearLayoutManager(this);
        manager3.setOrientation(LinearLayoutManager.VERTICAL);
        adapter4=new AAdapter();
        adapter4.add(list);
        LinearLayoutManager manager4= new LinearLayoutManager(this);
        manager4.setOrientation(LinearLayoutManager.VERTICAL);
        adapter5=new AAdapter();
        adapter5.add(list);
        LinearLayoutManager manager5= new LinearLayoutManager(this);
        manager5.setOrientation(LinearLayoutManager.VERTICAL);
        adapter6=new AAdapter();
        adapter6.add(list);
        LinearLayoutManager manager6= new LinearLayoutManager(this);
        manager6.setOrientation(LinearLayoutManager.VERTICAL);
        recycler1.setLayoutManager(manager1);
        recycler1.setAdapter(adapter1);
        recycler2.setLayoutManager(manager2);
        recycler2.setAdapter(adapter2);
        recycler3.setLayoutManager(manager3);
        recycler3.setAdapter(adapter3);
        recycler4.setLayoutManager(manager4);
        recycler4.setAdapter(adapter4);
        recycler5.setLayoutManager(manager5);
        recycler5.setAdapter(adapter5);
        recycler6.setLayoutManager(manager6);
        recycler6.setAdapter(adapter6);
    }


    class AAdapter extends BaseAdapter{
        List<String> list=new ArrayList<>();

        void add(List<String> list){
            this.list.addAll(list);
            notifyDataSetChanged();
        }
        @Override
        public void onBindViewHolder(Holder holder, int position) {
            holder.text.setText(list.get(position));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }
}
