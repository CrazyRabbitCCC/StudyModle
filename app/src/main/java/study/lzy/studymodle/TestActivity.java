package study.lzy.studymodle;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

// @author: lzy  time: 2016/10/13.


public class TestActivity extends AppCompatActivity {
        /** Called when the activity is first created. */
        private static final int RECOGNIZER_EXAMPLE=101;
        private TextView result;
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.main);
            result=(TextView)findViewById(R.id.text_result);
            Button start=(Button)findViewById(R.id.start_button);
            start.setOnClickListener(arg0 -> {
                // TODO Auto-generated method stub
                Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say a word or phrase\nand it will show as text");
                PackageManager packageManager = getPackageManager();
                List activities = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                if (activities.size()>0)
                startActivityForResult(intent,RECOGNIZER_EXAMPLE);
                else Logger.d("NO_ACTIVITY_HERE");
            });
        }
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            // TODO Auto-generated method stub
            if(requestCode==RECOGNIZER_EXAMPLE&&resultCode==RESULT_OK){
                ArrayList<String> resultList=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                StringBuffer resultListString=new StringBuffer();;
                for(String s:resultList){
                    resultListString.append(s+",");
                }
                result.setText(resultListString.toString());
            }
            super.onActivityResult(requestCode, resultCode, data);

        }



}
