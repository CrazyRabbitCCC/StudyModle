package czrbt.lzy.record.view.fragment;
// @author: lzy  time: 2016/11/01.


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import czrbt.lzy.record.R;
import czrbt.lzy.record.bean.Save;
import czrbt.lzy.record.data.Provider;
import czrbt.lzy.record.data.SaveColumns;
import czrbt.lzy.record.person.BasePerson;

public class MainFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<Cursor> {


    @Bind(R.id.recycler)
    RecyclerView recycler;
    @Bind(R.id.refresh)
    SwipeRefreshLayout refresh;
    List<Save> list;
    MyAdapter myAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = new ArrayList<>();
        myAdapter=new MyAdapter();
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this, view);
        refresh.setOnRefreshListener(this::refreshView);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            ArrayList<Save> saves = savedInstanceState.getParcelableArrayList("saves");
            myAdapter.addAll(saves);
            long position = savedInstanceState.getLong("position", 0);
            recycler.scrollToPosition((int) position);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        long position = recycler.getChildItemId(recycler.findChildViewUnder(1f, 1f));
        outState.putLong("position",position);
        outState.putParcelableArrayList("saves",myAdapter.getList());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.text:
            case R.id.voice:
            case R.id.picture:
            case R.id.money:
        }

        return true;
    }

    @Override
    public int getMenuId() {
        return R.menu.main;
    }

    @Override
    protected BasePerson getPerson() {
        return null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), Provider.SAVE.CONTENT_URI,null,null,null, SaveColumns.DATE+" ASC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        while (data!=null&&data.moveToNext()){
            myAdapter.update(Save.getInstance(data));
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

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

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    public SwipeRefreshLayout getRefreshLayout() {
        return null;
    }

    @Override
    public View getViewForSnack() {
        return null;
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.Holder> {
        ArrayList<Save> list;

        public MyAdapter() {
            list=new ArrayList<>();
        }

        public void add(Save save){
            list.add(save);
            notifyItemInserted(getItemCount()-1);
        }

        public void add(int position,Save save){
            list.add(position,save);
            notifyItemInserted(position);
        }

        public void update(Save save){
            int position=0;
            for (int i = 0; i < list.size(); i++)
                if(list.get(i).getId()==save.getId()){
                    if (list.get(i).equals(save))
                        return;
                    position=i;
                    break;
                }
            if (list.size()==position)
                add(save);
            else {
                list.get(position).update(save);
                notifyItemChanged(position);
            }
        }

        public void addAll(ArrayList<Save> saves){
            list.addAll(0,saves);
        }

        public ArrayList<Save> getList() {
            return list;
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
            return new Holder(itemView);
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            switch (list.get(position).getType()){
                case "text":break;
                case "voice":break;
                case "picture":break;
                case "money":break;
                default:break;
            }
            holder.title.setText(list.get(position).getTitle());
            holder.time.setText(list.get(position).getDate());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class Holder extends RecyclerView.ViewHolder {
            @Bind(R.id.image)
            ImageView image;
            @Bind(R.id.title)
            TextView title;
            @Bind(R.id.message)
            TextView message;
            @Bind(R.id.time)
            TextView time;
            public Holder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
                itemView.setOnClickListener(v->{
                });
            }
        }
    }



}
