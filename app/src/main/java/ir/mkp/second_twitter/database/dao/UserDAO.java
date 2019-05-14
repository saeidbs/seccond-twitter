package ir.mkp.second_twitter.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ir.mkp.second_twitter.database.dataHelper.tables.PostingTable;
import ir.mkp.second_twitter.database.dataHelper.tables.UserTable;
import ir.mkp.second_twitter.module.User;
import ir.mkp.second_twitter.utility.Values;

public class UserDAO {
    private final SQLiteDatabase DB;
    private SQLiteStatement insertStatement;

    private static String getInserQuery() {
        StringBuilder questionSymbols = new StringBuilder();
        for (int i = 0; i < UserTable.getAllColumnsWithoutID().length - 1; i++)
            questionSymbols.append("? ,");
        questionSymbols.append("?");
        return "INSERT INTO " + UserTable.TABLE_NAME +
                "(" +
                Arrays.toString(UserTable.getAllColumnsWithoutID()).replace("[", "").replace("]", "") +
                ") " +
                "VALUES (" + questionSymbols.toString() + ")";
    }

    public UserDAO(SQLiteDatabase db) {
        DB = db;
        insertStatement = DB.compileStatement(getInserQuery());
    }

    //    EMAIL_COLUMN,
//    ROLE_COLUMN,
//    USERNAME_COLUMN,
//    REGISTER_TIME_COLUMN,
//    BIO_COLUMN,
//    PASSWORD_COLUMN
    public long add(User user) {
        insertStatement.clearBindings();
        insertStatement.bindString(1, user.getEmail());
        insertStatement.bindString(2, user.getRole().toString());
        insertStatement.bindString(3, user.getUsername());
        insertStatement.bindLong(4, System.currentTimeMillis());
        insertStatement.bindString(5, user.getBio());
        insertStatement.bindString(6, user.getPassword());
        insertStatement.bindString(7, user.getSecureQuestion());
        insertStatement.bindString(8, user.getSecureAnswer());
        user.setId(insertStatement.executeInsert());

        return user.getId();
    }

    public void delete(User user) {
        DB.delete(UserTable.TABLE_NAME, UserTable.ID_COLUMN + " = ?", new String[]{Long.toString(user.getId())});
        // TODO Masoud 1/23/2019: (DB) delete all relations
    }

    public int update(User user) {
        final ContentValues contentValues = new ContentValues();

        contentValues.put(UserTable.BIO_COLUMN, user.getBio());
        contentValues.put(UserTable.EMAIL_COLUMN, user.getEmail());
        contentValues.put(UserTable.USERNAME_COLUMN, user.getUsername());
        contentValues.put(UserTable.PASSWORD_COLUMN, user.getPassword());

        try {

            return DB.update(UserTable.TABLE_NAME,
                    contentValues,
                    UserTable.ID_COLUMN + " = ?",
                    new String[]{user.getId() + ""});
        } catch (Exception e) {
            return 0;
        }
    }

    public void updateActiveType(User user, UserTable.ActiveType activeType){
        final ContentValues contentValues = new ContentValues();

        contentValues.put(UserTable.ACTIVE_TYPE_COLUMN, activeType.getcode());
        try {

            DB.update(UserTable.TABLE_NAME,
                    contentValues,
                    UserTable.ID_COLUMN + " = ?",
                    new String[]{user.getId() + ""});
        } catch (Exception e) {

        }

    }

