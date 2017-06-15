package study.lzy.qqimitate;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

import study.lzy.qqimitate.Bean.UserBean;
import study.lzy.qqimitate.DataBase.DbHelper;
import study.lzy.qqimitate.Fragment.AboutFragment;
import study.lzy.qqimitate.Fragment.DiscoverFragment;
import study.lzy.qqimitate.Fragment.FriendFragment;
import study.lzy.qqimitate.Fragment.WeixinFragment;
import study.lzy.qqimitate.selfView.BadgeView;
import study.lzy.qqimitate.selfView.TabView;


public class MainActivity extends AppCompatActivity{
    private ViewPager viewPager;
    private TabLayout tab;
    private WxApplication wxApp;
    String[] result;
    DbHelper dbHelper;
    Gson gson;
    BadgeView badgeView;
    View parent;
    private WeixinFragment weixinFragment;
    private AboutFragment aboutFragment;
    private FriendFragment friendFragment;
    private DiscoverFragment discoverFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        gson = new GsonBuilder().create();

        wxApp= (WxApplication) getApplication();


        dbHelper = new DbHelper(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                String[] msg=new String[2];
                Map<String,Object> header=new HashMap<>();
                Map<String,Object> body=new HashMap<>();
                header.put("TransCode","UserInfo");
                body.put("UserId",0);
                body.put("UserName","刘");
                msg[0]=gson.toJson(header);
                msg[1]=gson.toJson(body);
                result = dbHelper.getResult(msg);

                handler.sendEmptyMessage(0);
            }
        }).start();

//        wxApp.setLoginUser(User.getUserTest(10001,"唐").get(0));
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        initView();
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        tab = (TabLayout) findViewById(R.id.tab);
        parent=findViewById(R.id.parent);

        tab.setSelectedTabIndicatorHeight(0);
//        tab.setTabMode(TabLayout.MODE_SCROLLABLE);
        tab.setTabMode(TabLayout.MODE_FIXED);
        tab.setTabGravity(TabLayout.GRAVITY_FILL);
//        tab.setTabGravity(TabLayout.GRAVITY_CENTER);
        FragmentManager fm = getSupportFragmentManager();
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(fm);
        viewPager.setAdapter(myPagerAdapter);
        for (int i=0;i<myPagerAdapter.getCount();i++){
            TabLayout.Tab tab = this.tab.newTab().setText(myPagerAdapter.getPageTitle(i));
             tab = tab.setCustomView(myPagerAdapter.getTabView(i));
            this.tab.addTab(tab,false);

        }

        ((TabView) tab.getTabAt( viewPager.getCurrentItem()).getCustomView()).setSelection(true);
        tab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tabtab) {
                viewPager.setCurrentItem(tabtab.getPosition());
                for (int i=0;i<tab.getTabCount();i++)
                {
                    if (i==tabtab.getPosition())
                        ((TabView)tab.getTabAt(i).getCustomView()).setSelection(true);
                    else
                        ((TabView)tab.getTabAt(i).getCustomView()).setSelection(false);
                }
//                ((TabView)tabtab.getCustomView()).setSelection(true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                ((TabView)tab.getCustomView()).setSelection(false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tabtab) {
                viewPager.setCurrentItem(tabtab.getPosition());
                for (int i = 0; i < tab.getTabCount(); i++) {
                    if (i == tabtab.getPosition())
                        ((TabView) tab.getTabAt(i).getCustomView()).setSelection(true);
                    else
                        ((TabView) tab.getTabAt(i).getCustomView()).setSelection(false);
                }
//                ((TabView)tabtab.getCustomView()).setSelection(true);
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position<tab.getTabCount()-1) {
                    ((TabView) tab.getTabAt(position).getCustomView()).onPageScrolled(positionOffset, positionOffsetPixels);
                    ((TabView) tab.getTabAt(position + 1).getCustomView()).onPageScrolledNext(positionOffset, positionOffsetPixels);
                }
            }

            @Override
            public void onPageSelected(int position) {
                for (int i=0;i<tab.getTabCount();i++)
                {

                    if (i==position)
                        ((TabView)tab.getTabAt(i).getCustomView()).setSelection(true);
                    else
                        ((TabView)tab.getTabAt(i).getCustomView()).setSelection(false);
                }
                if (position<=1) {
                    badgeView.setLittlePoint();
                    badgeView.setText("");
                }
                else {
                    if (badgeView.isLittlePoint())
                    badgeView = badgeView.getNewView();
                    badgeView.setText(position + "");
                }

                badgeView.show();
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



    }

    @Override
    public void onBackPressed() {
            super.onBackPressed();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class MyPagerAdapter extends FragmentPagerAdapter {
        String[] titles = {"微信","通讯录","发现","我"};
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        public TabView getTabView(int position){
            TabView tabView=new TabView(MainActivity.this);
            tabView.setText(titles[position]);
            switch (position){
                case 0:
                    tabView.setImage(R.mipmap.chats,
                            R.mipmap.chats_green,0);
                    break;
                case 1:
                    tabView.setImage(R.mipmap.contacts,
                            R.mipmap.contacts_green,0);
                    break;
                case 2:
                    tabView.setImage(R.mipmap.discover,
                            R.mipmap.discover_green,
                            R.mipmap.discover_white);
                    break;
                case 3:
                    tabView.setImage(R.mipmap.about_me,
                            R.mipmap.about_me_green,0);
                    break;
            }
            return tabView;
        }
        @Override
        public Fragment getItem(int position) {
            if (position==0){
                if (weixinFragment==null) {
                    weixinFragment = new WeixinFragment();
                    weixinFragment.setParent(parent);
                }
                return weixinFragment;
            }
           else if (position==1){
                if (friendFragment==null) {
                    friendFragment = new FriendFragment();
                    friendFragment.setParent(parent);
                }
                    return friendFragment;
            }
            else if(position==2){
                if (discoverFragment==null) {
                    discoverFragment = new DiscoverFragment();
                    discoverFragment.setParent(parent);
                }
                    return discoverFragment;
            }
            else if(position==3){
                if (aboutFragment==null) {
                    aboutFragment = new AboutFragment();
                    aboutFragment.setParent(parent);
                }
                return aboutFragment;
            }else return new Fragment();
        }

        @Override
        public int getCount() {
            return 4;
        }

    }

    Handler handler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
           switch (msg.what)
           {
               case 0:
                   if (Utils.checkResult(tab,result)){
                       UserBean userBean=gson.fromJson(result[1],new TypeToken<UserBean>(){}.getType());
                       if (userBean!=null&&userBean.getUser()!=null){
                           wxApp.setLoginUser(userBean.getUser());
                       }
                   }
                   break;
               default:
                   break;
           }

        }
    };

}
