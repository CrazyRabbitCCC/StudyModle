package czrbt.lzy.record.data;
// @author: lzy  time: 2016/10/28.


import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

public class BillColumns {
    @DataType(DataType.Type.INTEGER)@PrimaryKey @AutoIncrement
    public static final String _ID="_id";
    @DataType(DataType.Type.TEXT)@NotNull
    public static final String MONEY="MONEY";
    @DataType(DataType.Type.TEXT)@NotNull
    public static final String IS_SAVED="is_saved";
    @DataType(DataType.Type.TEXT)@NotNull
    public static final String TYPE="type";
    @DataType(DataType.Type.TEXT)@NotNull
    public static final String MODE="mode";
    @DataType(DataType.Type.TEXT)@NotNull
    public static final String DATE="date";
}
