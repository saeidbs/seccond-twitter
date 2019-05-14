package ir.mkp.second_twitter.database.dataHelper.tables;

public class CommentLikeTable implements BaseTable {

    public final static String TABLE_NAME="commentLike";
    public final static String USER_ID="user_id_commentLike";
    public final static String COMMENT_ID="comment_id_commentLike";



    public static String getCreateTableStatment(){
        return "CREATE TABLE " + TABLE_NAME + "("
                + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USER_ID + " INTEGER, "
                + COMMENT_ID+" INTEGER"
                + ")";
    }

    public static String[] getAllColumns(){
        return new String[]{
                ID_COLUMN,
                USER_ID,
                COMMENT_ID
        };
    }

    public static String[] getAllColumnsWithoutID(){
        return new String[]{
                USER_ID,
                COMMENT_ID
        };
    }

}
