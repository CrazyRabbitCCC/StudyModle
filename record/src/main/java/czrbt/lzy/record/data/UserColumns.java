package czrbt.lzy.record.data;
// @author: lzy  time: 2016/10/28.


import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

public class UserColumns {
    @DataType(DataType.Type.TEXT)@PrimaryKey
    public static final String ACCOUNT="account";
    @DataType(DataType.Type.TEXT)@NotNull
    public static final String PASSWORD="password";
    @DataType(DataType.Type.TEXT)@NotNull
    public static final String IS_REMEMBER="is_remember";
    @DataType(DataType.Type.TEXT)@NotNull
    public static final String LAST_IMEI="last_imei";
    @DataType(DataType.Type.TEXT)@NotNull
    public static final String LAST_DATE="last_data";
}
