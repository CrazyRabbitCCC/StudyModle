package lzy.com.money.DateBase;// @author: lzy  time: 2016/09/02.

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import lzy.com.money.Bean.Bill;
import lzy.com.money.Bean.Function;
import lzy.com.money.R;

public class DbHelper extends SQLiteOpenHelper {

    private static final String TAG = "SQLite";
    public static final int VERSION = 1;

    private static final String DBNAME = "test.db";
    public DbHelper(Context context) {
        super(context, DBNAME, null, VERSION);
    }
    //必须要有构造函数
    public DbHelper(Context context, String name, CursorFactory factory,
                       int version) {
        super(new DatabaseContext(context), name, factory, version);
    }


    // 当第一次创建数据库的时候，调用该方法
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table bill_table(id INTEGER PRIMARY KEY AUTOINCREMENT,sfunction varchar(20),smoney varchar(20),stime varchar(20))";
//输出创建数据库的日志信息
        Log.i(TAG, "create Database------------->");
//execSQL函数用于执行SQL语句
        db.execSQL(sql);
        String sql2 = "create table function_table(id INTEGER PRIMARY KEY AUTOINCREMENT,sfunction varchar(20))";
        db.execSQL(sql2);

        db.execSQL("INSERT INTO function_table VALUES (NULL, ?)", new Object[]{"早餐"});
        db.execSQL("INSERT INTO function_table VALUES (NULL, ?)", new Object[]{"午餐"});
        db.execSQL("INSERT INTO function_table VALUES (NULL, ?)", new Object[]{"晚餐"});
    }

    //当更新数据库的时候执行该方法
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//输出更新数据库的日志信息
        Log.i(TAG, "update Database------------->");
    }



//    public class DBHelper {
//        //得到SD卡路径
//        private final String DATABASE_PATH = android.os.Environment
//                .getExternalStorageDirectory().getAbsolutePath()
//                + "/joke";
//        private final Activity activity;
//        //数据库名
//        private final String DATABASE_FILENAME;
//        public DBHelper(Context context) {
//            // TODO Auto-generated constructor stub
////这里直接给数据库名
//            DATABASE_FILENAME = "jokebook.db3";
//            activity = (Activity)context;
//        }
//        //得到操作数据库的对象
//        public  SQLiteDatabase openDatabase()
//        {
//            try
//            {
//                boolean b = false;
//                //得到数据库的完整路径名
//                String databaseFilename = DATABASE_PATH + "/" + DATABASE_FILENAME;
////将数据库文件从资源文件放到合适地方（资源文件也就是数据库文件放在项目的res下的raw目录中）
//                //将数据库文件复制到SD卡中   File dir = new File(DATABASE_PATH);
//                File dir=new File(DATABASE_PATH);
//                if (!dir.exists())
//                    b = dir.mkdir();
//                //判断是否存在该文件
//                if (!(new File(databaseFilename)).exists())
//                {//不存在
//
//                    //得到数据库输入流对象
////                    InputStream is = activity.getResources().openRawResource(
////                            R.raw.jokebook);
//                    //创建输出流
//                    FileOutputStream fos = new FileOutputStream(databaseFilename);
//                    //将数据输出
//                    byte[] buffer = new byte[8192];
//                    int count = 0;
//                    while ((count = is.read(buffer)) > 0)
//                    {
//                        fos.write(buffer, 0, count);
//                    }
//                    //关闭资源
//                    fos.close();
//                    is.close();
//                }
////得到SQLDatabase对象
//                SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(
//                        databaseFilename, null);
//                return database;
//            }
//            catch (Exception e)
//            {
//                System.out.println(e.getMessage());
//            }
//            return null;
//        }
//    }
}
