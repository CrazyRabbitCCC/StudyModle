package study.lzy.qqimitate;
// @author: lzy  time: 2016/09/26.


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

import study.lzy.qqimitate.Bean.UserBean;
import study.lzy.qqimitate.DataBase.DbHelper;

public class loginActivity extends AppCompatActivity{
    private WxApplication wxApp;
    String[] result;
    DbHelper dbHelper;
    Gson gson;
    TextView textView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textView = new TextView(this);
        textView.setText("Hello");
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(35);
        ViewGroup.LayoutParams params=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setContentView(textView,params);

        gson = new GsonBuilder().create();

        wxApp= (WxApplication) getApplication();

        dbHelper = new DbHelper(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String[] msg=new String[2];
                Map<String,Object> header=new HashMap<>();
                Map<String,Object> body=new HashMap<>();
                header.put("TransCode","UserInfo");
                body.put("UserId",0);
                body.put("UserName","刘");
                msg[0]=gson.toJson(header);
                msg[1]=gson.toJson(body);
                result = dbHelper.getResult(msg);
                {
                    int x=1;
                    Log.d("X",String.format("%d",x));
                }
                handler.sendEmptyMessage(0);
            }
        }).start();

    }
    Handler handler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case 0:
                    if (Utils.checkResult(textView,result)){
                        UserBean userBean=gson.fromJson(result[1],new TypeToken<UserBean>(){}.getType());
                        if (userBean!=null&&userBean.getUser()!=null){
                            wxApp.setLoginUser(userBean.getUser());
                            Intent i=new Intent(loginActivity.this,MainActivity.class);
                            startActivity(i);
                            finish();
                        }
                    }
                    break;
                default:
                    break;
            }

        }
    };
}
