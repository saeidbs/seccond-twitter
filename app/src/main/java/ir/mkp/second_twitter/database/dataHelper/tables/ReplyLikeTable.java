package ir.mkp.second_twitter.database.dataHelper.tables;

public class ReplyLikeTable implements BaseTable {

    public final static String TABLE_NAME="replyLike";
    public final static String USER_ID="user_id_replyLike";
    public final static String REPLY_ID="reply_id_replyLike";



    public static String getCreateTableStatment(){
        return "CREATE TABLE " + TABLE_NAME + "("
                + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USER_ID + " INTEGER, "
                + REPLY_ID+" INTEGER"
                + ")";
    }

    public static String[] getAllColumns(){
        return new String[]{
                ID_COLUMN,
                USER_ID,
                REPLY_ID
        };
    }

    public static String[] getAllColumnsWithoutID(){
        return new String[]{
                USER_ID,
                REPLY_ID
        };
    }

}

