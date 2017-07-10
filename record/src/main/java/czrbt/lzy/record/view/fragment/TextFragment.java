package czrbt.lzy.record.view.fragment;
// @author: lzy  time: 2016/11/01.


import android.animation.Animator;
import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.orhanobut.logger.Logger;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import czrbt.lzy.record.R;
import czrbt.lzy.record.bean.test;
import czrbt.lzy.record.person.TextPerson;
import czrbt.lzy.record.utils.AnimationUtils;
import czrbt.lzy.record.view.viewHelper.BaseHelper;
import rx.Observable;

public class TextFragment extends BaseFragment<BaseHelper, TextPerson> {

    @Bind(R.id.title)
    TextInputEditText title;
    @Bind(R.id.message)
    TextInputEditText message;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.fab1)
    Button fab1;
    @Bind(R.id.fab2)
    Button fab2;

    @Bind(R.id.record)
    LinearLayout record;

    private String TAG =TextFragment.class.getSimpleName();

    @Override
    protected void initView() {
        ButterKnife.bind(this, view);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            title.setText(savedInstanceState.getString("title", ""));
            message.setText(savedInstanceState.getString("message", ""));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (title.getText() != null && !title.getText().toString().isEmpty())
            outState.putString("title", title.getText().toString());
        if (message.getText() != null && !message.getText().toString().isEmpty())
            outState.putString("message", message.getText().toString());
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    @Override
    public int getMenuId() {
        return R.menu.frag_text_menu;
    }

    @Override
    protected TextPerson getPerson() {
        return new TextPerson(getActivity(), this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void refreshView() {

    }

    @Override
    public void addData(Object o) {

    }

    @Override
    public void postData(Object o) {

    }

    @Override
    public void afterLoading() {
        getActivity().setResult(Activity.RESULT_OK, null);
        getActivity().finish();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_text;
    }

    @Override
    public SwipeRefreshLayout getRefreshLayout() {
        return null;
    }

    @Override
    public View getViewForSnack() {
        return null;
    }


    boolean flag=false;
    boolean first =true;
    @OnClick({R.id.fab,R.id.fab1,R.id.fab2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                if (first){
                    first=false;
                    record.setVisibility(View.VISIBLE);
                    flag=true;
                }else
                if (flag) {
                    AnimationUtils fab1Anim=new AnimationUtils(record);
                    fab1Anim.addAlphaAnim(1,0);
                    fab1Anim.addTranslationXAnim(0,0,record.getWidth());
                    fab1Anim.addObjListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {

                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                    fab1Anim.startObjAnim(250);
                    Logger.d("false");
                    flag=false;
                }
                else {
                    AnimationUtils fabAnim=new AnimationUtils(record);
                    fabAnim.addAlphaAnim(0,1);
                    fabAnim.addTranslationXAnim(record.getWidth(),0);
                    fabAnim.startObjAnim(250);
                    Logger.d("true");
                    flag = true;
                }
                break;
            case R.id.fab1:
//                RecognizerDialog mDialog = new RecognizerDialog(getActivity(), null);
//                mDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
//                mDialog.setParameter(SpeechConstant.ACCENT, "mandarin");
//                mDialog.setListener(new RecognizerDialogListener() {
//                    @Override
//                    public void onResult(RecognizerResult recognizerResult, boolean b) {
//
//                    }
//
//                    @Override
//                    public void onError(SpeechError speechError) {
//
//                    }
//                });
//                mDialog.show();


                //1.创建RecognizerDialog对象
                RecognizerDialog mDialog = new RecognizerDialog(getActivity(), null);
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

//                mIat.startListening();

                break;
            case R.id.fab2:
                SpeechRecognizer mIat = SpeechRecognizer.createRecognizer(getActivity(),null);
                mIat.setParameter(SpeechConstant.DOMAIN,"iat");
                mIat.setParameter(SpeechConstant.LANGUAGE,"zh_cn");
                mIat.setParameter(SpeechConstant.ACCENT,"mandarin");

                mIat.startListening(mRecognizerListener);
                break;
//            case R.id.button3:
//                break;
//            case R.id.button4:
//                break;
        }
    }

    private RecognizerDialogListener mRecognizerDialogListener= new RecognizerDialogListener() {
        @Override
        public void onResult(RecognizerResult recognizerResult, boolean b) {
            Log.d(TAG,"RESULT:"+ recognizerResult.getResultString());
        }

        @Override
        public void onError(SpeechError speechError) {
            Log.e(TAG,"ERROR:"+speechError.getPlainDescription(true));
        }
    };

    private RecognizerListener mRecognizerListener=new RecognizerListener(){

        @Override
        public void onVolumeChanged(int i, byte[] bytes) {

        }

        @Override
        public void onBeginOfSpeech() {

        }

        @Override
        public void onEndOfSpeech() {

        }
        String string="";
        final Object key=new Object();
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
                .subscribe(w-> string+=w,Throwable::printStackTrace,
                ()->message.setText(string));
            }
        }

        @Override
        public void onError(SpeechError speechError) {
            Log.e(TAG,"ERROR:"+speechError.getPlainDescription(true));
            Observable.just("{\"sn\":1,\"ls\":false,\"bg\":0,\"ed\":0,\"ws\":[{\"bg\":0,\"cw\":[{\"sc\":0.00,\"w\":\"E\"},{\"sc\":0.00,\"w\":\"R\"},{\"sc\":0.00,\"w\":\"R\"},{\"sc\":0.00,\"w\":\"o\"},{\"sc\":0.00,\"w\":\"R\"}]}]}")
                    .map(s -> new GsonBuilder().create().
                            <test>fromJson(s, new TypeToken<test>(){}.getType()))
                    .flatMap(test -> Observable.from(test.getWs()))
                    .flatMap(ws ->Observable.from( ws.getCw()))
                    .map(test.Ws.Cw::getW)
                    .subscribe(w-> string+=w,Throwable::printStackTrace,
                            ()->message.setText(string));
        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }
    };

        //听写结果回调接口(返回Json格式结果，用户可参见 附录13.1)；
//一般情况下会通过onResults接口多次返回结果，完整的识别内容是多次结果的累加；
//关于解析Json的代码可参见Demo中JsonParser类；
//isLast等于true时会话结束。

//
}
