package study.lzy.qqimitate.CHat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.List;

import study.lzy.qqimitate.Adapter.BaseAdapter;
import study.lzy.qqimitate.Adapter.BaseHolder;
import study.lzy.qqimitate.Adapter.DividerDecoration;
import study.lzy.qqimitate.Info.ChatRecord;
import study.lzy.qqimitate.Info.Record;
import study.lzy.qqimitate.R;
import study.lzy.qqimitate.WxApplication;

import static android.view.View.GONE;

// @author: lzy  time: 2016/09/22.


public class ChatActivity extends AppCompatActivity implements View.OnClickListener {
    private WxApplication wxApp;
    private SwipeRefreshLayout refresh;
    private RecyclerView recycleView;
    private BaseAdapter adapter;
    private OnItemClickListener onItemClickListener;
    private ChatRecord chatRecord;
    private Toolbar toolbar;
    private LinearLayout llSay;
    private LinearLayout llWrite;
    private ImageButton love;
    private Button say;
    private ImageButton voice;
    private EditText editText;
    private ImageButton emoji;
    private ImageButton add;
    private ImageButton send;
    private TableLayout more;
    private LinearLayout test00;
    private LinearLayout test01;
    private LinearLayout test02;
    private LinearLayout test03;
    private LinearLayout test10;
    private LinearLayout test11;
    private LinearLayout test12;
    private LinearLayout test13;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.native_self);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Bundle bundle = getIntent().getBundleExtra("chart");
        chatRecord = (ChatRecord) bundle.getSerializable("chart");
        initView();

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (chatRecord.getChat().getUserId().equals(wxApp.getLoginUser().getUserId()))
            toolbar.setTitle(chatRecord.getChatWhit().getUserName());
        else if (chatRecord.getChatWhit().getUserId().equals(wxApp.getLoginUser().getUserId()))
            toolbar.setTitle(chatRecord.getChat().getUserName());

    }

    private void initView() {
        wxApp = (WxApplication) getApplication();

        recycleView = (RecyclerView) findViewById(R.id.recycle_view);
        refresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
        refresh.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
                refresh.setRefreshing(false);
            }
        });
        setRecyclerView();

        llSay = (LinearLayout) findViewById(R.id.ll_say);
        llWrite = (LinearLayout) findViewById(R.id.ll_write);

        love = (ImageButton) findViewById(R.id.love);
        say = (Button) findViewById(R.id.say);
        voice = (ImageButton) findViewById(R.id.voice);
        editText = (EditText) findViewById(R.id.edit_text);
        emoji = (ImageButton) findViewById(R.id.emoji);
        add = (ImageButton) findViewById(R.id.add);
        send = (ImageButton) findViewById(R.id.send);

        more = (TableLayout) findViewById(R.id.more);
        test00 = (LinearLayout) findViewById(R.id.test0_0);
        test01 = (LinearLayout) findViewById(R.id.test0_1);
        test02 = (LinearLayout) findViewById(R.id.test0_2);
        test03 = (LinearLayout) findViewById(R.id.test0_3);
        test10 = (LinearLayout) findViewById(R.id.test1_0);
        test11 = (LinearLayout) findViewById(R.id.test1_1);
        test12 = (LinearLayout) findViewById(R.id.test1_2);
        test13 = (LinearLayout) findViewById(R.id.test1_3);

        setViewByType(0);
        love.setOnClickListener(this);
        voice.setOnClickListener(this);
        add.setOnClickListener(this);
        send.setOnClickListener(this);
        test00.setOnClickListener(this);
        test01.setOnClickListener(this);
        test02.setOnClickListener(this);
        test03.setOnClickListener(this);
        test10.setOnClickListener(this);
        test11.setOnClickListener(this);
        test12.setOnClickListener(this);
        test13.setOnClickListener(this);


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null &&! "".equals(s.toString())) {
                    setViewByType(3);
                } else setViewByType(0);
            }
        });
    }

    private void setViewByType(int type) {
        switch (type) {
            case 0://初始状态
                add.setVisibility(View.VISIBLE);
                send.setVisibility(GONE);
                llSay.setVisibility(GONE);
                llWrite.setVisibility(View.VISIBLE);
                more.setVisibility(GONE);
                break;
            case 1://点+号
                add.setVisibility(View.VISIBLE);
                send.setVisibility(GONE);
                llSay.setVisibility(GONE);
                llWrite.setVisibility(View.VISIBLE);
                more.setVisibility(View.VISIBLE);
                break;
            case 2://点语言
                llSay.setVisibility(View.VISIBLE);
                llWrite.setVisibility(GONE);
                add.setVisibility(View.VISIBLE);
                send.setVisibility(GONE);
                more.setVisibility(GONE);
                break;
            case 3://有输入文字
                add.setVisibility(View.GONE);
                send.setVisibility(View.VISIBLE);
                llSay.setVisibility(GONE);
                llWrite.setVisibility(View.VISIBLE);
                more.setVisibility(GONE);
                break;


        }

    }

    private void setRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recycleView.setLayoutManager(manager);
        onItemClickListener = new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

            }
        };
        ChatAdapter adapter = new ChatAdapter(onItemClickListener, chatRecord.getRecords());
        recycleView.setAdapter(adapter);
        recycleView.scrollToPosition(adapter.getItemCount() - 1);
    }

    private void refresh() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                setViewByType(1);
                break;
            case R.id.send:
                editText.setText("");
                setViewByType(0);
                break;
            case R.id.love:
                if (editText.getText()!=null&&!editText.getText().toString().isEmpty()){
                    setViewByType(3);
                }else setViewByType(0);
                break;
            case R.id.voice:
                setViewByType(2);
                break;
            case R.id.test0_0:
                break;
            case R.id.test0_1:
                break;
            case R.id.test0_2:
                break;
            case R.id.test0_3:
                break;
            case R.id.test1_0:
                break;
            case R.id.test1_1:
                break;
            case R.id.test1_2:
                break;
            case R.id.test1_3:
                break;

        }
    }

    class ChatAdapter extends RecyclerView.Adapter<Holder> {
        List<Record> list;
        private OnItemClickListener onItemClickListener;

        public ChatAdapter(OnItemClickListener onItemClickListener, List<Record> list) {
            this.list = list;
            this.onItemClickListener = onItemClickListener;
        }

        @Override
        public int getItemViewType(int position) {
            if (list.get(position).getChat().getUserId().equals(wxApp.getLoginUser().getUserId()))
                return 2;
            else return 1;
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, parent, false);
            return new Holder(itemView, onItemClickListener, viewType);
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            holder.setImage(R.drawable.ic_menu_gallery);
            holder.setMessage(list.get(position).getMessage());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private OnItemClickListener onItemClickListener;
        private LinearLayout other;
        private ImageView imageOther;
        private TextView messageOther;
        private LinearLayout self;
        private TextView message;
        private ImageView image;

        public Holder(View itemView, OnItemClickListener onItemClickListener, int viewType) {
            super(itemView);

            other = (LinearLayout) itemView.findViewById(R.id.other);
            imageOther = (ImageView) itemView.findViewById(R.id.image_other);
            messageOther = (TextView) itemView.findViewById(R.id.message_other);
            self = (LinearLayout) itemView.findViewById(R.id.self);
            message = (TextView) itemView.findViewById(R.id.message);
            image = (ImageView) itemView.findViewById(R.id.image);

            if (viewType == 1) {
                self.setVisibility(GONE);
            } else {
                other.setVisibility(GONE);
            }

            WindowManager windowManager = getWindowManager();
            DisplayMetrics outMetrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(outMetrics);

            message.setMaxWidth(outMetrics.widthPixels - 140);
            messageOther.setMaxWidth(outMetrics.widthPixels - 140);

            this.onItemClickListener = onItemClickListener;
        }


        public void setImage(Drawable drawable) {
            image.setImageDrawable(drawable);
            imageOther.setImageDrawable(drawable);
        }

        public void setImage(@DrawableRes int id) {
            image.setImageResource(id);
            imageOther.setImageResource(id);
        }

        public TextView getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message.setText(message);
            messageOther.setText(message);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null)
                onItemClickListener.onItemClick(itemView, getPosition());
        }
    }

    interface OnItemClickListener {
        void onItemClick(View v, int position);
    }


}
