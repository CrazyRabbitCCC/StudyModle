package lzy.com.money.Application;// @author: lzy  time: 2016/09/02.

import android.app.Application;
import android.content.Intent;
import android.database.Cursor;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import lzy.com.money.Bean.Bill;
import lzy.com.money.Bean.Function;
import lzy.com.money.Bean.SystemConfig;
import lzy.com.money.DateBase.DbHelper;
import lzy.com.money.FloatWindows.FloatWindowService;
import lzy.com.money.LockView.LockPatternUtils;

public class moneyApplication extends Application{
    public static final String FileParent = Environment.getExternalStorageDirectory().getAbsolutePath() + "/lzy";

    public static final String systemPath = FileParent + "/lzy.lzy";
    public static final String historyFile = FileParent + "/history.lzy";
    private LockPatternUtils mLockPatternUtils;

    private Gson gson;
    private SystemConfig systemConfig;
    @Override
    public void onCreate() {
        super.onCreate();
        mLockPatternUtils = new LockPatternUtils();
        String system=readFile(systemPath);
        gson=new GsonBuilder().create();
        if (TextUtils.isEmpty(system))
            systemConfig=new SystemConfig();
        else
            systemConfig=gson.fromJson(system, SystemConfig.class);
        Intent intent = new Intent(this, FloatWindowService.class);
        startService(intent);
    }
    public  Boolean getSystemConfig(int num){
        return systemConfig.getSystemConfig(num);
    }

    public  String getSystemConfig(String config){
        return systemConfig.getSystemConfig(config);
    }


    public  void setSystemConfig(int num, Boolean result){
        systemConfig.setSystemConfig(num,result);
        writeFile(systemPath,gson.toJson(systemConfig));
    }
    public  void setSystemConfig(String config, String result){
        systemConfig.setSystemConfig(config,result);
        writeFile(systemPath,gson.toJson(systemConfig));
    }

    public static List<Function> getFunction(DbHelper helper){
        List<Function> functionList=new ArrayList<>();
        Function f=new Function();
        return f.select(helper);

    }

    public static List<Bill> getBill(DbHelper helper) {
        List<Bill> billList=new ArrayList<>();
        Bill f=new Bill();
        return f.select(helper);
    }


    public static String readFile(String path) {
        String content = ""; //文件内容字符串
        File file = new File(path);
        try {
            if (file.exists()) {
                InputStream instream = new FileInputStream(file);//读取输入流
                InputStreamReader inputreader = new InputStreamReader(instream);//设置流读取方式
                BufferedReader buffreader = new BufferedReader(inputreader);
                String line;
                while ((line = buffreader.readLine()) != null) {
                    content += line;//读取的文件内容
                }
                content = content.substring(0, content.length());
            }
        } catch (Exception ex) {
        }



        return content;
    }

    public static Boolean writeFile(String path, String body) {
        File file = new File(path);//
        try {
            if (!file.exists())
                file.createNewFile();

            OutputStream outstream = new FileOutputStream(file);//设置输出流
            OutputStreamWriter out = new OutputStreamWriter(outstream);//设置内容输出方式
            out.write(body);//输出内容到文件中
            out.close();
            Log.d("SAVE FILE", "成功");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("SAVE FILE", e.toString());
            return false;
        }
    }

    public LockPatternUtils getLockPatternUtils() {
        return mLockPatternUtils;
    }

}
