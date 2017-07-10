package udacity.lzy.test.recycler;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;

// @author: lzy  time: 2016/10/17.


public abstract class BaseHolder extends RecyclerView.ViewHolder implements OnClickListener,ItemTouch.onStateChangeListener {
    OnItemClickListener listener;

    public BaseHolder(View itemView,OnItemClickListener listener) {
        super(itemView);
        initView(itemView);
        itemView.getWidth();
        this.listener=listener;
        itemView.setOnClickListener(this);
    }

    protected abstract void initView(View itemView);

    @Override
    public void onClick(View v) {
        if (listener!=null)
            listener.onItemClick(getAdapterPosition(),v);
    }

    public interface OnItemClickListener {
        void onItemClick(int position, View view);
    }
    @Override
    public void onItemSelect() {
        itemView.setBackgroundColor(Color.BLUE);
    }

    @Override
    public void onItemClear() {
        itemView.setBackgroundColor(Color.argb(0,0,0,0));
    }
}
