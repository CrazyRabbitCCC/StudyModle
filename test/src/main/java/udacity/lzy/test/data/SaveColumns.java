package udacity.lzy.test.data;
// @author: lzy  time: 2016/10/28.

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

public class SaveColumns {
    @DataType(DataType.Type.INTEGER)@PrimaryKey@AutoIncrement
    public static final String _ID="_id";
    @DataType(DataType.Type.TEXT)@NotNull
    public static final String PATH="path";
    @DataType(DataType.Type.TEXT)@NotNull
    public static final String TITLE="title";
    @DataType(DataType.Type.TEXT)@NotNull
    public static final String IS_SAVED="is_saved";
    @DataType(DataType.Type.TEXT)@NotNull
    public static final String IS_CHANGED="is_changed";
    @DataType(DataType.Type.INTEGER)@NotNull
    public static final String TYPE="type";
    @DataType(DataType.Type.TEXT)@NotNull
    public static final String DATE="date";
}
