package study.lzy.qqimitate.Fragment;
// @author: lzy  time: 2016/09/23.


import android.content.Intent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import study.lzy.qqimitate.Adapter.BaseAdapter;
import study.lzy.qqimitate.Adapter.BaseHolder;
import study.lzy.qqimitate.TestActivity;
import study.lzy.qqimitate.selfView.BadgeView;
import study.lzy.qqimitate.selfView.MessageView;

public class DiscoverFragment extends BaseFragment{


    @Override
    protected void setOnItemClickListener() {

        onItemClickListener=new BaseHolder.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Action item = (Action) adapter.getItem(position);
                Intent intent=new Intent(getActivity(),item.getTo());
                intent.putExtra("Message",item.getText());
                startActivity(intent);
            }
        };
    }

    @Override
    protected void setAdapter() {
        List<Action> list=new ArrayList<>();
        for (int i=0;i<10;i++){
            list.add(new Action("功能 "+(i+1), TestActivity.class));
        }
        adapter=new Adapter(onItemClickListener,list);

    }

    @Override
    protected void refresh() {

    }

    class Adapter extends BaseAdapter<Action>{

        public Adapter(BaseHolder.OnItemClickListener onItemClickListener, List<Action> list) {
            super(onItemClickListener, list);
        }

        @Override
        protected BaseHolder getHolder(View itemView,int viewType) {
            return new BaseHolder(itemView,onItemClickListener,0);
        }

        @Override
        protected String getHeaderText(int position) {
            return " ";
        }

        @Override
        public void onBindViewHolder(BaseHolder holder, int position) {
            MessageView messageView=new MessageView(getActivity(),holder.iamge,parent);
            messageView.setText(position+"");
            messageView.show();
            holder.title.setText(list.get(position).text);

        }

        @Override
        public long getHeaderId(int position) {
            return (position+1)/2;
        }
    }
    class Action{
        String text;
        Class<?> to;

        public Action(String text, Class<?> to) {
            this.text = text;
            this.to = to;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public Class<?> getTo() {
            return to;
        }

        public void setTo(Class<?> to) {
            this.to = to;
        }
    }
}
