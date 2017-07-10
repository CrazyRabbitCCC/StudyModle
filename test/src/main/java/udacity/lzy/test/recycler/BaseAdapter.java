package udacity.lzy.test.recycler;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 可自由添加·删除·修改·移动数据 的RecyclerView.Adapter
 * @param <T> 包含数据类型
 * @param <H> ViewHolder
 * @author lzy  time: 2016/11/08.
 */
public abstract class BaseAdapter<T,H extends BaseHolder> extends RecyclerView.Adapter<H> implements ItemTouch.onMoveAndSwipedListener {
    protected List<T> list;
    protected BaseHolder.OnItemClickListener listener;
    public BaseAdapter() {
        list=new ArrayList<>();
    }

    public BaseAdapter(List<T> list) {
        this.list = list;
    }
    public void add(T e){
        list.add(e);
        notifyItemInserted(list.size()-1);
    }
    public void add( List<T> es){
        list.addAll(es);
        notifyDataSetChanged();
    }
    public void remove(int position){
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void clear(){
        list.clear();
        notifyDataSetChanged();
    }

    public void change(int position,T e){
        list.set(position,e);
        notifyItemChanged(position);
    }

    public void setListener(BaseHolder.OnItemClickListener listener) {
        this.listener = listener;
    }

    public T getItem(int position){
        return  list.get(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onItemDismiss(int position) {
        remove(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        //交换mItems数据的位置
        Collections.swap(list, fromPosition, toPosition);
        //交换RecyclerView列表中item的位置
        notifyItemMoved(fromPosition,toPosition);
        return true;
    }
}
