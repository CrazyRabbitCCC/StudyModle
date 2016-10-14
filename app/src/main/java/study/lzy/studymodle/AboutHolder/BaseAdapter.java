package study.lzy.studymodle.AboutHolder;// @author: lzy  time: 2016/09/18.

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import study.lzy.studymodle.R;

public abstract class BaseAdapter extends RecyclerView.Adapter<Holder> {

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(getItemView(parent,viewType),onClickListener);
    }

    public View getItemView(ViewGroup parent, int viewType){
       return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv, parent,false);
    }
}
