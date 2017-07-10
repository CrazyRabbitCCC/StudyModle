package czrbt.lzy.record.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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

import czrbt.lzy.record.R;
import czrbt.lzy.record.bean.Save;
import czrbt.lzy.record.bean.VecHelper;
import czrbt.lzy.record.bean.test;
import czrbt.lzy.record.data.Provider;
import rx.Observable;

public class testActivity extends AppCompatActivity {
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();




    }

    private void initView() {
        new Thread(() -> {
            SpeechUtility.createUtility(this, SpeechConstant.APPID+"=581fd337,"+ SpeechConstant.FORCE_LOGIN +"=true");
        }).start();
        text= (TextView) findViewById(R.id.text);
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

    public void btnClick(View view ){
        switch (view.getId()){
            case R.id.add:
                //1.创建RecognizerDialog对象
                RecognizerDialog mDialog = new RecognizerDialog(testActivity.this, null);
                //2.设置accent、language等参数
                mDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
                mDialog.setParameter(SpeechConstant.ACCENT, "mandarin");
                //若要将UI控件用于语义理解，必须添加以下参数设置，设置之后onResult回调返回将是语义理解//结果
                // mDialog.setParameter("asr_sch", "1");
                // mDialog.setParameter("nlp_version", "2.0");
                //3.设置回调接口
                mDialog.setListener(mRecognizerDialogListener);
                //4.显示dialog，接收语音输入
                mDialog.show();
                break;
            case R.id.clear:
                textString="";
                text.setText(textString);
                break;
            default:break;
        }

    }

    String textString ="";
    String now="";
    final Object key=new Object();
    private String TAG=testActivity.class.getSimpleName();
    private RecognizerDialogListener mRecognizerDialogListener= new RecognizerDialogListener() {
        @Override
        public void onResult(RecognizerResult recognizerResult, boolean b) {
            Log.d(TAG,"RESULT:"+ recognizerResult.getResultString());
            if (!textString.isEmpty())
                textString+="—————\n";
            textString+="RESULT:\t"+recognizerResult.getResultString()+"\n";
            text.setText(textString);
            synchronized (key) {
                Observable.just(recognizerResult.getResultString())
                .map(s -> new GsonBuilder().create().
                        <test>fromJson(s, new TypeToken<test>(){}.getType()))
                .flatMap(test -> Observable.from(test.getWs()))
                .flatMap(ws ->Observable.from( ws.getCw()))
                .map(test.Ws.Cw::getW)
                .subscribe(w-> now+=w,
                        Throwable::printStackTrace,
                        ()->checkAndDeal(now));
            }
        }

        @Override
        public void onError(SpeechError speechError) {
            Log.e(TAG,"ERROR:"+speechError.getPlainDescription(true));
            showError();

//            if (!textString.isEmpty())
//                textString+="—————\n";
//            textString+="ERROR:\n\t"+speechError.getPlainDescription(true)+"\n";
//            text.setText(textString);
        }
    };

    private void showError() {

    }

    Save save;
    private void checkAndDeal(String text){
        if (text!=null&&!text.isEmpty()){
            String function = text.substring(0,2);
            if (save==null)
                save=Save.getInstance();
            int type=0;
            switch (function){
                case "标题":
                    title();
                    break;
                case "保存":
                    save();
                    break;
                case "撤销":
                    Cancel();
                    break;
                case "替换":

                    break;
                case "记录":
                    text=text.substring(2,text.length());
                default:
                    record();
                    if (save.getTitle()==null||save.getTitle().isEmpty()) {
                        if (text.length() < 10)
                            save.setTitle(text);
                        else save.setTitle(text.substring(0, 9) + "..");
                        type=VecHelper.Til_Msg;
                    }else {
                        type=VecHelper.MESSAGE;
                    }
                    if (save.getDate()==null||save.getDate().isEmpty())
                        save.setDate(text);
                    else save.setDate(save.getDate()+text);
//                    VecHelper.yySave ySave = new VecHelper.yySave();
//                    ySave.setFunction("记录");
//                    ySave.setArg1(text);
//                    ySave.setType(type);
                    save.setType(text);
                    save.setChange(true);
                    VecHelper.add(save.getClone());
                    textString+=textString;
                    this.text.setText(textString);
                    break;
            }
        }
    }

    private void title() {

    }

    private void record() {
        VecHelper.removeLast();
        save=VecHelper.getLast();
    }

    private void save() {
        getContentResolver().insert(Provider.SAVE.CONTENT_URI,save.getValues());
        save=null;
    }

    private void Cancel() {
//        VecHelper.yySave ySave=VecHelper.getLast();
//        switch (ySave.getFunction()){
//
//        }

    }
}
