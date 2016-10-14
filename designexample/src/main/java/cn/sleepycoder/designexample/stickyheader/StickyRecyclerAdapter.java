package cn.sleepycoder.designexample.stickyheader;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;

import cn.sleepycoder.designexample.R;

/**
 * Created by Mark on 16/5/7.
 */
public class StickyRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {
    ArrayList<String> dataList;
    public StickyRecyclerAdapter(ArrayList<String> dataList) {
        this.dataList = dataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==0){
            BannerView banner = (BannerView) View.inflate(parent.getContext(),R.layout.banner,null);
            banner.setViewAdapter(new BannerAdapter());
            return new RecyclerView.ViewHolder(banner) {};
        }

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ContentHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(position>0 ){
            ((ContentHolder)holder).tvContent.setText(dataList.get(position-1));
            if(position>5 && position==dataList.size()){
                ((ContentHolder)holder).pBar.setVisibility(View.VISIBLE);
            }else{
                ((ContentHolder)holder).pBar.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public long getHeaderId(int position) {
        return (position-1)/5 -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.header, parent, false);

        return new HeadHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((HeadHolder)holder).title.setText("Head"+getHeaderId(position));
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return 0;
        }
        return 1;
    }


    @Override
    public int getItemCount() {
        if(dataList==null)
            return 1;
        return dataList.size()+1;
    }

    class HeadHolder extends RecyclerView.ViewHolder{
        private TextView title;
        public HeadHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }

    class ContentHolder extends RecyclerView.ViewHolder{
        TextView tvContent;
        ProgressBar pBar;
        public ContentHolder(View itemView) {
            super(itemView);
            tvContent = (TextView) itemView.findViewById(R.id.tvContent);
            pBar = (ProgressBar) itemView.findViewById(R.id.pBar);
        }
    }

    public interface LoadMoreListener{
        void onLoadMore();
    }
}
