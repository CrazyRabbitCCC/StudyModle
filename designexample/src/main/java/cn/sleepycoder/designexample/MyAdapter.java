package cn.sleepycoder.designexample;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Mark on 16/5/7.
 */
public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<String> mList;

    public MyAdapter(ArrayList<String> mList) {
        this.mList = mList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView textView = (TextView) View.inflate(parent.getContext(),android.R.layout.simple_list_item_1,null);
        return new VieHolder(textView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((TextView)(holder.itemView)).setText(mList.get(position));
    }

    @Override
    public int getItemCount() {
        if (mList==null) return 0;
        return mList.size();
    }

    class VieHolder extends RecyclerView.ViewHolder{

        public VieHolder(View itemView) {
            super(itemView);
        }
    }
}
