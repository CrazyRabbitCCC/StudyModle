package cn.sleepycoder.designexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    public void initViews(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "那些让你相见恨晚的方法、类或接口", Snackbar.LENGTH_LONG)
                        .setAction("查看", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(MainActivity.this,WebActivity.class);
                                String url = "http://blog.csdn.net/w804518214/article/details/51312327";
                                intent.putExtra("url",url);
                                startActivity(intent);
                            }
                        }).show();
            }
        });
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);


        FragmentManager fm = getSupportFragmentManager();
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(fm);
        viewPager.setAdapter(myPagerAdapter);
        viewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPager);
    }



    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent = new Intent(this,WebActivity.class);
        String url = "http://blog.csdn.net/w804518214/article/details/51231946";
        switch (id){
            case R.id.menu_add:
                url = "http://blog.csdn.net/w804518214/article/details/51231946";
                break;
            case R.id.menu_search:
                url = "http://mp.weixin.qq.com/s?__biz=MzAxMTI4MTkwNQ==&mid=2650820061&idx=1&sn=c1e5d4d10df62b9eb149eb005702a3f7&scene=0#wechat_redirect";
                break;
            default:
                break;
        }
        intent.putExtra("url",url);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent = new Intent(this,WebActivity.class);
        String url = "http://blog.csdn.net/w804518214/article/details/51285038";
        if (id == R.id.nav_as) {
            url = "http://blog.csdn.net/w804518214/article/details/51110987";
        } else if (id == R.id.nav_n) {
            url = "http://blog.csdn.net/lowprofile_coding/article/details/51331123";
        } else if (id == R.id.nav_bottom_bar) {
            url = "http://blog.csdn.net/w804518214/article/details/51285038";
        } else if (id == R.id.nav_git) {
            url = "http://blog.csdn.net/w804518214/article/details/51165640";
        } else if (id == R.id.nav_snackbar) {
            url = "http://mp.weixin.qq.com/s?__biz=MzAxMTI4MTkwNQ==&mid=2650820083&idx=1&sn=1cd6b3d66b9d0054054df548c5b4afbb&scene=0#wechat_redirect";
        } else if (id == R.id.nav_data_binding) {
            url = "http://mp.weixin.qq.com/s?__biz=MzA4NTQwNDcyMA==&mid=2650661563&idx=1&sn=ff4950622636f5e58636d28001c8875b&scene=23&srcid=05023OG3jklTzzxTtSW2wMIU#rd";
        }
        intent.putExtra("url",url);
        startActivity(intent);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
