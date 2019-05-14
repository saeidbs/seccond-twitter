package ir.mkp.second_twitter.database.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ir.mkp.second_twitter.database.dataHelper.tables.CommentingTable;
import ir.mkp.second_twitter.module.Comment;
import ir.mkp.second_twitter.module.Post;
import ir.mkp.second_twitter.module.User;


public class CommentingDAO  {

    private final SQLiteDatabase DB;
    private SQLiteStatement insertStatement;

    private static String getInserQuery(){
        StringBuilder questionSymbols = new StringBuilder();
        for (int i = 0; i < CommentingTable.getAllColumnsWithoutID().length - 1; i++)
            questionSymbols.append("? ,");
        questionSymbols.append("?");
        return "INSERT INTO " + CommentingTable.TABLE_NAME +
                "(" +
                Arrays.toString(CommentingTable.getAllColumnsWithoutID()).replace("[", "").replace("]", "") +
                ") " +
                "VALUES (" + questionSymbols.toString() + ")";
    }


    public CommentingDAO(SQLiteDatabase DB) {
        this.DB = DB;
        insertStatement = DB.compileStatement(getInserQuery());
    }


    public long add(User user, Post post, Comment comment){
        insertStatement.clearBindings();
        insertStatement.bindLong(1,comment.getID());
        insertStatement.bindLong(2,user.getId());
        insertStatement.bindLong(3,post.getID());
        insertStatement.bindLong(4,System.currentTimeMillis());


        return insertStatement.executeInsert();
    }




    public void delete(User user,Post post,Comment comment){
        DB.delete(CommentingTable.TABLE_NAME, CommentingTable.USER_ID + " = ? AND " + CommentingTable.POST_ID+" = ? AND "+CommentingTable.COMMENT_ID, new String[]{Long.toString(user.getId()),Long.toString(post.getID()),Long.toString(comment.getID())});
    }

    public void delete(Post post){
        DB.delete(CommentingTable.TABLE_NAME,  CommentingTable.POST_ID+" = ?", new String[]{Long.toString(post.getID())});
    }

    public void delete(User user){
        DB.delete(CommentingTable.TABLE_NAME,  CommentingTable.USER_ID+" = ?", new String[]{Long.toString(user.getId())});
    }
    public void delete(Comment comment){
        DB.delete(CommentingTable.TABLE_NAME,  CommentingTable.COMMENT_ID+" = ?", new String[]{Long.toString(comment.getID())});
    }

    public List<Comment> getComments(Post post) {
            List<Comment>list= new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = DB.query(CommentingTable.TABLE_NAME,
                    new String[]{CommentingTable.COMMENT_ID,CommentingTable.USER_ID},
                    CommentingTable.POST_ID + "= ?",
                    new String[]{Long.toString(post.getID())},
                    null,
                    null,
                    CommentingTable.TIME + " DESC"

            );

            while(cursor.moveToNext()){
                list.add(new Comment(cursor.getLong(cursor.getColumnIndex(CommentingTable.COMMENT_ID)),post.getID(),cursor.getLong(cursor.getColumnIndex(CommentingTable.USER_ID))));

            }
            return list;

        } catch (Exception exp) {
            exp.printStackTrace();
        } finally {
            assert cursor != null;
            cursor.close();
        }
        return null;
    }
}
