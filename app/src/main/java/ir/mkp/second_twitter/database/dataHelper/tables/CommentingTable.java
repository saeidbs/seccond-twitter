package ir.mkp.second_twitter.database.dataHelper.tables;

public class CommentingTable implements BaseTable {
    public static final String TABLE_NAME = "commenting";
    public static final String COMMENT_ID="comment_id";
    public static final String USER_ID="user_id";
    public static final String POST_ID="post_id";
    public static final String TIME="time";

    public static String getCreateTableStatment(){
        return "CREATE TABLE " + TABLE_NAME + "("
                + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                +COMMENT_ID + " INTEGER ,"
                + USER_ID +" INTEGER ,"
                + POST_ID +" INTEGER ,"
                + TIME+" INTEGER, "
                +   "UNIQUE(" + USER_ID+","+POST_ID +","+COMMENT_ID+ ")"
                + ")";
    }



    public static String[] getAllColumns(){
        return new String[]{
                ID_COLUMN,
                COMMENT_ID,
                USER_ID,
                POST_ID,
                TIME
        };
    }

    public static String[] getAllColumnsWithoutID(){
        return new String[]{
                COMMENT_ID,
                USER_ID,
                POST_ID,
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
