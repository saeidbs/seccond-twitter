package ir.mkp.second_twitter.database.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ir.mkp.second_twitter.database.dataHelper.tables.FollowingFollowerTable;
import ir.mkp.second_twitter.database.dataHelper.tables.PostLikeTable;
import ir.mkp.second_twitter.database.dataHelper.tables.PostTable;
import ir.mkp.second_twitter.database.dataHelper.tables.PostingTable;
import ir.mkp.second_twitter.database.dataHelper.tables.UserTable;
import ir.mkp.second_twitter.module.Post;
import ir.mkp.second_twitter.module.User;
import ir.mkp.second_twitter.utility.Values;

public class PostDAO {
    private final SQLiteDatabase DB;
    private SQLiteStatement insertStatement;

    public PostDAO(SQLiteDatabase db) {
        DB = db;
        insertStatement = DB.compileStatement(getInserQuery());
    }

    private static String getInserQuery(){
        StringBuilder questionSymbols = new StringBuilder();
        for (int i = 0; i < PostTable.getAllColumnsWithoutID().length - 1; i++)
            questionSymbols.append("? ,");
        questionSymbols.append("?");
        return "INSERT INTO " + PostTable.TABLE_NAME +
                "(" +
                Arrays.toString(PostTable.getAllColumnsWithoutID()).replace("[", "").replace("]", "") +
                ") " +
                "VALUES (" + questionSymbols.toString() + ")";
    }

    public long add(Post post){
        insertStatement.clearBindings();
        insertStatement.bindString(1,post.getText());

        post.setID(insertStatement.executeInsert());

        Values.dataManager.getPostingDAO().add(post.getUser(), post);

        for (String hashTag : post.getHashtagList())
            Values.dataManager.getHashtagDAO().add(post, hashTag);


        Cursor cursor = null;
        try{
            cursor = DB.query(UserTable.TABLE_NAME+" AS User"+" , "+PostingTable.TABLE_NAME,
                    new String[]{PostingTable.TIME,"User."+UserTable.REGISTER_TIME_COLUMN,"User."+UserTable.ACTIVE_TYPE_COLUMN},
                    "User."+UserTable.ID_COLUMN +" = "+ PostingTable.USER_ID+" AND User."+UserTable.ID_COLUMN+" = "+Long.toString(post.getUser().getId()),
                    null,
                    null,
                    null,
                    PostingTable.TIME+" DESC","2");
            long lastPostTime = 0;
            while (cursor.moveToNext()){
                long posTtime=cursor.getLong(cursor.getColumnIndex(PostingTable.TIME));
                long registeryTime=cursor.getLong(cursor.getColumnIndex(UserTable.REGISTER_TIME_COLUMN));
                int type=cursor.getInt(cursor.getColumnIndex(UserTable.ACTIVE_TYPE_COLUMN));

                if(type==UserTable.ActiveType.UNKNOWN.getcode()){
                    if(((posTtime-registeryTime)/86400000)<=1){
                        Values.dataManager.getUserDAO().updateActiveType(new User(post.getUser().getId()),UserTable.ActiveType.HOT);
                    }
                    else {
                        Values.dataManager.getUserDAO().updateActiveType(new User(post.getUser().getId()),UserTable.ActiveType.NORMAL);
                    }
                }
                if(type==UserTable.ActiveType.NORMAL.getcode()){
                   break;
                }
                if(type==UserTable.ActiveType.HOT.getcode()){
                    if(lastPostTime==0) {
                        lastPostTime = posTtime;
                    }
                    else if (lastPostTime!=0) {
                        if (((posTtime - lastPostTime) / 86400000) <= 1){

                            Values.dataManager.getUserDAO().updateActiveType(new User(post.getUser().getId()),UserTable.ActiveType.HOT);
                        }
                        else {
                            Values.dataManager.getUserDAO().updateActiveType(new User(post.getUser().getId()),UserTable.ActiveType.NORMAL);
                        }
                    }
                }





            }

        }catch (Exception exp){
            exp.printStackTrace();
        }finally {
            assert cursor != null;
            cursor.close();
        }



        return post.getID();
    }

    public void delete(Post post){
        DB.delete(PostTable.TABLE_NAME, PostTable.ID_COLUMN + " = ?" , new String[]{Long.toString(post.getID())});
        // TODO Masoud 1/23/2019: (DB) delete in post relation
    }

