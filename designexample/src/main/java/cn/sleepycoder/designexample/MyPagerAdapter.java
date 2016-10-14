package cn.sleepycoder.designexample;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Mark on 16/5/7.
 */
public class MyPagerAdapter extends FragmentPagerAdapter {
    FragmentManager fm;
    String[] titles = {"tab1","tab2","tab3"};
    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0){
            return new StickyHeaderFragment();
        }
        return new MyFragment();
    }

    @Override
    public int getCount() {
        return 3;
    }

}
