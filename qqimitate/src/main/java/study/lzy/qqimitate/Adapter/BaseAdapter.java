package study.lzy.qqimitate.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.security.PublicKey;
import java.util.List;

import study.lzy.qqimitate.R;

// @author: lzy  time: 2016/09/21.


public abstract class BaseAdapter<TT> extends RecyclerView.Adapter<BaseHolder>
        implements StickyRecyclerHeadersAdapter<TitleHolder> {
    protected BaseHolder.OnItemClickListener onItemClickListener;
    protected List<TT> list;

    public BaseAdapter(BaseHolder.OnItemClickListener onItemClickListener, List<TT> list) {
        this.onItemClickListener = onItemClickListener;
        this.list = list;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item, parent, false);
        return getHolder(itemView, viewType);
    }

    protected abstract BaseHolder getHolder(View itemView, int viewType);


    public TT getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    @Override
    public TitleHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_item, parent, false);
        return new TitleHolder(itemView);
    }

    @Override
    public void onBindHeaderViewHolder(TitleHolder holder, int position) {
        holder.textView.setText(getHeaderText(position));
    }

    protected abstract String getHeaderText(int position);
}


class TitleHolder extends RecyclerView.ViewHolder {
    TextView textView;

    TitleHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.text);
    }
}
