package study.lzy.qqimitate.Permission;
// @author: lzy  time: 2016/09/29.


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public  class PermissionHelper {
    private Activity mActivity;

    public PermissionHelper(Activity activity) {
        this.mActivity = activity;
    }

    public void getPermission( int code,String...perms) {
        getPermission(perms,code);
    }
    public void getPermission( String[] perms,int code){
//        String[] requestPerms
        boolean flag=true;

        for (int i=0;i<perms.length;i++){
            int checkSelfPermission = ContextCompat.checkSelfPermission(mActivity,perms[i]);
            if(checkSelfPermission != PackageManager.PERMISSION_GRANTED){
                flag=false;
            }

        }

        int checkSelfPermission = ContextCompat.checkSelfPermission(mActivity, Manifest.permission.READ_CONTACTS);
        if(checkSelfPermission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(mActivity,new String[]{Manifest.permission.READ_CONTACTS},123);
        }else{

        }
    }



    public interface PermissionListener{
        void getPermission();
    }

}
