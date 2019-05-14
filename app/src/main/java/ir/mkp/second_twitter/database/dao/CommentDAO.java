package ir.mkp.second_twitter.database.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.Arrays;

import ir.mkp.second_twitter.database.dataHelper.tables.CommentTable;
import ir.mkp.second_twitter.database.dataHelper.tables.CommentingTable;
import ir.mkp.second_twitter.module.Comment;
import ir.mkp.second_twitter.utility.Values;

public class CommentDAO {
    private final SQLiteDatabase DB;
    private SQLiteStatement insertStatement;

    public CommentDAO(SQLiteDatabase db) {
        DB = db;
        insertStatement = DB.compileStatement(getInserQuery());
    }

    private static String getInserQuery(){
        StringBuilder questionSymbols = new StringBuilder();
        for (int i = 0; i < CommentTable.getAllColumnsWithoutID().length - 1; i++)
            questionSymbols.append("? ,");
        questionSymbols.append("?");
        return "INSERT INTO " + CommentTable.TABLE_NAME +
                "(" +
                Arrays.toString(CommentTable.getAllColumnsWithoutID()).replace("[", "").replace("]", "") +
                ") " +
                "VALUES (" + questionSymbols.toString() + ")";
    }

    public long add(Comment comment){
        insertStatement.clearBindings();
        insertStatement.bindString(1,comment.getText());

        comment.setID(insertStatement.executeInsert());
        Values.dataManager.getCommentingDAO().add(comment.getUser(), comment.getPost(), comment);

        return comment.getID();
    }

    public void delete(Comment comment){
        DB.delete(CommentTable.TABLE_NAME, CommentTable.ID_COLUMN + " = ?" , new String[]{Long.toString(comment.getID())});
        // TODO Masoud 1/23/2019: (DB) delete in post relation
    }

    public String getText(long id){
        Cursor cursor = null;
        try{
            cursor = DB.query(CommentTable.TABLE_NAME,
                    new String[]{CommentTable.TEXT_COLUMN},
                    CommentTable.ID_COLUMN + " = ?",
                    new String[]{Long.toString(id)},
                    null,
                    null,
                    null);
            if (cursor.moveToFirst()){
                return cursor.getString(cursor.getColumnIndex(CommentTable.TEXT_COLUMN));
            }
        }catch (Exception exp){
            exp.printStackTrace();
        }finally {
            assert cursor != null;
            cursor.close();
        }

        return null;
    }

    public Comment getComment(long id){
        Cursor cursor = null;
        try {
            cursor = DB.query(CommentingTable.TABLE_NAME,
                    new String[]{CommentingTable.USER_ID, CommentingTable.POST_ID},
                    CommentingTable.ID_COLUMN + " = ?",
                    new String[]{Long.toString(id)},
                    null,
                    null,
                    CommentingTable.TIME + " DESC"
            );
            if (cursor.moveToFirst()){
                return new Comment(id,
                        cursor.getLong(cursor.getColumnIndex(CommentingTable.POST_ID)),
                        cursor.getLong(cursor.getColumnIndex(CommentingTable.USER_ID)));
            }
        } catch (Exception exp) {
            exp.printStackTrace();
        } finally {
            assert cursor != null;
            cursor.close();
        }
        return null;
    }

}
