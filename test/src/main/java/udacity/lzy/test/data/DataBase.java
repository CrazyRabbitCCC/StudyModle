package udacity.lzy.test.data;
// @author: lzy  time: 2016/10/28.

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

@Database(version = DataBase.DATABASE_VERSION)
public class DataBase {
    public static final int DATABASE_VERSION = 2;
    @Table(value = SaveColumns.class)
    public static final String SAVES="saves";
//    @Table(value = BillColumns.class)
//    public static final String BILLS="bills";
//    @Table(value = UserColumns.class)
//    public static final String USERS="users";
}
//public class DataBase extends SQLiteOpenHelper {
//    private static final int DATABASE_VERSION = 2;
//    public static final String DATABASE_NAME="record.db";
//    public static final String SAVES="saves";
//    public static final String BILLS="bills";
//    public static final String SAVE_SQL = "CREATE TABLE saves ("
//            + SaveColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
//            + SaveColumns.PATH + " TEXT NOT NULL,"
//            + SaveColumns.IS_SAVED + " TEXT NOT NULL,"
//            + SaveColumns.TYPE + " INTEGER NOT NULL,"
//            + SaveColumns.DATE + " TEXT NOT NULL)";
//
//    public static final String BILL_SQL = "CREATE TABLE bills ("
//            + BillColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
//            + BillColumns.MONEY + " TEXT NOT NULL,"
//            + BillColumns.IS_SAVED + " TEXT NOT NULL,"
//            + BillColumns.TYPE + " TEXT NOT NULL,"

//            + BillColumns.DATE + " TEXT NOT NULL)";
//
//    private static volatile DataBase instance;
//
//    private Context context;
//
//    private DataBase(Context context) {
//        super(new DatabaseContext(context), DATABASE_NAME, null, DATABASE_VERSION);
//        this.context = context;
//    }
//
//    public static DataBase getInstance(Context context) {
//        if (instance == null) {
//            synchronized (DataBase.class) {
//                if (instance == null) {
//                    instance = new DataBase(context);
//                }
//            }
//        }
//        return instance;
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(SAVE_SQL);
//        db.execSQL(BILL_SQL);
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//    }
//}