package study.lzy.studymodle;// @author: lzy  time: 2016/09/18.

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.security.Permission;
import java.util.ArrayList;
import java.util.List;

import study.lzy.studymodle.AboutHolder.BaseAdapter;
import study.lzy.studymodle.AboutHolder.Holder;
import study.lzy.studymodle.AboutHolder.OnClickListener;
import study.lzy.studymodle.Animation.AnimationActivity1;
import study.lzy.studymodle.Animation.AnimationActivity2;
import study.lzy.studymodle.Animation.AnimationActivity3;
import study.lzy.studymodle.Animation.AnimationActivity4;
import study.lzy.studymodle.Game2048.Game2048Activity;
import study.lzy.studymodle.MesagePoint.DemoActivity;
import study.lzy.studymodle.Phone.PhoneActivity;
import study.lzy.studymodle.SenirView.VideoViewActivity;
import study.lzy.studymodle.Tree.DividerItemDecoration;
import study.lzy.studymodle.Tree.TreeActivity;
import study.lzy.studymodle.chess.InterChessActivity;
import study.lzy.studymodle.gomoku.GomokuActivity;
import study.lzy.studymodle.star.StarActivity;
import study.lzy.studymodle.star.星星页面;
import study.lzy.studymodle.sweep.SweepActivity;
import study.lzy.studymodle.toolbar.CollapsingActivity;
import study.lzy.studymodle.toolbar.ToolBarActivity;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv;
    private Adapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = (RecyclerView) findViewById(R.id.rv);

        GridLayoutManager manager = new GridLayoutManager(this, 2);
//        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);
        rv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
        adapter = new Adapter();
        adapter.setOnClickListener((v, position) -> {
            if (adapter.getItem(position).intent != null) {
                startActivity(adapter.getItem(position).intent);
                overridePendingTransition(R.anim.translate_enter,R.anim.translate_exit);
            }
        });
        rv.setAdapter(adapter);
        add("多级树", TreeActivity.class);
        add("帧动画", AnimationActivity4.class);
        add("动画效果", AnimationActivity1.class);
        add("动画效果_xml", AnimationActivity2.class);
        add("属性动画", AnimationActivity3.class);
        add("videoView", VideoViewActivity.class);
        add("电话本", PhoneActivity.class);
        add("2048", Game2048Activity.class);
        add("可拖动小红点", DemoActivity.class);
        add("权限管理", PermissionActivity.class);
        add("扫雷", SweepActivity.class);
        add("消灭星星", StarActivity.class);
        add("消灭星星", 星星页面.class,R.drawable.eaten);
        add("toolbar", ToolBarActivity.class);
        add("toolbar2", CollapsingActivity.class);
        add("五子棋", GomokuActivity.class);
        add("国际象棋", InterChessActivity.class,R.drawable.black_horse);
    }

    private void add(String text, Class<?> cls) {
        add(text,cls,-1);
    }
    private void add(String text, Class<?> cls,int imageId) {
        Intent intent = new Intent(this, cls);
        adapter.add(new ClickIntent(text, intent,imageId));
    }

    class Adapter extends BaseAdapter {
        List<ClickIntent> list;

        public Adapter() {
            list = new ArrayList<>();
        }

        public void add(ClickIntent clickIntent) {
            list.add(clickIntent);
            notifyDataSetChanged();
        }

        public View getItemView(ViewGroup parent, int viewType) {
            return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            holder.image.setVisibility(View.VISIBLE);
            holder.text.setText(list.get(position).text);
            if (getItem(position).imageId!=-1){
                holder.image.setImageResource(getItem(position).imageId);
            }
        }

        public ClickIntent getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }


    class ClickIntent {
        public ClickIntent(String text, Intent intent) {
            this.text = text;
            this.intent = intent;
        }

        public ClickIntent(String text, Intent intent, int imageId) {
            this.text = text;
            this.intent = intent;
            this.imageId = imageId;
        }

        String text;
        Intent intent;
        int imageId = -1;
    }
}
