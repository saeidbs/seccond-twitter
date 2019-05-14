package ir.mkp.second_twitter.database.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.Arrays;

import ir.mkp.second_twitter.database.dataHelper.tables.ReplyTable;
import ir.mkp.second_twitter.database.dataManager.DataManager;
import ir.mkp.second_twitter.module.Reply;
import ir.mkp.second_twitter.utility.Values;


public class ReplyDAO {
    private final SQLiteDatabase DB;
    private SQLiteStatement insertStatement;

    public ReplyDAO(SQLiteDatabase db) {
        DB = db;
        insertStatement = DB.compileStatement(getInserQuery());
    }

    private static String getInserQuery(){
        StringBuilder questionSymbols = new StringBuilder();
        for (int i = 0; i < ReplyTable.getAllColumnsWithoutID().length - 1; i++)
            questionSymbols.append("? ,");
        questionSymbols.append("?");
        return "INSERT INTO " + ReplyTable.TABLE_NAME +
                "(" +
                Arrays.toString(ReplyTable.getAllColumnsWithoutID()).replace("[", "").replace("]", "") +
                ") " +
                "VALUES (" + questionSymbols.toString() + ")";
    }

    public long add(Reply reply){
        insertStatement.clearBindings();
        insertStatement.bindString(1,reply.getText());

        reply.setID(insertStatement.executeInsert());
        Values.dataManager.getReplyingDAO().add(reply.getUser(), reply.getComment(), reply);

        return reply.getID();
    }

    public void delete(Reply reply){
        DB.delete(ReplyTable.TABLE_NAME, ReplyTable.ID_COLUMN + " = ?" , new String[]{Long.toString(reply.getID())});
        // TODO Masoud 1/23/2019: (DB) delete in post relation
    }

    public String getText(long id){
        Cursor cursor = null;
        try{
            cursor = DB.query(ReplyTable.TABLE_NAME,
                    ReplyTable.getAllColumns(),
                    ReplyTable.ID_COLUMN + " = ?",
                    new String[]{Long.toString(id)},
                    null,
                    null,
                    null);
            if (cursor.moveToFirst()){
                return cursor.getString(cursor.getColumnIndex(ReplyTable.TEXT_COLUMN));
            }
        }catch (Exception exp){
            exp.printStackTrace();
        }finally {
            assert cursor != null;
            cursor.close();
        }
        return null;
    }

}
