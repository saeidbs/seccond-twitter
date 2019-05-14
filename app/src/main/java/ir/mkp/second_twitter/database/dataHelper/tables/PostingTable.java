package ir.mkp.second_twitter.database.dataHelper.tables;

public class PostingTable implements BaseTable {

    public static final String TABLE_NAME = "posting";
    public static final String USER_ID="user_id_posting";
    public static final String POST_ID="post_id_posting";
    public static final String TIME="time";


    public static String getCreateTableStatment(){
        return "CREATE TABLE " + TABLE_NAME + "("
                + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + USER_ID +" INTEGER ,"
                + POST_ID +" INTEGER ,"
                + TIME+" INTEGER, "
                +   "UNIQUE(" + USER_ID+","+POST_ID + ")"
                + ")";
    }




    public static String[] getAllColumns(){
        return new String[]{
                ID_COLUMN,
                USER_ID,
                POST_ID,
                TIME
        };
    }
    public static String[] getAllColumnsWithoutID(){
        return new String[]{
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
