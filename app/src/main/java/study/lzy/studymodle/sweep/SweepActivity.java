package study.lzy.studymodle.sweep;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import study.lzy.studymodle.R;

/**
 * @author Gavin
 * @date 2017/06/15.
 */

public class SweepActivity extends AppCompatActivity {
    private Spinner spinner;
    private TextInputEditText edit;
    private Button confirm;
    private GridLayout sweepLayout;
    private SweepController sweepController;
    private  Timer timer ;
    private int second=0;
    private TextView secondText;
    private TextView stepText;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sweep);
        initView();
        initData();
        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(0);
            }
        },1000,1000);
    }
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            second++;
            secondText.setText(second+"");
        }
    };



    String[] spinnerTexts= new String[]{"10 X 10","12 X 12","15 X 15","18 X 18","21 X 21","25 X 25","测试"};
    SparseIntArray array= new SparseIntArray();
    {
        array.put(0,10);
        array.put(1,12);
        array.put(2,15);
        array.put(3,18);
        array.put(4,21);
        array.put(5,25);
        array.put(6,5);
    }
    ArrayAdapter<String> adapter;
    private void initView() {
        spinner = (Spinner) findViewById(R.id.spinner);
        edit = (TextInputEditText) findViewById(R.id.edit);
        confirm = (Button) findViewById(R.id.confirm);
        sweepLayout = (GridLayout) findViewById(R.id.sweep_layout);
        secondText = (TextView) findViewById(R.id.second_text);
        stepText = (TextView) findViewById(R.id.step_text);
    }

    private int selection = 0;
    private int minMine= 10;
    private int maxMine = 33;
    private int  mineNum =10 ;;
    private void initData() {
        sweepController = new SweepController(sweepLayout,this);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,spinnerTexts);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selection =position;
                int num = array.get(position,10);
                minMine = num;
                maxMine = num*num/3;
                edit.setHint(num+"");
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edit.getText()!=null&&!edit.getText().toString().isEmpty()){
                    mineNum=Integer.parseInt(edit.getText().toString());
                    if (mineNum<minMine)
                    {
                        Toast.makeText(SweepActivity.this,"地雷数不能小于"+minMine,Toast.LENGTH_SHORT).show();
                        return;
                    }else if (mineNum>maxMine){
                        Toast.makeText(SweepActivity.this,"地雷数不能大于"+maxMine,Toast.LENGTH_SHORT).show();
                        return;
                    }
                }else
                    mineNum = minMine;
                showReloadDialog(mineNum,sweepController.gameOver);
            }
        });

        sweepController.setOnResultDeal(new SweepController.OnResultDeal() {
            @Override
            public void onSuccess() {
                showReloadDialog(mineNum,true);
            }

            @Override
            public void onFailed() {
                showReloadDialog(mineNum,true);
            }

            @Override
            public void onStep(int step) {
                stepText.setText(step+"");

            }
        });
    }
    AlertDialog mDialog;
    private boolean gameStart = false;
    private void reloadGame(final int mineNum){
        sweepController.reload(array.get(selection, 10), mineNum);
        second = 0 ;
        secondText.setText("0");
        stepText.setText("0");
    }
    private void showReloadDialog(final int mineNum,boolean gameOver){
        if (!gameStart) {
            reloadGame(mineNum);
            gameStart =true;
            return;
        }
        if (mDialog!=null&&mDialog.isShowing())
            return;
        if (mDialog==null){
            mDialog = new AlertDialog.Builder(this)
                    .setTitle("重新开始游戏")
                    .setMessage((gameOver?"":"游戏还未结束，")+"你确定要重新开始游戏吗？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sweepController.reload(array.get(selection, 10), mineNum);
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
        }else {
            mDialog.setButton(AlertDialog.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    sweepController.reload(array.get(selection, 10), mineNum);
                }
            });
        }
        mDialog.show();
    }

}
