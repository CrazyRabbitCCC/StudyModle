package study.lzy.studymodle.Utils;
// @author: lzy  time: 2016/09/29.


import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public  class PermissionHelper {
    private Activity mActivity;
    private PermissionListener permissionListener;

    public PermissionHelper(Activity activity) {
        this.mActivity = activity;
    }


    public PermissionHelper(Activity mActivity, PermissionListener permissionListener) {
        this.mActivity = mActivity;
        this.permissionListener = permissionListener;
    }

    public void getPermission(int code, String...perms) {
        List<String> requestList=new ArrayList<>();

        for (String perm : perms) {
            int checkSelfPermission = ContextCompat.checkSelfPermission(mActivity, perm);
            if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {
                requestList.add(perm);
            }
        }
        if (requestList.size()>0){
            for (int i = 0; i < requestList.size(); i++) {
                 if (!ActivityCompat.shouldShowRequestPermissionRationale(mActivity, requestList.get(i)))
                     permissionListener.notShowDialog(code,perms[i]);
            }
            ActivityCompat.requestPermissions(mActivity,BaseUtil.ListTOStrings(requestList),code);
        }else {
            permissionListener.haveAllPerms(code);
        }
    }
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults){
        HashMap<String,Integer> permsResults=new HashMap<>();
        List<String> noPermsList=new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            if (i<grantResults.length)
            permsResults.put(permissions[i],grantResults[i]);
            else permsResults.put(permissions[i],-1);
        }
        for (String permission : permissions) {
            if (permsResults.get(permission) != PackageManager.PERMISSION_GRANTED)
                noPermsList.add(permission);
        }
        if (noPermsList.size()>0)
            permissionListener.notAllPerms(requestCode,BaseUtil.ListTOStrings(noPermsList));
        else permissionListener.haveAllPerms(requestCode);
    }

    public void setPermissionListener(PermissionListener permissionListener) {
        this.permissionListener = permissionListener;
    }

    public interface PermissionListener{
        void haveAllPerms(int requestCode);
        void notAllPerms(int requestCode,String... perms);
        void notShowDialog(int requestCode,String... perms);
    }

}
