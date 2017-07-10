package czrbt.lzy.mylibrary.sync;



import android.accounts.Account;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;

// @author: lzy  time: 2016/10/31.

public abstract class TableObserver extends ContentObserver {
    protected Account ACCOUNT;
    /**
     * Creates a content observer.
     *
     * @param handler The handler to run {@link #onChange} on, or null if none.
     */
    public TableObserver(Account account,Handler handler) {
        super(handler);
        ACCOUNT=account;
    }
    /**
     * Define a method that's called when data in the
     * observed content provider changes.
     * This method signature is provided for compatibility with
     * older platforms.
     * @param selfChange
     */
    @Override
    public void onChange(boolean selfChange) {
            /*
             * Invoke the method signature available as of
             * Android platform version 4.1, with a null URI.
             */
        onChange(selfChange, null);
    }


    /**
     * Define a method that's called when data in the
     * observed content provider changes.
     * @param selfChange
     * @param changeUri
     */
    @Override
    public abstract void onChange(boolean selfChange, Uri changeUri);{
            /*
             * Ask the framework to run your sync adapter.
             * To maintain backward compatibility, assume that
             * changeUri is null
             */
    }

}
