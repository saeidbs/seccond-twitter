package ir.mkp.second_twitter.database.dataHelper.tables;

public class HashtagTable implements BaseTable {


    public static  final String TABLE_NAME="hashtag_table";
    public static final String POST_ID="post_id";
    public static final String TEXT="text_hashtag";

    public static String getCreateTableStatment(){
        return "CREATE TABLE " + TABLE_NAME + "("
                + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +POST_ID+" INTEGER, "
                + TEXT + " TEXT ,"
                +   "UNIQUE(" + ID_COLUMN+","+POST_ID + ")"
                + ")";
    }

    public static String[] getAllColumns(){
        return new String[]{
                ID_COLUMN,
                POST_ID,
                TEXT
        };
    }

    public static String[] getAllColumnsWithoutID(){
        return new String[]{
                POST_ID,
                TEXT
        };
    }


}
