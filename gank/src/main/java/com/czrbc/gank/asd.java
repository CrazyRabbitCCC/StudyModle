package com.czrbc.gank;

import android.support.v4.app.Fragment;

import com.czrbc.gank.adapter.LoadData;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gavin
 * @date 2017/06/20.
 */

public class asd {


    static List<Fragment> mList = new ArrayList<>();

    public asd(List<Fragment> fragmentList) {
        mList = fragmentList;
        mList.add(new Fragment());
    }

    public static void asddd(){
        for (int i = 0; i < mList.size(); i++) {

        }
        LoadData load = new LoadData() {
            @Override
            public void load(int rows) {

            }

            @Override
            public void loadMore(int page, int pageSize) {

            }
        };
    }

}
