package study.lzy.studymodle.star;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import study.lzy.studymodle.R;

/**
 * @author Gavin
 * @date 2017/06/16.
 */

public class StarActivity extends AppCompatActivity {

    private GridLayout gameLayout;
    private StarController mController;
    private int selection = 0;
    private List<String> mStrings;
    private Spinner spinner;
    private Button confirm;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star);
        initView();
        initData();
        mController = new StarController(this,gameLayout);
        mController.reloadCards(5);


    }

    private void initView() {
        gameLayout =(GridLayout) findViewById(R.id.game_layout);
        spinner = (Spinner) findViewById(R.id.spinner);
        confirm = (Button) findViewById(R.id.confirm);
    }

    private void initData() {
        mStrings = new ArrayList<>();
        for (int i=0;i<5;i++){
            mStrings.add((i+1)+"");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,mStrings);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selection = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        confirm.setOnClickListener(v->{
            mController.reloadCards(selection+3);
        });

    }

}
