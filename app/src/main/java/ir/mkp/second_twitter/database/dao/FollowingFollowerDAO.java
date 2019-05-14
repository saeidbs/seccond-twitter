package ir.mkp.second_twitter.database.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ir.mkp.second_twitter.database.dataHelper.tables.FollowingFollowerTable;
import ir.mkp.second_twitter.database.dataHelper.tables.UserTable;
import ir.mkp.second_twitter.module.User;
import ir.mkp.second_twitter.utility.Values;

public class FollowingFollowerDAO  {
    private final SQLiteDatabase DB;
    private SQLiteStatement insertStatement;

    public FollowingFollowerDAO(SQLiteDatabase DB) {
        this.DB = DB;
        insertStatement = DB.compileStatement(getInserQuery());
    }

    private static String getInserQuery(){
        StringBuilder questionSymbols = new StringBuilder();
        for (int i = 0; i < FollowingFollowerTable.getAllColumnsWithoutID().length - 1; i++)
            questionSymbols.append("? ,");
        questionSymbols.append("?");
        return "INSERT INTO " + FollowingFollowerTable.TABLE_NAME +
                "(" +
                Arrays.toString(FollowingFollowerTable.getAllColumnsWithoutID()).replace("[", "").replace("]", "") +
                ") " +
                "VALUES (" + questionSymbols.toString() + ")";
    }


    public long add(User following, User follower ){
        insertStatement.clearBindings();
        insertStatement.bindLong(1,following.getId());
        insertStatement.bindLong(2,follower.getId());



        return insertStatement.executeInsert();
    }




    public void deleteUser(User user){

        DB.delete(FollowingFollowerTable.TABLE_NAME,  FollowingFollowerTable.FOLLOWING+" = ? OR "+FollowingFollowerTable.FOLLOWER+" = ?", new String[]{Long.toString(user.getId()),Long.toString(user.getId())});


    }

    public void unFollow(User follower, User following){
        DB.delete(FollowingFollowerTable.TABLE_NAME,
                FollowingFollowerTable.FOLLOWER + " = ? AND " + FollowingFollowerTable.FOLLOWING + " = ?",
                new String[]{Long.toString(follower.getId()), Long.toString(following.getId())});
    }


    public int followingCounter(User user){
        Cursor cursor = null;
        try {
            cursor = DB.query(FollowingFollowerTable.TABLE_NAME,
                    FollowingFollowerTable.getAllColumns(),
                    FollowingFollowerTable.FOLLOWER + "= ?",
                    new String[]{Long.toString(user.getId())},
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

    public int followerCounter(User user){
        Cursor cursor = null;
        try {
            cursor = DB.query(FollowingFollowerTable.TABLE_NAME,
                    FollowingFollowerTable.getAllColumns(),
                    FollowingFollowerTable.FOLLOWING + "= ?",
                    new String[]{Long.toString(user.getId())},
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
    public List<User> getUsersFollowFollowBack() {
        List<User> list = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = DB.query(FollowingFollowerTable.TABLE_NAME + " As A ," + FollowingFollowerTable.TABLE_NAME + " As B ",
                    new String[]{"DISTINCT A." + FollowingFollowerTable.FOLLOWER},
                    "A." + FollowingFollowerTable.FOLLOWING + " = " + "B." + FollowingFollowerTable.FOLLOWER
                            + " AND " + "B." + FollowingFollowerTable.FOLLOWING + " = " + "A." + FollowingFollowerTable.FOLLOWER+" AND "+"A."+FollowingFollowerTable.FOLLOWER+ " <> " + Values.loginUser.getId()
                    ,
                    null,
                    null,
                    null,
                    null

            );


            while (cursor.moveToNext()) {
                list.add(new User(cursor.getLong(cursor.getColumnIndex(FollowingFollowerTable.FOLLOWER))));

            }



        } catch (Exception exp) {
            exp.printStackTrace();
        } finally {
            assert cursor != null;
            cursor.close();
        }
        return list;
    }
//    public List<User> getUsersFollowFollowBack(){
//        Cursor cursor=null;
//        try {
//            cursor = DB.query(FollowingFollowerTable.TABLE_NAME,
//                    new String[]{FollowingFollowerTable.FOLLOWER},
//                    FollowingFollowerTable.FOLLOWING + " IN ("
//                            +" Select " +"F."+FollowingFollowerTable.FOLLOWER
//                            +" From " + FollowingFollowerTable.TABLE_NAME +"As F"
//                            +" Where "+" F."+FollowingFollowerTable.FOLLOWING+"="+FollowingFollowerTable.FOLLOWER
//                            +""
//
//
//                            +")",
//                    null,
//                    null,
//                    null,
//                    null
//
//            );
//
//
//            return cursor.getCount();
//
//
//        } catch (Exception exp) {
//            exp.printStackTrace();
//        } finally {
//            assert cursor != null;
//            cursor.close();
//        }






    public boolean isFollow(User follower, User following){
        Cursor cursor = null;
        try{
            cursor = DB.query(FollowingFollowerTable.TABLE_NAME,
                    FollowingFollowerTable.getAllColumns(),
                    FollowingFollowerTable.FOLLOWER + " = ? AND " + FollowingFollowerTable.FOLLOWING + " = ?",
                    new String[]{Long.toString(follower.getId()), Long.toString(following.getId())},
                    null,
                    null,
                    null);
            return cursor.getCount() > 0;
        }catch (Exception exp) {
            exp.printStackTrace();
        } finally {
            assert cursor != null;
            cursor.close();
        }
        return false;
    }

    public List<User> getFollowers (User user){
        Cursor cursor = null;
        List<User> result = new ArrayList<>();

        try{
            cursor = DB.query(FollowingFollowerTable.TABLE_NAME,
                    FollowingFollowerTable.getAllColumns(),
                    FollowingFollowerTable.FOLLOWING + " = ?",
                    new String[]{Long.toString(user.getId())},
                    null,
                    null,
                    null);
            while (cursor.moveToNext()){
                result.add(new User(cursor.getLong(cursor.getColumnIndex(FollowingFollowerTable.FOLLOWER))));
            }
        }catch (Exception exp){
            exp.printStackTrace();
        }finally {
            assert cursor != null;
            cursor.close();
        }
        return result;
    }

    public List<User> getFollowings (User user){
        Cursor cursor = null;
        List<User> result = new ArrayList<>();

        try{
            cursor = DB.query(FollowingFollowerTable.TABLE_NAME,
                    FollowingFollowerTable.getAllColumns(),
                    FollowingFollowerTable.FOLLOWER + " = ?",
                    new String[]{Long.toString(user.getId())},
                    null,
                    null,
                    null);
            while (cursor.moveToNext()){
                result.add(new User(cursor.getLong(cursor.getColumnIndex(FollowingFollowerTable.FOLLOWING))));
            }
        }catch (Exception exp){
            exp.printStackTrace();
        }finally {
            assert cursor != null;
            cursor.close();
        }
        return result;
    }

}

