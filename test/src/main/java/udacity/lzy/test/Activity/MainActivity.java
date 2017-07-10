package udacity.lzy.test.Activity;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import udacity.lzy.test.R;
import udacity.lzy.test.bean.Save;
import udacity.lzy.test.data.Provider;
import udacity.lzy.test.recycler.BaseAdapter;
import udacity.lzy.test.recycler.BaseHolder;
import udacity.lzy.test.recycler.DividerItemDecoration;
import udacity.lzy.test.recycler.ItemTouch;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    @butterknife.Bind(R.id.toolbar)
    Toolbar toolbar;
    @butterknife.Bind(R.id.recycler)
    RecyclerView recycler;
    @butterknife.Bind(R.id.fab)
    FloatingActionButton fab;
    BaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        butterknife.ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        getLoaderManager().initLoader(0, null, this);
        fab.setOnClickListener(view -> {
            Intent mIntent = new Intent(this, TextActivity.class);
            mIntent.putExtra("haveData", false);
            startActivity(mIntent);
        });

        adapter =new TextAdapter();
        adapter.setListener((position, view) -> {
            Intent mIntent = new Intent(this, TextActivity.class);
            mIntent.putExtra("haveData", true);
            mIntent.putExtra("save",(Save) adapter.getItem(position));
            startActivity(mIntent);
        });
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(manager);
        recycler.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        recycler.setAdapter(adapter);
        ItemTouchHelper.Callback callback = new ItemTouch(adapter);
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recycler);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return
                new CursorLoader(this, Provider.SAVE.CONTENT_URI,null,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        while (data!=null&&data.moveToNext()){
            Save sa=Save.getInstance(data);
            if (indexOf(sa)<0)
                adapter.add(sa);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public int indexOf(Save o) {
        for (int i = 0; i < adapter.getItemCount(); i++) {
            Save item = (Save) adapter.getItem(i);
            if (item.getId()==o.getId()){
                item.update(o);
                adapter.notifyItemChanged(i);
                return i;
            }
        }
        return -1;
    }

    class TextAdapter extends BaseAdapter<Save, TextHolder> {

        @Override
        public TextHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_item, parent, false);
            return new TextHolder(itemView, listener);
        }

        @Override
        public void onBindViewHolder(TextHolder holder, int position) {
            holder.title.setText(list.get(position).getTitle());
            holder.msg.setText(list.get(position).getDate());
        }

        @Override
        public void remove(int position) {
            Save save = list.get(position);
            super.remove(position);
            getContentResolver().delete(Provider.SAVE.withId(save.getId()),null,null);
        }
    }

    public class TextHolder extends BaseHolder {
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.msg)
        TextView msg;
        public TextHolder(View itemView, OnItemClickListener listener) {
            super(itemView, listener);
        }

        @Override
        protected void initView(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }
}
