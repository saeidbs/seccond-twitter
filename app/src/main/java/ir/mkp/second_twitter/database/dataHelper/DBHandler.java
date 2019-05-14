package ir.mkp.second_twitter.database.dataHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ir.mkp.second_twitter.database.dataHelper.tables.BlockTable;
import ir.mkp.second_twitter.database.dataHelper.tables.CommentLikeTable;
import ir.mkp.second_twitter.database.dataHelper.tables.CommentTable;
import ir.mkp.second_twitter.database.dataHelper.tables.CommentingTable;
import ir.mkp.second_twitter.database.dataHelper.tables.FollowingFollowerTable;
import ir.mkp.second_twitter.database.dataHelper.tables.HashtagTable;
import ir.mkp.second_twitter.database.dataHelper.tables.PostLikeTable;
import ir.mkp.second_twitter.database.dataHelper.tables.PostTable;
import ir.mkp.second_twitter.database.dataHelper.tables.PostingTable;
import ir.mkp.second_twitter.database.dataHelper.tables.ReplyLikeTable;
import ir.mkp.second_twitter.database.dataHelper.tables.ReplyTable;
import ir.mkp.second_twitter.database.dataHelper.tables.ReplyingTable;
import ir.mkp.second_twitter.database.dataHelper.tables.UserTable;
import ir.mkp.second_twitter.module.Reply;

public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERTION = 1;
    private static final String DATABASE_NAME = "secondTwitterDatabase";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERTION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(UserTable.getCreateTableStatment());
        sqLiteDatabase.execSQL(PostTable.getCreateTableStatment());
        sqLiteDatabase.execSQL(CommentTable.getCreateTableStatment());
        sqLiteDatabase.execSQL(ReplyingTable.getCreateTableStatment());
        sqLiteDatabase.execSQL(PostingTable.getCreateTableStatment());
        sqLiteDatabase.execSQL(CommentingTable.getCreateTableStatment());
        sqLiteDatabase.execSQL(BlockTable.getCreateTableStatment());
        sqLiteDatabase.execSQL(FollowingFollowerTable.getCreateTableStatment());
        sqLiteDatabase.execSQL(PostLikeTable.getCreateTableStatment());
        sqLiteDatabase.execSQL(ReplyTable.getCreateTableStatment());
        sqLiteDatabase.execSQL(HashtagTable.getCreateTableStatment());
        sqLiteDatabase.execSQL(ReplyLikeTable.getCreateTableStatment());
        sqLiteDatabase.execSQL(CommentLikeTable.getCreateTableStatment());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
