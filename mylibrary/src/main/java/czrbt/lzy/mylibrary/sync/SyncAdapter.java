package czrbt.lzy.mylibrary.sync;
// @author: lzy  time: 2016/10/31.


import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

public  class SyncAdapter extends AbstractThreadedSyncAdapter{
    protected ContentResolver mContentResolver;
    protected SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        mContentResolver=context.getContentResolver();
    }

    protected SyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        mContentResolver=context.getContentResolver();
    }
    // TODO: 更新内容
    @Override
    public  void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {

    }

}
