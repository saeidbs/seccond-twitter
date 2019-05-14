package ir.mkp.second_twitter.database.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ir.mkp.second_twitter.database.dataHelper.tables.PostingTable;
import ir.mkp.second_twitter.module.Post;
import ir.mkp.second_twitter.module.User;


public class PostingDAO {

    private final SQLiteDatabase DB;
    private SQLiteStatement insertStatement;

    private static String getInserQuery(){
        StringBuilder questionSymbols = new StringBuilder();
        for (int i = 0; i < PostingTable.getAllColumnsWithoutID().length - 1; i++)
            questionSymbols.append("? ,");
        questionSymbols.append("?");
        return "INSERT INTO " + PostingTable.TABLE_NAME +
                "(" +
                Arrays.toString(PostingTable.getAllColumnsWithoutID()).replace("[", "").replace("]", "") +
                ") " +
                "VALUES (" + questionSymbols.toString() + ")";
    }


    public PostingDAO(SQLiteDatabase DB) {
        this.DB = DB;
        insertStatement = DB.compileStatement(getInserQuery());
    }



    public long add(User user, Post post){
        insertStatement.clearBindings();
        insertStatement.bindLong(1,user.getId());
        insertStatement.bindLong(2,post.getID());
        insertStatement.bindLong(3,System.currentTimeMillis());


        return insertStatement.executeInsert();
    }


    public void delete(User user,Post post){
        DB.delete(PostingTable.TABLE_NAME, PostingTable.USER_ID + " = ? AND " + PostingTable.POST_ID+" = ?", new String[]{Long.toString(user.getId()),Long.toString(post.getID())});
    }

    public void delete(Post post){
        DB.delete(PostingTable.TABLE_NAME,  PostingTable.POST_ID+" = ?", new String[]{Long.toString(post.getID())});
    }
    public void delete(User user){
        DB.delete(PostingTable.TABLE_NAME, PostingTable.USER_ID +" = ?", new String[]{Long.toString(user.getId())});


    }

    public User getUser(Post post) {

        Cursor cursor = null;
        try {
            cursor = DB.query(PostingTable.TABLE_NAME,
                    new String[]{PostingTable.USER_ID},
                    PostingTable.POST_ID + "= ?",
                    new String[]{Long.toString(post.getID())},
                    null,
                    null,
                    null

                    );

            return new User(cursor.getLong(cursor.getColumnIndex(PostingTable.USER_ID)));


        } catch (Exception exp) {
            exp.printStackTrace();
        } finally {
            assert cursor != null;
            cursor.close();
        }
        return null;
    }


    public List<Post> getWhoPosted(User user){
        List<Post> list=new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = DB.query(PostingTable.TABLE_NAME,
                    new String[]{PostingTable.POST_ID},
                    PostingTable.USER_ID + "= ?",
                    new String[]{Long.toString(user.getId())},
                    null,
                    null,
                    null

            );


            //return new User(cursor.getLong(cursor.getColumnIndex(PostingTable.USER_ID)));
            while(cursor.moveToNext()){
                list.add(new Post(cursor.getLong(cursor.getColumnIndex(PostingTable.POST_ID))));

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
