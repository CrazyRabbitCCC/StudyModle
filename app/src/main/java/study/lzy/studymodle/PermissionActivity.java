package study.lzy.studymodle;
// @author: lzy  time: 2016/09/29.


import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.security.Permission;
import java.util.ArrayList;
import java.util.List;

import study.lzy.studymodle.AboutHolder.BaseAdapter;
import study.lzy.studymodle.AboutHolder.Holder;
import study.lzy.studymodle.AboutHolder.OnClickListener;
import study.lzy.studymodle.Tree.DividerItemDecoration;
import study.lzy.studymodle.Utils.PermissionHelper;

public class PermissionActivity extends AppCompatActivity {

    private PermAdapter permAdapter;
    private RecyclerView rv;
    private PermissionHelper permHelper;
    private PermissionHelper.PermissionListener permissionListener;
    private OnClickListener onClickListener;
    private List<Perm> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        permHelper = new PermissionHelper(this);
        rv = (RecyclerView) findViewById(R.id.rv);
        initListener();
        initListData();
        initData();
    }

    private void initListData() {
        list = new ArrayList<>();
        list.add(new Perm(101, "日历", Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR));
        list.add(new Perm(102, "相机", Manifest.permission.CAMERA));
        list.add(new Perm(103, "通讯录", Manifest.permission.READ_CONTACTS));
        list.add(new Perm(104, "位置信息", Manifest.permission.ACCESS_FINE_LOCATION));
        list.add(new Perm(105, "麦克风", Manifest.permission.RECORD_AUDIO));
        list.add(new Perm(106, "电话", Manifest.permission.READ_PHONE_STATE));
        list.add(new Perm(107, "身体传感器", Manifest.permission.BODY_SENSORS));
        list.add(new Perm(108, "短信", Manifest.permission.SEND_SMS));
        list.add(new Perm(109, "存储空间", Manifest.permission.READ_EXTERNAL_STORAGE));
    }

    private void initData() {
        permHelper.setPermissionListener(permissionListener);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
//        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);
        rv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
        permAdapter = new PermAdapter(list);
        permAdapter.setOnClickListener(onClickListener);
        rv.setAdapter(permAdapter);
    }


    private void initListener() {
        onClickListener = new OnClickListener() {
            @Override
            public void onClick(View v, int position) {
                permHelper.getPermission(permAdapter.getItem(position).code, permAdapter.getItem(position).perms);
            }
        };
        permissionListener = new PermissionHelper.PermissionListener() {
            @Override
            public void haveAllPerms(int requestCode) {
                Perm thisPerm = new Perm();
                for (Perm perm : list) {
                    if (perm.code == requestCode) {
                        thisPerm = perm;
                        break;
                    }
                }
                Snackbar.make(rv, "取得" + thisPerm.name + "权限 成功", Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void notAllPerms(int requestCode, String... perms) {
                Perm thisPerm = new Perm();
                for (Perm perm : list) {
                    if (perm.code == requestCode) {
                        thisPerm = perm;
                        break;
                    }
                }
                Snackbar.make(rv, "取得" + thisPerm.name + "权限 失败", Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void notShowDialog(int requestCode, String... perms) {
                String message = "";
                for (Perm perm : list) {
                    if (perm.code == requestCode) {
                        message += perm.name;
                        break;
                    }
                }
                Snackbar.make(rv, "不能取得" + message + "权限，已被禁止\n请在设置里打开该权限", Snackbar.LENGTH_SHORT).show();
            }
        };
    }

    class PermAdapter extends BaseAdapter {
        List<Perm> list;

        public PermAdapter(List<Perm> list) {
            this.list = list;
        }

        public Perm getItem(int position) {
            return list.get(position);
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            holder.text.setText(list.get(position).name);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    class Perm {
        int code = 0;
        String name = "";
        String[] perms;

        public Perm() {
        }

        public Perm(int code, String name, String... perms) {
            this.code = code;
            this.name = name;
            this.perms = perms;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
