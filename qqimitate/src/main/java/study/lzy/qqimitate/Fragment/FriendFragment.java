package study.lzy.qqimitate.Fragment;
// @author: lzy  time: 2016/09/21.


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import study.lzy.qqimitate.Adapter.BaseAdapter;
import study.lzy.qqimitate.Adapter.BaseHolder;
import study.lzy.qqimitate.Bean.FriendBean;
import study.lzy.qqimitate.CHat.ChatActivity;
import study.lzy.qqimitate.Info.ChatRecord;
import study.lzy.qqimitate.Info.Friend;
import study.lzy.qqimitate.Info.Record;
import study.lzy.qqimitate.Info.User;
import study.lzy.qqimitate.Utils;
import study.lzy.qqimitate.WxApplication;
import study.lzy.qqimitate.selfView.BadgeView;
import study.lzy.qqimitate.selfView.MessageView;

public class FriendFragment extends BaseFragment {

    List<Friend> list;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case 0:
                    if (Utils.checkResult(recycleView,result))
                    {
                        FriendBean friendBean=gson.fromJson(result[1],new TypeToken<FriendBean>(){}.getType());
                        if (friendBean!=null&&friendBean.getFriendList()!=null) {
                            wxApp.setFriendList( friendBean.getFriendList());
                            list.addAll(friendBean.getFriendList());
                            for (int i=0;i<list.size()-1;i++)
                                for (int j=i+1;j<list.size();j++)
                                {   char iHeader=WxApplication.getFirstChar(list.get(i).getUser().getUserName());
                                    char jHeader=WxApplication.getFirstChar(list.get(j).getUser().getUserName());
                                    if (jHeader<iHeader){
                                        Friend jFriend=list.get(j);
                                        list.remove(j);
                                        list.add(i,jFriend);
                                        j--;
                                    }
                                }
                            recycleView.invalidateItemDecorations();
                            adapter.notifyDataSetChanged();
                        }
                    }
                    break;
                default:break;
            }
        }
    };

    @Override
    protected void setOnItemClickListener() {
        onItemClickListener=new BaseHolder.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Friend friend = (Friend) adapter.getItem(position);
                Intent intent=new Intent(getActivity(), ChatActivity.class);
                Bundle bundle=new Bundle();

                ChatRecord chatRecord;
                    chatRecord = ChatRecord.get(wxApp.getLoginUser().getUserId(), friend.getUser().getUserId(), wxApp.getChatRecordList());

                bundle.putSerializable("chart",chatRecord);
                intent.putExtra("chart",bundle);
                startActivity(intent);
//                if (friend.getUser2().getUserId().equals(wxApp.getLoginUser().getUserId()))
//                Snackbar.make(v,friend.getUser1().getUserName(),Snackbar.LENGTH_SHORT).show();
//                else
//                    Snackbar.make(v,friend.getUser2().getUserName(),Snackbar.LENGTH_SHORT).show();

            }
        };
    }

    @Override
    protected void setAdapter() {
        list = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                String[] msg=new String[2];
                Map<String,Object> header=new HashMap<>();
                Map<String,Object> body=new HashMap<>();
                header.put("TransCode","Friend");
                body.put("UserId",1);
                msg[0]=gson.toJson(header);
                msg[1]=gson.toJson(body);
                result = dbHelper.getResult(msg);

                handler.sendEmptyMessage(0);
            }
        }).start();
        recycleView.invalidateItemDecorations();
        adapter=new Adapter(onItemClickListener,list);
    }

    @Override
    protected void refresh() {

    }
    class Adapter extends BaseAdapter<Friend>{
        List<Character> header=new ArrayList<>();
        List<Character> chars=new ArrayList<>();

        public Adapter(BaseHolder.OnItemClickListener onItemClickListener, List<Friend> list) {
            super(onItemClickListener, list);
            header.add('#');
            for(char c=65;c<91;c++){
                header.add(c);
            }
        }

        @Override
        protected BaseHolder getHolder(View itemView,int viewType) {
            return new BaseHolder(itemView,onItemClickListener,0);
        }

        @Override
        protected String getHeaderText(int position) {
            return String.valueOf(header.get(position));
        }

        @Override
        public void onBindViewHolder(BaseHolder holder, int position) {
            MessageView messageView=new MessageView(getActivity(),holder.iamge,parent);
            messageView.setText(position+"");
            messageView.show();
                holder.title.setText(list.get(position).getUser().getUserName());
        }

        @Override
        public long getHeaderId(int position) {
            Character c='1';
                c=WxApplication.getFirstChar(list.get(position).getUser().getUserName());

            if (!chars.contains(c)) {
                chars.add(c);
                header.remove(c);
                header.add(chars.size()-1,c);
                return chars.size()-1;
            }
            else
                return chars.indexOf(c);


        }
    }
}
