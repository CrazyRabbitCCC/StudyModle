package study.lzy.qqimitate.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import study.lzy.qqimitate.Adapter.BaseAdapter;
import study.lzy.qqimitate.Adapter.BaseHolder;
import study.lzy.qqimitate.Bean.RecordBean;
import study.lzy.qqimitate.CHat.ChatActivity;
import study.lzy.qqimitate.DataBase.DbHelper;
import study.lzy.qqimitate.Info.ChatRecord;
import study.lzy.qqimitate.Info.Record;
import study.lzy.qqimitate.Info.User;
import study.lzy.qqimitate.R;
import study.lzy.qqimitate.Utils;
import study.lzy.qqimitate.selfView.BadgeView;
import study.lzy.qqimitate.selfView.MessageTouchView;
import study.lzy.qqimitate.selfView.MessageView;

// @author: lzy  time: 2016/09/21.


public class WeixinFragment extends BaseFragment {

    List<ChatRecord> list;

    @Override
    protected void setOnItemClickListener() {
        onItemClickListener=new BaseHolder.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                ChatRecord chatRecord = (ChatRecord) adapter.getItem(position);
                Intent intent=new Intent(getActivity(), ChatActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("chart",chatRecord);
                intent.putExtra("chart",bundle);
                startActivity(intent);
            }
        };
    }

    @Override
    protected void setAdapter() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String[] msg=new String[2];
                Map<String,Object> header=new HashMap<>();
                Map<String,Object> body=new HashMap<>();
                header.put("TransCode","ChatRecord");
                body.put("UserId",1);
                msg[0]=gson.toJson(header);
                msg[1]=gson.toJson(body);
                result = dbHelper.getResult(msg);

                handler.sendEmptyMessage(0);
            }
        }).start();
        list = new ArrayList<>();
        recycleView.invalidateItemDecorations();
        adapter=new weixinAdapter(onItemClickListener,list);
    }

    @Override
    protected void refresh() {

    }

    class weixinAdapter extends BaseAdapter<ChatRecord>{

        public weixinAdapter(BaseHolder.OnItemClickListener onItemClickListener, List<ChatRecord> list) {
            super(onItemClickListener, list);
        }

        @Override
        protected BaseHolder getHolder(View itemView,int viewType) {
            return new BaseHolder(itemView,onItemClickListener,1);
        }

        @Override
        protected String getHeaderText(int position) {
            return "";
        }

        @Override
        public void onBindViewHolder(BaseHolder holder, int position) {
            holder.iamge.setImageResource(R.drawable.ic_menu_camera);
//            BadgeView badgeView=new BadgeView(getActivity(),holder.iamge);
//            badgeView.setText(position+"");
//            badgeView.show();
            MessageView messageView=new MessageView(getActivity(),holder.iamge,parent);
            messageView.setText(position+"");
            messageView.show();
            List<Record> records = list.get(position).getRecords();
            Record record = records.get(records.size() - 1);

            holder.time.setText(record.getTime());
            holder.title.setText( list.get(position).getChatWhit().getUserName());
            if (record.getChat().getUserId().equals( list.get(position).getChat().getUserId()))
                holder.message.setText("我:"+record.getMessage());

            else
                holder.message.setText(record.getChat().getUserName()+":"+record.getMessage());
        }

        @Override
        public long getHeaderId(int position) {
            return -1;
        }
    }

    Handler handler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case 0:
                    if (Utils.checkResult(recycleView,result)){
                        RecordBean recordBean=gson.fromJson(result[1],new TypeToken<RecordBean>(){}.getType());
                        if (recordBean!=null&&recordBean.getRecordList()!=null) {
                            wxApp.setChatRecordList(ChatRecord.getChatRecord(wxApp.getLoginUser(), recordBean.getRecordList()));
                            list.addAll(ChatRecord.getChatRecord(wxApp.getLoginUser(), recordBean.getRecordList()));
                            adapter.notifyDataSetChanged();
                            recycleView.invalidateItemDecorations();
                        }
                    }
//                    list = ChatRecord.getChatRecord(wxApp.getLoginUser(), records);

                    break;
                case 1:
                    break;
            }
        }
    };
}
