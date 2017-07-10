package study.lzy.studymodle.star;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import study.lzy.studymodle.Utils.BaseUtil;

/**
 * @author Gavin
 * @date 2017/07/07.
 */

public class 星星控制 implements View.OnClickListener{
    private 星星小格子[][] 游戏格子;
    private Context 环境;
    private int 横向格子数=15,纵向格子数=18;
    private int 当前宽度,当前高度;
    private GridLayout 游戏布局;

    public 星星控制(Context context, GridLayout 游戏布局) {
        环境 = context;
        this.游戏布局 = 游戏布局;
        初始化();
    }

    public void 初始化(){
        游戏布局.setColumnCount(横向格子数);
        游戏格子 = new 星星小格子[横向格子数][纵向格子数];
        DisplayMetrics 屏幕参数 = BaseUtil.getScreenMetrics(环境);
        int 格子边长 = 屏幕参数.widthPixels / 横向格子数 ;
        ViewGroup.LayoutParams 布局属性 = 游戏布局.getLayoutParams();
        int h = 格子边长* 纵向格子数;
        if (布局属性==null)
            布局属性 = new ViewGroup.LayoutParams(屏幕参数.widthPixels,h);
        else {
            布局属性.width = 屏幕参数.widthPixels;
            布局属性.height = h;
        }
        游戏布局.setLayoutParams(布局属性);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(格子边长, 格子边长);
        for (int 数2=0;数2<纵向格子数;数2++){
            for (int 数=0;数<横向格子数;数++){
                游戏格子[数][数2]= new 星星小格子(环境);
                游戏格子[数][数2].设置格子序列(数2*横向格子数+数);
                游戏格子[数][数2].setOnClickListener(this);
                游戏布局.addView(游戏格子[数][数2],lp);
            }
        }
    }

    public void 重新加载格子(int level){
        当前高度= 0;
        当前宽度= 横向格子数;
        for (int 数2=0;数2<纵向格子数;数2++){
            for (int 数=0;数<横向格子数;数++){
                游戏格子[数][数2].设置数据((int)(Math.random()*level)+1);
            }
        }
    }


    private void 加载数据(){
        int 横 = -1;
        for (int 数 = 0; 数 < 当前宽度; 数++) {
            boolean isColumnEmpty = 横向加载(数);
            if (isColumnEmpty){
                if (横 ==-1)
                    横 = 数 ;
            }else if (横 !=-1){
                横向交换(数,横);
                横++;
            }
        }
        if (横!=-1)
            当前宽度 = 横;
        else
            当前宽度 = 横向格子数;
    }

    private void 横向交换(int 数,int 横){
        for (int 纵=纵向格子数-1;纵>当前高度-1;纵--){
            游戏格子[横][纵].设置数据(游戏格子[数][纵].获取数据());
            游戏格子[数][纵].设置数据(0);
        }
    }

    private boolean 横向加载(int 数){
        boolean 标识符 =true;
        int 横 = -1;
        for (int 数2=纵向格子数-1;数2>当前高度-1;数2--){
            星星小格子 card = 游戏格子[数][数2];
            int 格子数据 = card.获取数据();
            if (格子数据!=0){
                标识符= false;
                if (横!=-1){
                    card.设置数据(0);
                    游戏格子[数][横].设置数据(格子数据);
                    横--;
                }
            }else {
                if (横 ==-1)
                    横 = 数2;
            }
        }
        if (横 == -1){
            当前高度=0;
        }else {
            当前高度=Math.min(当前高度,横);
        }
        return 标识符;
    }

    private   boolean 是否游戏结束(){
        for (int 数=0;数<当前宽度-1;数++){
            for (int 数2=纵向格子数-1;数2>当前高度;数2--){
                int 格子数据 = 游戏格子[数][数2].获取数据();
                if (格子数据!=0){
                    if (格子数据 == 游戏格子[数][数2-1].获取数据())
                        return false;
                    if (格子数据 == 游戏格子[数+1][数2].获取数据())
                        return false;
                }
            }
        }
        return true;
    }

    private  boolean 检查所有格子(int 数,int 数2){
        int 格子数据=游戏格子[数][数2].获取数据();
        if (格子数据==0)
            return false;
        if (数>0 && 格子数据== 游戏格子[数-1][数2].获取数据()){
            return true;
        }
        if (数<当前宽度-1 && 格子数据== 游戏格子[数+1][数2].获取数据()){
            return true;
        }
        if (数2>当前高度 && 格子数据== 游戏格子[数][数2-1].获取数据()){
            return true;
        }
        if (数2<纵向格子数-1 && 格子数据== 游戏格子[数][数2+1].获取数据()){
            return true;
        }
        return false;
    }

    private  int 格子爆炸(int 数,int 数2){
        int 结果 = 1;
        int 格子数据=游戏格子[数][数2].获取数据();
        游戏格子[数][数2].设置数据(0);
        if (数>0 && 格子数据== 游戏格子[数-1][数2].获取数据()){
            结果+=格子爆炸(数-1,数2);
        }
        if (数<当前宽度-1 && 格子数据== 游戏格子[数+1][数2].获取数据()){
            结果+=格子爆炸(数+1,数2);
        }
        if (数2>当前高度 && 格子数据== 游戏格子[数][数2-1].获取数据()){
            结果+=格子爆炸(数,数2-1);
        }
        if (数2<纵向格子数-1 && 格子数据== 游戏格子[数][数2+1].获取数据()){
            结果+=格子爆炸(数,数2+1);
        }
        return 结果;
    }

    @Override
    public void onClick(View v) {
        if (v instanceof 星星小格子){
            int cardId = ((星星小格子) v).获取格子序列();
            int 数2 = cardId/横向格子数;
            int 数 = cardId%横向格子数;
            if (检查所有格子(数,数2)){
                格子爆炸(数,数2);
                加载数据();
            }
        }
    }
}
