package czrbt.lzy.record.data;
// @author: lzy  time: 2016/10/28.

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

@ContentProvider(authority = Provider.AUTHORITY, database = DataBase.class)
public class Provider {
    public static final String AUTHORITY="czrbt.lzy.record.data.provider";

    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    interface Path {
        String SAVES = "saves";
        String BILLS = "bills";
        String USERS ="users";
    }

    private static Uri buildUri(String... paths) {
        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
        for (String path : paths) {
            builder.appendPath(path);
        }
        return builder.build();
    }

    @TableEndpoint(table = DataBase.SAVES)
    public static class SAVE {
        @ContentUri(
                path = Path.SAVES,
                type = "vnd.android.cursor.dir/save"
        )
        public static final Uri CONTENT_URI = buildUri(Path.SAVES);

        @InexactContentUri(
                name = "QUOTE_ID",
                path = Path.SAVES + "/*",
                type = "vnd.android.cursor.item/save",
                whereColumn = SaveColumns.PATH,
                pathSegment = 1
        )
        public static Uri withPath(String path) {
            return buildUri(Path.SAVES, path);
        }
    }

    @TableEndpoint(table = DataBase.BILLS)
    public static class Bill {
        @ContentUri(
                path = Path.BILLS,
                type = "vnd.android.cursor.dir/bill"
        )
        public static final Uri CONTENT_URI = buildUri(Path.BILLS);

        @InexactContentUri(
                name = "BILLS_ID",
                path = Path.BILLS + "/*",
                type = "vnd.android.cursor.item/bill",
                whereColumn = BillColumns._ID,
                pathSegment = 1
        )
        public static Uri withID(String symbol) {
            return buildUri(Path.BILLS, symbol);
        }
    }
    @TableEndpoint(table = DataBase.USERS)
    public static class Users {
        @ContentUri(
                path = Path.USERS,
                type = "vnd.android.cursor.dir/user"
        )
        public static final Uri CONTENT_URI = buildUri(Path.USERS);

        @InexactContentUri(
                name = "BILLS_ID",
                path = Path.USERS + "/*",
                type = "vnd.android.cursor.item/user",
                whereColumn = UserColumns.ACCOUNT,
                pathSegment = 1
        )
        public static Uri withAccount(String account) {
            return buildUri(Path.USERS, account);
        }
    }

}
