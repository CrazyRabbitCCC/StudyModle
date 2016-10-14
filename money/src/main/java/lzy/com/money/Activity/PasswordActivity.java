package lzy.com.money.Activity;// @author: lzy  time: 2016/09/08.

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.LinearLayout;

import lzy.com.money.Fragment.money;
import lzy.com.money.Fragment.show;
import lzy.com.money.Fragment.system;
import lzy.com.money.R;

public class PasswordActivity extends AppCompatActivity{
    private static FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        manager = getFragmentManager();
        replace(new Fragment());

    }

    private static void replace(Fragment fragment) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.replace(R.id.frame, fragment);
        transaction.commit();
    }
}
