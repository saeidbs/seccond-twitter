package ir.mkp.second_twitter.database.dataHelper.tables;

public class UserTable implements BaseTable {
    public static final String TABLE_NAME = "user";
    public static final String EMAIL_COLUMN = "email_user";
    public static final String ROLE_COLUMN = "role_user";
    public static final String USERNAME_COLUMN = "username_user";
    public static final String REGISTER_TIME_COLUMN = "register_time_user";
    public static final String BIO_COLUMN = "bio_user";
    public static final String SECURE_QUESTION_COLUMN = "secure_question_column";
    public static final String SECURE_ANSWER_COLUMN = "secure_answer_column";
    public static final String PASSWORD_COLUMN = "password_user";
    public static final String ACTIVE_TYPE_COLUMN ="active_type";

    public static enum ActiveType {
        NORMAL,UNKNOWN,HOT;
        private static final int normal_code = 0;
        private static  final int unknown_code = -1;
        private static  final int hot_code = 1;
        public int getcode(){
            switch (this){
                case HOT:return hot_code;
                case NORMAL:return normal_code;
                case UNKNOWN:return unknown_code;
                default:return unknown_code;
            }
        }
        public static ActiveType getType(int code){
            switch (code){
                case normal_code:return NORMAL;
                case unknown_code:return UNKNOWN;
                case hot_code:return HOT;
                default:return UNKNOWN;

            }
        }
    }

    public static String getCreateTableStatment(){
        return "CREATE TABLE " + TABLE_NAME + "("
                + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + EMAIL_COLUMN + " TEXT, "
                + ROLE_COLUMN + " TEXT, "
                + USERNAME_COLUMN + " TEXT, "
                + REGISTER_TIME_COLUMN + " INTEGER, "
                + BIO_COLUMN + " TEXT, "
                + PASSWORD_COLUMN + " TEXT, "
                + SECURE_QUESTION_COLUMN + " TEXT, "
                + SECURE_ANSWER_COLUMN + " TEXT, "
                + ACTIVE_TYPE_COLUMN + " INTEGER DEFAULT -1, "
                + "UNIQUE(" + EMAIL_COLUMN + ")"
                + ")";
    }

    public static String[] getAllColumns(){
        return new String[]{
                ID_COLUMN,
                EMAIL_COLUMN,
                ROLE_COLUMN,
                USERNAME_COLUMN,
                REGISTER_TIME_COLUMN,
                BIO_COLUMN,
                PASSWORD_COLUMN,
                SECURE_QUESTION_COLUMN,
                SECURE_ANSWER_COLUMN
        };
    }

    public static String[] getAllColumnsWithoutID(){
        return new String[]{
                EMAIL_COLUMN,
                ROLE_COLUMN,
                USERNAME_COLUMN,
                REGISTER_TIME_COLUMN,
                BIO_COLUMN,
                PASSWORD_COLUMN,
                SECURE_QUESTION_COLUMN,
                SECURE_ANSWER_COLUMN
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
