package ir.mkp.second_twitter.database.dataHelper.tables;

public class PostTable implements BaseTable {
    public static final String TABLE_NAME = "post";
    public static final String TEXT_COLUMN = "text_post";

    public static String getCreateTableStatment(){
        return "CREATE TABLE " + TABLE_NAME + "("
                + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TEXT_COLUMN + " TEXT "
                + ")";
    }

    public static String[] getAllColumns(){
        return new String[]{
                ID_COLUMN,
                TEXT_COLUMN
        };
    }

    public static String[] getAllColumnsWithoutID(){
        return new String[]{
                TEXT_COLUMN
        };
    }
}
