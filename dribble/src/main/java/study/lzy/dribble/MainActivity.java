package study.lzy.dribble;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread(new Runnable() {
            @Override
            public void run() {
              String  s=  NetUtils.get("https://api.dribbble.com/v1/users/simplebits");
                int x=0;
            }
        }).start();
    }
}
