package study.lzy.studymodle.MesagePoint;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import study.lzy.studymodle.R;

// @author: lzy  time: 2016/09/28.


public class DemoActivity extends AppCompatActivity {
    private LinearLayout parent;
    private TextView text;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_view);
        parent = (LinearLayout) findViewById(R.id.base);
        text = (TextView) findViewById(R.id.text);
        MessageTouchView messageTouchView=new MessageTouchView(this,text,parent);
        messageTouchView.setText("99+");
        messageTouchView.show();
        TextView text2 = (TextView) findViewById(R.id.text2);
        MessageView messageView=new MessageView(this,text2,parent);
        messageView.setText("99+");
        messageView.show();
    }
}
