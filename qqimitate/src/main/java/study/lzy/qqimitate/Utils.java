package study.lzy.qqimitate;
// @author: lzy  time: 2016/09/26.


import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

public class Utils {
    public static boolean checkResult(View v, String[] result){
        if (result==null) {
            Snackbar.make(v,"",Snackbar.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(result[1])){
            Snackbar.make(v,"",Snackbar.LENGTH_SHORT).show();
            return false;
        }
        Gson gson= new GsonBuilder().create();
        Map<String,Object> map=new HashMap<>();
        map=gson.fromJson(result[0],map.getClass());
        String resultCode = (String) map.get("ResultCode");
        if (TextUtils.isEmpty(resultCode)) {
            Snackbar.make(v,"ERROR",Snackbar.LENGTH_SHORT).show();
            return false;
        }
        String message = (String) map.get("Message");
        if (TextUtils.isEmpty(message)) {
            Snackbar.make(v,"ERROR",Snackbar.LENGTH_SHORT).show();
            return false;
        }
        if (resultCode.equals("9999")) {
            Snackbar.make(v,message,Snackbar.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
