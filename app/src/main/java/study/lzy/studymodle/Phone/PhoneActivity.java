package study.lzy.studymodle.Phone;// @author: lzy  time: 2016/09/19.

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import study.lzy.studymodle.AboutHolder.BaseAdapter;
import study.lzy.studymodle.AboutHolder.Holder;
import study.lzy.studymodle.AboutHolder.OnClickListener;
import study.lzy.studymodle.R;
import study.lzy.studymodle.Tree.DividerItemDecoration;

public class PhoneActivity  extends AppCompatActivity{

    private RecyclerView rv;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        rv = (RecyclerView) findViewById(R.id.rv);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);
        rv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
        adapter = new Adapter();
        adapter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v, int position) {
                if (adapter.getItem(position).number != null) {
                    Snackbar.make(rv,adapter.getItem(position).toString(),Snackbar.LENGTH_SHORT).show();
                }
            }
        });

//        if (Build.VERSION.SDK_INT>=23) {
            int checkSelfPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
            if(checkSelfPermission != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.shouldShowRequestPermissionRationale(this,"");
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},123);
            }else{
                getPhone();
            }
//
//        }else {
//            getPhone();
//        }


    }

    private void getPhone() {
        Cursor cursor= getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
        while (cursor.moveToNext()){
            String name=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String number=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            adapter.add(new PhoneDate(name,number));
        }
        cursor.close();
        rv.setAdapter(adapter);
    }

    class Adapter extends BaseAdapter {
        List<PhoneDate> list;

        public Adapter() {
            list = new ArrayList<>();
        }

        public void add(PhoneDate clickIntent) {
            list.add(clickIntent);
            notifyDataSetChanged();
        }

        public void setList(List<PhoneDate> list) {
            this.list = list;
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            holder.image.setVisibility(View.VISIBLE);
            holder.text.setText(list.get(position).name+":"+list.get(position).number);
        }

        public PhoneDate getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }


    class PhoneDate {

        @Override
        public String toString() {
            return
                    "name='" + name + '\'' +
                    ", number='" + number + '\'' ;
        }

        public PhoneDate(String name, String number) {
            this.name = name;
            this.number = number;

        }

        String name;
        String number;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 123:
                HashMap<String,Integer> perms =new HashMap<>();
                perms.put(Manifest.permission.READ_CONTACTS,PackageManager.PERMISSION_GRANTED);
                for (int i=0;i<permissions.length;i++){
                    perms.put(permissions[i],grantResults[i]);
                }
                boolean haveAllPerms=true;
                for (String perm:permissions){
                    if (perms.get(perm)!=PackageManager.PERMISSION_GRANTED){
                        haveAllPerms=false;
                        break;
                    }
                }
                if (haveAllPerms){
                    getPhone();
                }else
                    Snackbar.make(rv,"抱歉，没有足够权限进行此操作",Snackbar.LENGTH_SHORT)
                            .setAction("获取权限", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ActivityCompat.requestPermissions(PhoneActivity.this,new String[]{Manifest.permission.READ_CONTACTS},123);
                        }
                    }).show();
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);

                break;
        }
    }
}
