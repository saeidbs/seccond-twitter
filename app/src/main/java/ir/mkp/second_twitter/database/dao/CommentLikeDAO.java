package ir.mkp.second_twitter.database.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.Arrays;

import ir.mkp.second_twitter.database.dataHelper.tables.CommentLikeTable;
import ir.mkp.second_twitter.module.Comment;
import ir.mkp.second_twitter.module.User;

public class CommentLikeDAO {



    private final SQLiteDatabase DB;
    private SQLiteStatement insertStatement;

    public CommentLikeDAO(SQLiteDatabase db) {
        DB = db;
        insertStatement = DB.compileStatement(getInserQuery());
    }

    private static String getInserQuery(){
        StringBuilder questionSymbols = new StringBuilder();
        for (int i = 0; i < CommentLikeTable.getAllColumnsWithoutID().length - 1; i++)
            questionSymbols.append("? ,");
        questionSymbols.append("?");
        return "INSERT INTO " + CommentLikeTable.TABLE_NAME +
                "(" +
                Arrays.toString(CommentLikeTable.getAllColumnsWithoutID()).replace("[", "").replace("]", "") +
                ") " +
                "VALUES (" + questionSymbols.toString() + ")";
    }


    public long add(User user, Comment comment ){
        insertStatement.clearBindings();
        insertStatement.bindLong(1,user.getId());
        insertStatement.bindLong(2,comment.getID());



        return insertStatement.executeInsert();
    }


    public void delete(User user,Comment comment){
        DB.delete(CommentLikeTable.TABLE_NAME,  CommentLikeTable.COMMENT_ID+" = ? AND "+CommentLikeTable.USER_ID+" = ?", new String[]{Long.toString(comment.getID()),Long.toString(user.getId())});
    }

    public void delete(User user){
        DB.delete(CommentLikeTable.TABLE_NAME,  CommentLikeTable.USER_ID+" = ?", new String[]{Long.toString(user.getId())});
    }

    public void delete(Comment comment){
        DB.delete(CommentLikeTable.TABLE_NAME,  CommentLikeTable.COMMENT_ID+" = ?", new String[]{Long.toString(comment.getID())});
    }

    public int likeCounter(Comment comment){
        Cursor cursor = null;
        try {
            cursor = DB.query(CommentLikeTable.TABLE_NAME,
                    new String[]{CommentLikeTable.USER_ID},
                    CommentLikeTable.COMMENT_ID + "= ?",
                    new String[]{Long.toString(comment.getID())},
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

    public boolean isLike(User user, Comment comment){
        Cursor cursor = null;
        try {
            cursor = DB.query(CommentLikeTable.TABLE_NAME,
                    new String[]{CommentLikeTable.USER_ID},
                    CommentLikeTable.COMMENT_ID + " = ? AND " + CommentLikeTable.USER_ID + " = ?",
                    new String[]{Long.toString(comment.getID()), Long.toString(user.getId())},
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
