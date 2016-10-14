package study.lzy.studymodle.Tree;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import study.lzy.studymodle.AboutHolder.BaseAdapter;
import study.lzy.studymodle.AboutHolder.Holder;
import study.lzy.studymodle.AboutHolder.OnClickListener;
import study.lzy.studymodle.R;

public class TreeActivity extends AppCompatActivity {
    RecyclerView rv;
    Adapter adapter;
    Map<Integer,Tree> treeMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = (RecyclerView) findViewById(R.id.rv);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);
        rv.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        List<Test> test=new ArrayList<>();
        for (int i=0;i<5;i++){
            test.add(new Test("","head"+(i+1)));
            for (int j=0;j<5;j++){
                test.add(new Test("head"+(i+1),"body"+(i+1)+"0"+(j+1)));

                for (int k=0;k<5;k++){
                    test.add(new Test("body"+(i+1)+"0"+(j+1),"tree"+(i+1)+"0"+(j+1)+"0"+(k+1)));
                }
            }
        }
        treeMap=new HashMap<>();

        TreeListener listener = new TreeListener() {
            @Override
            public void noChild(Tree tree) {
                Snackbar.make(rv, tree.getTitle(), Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void showChild(Tree tree) {
                adapter.setList(Tree.getChild(-1,treeMap));
            }

            @Override
            public void dismissChild(Tree tree) {
                adapter.setList(Tree.getChild(-1,treeMap));
            }

            @Override
            public Tree FindParent(Object data) {
                Test test=(Test)data;
                for (Map.Entry<Integer,Tree> entry:treeMap.entrySet()){
                    if (test.parent.equals(((Test)(entry.getValue().getData())).body))
                        return entry.getValue();
                }
                return null;
            }

            @Override
            public String getTitle(Object data) {
                return ((Test)data).body;
            }
        };


        for(Test data:test){
            Tree tree=Tree.getInstance(data,listener);
            treeMap=tree.saveToMap(treeMap);
        }

        adapter=new Adapter(Tree.getChild(-1,treeMap));
        adapter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v, int position) {
                adapter.getList().get(position).deal();
            }
        });
        rv.setAdapter(adapter);
    }

    class Adapter extends BaseAdapter {
        private List<Tree> list;

        public Adapter(List<Tree> list) {
            this.list = list;
        }

        public List<Tree> getList() {
            return list;
        }

        public void setList(List<Tree> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        @Override
        public void onBindViewHolder(Holder holder, final int position) {

            if (!list.get(position).isHasChild()) {
                holder.image.setImageResource(R.drawable.ic_menu_gallery);
            } else if (!list.get(position).isChildShow()) {
                holder.image.setImageResource(R.drawable.ic_menu_share);
            } else
                holder.image.setImageResource(R.drawable.ic_menu_send);

            holder.itemView.setPadding(2+list.get(position).getLevel()*10,2,2,2);
            holder.itemView.setScaleX((100f - list.get(position).getLevel() * 10) / 100);
            holder.itemView.setScaleY((100f - list.get(position).getLevel() * 10) / 100);
            holder.text.setText(list.get(position).getTitle());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    class Test{
        public Test(String level1, String level2) {
            this.parent = level1;
            this.body = level2;
        }
        String parent;
        String body;

        public String getParent() {
            return parent;
        }

        public void setParent(String parent) {
            this.parent = parent;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }
    }

}
