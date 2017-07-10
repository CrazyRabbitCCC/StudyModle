package study.lzy.studymodle.AboutHolder;// @author: lzy  time: 2016/09/18.

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import study.lzy.studymodle.R;

public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public ImageView image;
    public TextView text;
    private OnClickListener listener;

    public Holder(View itemView,OnClickListener listener) {
        super(itemView);
        this.listener=listener;
        image = (ImageView) itemView.findViewById(R.id.image);
        text = (TextView) itemView.findViewById(R.id.text);

        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (listener!=null)
        listener.onClick(v,getPosition());
    }
}
