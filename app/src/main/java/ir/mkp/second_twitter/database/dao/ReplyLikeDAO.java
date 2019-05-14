package ir.mkp.second_twitter.database.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.Arrays;

import ir.mkp.second_twitter.database.dataHelper.tables.ReplyLikeTable;
import ir.mkp.second_twitter.module.Reply;
import ir.mkp.second_twitter.module.User;

public class ReplyLikeDAO {

    private final SQLiteDatabase DB;
    private SQLiteStatement insertStatement;

    public ReplyLikeDAO(SQLiteDatabase db) {
        DB = db;
        insertStatement = DB.compileStatement(getInserQuery());
    }

    private static String getInserQuery(){
        StringBuilder questionSymbols = new StringBuilder();
        for (int i = 0; i < ReplyLikeTable.getAllColumnsWithoutID().length - 1; i++)
            questionSymbols.append("? ,");
        questionSymbols.append("?");
        return "INSERT INTO " + ReplyLikeTable.TABLE_NAME +
                "(" +
                Arrays.toString(ReplyLikeTable.getAllColumnsWithoutID()).replace("[", "").replace("]", "") +
                ") " +
                "VALUES (" + questionSymbols.toString() + ")";
    }


    public long add(User user, Reply reply ){
        insertStatement.clearBindings();
        insertStatement.bindLong(1,user.getId());
        insertStatement.bindLong(2,reply.getID());



        return insertStatement.executeInsert();
    }


    public void delete(User user,Reply reply){
        DB.delete(ReplyLikeTable.TABLE_NAME,  ReplyLikeTable.REPLY_ID+" = ? AND "+ReplyLikeTable.USER_ID+" = ?", new String[]{Long.toString(reply.getID()),Long.toString(user.getId())});
    }

    public void delete(User user){
        DB.delete(ReplyLikeTable.TABLE_NAME,  ReplyLikeTable.USER_ID+" = ?", new String[]{Long.toString(user.getId())});
    }

    public void delete(Reply reply){
        DB.delete(ReplyLikeTable.TABLE_NAME,  ReplyLikeTable.REPLY_ID+" = ?", new String[]{Long.toString(reply.getID())});
    }

    public int likeCounter(Reply reply){
        Cursor cursor = null;
        try {
            cursor = DB.query(ReplyLikeTable.TABLE_NAME,
                    new String[]{ReplyLikeTable.USER_ID},
                    ReplyLikeTable.REPLY_ID + "= ?",
                    new String[]{Long.toString(reply.getID())},
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

    public boolean isLike(User user, Reply reply){
        Cursor cursor = null;
        try {
            cursor = DB.query(ReplyLikeTable.TABLE_NAME,
                    new String[]{ReplyLikeTable.USER_ID},
                    ReplyLikeTable.REPLY_ID + " = ? AND " + ReplyLikeTable.USER_ID + " = ?",
                    new String[]{Long.toString(reply.getID()), Long.toString(user.getId())},
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