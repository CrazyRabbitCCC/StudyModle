package udacity.lzy.test.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.iflytek.sunflower.FlowerCollector;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import udacity.lzy.test.R;
import udacity.lzy.test.bean.Save;
import udacity.lzy.test.bean.VecHelper;
import udacity.lzy.test.bean.test;
import udacity.lzy.test.data.Provider;

public class TextActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.title)
    EditText title;
    @Bind(R.id.data)
    EditText data;
    @Bind(R.id.msg)
    TextView msg;
    @Bind(R.id.save)
    Button save;
    @Bind(R.id.clear)
    Button clear;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    boolean isChange;
    Save saveData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        SpeechUtility.createUtility(this, SpeechConstant.APPID+"=581fd337");
//        new Thread(() -> {
//            SpeechUtility.createUtility(this, SpeechConstant.APPID+"=581fd337,"+ SpeechConstant.FORCE_LOGIN +"=true");
//        }).start();
        ButterKnife.bind(this);
        isChange=getIntent().getBooleanExtra("haveData",false);
        saveData=getIntent().getParcelableExtra("save");
        if (isChange)
            toolbar.setTitle("修改记录内容");
        if (isChange)
            toolbar.setTitle("记录");
        setSupportActionBar(toolbar);
        if (isChange){
            title.setFocusable(true);
            title.setFocusableInTouchMode(true);
            data.setFocusable(true);
            data.setFocusableInTouchMode(true);
            title.setText(saveData.getTitle());
            data.setText(saveData.getDate());
        }else {
            title.setFocusableInTouchMode(false);
            title.setFocusable(false);
            data.setFocusableInTouchMode(false);
            data.setFocusable(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        FlowerCollector.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        FlowerCollector.onPause(this);
    }
    @OnClick({R.id.save, R.id.clear, R.id.fab})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.save:
                saveDatas();
                break;
            case R.id.clear:
                saveData= Save.getInstance();
                saveData.setChange(true);
                break;
            case R.id.fab:
                RecognizerDialog mDialog = new RecognizerDialog(this, null);
                mDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
                mDialog.setParameter(SpeechConstant.ACCENT, "mandarin");
                //若要将UI控件用于语义理解，必须添加以下参数设置，设置之后onResult回调返回将是语义理解//结果
                // mDialog.setParameter("asr_sch", "1");
                // mDialog.setParameter("nlp_version", "2.0");
                mDialog.setListener(mRecognizerDialogListener);
                mDialog.show();
                break;
        }
    }
    private String msgString="";
    private String dataString="";
    public String now="";
    private  String TAG=TextActivity.class.getSimpleName();
    private static final Object key=new Object();
    private RecognizerDialogListener mRecognizerDialogListener=new RecognizerDialogListener() {


        @Override
        public void onResult(RecognizerResult recognizerResult, boolean b) {
            Log.d(TAG,"RESULT:"+ recognizerResult.getResultString());
            synchronized (key) {
                Observable.just(recognizerResult.getResultString())
                        .map(s -> new GsonBuilder().create().
                                <test>fromJson(s, new TypeToken<test>(){}.getType()))
                        .flatMap(test -> Observable.from(test.getWs()))
                        .flatMap(ws ->Observable.from( ws.getCw()))
                        .map(test.Ws.Cw::getW)
                        .subscribe(w-> now+=w,
                                Throwable::printStackTrace,
                                ()->{checkAndDeal(now);
                                    now="";
                                });
            }
        }

        @Override
        public void onError(SpeechError speechError) {
            Log.e(TAG,"ERROR:"+speechError.getPlainDescription(true));
            showError();
        }
    };

    private void showError() {
        now="";
        Observable.just("{\"sn\":1,\"ls\":false,\"bg\":0,\"ed\":0,\"ws\":[{\"bg\":0,\"cw\":[{\"sc\":0.00,\"w\":\"E\"},{\"sc\":0.00,\"w\":\"R\"},{\"sc\":0.00,\"w\":\"R\"},{\"sc\":0.00,\"w\":\"o\"},{\"sc\":0.00,\"w\":\"R\"}]}]}")
                .map(s -> new GsonBuilder().create().
                        <test>fromJson(s, new TypeToken<test>(){}.getType()))
                .flatMap(test -> Observable.from(test.getWs()))
                .flatMap(ws ->Observable.from( ws.getCw()))
                .map(test.Ws.Cw::getW)
                .subscribe(w-> now+=w,Throwable::printStackTrace,
                        ()->{checkAndDeal(now);
                            now="";
                        });
    }

    private void checkAndDeal(String s) {
        if (!TextUtils.isEmpty(msgString))
            msgString+="\n|———————————————\n|";
        else msgString+="|";
        msgString+=s;
        String function;
        if (!TextUtils.isEmpty(s)&&s.length()>2){
            function=s.substring(0,2);
            switch (function){
                case "标题":
                    s=s.substring(2,s.length()-1);
                    changeTitle(s);
                    break;
                case "保存":
                    saveDatas();
                    break;
                case "退出":
                    finish();
                    break;
                case "撤销":
                    cancel();
                    break;
                case "记录":
                    s=s.substring(2,s.length());
                default:
                    record(s);
                    break;
            }
            saveData.setChange(true);
        }
        
    }

    private void cancel() {
        VecHelper.removeLast();
       saveData= VecHelper.getLast();
        title.setText(saveData.getTitle());
        data.setText(saveData.getDate());
        msg.setText("");
    }

    private void record(String s ){
        if (saveData==null)
            saveData=Save.getInstance();
        if (saveData.getTitle()==null||saveData.getTitle().isEmpty()) {
            if (s.length() < 10)
                saveData.setTitle(s);
            else saveData.setTitle(s.substring(0, 9) + "..");
        }
        if (saveData.getDate()==null||saveData.getDate().isEmpty())
            saveData.setDate(s);
        else saveData.setDate(saveData.getDate()+s);
        saveData.setType(s);
        saveData.setChange(true);
        VecHelper.add(saveData.getClone());
        dataString+=s;
        data.setText(dataString);
        title.setText(saveData.getTitle());
        msg.setText(msgString);
    }

    private void saveDatas() {
        if (saveData!=null) {
            saveData.setTitle(title.getText().toString());
            saveData.setDate(data.getText().toString());
            if (isChange&&saveData.isChange()) {
                saveData.setChange(false);
                getContentResolver().update(Provider.SAVE.withId(saveData.getId()), saveData.getValues(), null, null);
            }
            else
                getContentResolver().insert(Provider.SAVE.CONTENT_URI, saveData.getValues());
        }
        saveData=Save.getInstance();
        VecHelper.clear();
        finish();
    }

    private void changeTitle(String s) {
        title.setText(s);
        saveData.setTitle(s);
    }
}
