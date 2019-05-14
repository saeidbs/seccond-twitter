package ir.mkp.second_twitter.database.dataHelper.tables;

public class ReplyingTable implements BaseTable {


    public static final String TABLE_NAME = "replying";
    public static final String REPLY_ID ="replay_id";
    public static final String COMMENT_ID="comment_id";
    public static final String USER_ID="user_id";
    public static final String TIME="time";

    public static String getCreateTableStatment(){
        return "CREATE TABLE " + TABLE_NAME + "("
                + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + REPLY_ID + " INTEGER ,"
                + USER_ID +" INTEGER ,"
                +COMMENT_ID+ " INTEGER,"
                + TIME+" INTEGER, "
                +   "UNIQUE(" + USER_ID+","+ REPLY_ID + ")"
                + ")";
    }



    public static String[] getAllColumns(){
        return new String[]{
                ID_COLUMN,
                REPLY_ID,
                USER_ID,
                COMMENT_ID,
                TIME
        };
    }

    public static String[] getAllColumnsWithoutID(){
        return new String[]{
                REPLY_ID,
                USER_ID,
                COMMENT_ID,
                TIME
        };
    }


    public static boolean isValidColumnName(String columnName){
        for (String column:getAllColumns()){
            if (column.equalsIgnoreCase(columnName))
                return true;
        }
        return false;
    }

}
