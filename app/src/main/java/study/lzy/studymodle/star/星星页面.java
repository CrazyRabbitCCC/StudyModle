package study.lzy.studymodle.star;

import android.os.Bundle;
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
 * @date 2017/07/07.
 */

public class 星星页面 extends AppCompatActivity {
    private GridLayout 游戏布局;
    private 星星控制 游戏控制;
    private int 选中难度 = 0;
    private List<String> 难度;
    private Spinner 难度下拉框;
    private Button 确认按钮;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star);
        初始化视图();
        初始化数据();
        游戏控制 = new 星星控制(this,游戏布局);
        游戏控制.重新加载格子(5);


    }

    private void 初始化视图() {
        游戏布局 =(GridLayout) findViewById(R.id.game_layout);
        难度下拉框 = (Spinner) findViewById(R.id.spinner);
        确认按钮 = (Button) findViewById(R.id.confirm);
    }

    private void 初始化数据() {
        难度 = new ArrayList<>();
        for (int i=0;i<5;i++){
            难度.add((i+1)+"");
        }
        ArrayAdapter<String> 适配器 = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,难度);
        适配器.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        难度下拉框.setAdapter(适配器);
        难度下拉框.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                选中难度 = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        确认按钮.setOnClickListener(v-> 游戏控制.重新加载格子(选中难度+3));

    }
}