    public Post getPost(Long ID){

        Cursor cursor = null;
        try {
            cursor = DB.query(PostTable.TABLE_NAME+" , "+PostingTable.TABLE_NAME,
                    new String[]{PostTable.TEXT_COLUMN,PostingTable.USER_ID},
                    PostTable.TABLE_NAME + "." + PostTable.ID_COLUMN + " = " + Long.toString(ID) + " AND "
                            +PostTable.TABLE_NAME + "." + PostTable.ID_COLUMN+" = " + PostingTable.TABLE_NAME+ "." + PostingTable.POST_ID,
                    null,
                    null,
                    null,
                    null

            );

           if(cursor.moveToFirst()){
            return new Post(new User(cursor.getLong(cursor.getColumnIndex(PostingTable.USER_ID))),
                    cursor.getString(cursor.getColumnIndex(PostTable.TEXT_COLUMN)));
            }


        } catch (Exception exp) {
            exp.printStackTrace();
        } finally {
            assert cursor != null;
            cursor.close();
        }
        return null;
    }
    public List<Post> get100PostsFromUserFollowing(Long FollowerID){
            List<Post> list=new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = DB.query(PostTable.TABLE_NAME+" , "+PostingTable.TABLE_NAME+ " , "+FollowingFollowerTable.TABLE_NAME,
                    new String[]{PostTable.TABLE_NAME+"."+PostTable.ID_COLUMN},
                    FollowingFollowerTable.FOLLOWER+ " = " + Long.toString(FollowerID) + " AND "
                            + FollowingFollowerTable.FOLLOWING+" = " + PostingTable.USER_ID +" AND "
                            + PostTable.TABLE_NAME+"."+PostTable.ID_COLUMN + " = " + PostingTable.POST_ID,
                    null,
                    null,
                    null,
                    PostingTable.TIME+" DESC",
                    "100"
            );


            //return new User(cursor.getLong(cursor.getColumnIndex(PostingTable.USER_ID)));
            while (cursor.moveToNext()){
                list.add(new Post(cursor.getLong(cursor.getColumnIndex(PostTable.ID_COLUMN))));
            }


        } catch (Exception exp) {
            exp.printStackTrace();
        } finally {
            assert cursor != null;
            cursor.close();
        }
        return list;
    }


    public List<Post> getHotPostsForExplorer(){
        List<Post> list=new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = DB.query(PostTable.TABLE_NAME+" , "+PostLikeTable.TABLE_NAME,
                    new String[]{PostTable.TABLE_NAME+"."+PostTable.ID_COLUMN,"COUNT("+PostLikeTable.USER_ID+") AS A"},
                    PostTable.TABLE_NAME+"."+PostTable.ID_COLUMN+" = "+PostLikeTable.POST_ID,
                null,
                    PostTable.TABLE_NAME+"."+PostTable.ID_COLUMN,
                    null,
                    "A"+" DESC",
                    "100"

            );


            //return new User(cursor.getLong(cursor.getColumnIndex(PostingTable.USER_ID)));
            while (cursor.moveToNext()){

                list.add(new Post(cursor.getLong(cursor.getColumnIndex(PostTable.ID_COLUMN))));
            }


        } catch (Exception exp) {
            exp.printStackTrace();
        } finally {
            assert cursor != null;
            cursor.close();
        }
        return list;
    }




    public List<Post> getUserPost(User user){
        Cursor cursor = null;
        List<Post> result = new ArrayList<>();
        try{
            cursor = DB.query(PostTable.TABLE_NAME + " , " + PostingTable.TABLE_NAME,
                    new String[]{PostTable.TABLE_NAME + "." +PostTable.ID_COLUMN},
                    PostingTable.POST_ID + " = " + PostTable.TABLE_NAME + "." +PostTable.ID_COLUMN + " AND " + PostingTable.USER_ID + " = ?",
                    new String[]{Long.toString(user.getId())},
                    null,
                    null,
                    PostingTable.TIME + " DESC");
            while (cursor.moveToNext()){
                long id = cursor.getLong(cursor.getColumnIndex(PostTable.ID_COLUMN));
                result.add(new Post(id));
            }
        }catch (Exception exp){
            exp.printStackTrace();
        }finally {
            assert cursor != null;
            cursor.close();
        }
        return result;
    }

    public List<Post> getUserPost(long userID){
        Cursor cursor = null;
        List<Post> result = new ArrayList<>();
        try{
            cursor = DB.query(PostTable.TABLE_NAME + " , " + PostingTable.TABLE_NAME,
                    new String[]{PostingTable.TIME, PostTable.TEXT_COLUMN},
                    PostingTable.POST_ID + " = " + PostTable.TABLE_NAME + "." +PostTable.ID_COLUMN + " AND " + PostingTable.USER_ID + " = ?",
                    new String[]{Long.toString(userID)},
                    null,
                    null,
                    PostingTable.TIME + " DESC");
            while (cursor.moveToNext()){
                String text = cursor.getString(cursor.getColumnIndex(PostTable.TEXT_COLUMN));
                result.add(new Post(new User(userID), text));
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
