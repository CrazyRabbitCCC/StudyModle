package com.why.drawabletinttest;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {
    private Button mSimpleTest;
    private Button mViewpagerTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSimpleTest= (Button) findViewById(R.id.simpel_test);
        mViewpagerTest= (Button) findViewById(R.id.pager_test);
        mSimpleTest.setOnClickListener(this);
        mViewpagerTest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.simpel_test:
            {
                Intent intent=new Intent(MainActivity.this,SimpleActivity.class);
                startActivity(intent);
            }
                break;
            case R.id.pager_test:
            {
                Intent intent=new Intent(MainActivity.this,PagerActivity.class);
                startActivity(intent);
            }
                break;
        }

    }
}
