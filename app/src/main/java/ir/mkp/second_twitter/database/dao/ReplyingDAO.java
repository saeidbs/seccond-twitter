package ir.mkp.second_twitter.database.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ir.mkp.second_twitter.database.dataHelper.tables.ReplyingTable;
import ir.mkp.second_twitter.module.Comment;
import ir.mkp.second_twitter.module.Post;
import ir.mkp.second_twitter.module.Reply;
import ir.mkp.second_twitter.module.User;

public class ReplyingDAO {
    private final SQLiteDatabase DB;
    private SQLiteStatement insertStatement;

    private static String getInserQuery(){
        StringBuilder questionSymbols = new StringBuilder();
        for (int i = 0; i < ReplyingTable.getAllColumnsWithoutID().length - 1; i++)
            questionSymbols.append("? ,");
        questionSymbols.append("?");
        return "INSERT INTO " + ReplyingTable.TABLE_NAME +
                "(" +
                Arrays.toString(ReplyingTable.getAllColumnsWithoutID()).replace("[", "").replace("]", "") +
                ") " +
                "VALUES (" + questionSymbols.toString() + ")";
    }


    public ReplyingDAO(SQLiteDatabase DB) {
        this.DB = DB;
        insertStatement = DB.compileStatement(getInserQuery());
    }


    public long add(User user ,Comment comment, Reply reply){
        insertStatement.clearBindings();
        insertStatement.bindLong(1,reply.getID());
        insertStatement.bindLong(2,user.getId());
        insertStatement.bindLong(3,comment.getID());
        insertStatement.bindLong(4,System.currentTimeMillis());


        return insertStatement.executeInsert();
    }





    public void delete(User user){
        DB.delete(ReplyingTable.TABLE_NAME,  ReplyingTable.USER_ID+" = ?", new String[]{Long.toString(user.getId())});
    }
    public void delete(Comment comment){
        DB.delete(ReplyingTable.TABLE_NAME,  ReplyingTable.COMMENT_ID+" = ?", new String[]{Long.toString(comment.getID())});
    }
    public void delete(Reply reply){
        DB.delete(ReplyingTable.TABLE_NAME,  ReplyingTable.COMMENT_ID+" = ?", new String[]{Long.toString(reply.getID())});
    }

    public List<Reply> getReplays(Comment comment) {
        List<Reply>list= new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = DB.query(ReplyingTable.TABLE_NAME,
                    new String[]{ReplyingTable.COMMENT_ID,ReplyingTable.USER_ID,ReplyingTable.REPLY_ID},
                    ReplyingTable.COMMENT_ID + "= ?",
                    new String[]{Long.toString(comment.getID())},
                    null,
                    null,
                    ReplyingTable.TIME + " DESC"
            );

            while(cursor.moveToNext()){
                list.add(new Reply(cursor.getLong(cursor.getColumnIndex(ReplyingTable.USER_ID)),comment.getID(),cursor.getLong(cursor.getColumnIndex(ReplyingTable.REPLY_ID))));

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
