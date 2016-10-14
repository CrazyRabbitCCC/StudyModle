package study.lzy.studymodle.SenirView;// @author: lzy  time: 2016/09/19.

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import study.lzy.studymodle.R;

public class VideoViewActivity extends AppCompatActivity implements View.OnClickListener{
    private VideoView video;
    private Button start;
    private Button stop;
    private Button pause;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        initView();
    }

    private void initView() {


        video = (VideoView) findViewById(R.id.video);
        start = (Button) findViewById(R.id.start);
        stop = (Button) findViewById(R.id.stop);
        pause = (Button) findViewById(R.id.pause);

        video.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.video));
        video.setOnClickListener(this);
        start.setOnClickListener(this);
        stop.setOnClickListener(this);
        pause.setOnClickListener(this);

        video.start();
        video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                video.start();

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.video:
                break;
            case R.id.start:
                video.start();
                break;
            case R.id.stop:
                video.stopPlayback();
                video.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video));
                break;
            case R.id.pause:
                video.pause();
                break;
        }

    }
}
