package udacity.lzy.test.recycler;// @author: lzy  time: 2016/09/01.

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * RecyclerView  item操作
 */
public class ItemTouch extends ItemTouchHelper.Callback {

    private onMoveAndSwipedListener mAdapter;

    public ItemTouch(onMoveAndSwipedListener listener){
        mAdapter = listener;
    }

    /**设置拖动的方向以及侧滑的方向的*/
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        //如果是ListView样式的RecyclerView
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager){
            if (((LinearLayoutManager) recyclerView.getLayoutManager()).getOrientation()==LinearLayoutManager.VERTICAL) {
                //设置拖拽方向为上下
                final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                //设置侧滑方向为从左到右和从右到左都可以
            final int swipeFlags = ItemTouchHelper.START|ItemTouchHelper.END;
                //将方向参数设置进去
                return makeMovementFlags(dragFlags, swipeFlags);
            }
            else {
                //设置拖拽方向为左右
                final int dragFlags = ItemTouchHelper.START|ItemTouchHelper.END;
                //设置侧滑方向为从上到下和从下到上都可以
                final int swipeFlags= ItemTouchHelper.UP | ItemTouchHelper.DOWN;

                //将方向参数设置进去
                return makeMovementFlags(dragFlags, swipeFlags);
            }
        }else{//如果是GridView样式的RecyclerView
            //设置拖拽方向为上下左右
            final int dragFlags = ItemTouchHelper.UP| ItemTouchHelper.DOWN|
                    ItemTouchHelper.LEFT| ItemTouchHelper.RIGHT;
            //不支持侧滑
            final int swipeFlags = 0;
            return makeMovementFlags(dragFlags,swipeFlags);
        }
    }
    /**拖动item时会回调此方法*/
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        if (viewHolder.getItemViewType() != target.getItemViewType()){
            return false;
        }
        //回调adapter中的onItemMove方法
        return mAdapter.onItemMove(viewHolder.getAdapterPosition(),target.getAdapterPosition());
    }
    /**侧滑item时会回调此方法*/
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mAdapter.onItemDismiss(viewHolder.getAdapterPosition());
    }

    /** 选中的时候回调此方法*/
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE){
            //看看这个viewHolder是否实现了onStateChangedListener接口
            if (viewHolder instanceof onStateChangeListener) {
                onStateChangeListener listener = (onStateChangeListener)viewHolder;
                //回调ItemViewHolder中的onItemSelected方法来改变item的背景颜色
                listener.onItemSelect();
            }
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (viewHolder instanceof onStateChangeListener) {
            onStateChangeListener listener = (onStateChangeListener)viewHolder;
            //回调ItemViewHolder中的onItemSelected方法来改变item的背景颜色
            listener.onItemClear();
        }
        super.clearView(recyclerView, viewHolder);
    }



    public interface onMoveAndSwipedListener {
        boolean onItemMove(int fromPosition, int toPosition);
        void onItemDismiss(int position);
    }

    public interface onStateChangeListener{
        void onItemSelect();
        void onItemClear();
    }
}
