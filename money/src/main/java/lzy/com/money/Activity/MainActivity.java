package lzy.com.money.Activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import lzy.com.money.Application.moneyApplication;
import lzy.com.money.Fragment.money;
import lzy.com.money.Fragment.show;
import lzy.com.money.Fragment.system;
import lzy.com.money.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager manager;
    private money m;
    private show s;
    private system sys;
    private int PHOTO = 0X3423, HEADER = 0x8f68;
    moneyApplication mApplication;
    private long mCurTime;
    private long mLastTime;
    DrawerLayout drawer;
    private NavigationView navigationView;
    private static int colorNum = 0;
    private ImageView imageView;
    private View headerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApplication = (moneyApplication) getApplication();
        setContentView(R.layout.activity_main);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
         headerView = navigationView.getHeaderView(0);
         imageView = (ImageView) headerView.findViewById(R.id.imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLastTime = mCurTime;
                mCurTime = System.currentTimeMillis();
                if (mCurTime - mLastTime < 300) {
                    Intent i = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);//调用android的图库
                    startActivityForResult(i, HEADER);
                }
            }
        });
        navigationView.setNavigationItemSelectedListener(this);
        String imgPath = moneyApplication.readFile(moneyApplication.FileParent + "imgPath.lzy");
        if (imgPath != null && !imgPath.isEmpty()) {
//            ColorStateList colorStateList = ColorStateList.valueOf(getAdverseColor(imgPath));
//            navigationView.setItemTextColor(colorStateList);
            try {
                Drawable d = Drawable.createFromPath(imgPath);
                drawer.setBackgroundDrawable(d);

            } catch (NullPointerException ne) {
                drawer.setBackgroundResource(R.drawable.side_nav_bar);
            }
            try {
                Drawable c = Drawable.createFromPath(imgPath);
                navigationView.setBackgroundDrawable(c);
            } catch (NullPointerException ne) {
                navigationView.setBackgroundColor(Color.WHITE);
            }
        } else
            drawer.setBackgroundResource(R.drawable.side_nav_bar);

        String headerPath = moneyApplication.readFile(moneyApplication.FileParent + "headerPath.lzy");
        if (headerPath != null && !headerPath.isEmpty()) {
            try {
                Drawable d = Drawable.createFromPath(headerPath);
                headerView.setBackgroundDrawable(d);

            } catch (NullPointerException ne) {
                headerView.setBackgroundResource(R.drawable.side_nav_bar);
            }
        } else
            headerView.setBackgroundResource(R.drawable.side_nav_bar);


        navigationView.setCheckedItem(R.id.nav_send);
        onNavigationItemSelected(R.id.nav_send);


        init();
    }

    private int getAdverseColor(String imgPath) {
        Bitmap bitmap = BitmapFactory.decodeFile(imgPath);
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        int R = 0, G = 0, B = 0;
        for (int y = 3 * height / 10; y < height * 7 / 10; y++)
            for (int x = width / 5; x < width * 9 / 20; x++) {
                int pixColor = bitmap.getPixel(x, y);
                int r = Color.red(pixColor);
                int g = Color.green(pixColor);
                int b = Color.blue(pixColor);
                R += r;
                G += g;
                B += b;
            }
        R /= (height * 4 / 10) * (width / 4);
        G /= (height * 4 / 10) * (width / 4);
        B /= (height * 4 / 10) * (width / 4);
        R = 255 - R;
        G = 255 - G;
        B = 255 - B;

        return Color.argb(0Xff, R, G, B);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            drawer.openDrawer(GravityCompat.START);
//            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    private boolean onNavigationItemSelected(int id) {
        manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        if (id == R.id.nav_gallery) {
            if (s == null) {
                s = new show();
            }
            transaction.replace(R.id.frame, s);
        } else if (id == R.id.nav_send) {
            if (m == null) {
                m = new money();
            }
            transaction.replace(R.id.frame, m);
        }else if (id == R.id.nav_config) {
            if (sys == null) {
                sys = new system();
            }
            transaction.replace(R.id.frame, sys);
        } else if (id == R.id.nav_photo) {

            Intent i = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);//调用android的图库
            startActivityForResult(i, PHOTO);
        } else if (id == R.id.nav_out) {
            finish();
        }

        transaction.commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        return onNavigationItemSelected(id);
    }

    void init() {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            switch (resultCode) {

                case RESULT_OK: {
                    Uri uri = data.getData();
                    Cursor cursor = getContentResolver().query(uri, null,
                            null, null, null);
                    cursor.moveToFirst();
                    String imgNo = cursor.getString(0); // 图片编号
                    String imgPath = cursor.getString(1); // 图片文件路径
                    String imgSize = cursor.getString(2); // 图片大小
                    String imgName = cursor.getString(3); // 图片文件名
                    cursor.close();

                    if (requestCode == PHOTO) {
                        Drawable d = Drawable.createFromPath(imgPath);
                        Drawable c = Drawable.createFromPath(imgPath);
                        moneyApplication.writeFile(moneyApplication.FileParent + "imgPath.lzy", imgPath );
                        drawer.setBackgroundDrawable(d);
                        navigationView.setBackgroundDrawable(c);
//                        ColorStateList colorStateList = ColorStateList.valueOf(getAdverseColor(imgPath));
//                        navigationView.setItemTextColor(colorStateList);
                    }
                    else if (requestCode ==HEADER){
                        moneyApplication.writeFile(moneyApplication.FileParent + "headerPath.lzy", imgPath);
                        Drawable d=Drawable.createFromPath(imgPath);
                        headerView.setBackgroundDrawable(d);
                    }
                }
                break;
                case RESULT_CANCELED:// 取消
                    break;


            }


    }
}