    public List<User> search(String selection, String[] selectionArg, String groupBy, String having, String order) {
        List<User> result = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = DB.query(UserTable.TABLE_NAME,
                    UserTable.getAllColumns(),
                    selection,
                    selectionArg,
                    groupBy,
                    having,
                    order);
            while (cursor.moveToNext()) {
                User item = bindWhitCursor(cursor);
                if (item != null) {
                    result.add(item);
                }
            }
            return result;
        } catch (Exception exp) {
            exp.printStackTrace();
        } finally {
            assert cursor != null;
            cursor.close();
        }
        return null;
    }

    public User bindWhitCursor(Cursor cursor) {
        User result = null;
        if (cursor != null) {
            long id = cursor.getLong(cursor.getColumnIndex(UserTable.ID_COLUMN));
            String email = cursor.getString(cursor.getColumnIndex(UserTable.EMAIL_COLUMN));
            String role = cursor.getString(cursor.getColumnIndex(UserTable.ROLE_COLUMN));
            String username = cursor.getString(cursor.getColumnIndex(UserTable.USERNAME_COLUMN));
            long register_time = cursor.getLong(cursor.getColumnIndex(UserTable.REGISTER_TIME_COLUMN));
            String bio = cursor.getString(cursor.getColumnIndex(UserTable.BIO_COLUMN));
            String password = cursor.getString(cursor.getColumnIndex(UserTable.PASSWORD_COLUMN));
            String secureQustion = cursor.getString(cursor.getColumnIndex(UserTable.SECURE_QUESTION_COLUMN));
            String secureAnswer = cursor.getString(cursor.getColumnIndex(UserTable.SECURE_QUESTION_COLUMN));

            result = new User(id, email, username, password, User.Role.getRole(role), bio, secureQustion, secureAnswer);
        }
        return result;
    }

    public User getUserById(long id) {
        return search(UserTable.ID_COLUMN + " = ?", new String[]{Long.toString(id)}, null, null, null).get(0);
    }

    public boolean haveEmail(String email) {
        Cursor cursor = null;
        try {
            cursor = DB.query(UserTable.TABLE_NAME,
                    UserTable.getAllColumns(),
                    UserTable.EMAIL_COLUMN + " = ?",
                    new String[]{email},
                    null,
                    null,
                    null);
            return cursor.getCount() > 0;
        } catch (Exception exp) {
            exp.printStackTrace();
        } finally {
            assert cursor != null;
            cursor.close();
        }
        return false;
    }

    public boolean haveUsername(String username) {
        Cursor cursor = null;
        try {
            cursor = DB.query(UserTable.TABLE_NAME,
                    UserTable.getAllColumns(),
                    UserTable.USERNAME_COLUMN + " = ?",
                    new String[]{username},
                    null,
                    null,
                    null);
            return cursor.getCount() > 0;
        } catch (Exception exp) {
            exp.printStackTrace();
        } finally {
            assert cursor != null;
            cursor.close();
        }
        return false;
    }

    public User login(String email, String pass) {
        List<User> result = search(UserTable.EMAIL_COLUMN + " = ? AND " + UserTable.PASSWORD_COLUMN + " = ?",
                new String[]{email, pass},
                null,
                null,
                null
        );
        if (result.size() > 0)
            return result.get(0);
        return null;
    }

    public List<User> searchByUsername(String username) {
        return search(UserTable.USERNAME_COLUMN + " LIKE \"%" + username + "%\" AND " + UserTable.ID_COLUMN + " <> " + Values.loginUser.getId(), null, null, null, UserTable.USERNAME_COLUMN);
    }


    public List<User> detectCheaters() {
        List<User> list = new ArrayList<>();

        Cursor cursor = null;
        try {
//            cursor = DB.query(UserTable.TABLE_NAME + " AS User " + " , " + PostingTable.TABLE_NAME
//
//                    , new String[]{"User._id", "COUNT(" + PostingTable.POST_ID + ")"}
//                    , "User._id" + " = " + PostingTable.USER_ID + " OR " + "User._id" + " NOT IN (" + "SELECT p.user_id_posting from posting AS p)"
//                    , null
//                    , "User._id"
//
//
//                    , "((COUNT(" + PostingTable.POST_ID + ")" + "/" + "((" + "1548339202350" + " - User.register_time_user" + ")/" + "(86400000)))<0.2) OR " + "COUNT(" + PostingTable.POST_ID + ") = 4"
//                            + " AND " + "EXISTS(" + " SELECT " + PostingTable.USER_ID + " From " + PostingTable.TABLE_NAME + " , " + PostLikeTable.TABLE_NAME
//                            + " WHERE " + PostingTable.POST_ID + " = " + PostLikeTable.POST_ID
//                            + " AND " + PostLikeTable.USER_ID + " = " + "User._id"
//                            + " GROUP BY " + PostingTable.USER_ID
//                            + " Having " + "COUNT(" + PostLikeTable.USER_ID + ")/2 < " + "COUNT(" + PostingTable.POST_ID + ")"
//
//
//                            + ")"
//                    , null
//
//            );

            String query="select user._id\n" +
                    "from user, posting\n" +
                    "where user._id = user_id_posting\n" +
                    "group by user._id\n" +
                    "having\n" +
                    "  (count(post_id_posting)  / (("+System.currentTimeMillis()+" - user.register_time_user) / 86400000)) < 0.2\n" +
                    "  and\n" +
                    "  exists(select user_id_posting from posting, postlike\n" +
                    "  where post_id_posting = post_id_postlike and user_id_postlike = user._id\n" +
                    "  group by user_id_posting\n" +
                    "  having (count(user_id_postlike) / 2) < post_id_posting)\n" +
                    "union\n" +
                    "select user._id\n" +
                    "from user, posting\n" +
                    "where user._id not in (select user_id_posting from posting)\n" +
                    "group by user._id\n" +
                    "having exists(select user_id_posting from posting, postlike\n" +
                    "  where post_id_posting = post_id_postlike and user_id_postlike = user._id\n" +
                    "  group by user_id_posting\n" +
                    "  having (count(user_id_postlike) / 2) < post_id_posting)";

            cursor=DB.rawQuery(query,null);

            while (cursor.moveToNext()) {
                list.add(new User(cursor.getLong(cursor.getColumnIndex(UserTable.ID_COLUMN))));
            }


        } catch (Exception exp) {
            exp.printStackTrace();
        } finally {
            assert cursor != null;
            cursor.close();
        }


        return list;
    }

    public List<User> getActiveUsers(){
        List<User> list=new ArrayList<>();

        Cursor cursor = null;
        try{
            cursor = DB.query(UserTable.TABLE_NAME +" AS User , "+PostingTable.TABLE_NAME,
                    new String[]{" User."+UserTable.ID_COLUMN,UserTable.ACTIVE_TYPE_COLUMN,PostingTable.TIME},
                    " User."+UserTable.ACTIVE_TYPE_COLUMN +" = "+Long.toString(UserTable.ActiveType.HOT.getcode())+" AND "+PostingTable.USER_ID+" = "+"User."+UserTable.ID_COLUMN,
                    null,
                    "User."+UserTable.ID_COLUMN,
                    null,
                    UserTable.REGISTER_TIME_COLUMN);
            while (cursor.moveToNext()){

                int type=cursor.getInt(cursor.getColumnIndex(UserTable.ACTIVE_TYPE_COLUMN));
                long posTtime=cursor.getLong(cursor.getColumnIndex(PostingTable.TIME));
                if(type==UserTable.ActiveType.HOT.getcode()){
                    if(((System.currentTimeMillis()-posTtime)/86400000)>=1){
                       updateActiveType(new User(cursor.getLong(cursor.getColumnIndex(UserTable.ID_COLUMN))),UserTable.ActiveType.NORMAL);

                    }
                    else {
                        list.add(new User(cursor.getLong(cursor.getColumnIndex(UserTable.ID_COLUMN))));
                    }

                }

            }
        } catch (Exception exp) {
            exp.printStackTrace();
        } finally {
            assert cursor != null;
            cursor.close();
        }

        return list;
    }

    public List<User> getHotUsers(){
        List<User> list=new ArrayList<>();

        Cursor cursor = null;
        try{
//            cursor = DB.query(UserTable.TABLE_NAME+" AS User , "+ReplyingTable.TABLE_NAME+" AS Reply , "+CommentingTable.TABLE_NAME+" AS Comment , "+PostTable.TABLE_NAME+" AS Post",
//                    new String[]{"DISTINCT User."+UserTable.ID_COLUMN ," Post."+PostTable.ID_COLUMN + " AS post_column"," COUNT(Post."+PostTable.ID_COLUMN+") AS tedad"},
//                    " User."+UserTable.ID_COLUMN +" = Reply."+ReplyingTable.USER_ID+" OR User."+UserTable.ID_COLUMN+" = Comment."+CommentingTable.USER_ID+" AND Reply."+ReplyingTable.COMMENT_ID+" = Comment."+CommentingTable.COMMENT_ID+" AND Comment."+CommentingTable.POST_ID+" = post_column " ,
//                    null,
//                    "post_column",
//                    "(COUNT(Reply."+ReplyingTable.COMMENT_ID+") + "+" COUNT(Comment."+CommentingTable.POST_ID+"))>= 10",
//                   null);
//            cursor = DB.query(UserTable.TABLE_NAME+" AS User , "+ReplyingTable.TABLE_NAME+" AS Reply , "+CommentingTable.TABLE_NAME+" AS Comment , "+PostTable.TABLE_NAME+" AS Post",
//                    new String[]{"User."+UserTable.ID_COLUMN ," Post."+PostTable.ID_COLUMN+" AS postidcolumn" ," COUNT(Post."+PostTable.ID_COLUMN+") AS tedad"},
//                    " User."+UserTable.ID_COLUMN +" = Reply."+ReplyingTable.USER_ID+" OR User."+UserTable.ID_COLUMN+" = Comment."+CommentingTable.USER_ID+" AND Reply."+ReplyingTable.COMMENT_ID+" = Comment."+CommentingTable.COMMENT_ID+" AND Comment."+CommentingTable.POST_ID+" = postidcolumn" ,
//                    null,
//                    "postidcolumn",
//                    "(COUNT(Reply."+ReplyingTable.COMMENT_ID+") + "+" COUNT(Comment."+CommentingTable.POST_ID+"))>= 10",
//                    null);

            String query = "select distinct a\n " +
            "from \n" +
                    "  (select commenting.user_id as a, commenting.post_id, count(replying.replay_id) as rc\n" +
                    "  from  replying, commenting\n" +
                    "  where replying.comment_id = commenting.comment_id\n" +
                    "  group by a, commenting.post_id) as t1\n" +
                    "  , \n" +
                    "  (select commenting.user_id as b, commenting.post_id, count(commenting.comment_id) as cc\n" +
                    "  from commenting\n" +
                    "  group by b, commenting.post_id)\n" +
                    "where a = b and (rc + cc) >= 10\n" +
                    "group by t1.post_id\n" +
                    "having count(t1.post_id) >= 2";

            cursor = DB.rawQuery(query, null);

            while (cursor.moveToNext()){
                list.add(new User(cursor.getLong(cursor.getColumnIndex("a"))));

            }


        }catch (Exception exp){
            exp.printStackTrace();
        }finally {
            assert cursor != null;
            cursor.close();
        }

        return list;
    }

    public String getQuestion(String email) {
        Cursor cursor = null;
        try {
            cursor = DB.query(UserTable.TABLE_NAME,
                    new String[]{UserTable.SECURE_QUESTION_COLUMN},
                    UserTable.EMAIL_COLUMN + " LIKE \"" + email + "\"",
                    null,
                    null,
                    null,
                    null);

            if (cursor.moveToFirst())
                return cursor.getString(cursor.getColumnIndexOrThrow(UserTable.SECURE_QUESTION_COLUMN));
        } catch (Exception exp) {
            exp.printStackTrace();
        } finally {
            assert cursor != null;
            cursor.close();
        }
        return "";
    }


    public boolean checkAnswer(String email, String answer) {
        Cursor cursor = null;
        try {
            cursor = DB.query(UserTable.TABLE_NAME,
                    new String[]{UserTable.SECURE_QUESTION_COLUMN},
                    UserTable.EMAIL_COLUMN + " LIKE \"" + email + "\" AND " + UserTable.SECURE_ANSWER_COLUMN + " LIKE \"" + answer + "\"",
                    null,
                    null,
                    null,
                    null);

            return cursor.getCount() > 0;
        } catch (Exception exp) {
            exp.printStackTrace();
        } finally {
            assert cursor != null;
            cursor.close();
        }
        return false;

    }

    public void changePass(String email, String pass) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserTable.PASSWORD_COLUMN, pass);
        DB.update(UserTable.TABLE_NAME, contentValues, UserTable.EMAIL_COLUMN + " LIKE \"" + email + "\"", null);
    }
}
