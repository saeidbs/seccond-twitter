package ir.mkp.second_twitter.database.dataHelper.tables;

public class FollowingFollowerTable implements BaseTable {



    public static  final String TABLE_NAME="following_follower";
    public static final String FOLLOWING="following";
    public static final String FOLLOWER="follower";

    public static String getCreateTableStatment(){
        return "CREATE TABLE " + TABLE_NAME + "("
                + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +FOLLOWING+" INTEGER, "
                + FOLLOWER + " INTEGER "
                + ")";
    }

    public static String[] getAllColumns(){
        return new String[]{
                ID_COLUMN,
                FOLLOWING,
                FOLLOWER
        };
    }

    public static String[] getAllColumnsWithoutID(){
        return new String[]{
                FOLLOWING,
                FOLLOWER
        };
    }
}
