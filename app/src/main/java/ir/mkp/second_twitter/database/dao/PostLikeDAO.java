package ir.mkp.second_twitter.database.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.Arrays;

import ir.mkp.second_twitter.database.dataHelper.tables.PostLikeTable;
import ir.mkp.second_twitter.module.Post;
import ir.mkp.second_twitter.module.User;

public class PostLikeDAO {

    private final SQLiteDatabase DB;
    private SQLiteStatement insertStatement;

    public PostLikeDAO(SQLiteDatabase db) {
        DB = db;
        insertStatement = DB.compileStatement(getInserQuery());
    }

    private static String getInserQuery(){
        StringBuilder questionSymbols = new StringBuilder();
        for (int i = 0; i < PostLikeTable.getAllColumnsWithoutID().length - 1; i++)
            questionSymbols.append("? ,");
        questionSymbols.append("?");
        return "INSERT INTO " + PostLikeTable.TABLE_NAME +
                "(" +
                Arrays.toString(PostLikeTable.getAllColumnsWithoutID()).replace("[", "").replace("]", "") +
                ") " +
                "VALUES (" + questionSymbols.toString() + ")";
    }


    public long add(User user, Post post ){
        insertStatement.clearBindings();
        insertStatement.bindLong(1,user.getId());
        insertStatement.bindLong(2,post.getID());



        return insertStatement.executeInsert();
    }


    public void delete(User user,Post post){
        DB.delete(PostLikeTable.TABLE_NAME,  PostLikeTable.POST_ID+" = ? AND "+PostLikeTable.USER_ID+" = ?", new String[]{Long.toString(post.getID()),Long.toString(user.getId())});
    }

    public void delete(User user){
        DB.delete(PostLikeTable.TABLE_NAME,  PostLikeTable.USER_ID+" = ?", new String[]{Long.toString(user.getId())});
    }

    public void delete(Post post){
        DB.delete(PostLikeTable.TABLE_NAME,  PostLikeTable.POST_ID+" = ?", new String[]{Long.toString(post.getID())});
    }

    public int likeCounter(Post post){
        Cursor cursor = null;
        try {
            cursor = DB.query(PostLikeTable.TABLE_NAME,
                    new String[]{PostLikeTable.USER_ID},
                    PostLikeTable.POST_ID + "= ?",
                    new String[]{Long.toString(post.getID())},
                    null,
                    null,
                 null

            );


            return cursor.getCount();


        } catch (Exception exp) {
            exp.printStackTrace();
        } finally {
            assert cursor != null;
            cursor.close();
        }
        return 0;

    }

    public boolean isLike(User user, Post post){
        Cursor cursor = null;
        try {
            cursor = DB.query(PostLikeTable.TABLE_NAME,
                    new String[]{PostLikeTable.USER_ID},
                    PostLikeTable.POST_ID + " = ? AND " + PostLikeTable.USER_ID + " = ?",
                    new String[]{Long.toString(post.getID()), Long.toString(user.getId())},
                    null,
                    null,
                 null
            );
            return cursor.getCount() > 0;
        } catch (Exception exp) {
            exp.printStackTrace();
        } finally {
            assert cursor != null;
            cursor.close();
        }
        return false;
    }

}
