package ir.mkp.second_twitter.database.dataHelper.tables;

public class PostLikeTable implements BaseTable{
    public final static String TABLE_NAME="postLike";
    public final static String USER_ID="user_id_postLike";
    public final static String POST_ID="post_id_postLike";



    public static String getCreateTableStatment(){
        return "CREATE TABLE " + TABLE_NAME + "("
                + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USER_ID + " INTEGER, "
                + POST_ID+" INTEGER"
                + ")";
    }

    public static String[] getAllColumns(){
        return new String[]{
                ID_COLUMN,
                USER_ID,
                POST_ID
        };
    }

    public static String[] getAllColumnsWithoutID(){
        return new String[]{
                USER_ID,
                POST_ID
        };
    }





}
