package ir.mkp.second_twitter.database.dataHelper.tables;

public class BlockTable implements BaseTable {

    public static  final String TABLE_NAME="block";
    public static final String BLOCKING="blocking";
    public static final String BLOCKER="blocker";

    public static String getCreateTableStatment(){
        return "CREATE TABLE " + TABLE_NAME + "("
                + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +BLOCKING+" INTEGER, "
                + BLOCKER + " INTEGER "
                + ")";
    }

    public static String[] getAllColumns(){
        return new String[]{
                ID_COLUMN,
                BLOCKING,
                BLOCKER
        };
    }

    public static String[] getAllColumnsWithoutID(){
        return new String[]{
                BLOCKING,
                BLOCKER
        };
    }
}
