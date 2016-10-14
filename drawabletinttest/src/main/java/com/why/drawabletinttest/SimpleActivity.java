package com.why.drawabletinttest;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class SimpleActivity extends Activity {
    private ImageView mGreenImageView;
    private ImageView mTransparentImageView;
    private ImageView mRedImageView;
    //透明度滑动条
    private SeekBar mTransparentSeekBar;
    private Spinner mSpinner;
    //红色滑动条
    private SeekBar mRedSeekBar;
    //绿色滑动条
    private SeekBar mGreenSeekBar;
    //蓝色滑动条
    private SeekBar mBlueSeekBar;
    private TextView mTextView;
    private SeekBar.OnSeekBarChangeListener mOnSeekBarChangeListener; //滑动条监听器
    //PorterDuff.Mode 列表
    private static final PorterDuff.Mode[] MODES = new PorterDuff.Mode[]{
            PorterDuff.Mode.ADD,
            PorterDuff.Mode.CLEAR,
            PorterDuff.Mode.DARKEN,
            PorterDuff.Mode.DST,
            PorterDuff.Mode.DST_ATOP,
            PorterDuff.Mode.DST_IN,
            PorterDuff.Mode.DST_OUT,
            PorterDuff.Mode.DST_OVER,
            PorterDuff.Mode.LIGHTEN,
            PorterDuff.Mode.MULTIPLY,
            PorterDuff.Mode.OVERLAY,
            PorterDuff.Mode.SCREEN,
            PorterDuff.Mode.SRC,
            PorterDuff.Mode.SRC_ATOP,
            PorterDuff.Mode.SRC_IN,
            PorterDuff.Mode.SRC_OUT,
            PorterDuff.Mode.SRC_OVER,
            PorterDuff.Mode.XOR
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        mGreenImageView = (ImageView) findViewById(R.id.green);
        mTransparentImageView= (ImageView) findViewById(R.id.transparent);
        mRedImageView= (ImageView) findViewById(R.id.red);
        mTextView= (TextView) findViewById(R.id.text);
        mTransparentSeekBar = (SeekBar) findViewById(R.id.alpha_seekbar);
        mRedSeekBar= (SeekBar) findViewById(R.id.red_seekbar);
        mGreenSeekBar= (SeekBar) findViewById(R.id.green_seekbar);
        mBlueSeekBar= (SeekBar) findViewById(R.id.blue_seekbar);
        mSpinner= (Spinner) findViewById(R.id.spinner);
        SpinnerAdapter spinnerAdapter=ArrayAdapter.createFromResource(SimpleActivity.this,R.array.blend_modes, android.R.layout.simple_list_item_1);
        mSpinner.setAdapter(spinnerAdapter);
        initListener();


    }

    private void initListener() {
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateImage(getRGBColor(),getMode());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mOnSeekBarChangeListener=new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateImage(getRGBColor(),getMode());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };
        mTransparentSeekBar.setOnSeekBarChangeListener(mOnSeekBarChangeListener);
        mRedSeekBar.setOnSeekBarChangeListener(mOnSeekBarChangeListener);
        mGreenSeekBar.setOnSeekBarChangeListener(mOnSeekBarChangeListener);
        mBlueSeekBar.setOnSeekBarChangeListener(mOnSeekBarChangeListener);
    }

    private PorterDuff.Mode getMode() {
        return MODES[mSpinner.getSelectedItemPosition()];
    }

    /**
     * @return 根据ARGB颜色滑动条的数值计算颜色值
     */
    private int getRGBColor() {
        int alpha= mTransparentSeekBar.getProgress();
        int red=mRedSeekBar.getProgress();
        int green=mGreenSeekBar.getProgress();
        int blue=mBlueSeekBar.getProgress();
        return Color.argb(alpha,red,green,blue);
    }

    /**
     * 更新颜色 模式
     * @param color
     * @param mode
     */
    private void updateImage(int color,  PorterDuff.Mode mode ) {
        mGreenImageView.setColorFilter(color,mode);
        mTransparentImageView.setColorFilter(color,mode);
        mRedImageView.setColorFilter(color,mode);
        mTextView.setTextColor(color);
    }
}
