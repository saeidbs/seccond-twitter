package ir.mkp.second_twitter.database.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.Arrays;

import ir.mkp.second_twitter.database.dataHelper.tables.BlockTable;
import ir.mkp.second_twitter.module.User;

public class BlockDAO {
    private final SQLiteDatabase DB;
    private SQLiteStatement insertStatement;

    public BlockDAO(SQLiteDatabase DB) {
        this.DB = DB;
        insertStatement = DB.compileStatement(getInserQuery());
    }

    private static String getInserQuery(){
        StringBuilder questionSymbols = new StringBuilder();
        for (int i = 0; i < BlockTable.getAllColumnsWithoutID().length - 1; i++)
            questionSymbols.append("? ,");
        questionSymbols.append("?");
        return "INSERT INTO " + BlockTable.TABLE_NAME +
                "(" +
                Arrays.toString(BlockTable.getAllColumnsWithoutID()).replace("[", "").replace("]", "") +
                ") " +
                "VALUES (" + questionSymbols.toString() + ")";
    }


    public long block(User blocking, User blocker ){
        insertStatement.clearBindings();
        insertStatement.bindLong(1,blocking.getId());
        insertStatement.bindLong(2,blocker.getId());
        return insertStatement.executeInsert();
    }

    public void unBlock(User blocker, User blocking){
        DB.delete(BlockTable.TABLE_NAME,  BlockTable.BLOCKING+" = ? AND "+BlockTable.BLOCKER+" = ?", new String[]{Long.toString(blocking.getId()),Long.toString(blocker.getId())});
    }

    public void deleteUser(User user){
        DB.delete(BlockTable.TABLE_NAME,  BlockTable.BLOCKING+" = ? OR "+BlockTable.BLOCKER+" = ?", new String[]{Long.toString(user.getId()),Long.toString(user.getId())});
    }


    public boolean isBlock(User main, User user){
        Cursor cursor = null;
        try {
            cursor = DB.query(BlockTable.TABLE_NAME,
                    BlockTable.getAllColumns(),
                    BlockTable.BLOCKER + " = " + main.getId() + " AND " + BlockTable.BLOCKING + " = " + user.getId(),
                    null,
                    null,
                    null,
                    null);
            return cursor.getCount() > 0;
        }catch (Exception exp){
            exp.printStackTrace();
        }finally {
            assert cursor != null;
            cursor.close();
        }
        return false;
    }

    public boolean isBlockByDirection(User user, User user1){
        Cursor cursor = null;
        try {
            cursor = DB.query(BlockTable.TABLE_NAME,
                    BlockTable.getAllColumns(),
                    "("+ BlockTable.BLOCKER + " = " + user1.getId() + " AND " + BlockTable.BLOCKING + " = " + user.getId() + ") OR (" +
                            BlockTable.BLOCKER + " = " + user.getId() + " AND " + BlockTable.BLOCKING + " = " + user1.getId() + ")",
                    null,
                    null,
                    null,
                    null);
            return cursor.getCount() > 0;
        }catch (Exception exp){
            exp.printStackTrace();
        }finally {
            assert cursor != null;
            cursor.close();
        }
        return false;

    }


}
