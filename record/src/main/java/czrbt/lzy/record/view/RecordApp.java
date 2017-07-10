package czrbt.lzy.record.view;

import android.app.Application;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;

import czrbt.lzy.record.data.Provider;

// @author: lzy  time: 2016/10/31.


public class RecordApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

    }

    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, Provider.SAVE.CONTENT_URI,null,null,null,null);
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
