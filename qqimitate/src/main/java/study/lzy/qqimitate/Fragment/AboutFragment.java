package study.lzy.qqimitate.Fragment;
// @author: lzy  time: 2016/09/27.


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import study.lzy.qqimitate.Adapter.BaseAdapter;
import study.lzy.qqimitate.Adapter.BaseHolder;
import study.lzy.qqimitate.R;
import study.lzy.qqimitate.TestActivity;
import study.lzy.qqimitate.WxApplication;
import study.lzy.qqimitate.selfView.MessageView;

public class AboutFragment extends BaseFragment {



    @Override
    protected void setOnItemClickListener() {
        onItemClickListener=new BaseHolder.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Action item = (Action) adapter.getItem(position);
                Intent intent=new Intent(getActivity(),item.getTo());
                intent.putExtra("Message",item.getText());
                startActivity(intent);
                //so?
            }
        };
    }

    @Override
    protected void setAdapter() {

        List<Action> list=new ArrayList<>();
        list.add(new Action("测试账号", TestActivity.class));
        for (int i=0;i<10;i++){
            list.add(new Action("功能 "+(i+1), TestActivity.class));
        }
        adapter=new AboutAdapter(onItemClickListener,list);

    }

    @Override
    protected void refresh() {

    }

    class AboutAdapter extends BaseAdapter<Action> {

        public AboutAdapter(BaseHolder.OnItemClickListener onItemClickListener, List<Action> list) {
            super(onItemClickListener, list);
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0)
                return 0;
            else return 1;
        }

        @Override
        public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == 0) {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.about_head_item, parent, false);
                return getHolder(itemView, viewType);
            } else
                return super.onCreateViewHolder(parent, viewType);
        }

        @Override
        protected BaseHolder getHolder(View itemView, int viewType) {
            if (viewType == 0)
                return new BaseHolder(itemView, onItemClickListener, 1);
            return new BaseHolder(itemView, onItemClickListener, 0);
        }

        @Override
        protected String getHeaderText(int position) {
            return "";
        }

        @Override
        public void onBindViewHolder(BaseHolder holder, int position) {
            if (getItemViewType(position) == 0) {
                holder.message.setText("微信号：" + WxApplication.getEnglish(list.get(position).getText()));
                holder.time.setText("");
            }
            MessageView messageView=new MessageView(getActivity(),holder.iamge,parent);
//            BadgeView badgeView=new BadgeView(getActivity(),holder.iamge);
            messageView.setText(position+"");
            messageView.show();
            holder.title.setText(list.get(position).getText());

        }

        @Override
        public long getHeaderId(int position) {
            return (position + 1) / 2;
        }
    }

    class Action {
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
