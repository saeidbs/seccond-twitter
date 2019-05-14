package ir.mkp.second_twitter.database.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ir.mkp.second_twitter.database.dataHelper.tables.HashtagTable;
import ir.mkp.second_twitter.database.dataHelper.tables.PostTable;
import ir.mkp.second_twitter.database.dataHelper.tables.PostingTable;
import ir.mkp.second_twitter.module.Post;
import ir.mkp.second_twitter.module.User;

public class HashtagDAO {

    private final SQLiteDatabase DB;
    private SQLiteStatement insertStatement;

    public HashtagDAO(SQLiteDatabase DB) {
        this.DB = DB;
        insertStatement = DB.compileStatement(getInserQuery());
    }

    private static String getInserQuery() {
        StringBuilder questionSymbols = new StringBuilder();
        for (int i = 0; i < HashtagTable.getAllColumnsWithoutID().length - 1; i++)
            questionSymbols.append("? ,");
        questionSymbols.append("?");
        return "INSERT INTO " + HashtagTable.TABLE_NAME +
                "(" +
                Arrays.toString(HashtagTable.getAllColumnsWithoutID()).replace("[", "").replace("]", "") +
                ") " +
                "VALUES (" + questionSymbols.toString() + ")";
    }


    public long add(Post post, String string) {
        insertStatement.clearBindings();
        insertStatement.bindLong(1, post.getID());
        insertStatement.bindString(2, string);

        return insertStatement.executeInsert();
    }

    public void delete(Post post) {

        DB.delete(HashtagTable.TABLE_NAME, HashtagTable.POST_ID + " = ?", new String[]{Long.toString(post.getID())});

    }

    public List<String> getHashtags(Post post) {
        List<String> list = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = DB.query(HashtagTable.TABLE_NAME,
                    new String[]{HashtagTable.TEXT},
                    HashtagTable.POST_ID + "= ?",
                    new String[]{Long.toString(post.getID())},
                    null,
                    null,
                    null

            );

            while (cursor.moveToNext()) {
                list.add(cursor.getString(cursor.getColumnIndex(HashtagTable.TEXT)));

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

    public List<Post> getPosts(String string) {
        List<Post> list = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = DB.query(HashtagTable.TABLE_NAME + ", " + PostTable.TABLE_NAME + " ," + PostingTable.TABLE_NAME,
                    new String[]{PostTable.TABLE_NAME + "." + PostTable.ID_COLUMN},
                    HashtagTable.POST_ID + " = "+ PostTable.TABLE_NAME + "." + PostTable.ID_COLUMN +" AND " + PostingTable.POST_ID + " = " + PostTable.TABLE_NAME + "." + PostTable.ID_COLUMN +" AND " + HashtagTable.TEXT + " LIKE \"" + string + "\"",
                    null,
                    null,
                    null,
                    null

            );

            while (cursor.moveToNext()) {
                list.add(new Post(cursor.getLong(cursor.getColumnIndex(PostTable.TABLE_NAME + "." + PostTable.ID_COLUMN))));


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

    public List<String> search(String hashtag) {
        Cursor cursor = null;
        List<String> result = new ArrayList<>();
        try {
            cursor = DB.query(HashtagTable.TABLE_NAME,
                    new String[]{HashtagTable.TEXT},
                    HashtagTable.TEXT + " LIKE \"%" + hashtag + "%\"",
                    null,
                    null,
                    null,
                    null);
            while (cursor.moveToNext()) {
                result.add(cursor.getString(cursor.getColumnIndex(HashtagTable.TEXT)));
            }
        } catch (Exception exp) {
            exp.printStackTrace();
        } finally {
            assert cursor != null;
            cursor.close();
        }
        return result;
    }


}
