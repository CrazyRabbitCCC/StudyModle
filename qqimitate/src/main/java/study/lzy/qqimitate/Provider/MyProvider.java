package study.lzy.qqimitate.Provider;
// @author: lzy  time: 2016/09/23.


import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

public class MyProvider extends ContentProvider{
    public static final String PROVIDER_NAME =
            "study.lzy.qqimitate.Provider.MyProvider";

    public static final Uri CONTENT_URI =
            Uri.parse("content://"+ PROVIDER_NAME + "/test");

    private static final int USER=1;
    private static final int CHAT=2;
    private static final int FRIEND=3;
    static {
        UriMatcher uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME,"UserTable",USER);
        uriMatcher.addURI(PROVIDER_NAME,"ChatRecord",CHAT);
        uriMatcher.addURI(PROVIDER_NAME,"Friend",FRIEND);
    }
    @Override
    public boolean onCreate() {
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
