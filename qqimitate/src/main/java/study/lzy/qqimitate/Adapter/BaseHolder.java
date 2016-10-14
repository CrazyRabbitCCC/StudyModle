package study.lzy.qqimitate.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import study.lzy.qqimitate.R;

// @author: lzy  time: 2016/09/21.


public class BaseHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public ImageView iamge;
    public TextView title;
    public TextView time;
    public LinearLayout messageLine;
    public TextView message;
    public ImageView littleImage;
    private OnItemClickListener onItemClickListener;
    public BaseHolder(View itemView,OnItemClickListener onItemClickListener,int type) {
        super(itemView);

        iamge = (ImageView) itemView.findViewById(R.id.iamge);
        title = (TextView) itemView.findViewById(R.id.title);
        time = (TextView) itemView.findViewById(R.id.time);
        messageLine = (LinearLayout) itemView.findViewById(R.id.message_line);
        message = (TextView) itemView.findViewById(R.id.message);
        littleImage = (ImageView) itemView.findViewById(R.id.little_image);

        if (type==0){
            time .setVisibility(View.GONE);
            messageLine.setVisibility(View.GONE);;
        }

        itemView.setOnClickListener(this);
        this.onItemClickListener=onItemClickListener;
    }

    @Override
    public void onClick(View v) {
        if (onItemClickListener!=null)
            onItemClickListener.onItemClick(itemView,getPosition());
    }

    public interface OnItemClickListener{
        void onItemClick(View v,int  position);
    }
}
