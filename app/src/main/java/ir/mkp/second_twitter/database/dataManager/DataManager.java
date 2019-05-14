package ir.mkp.second_twitter.database.dataManager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import ir.mkp.second_twitter.database.dao.BlockDAO;
import ir.mkp.second_twitter.database.dao.CommentDAO;
import ir.mkp.second_twitter.database.dao.CommentLikeDAO;
import ir.mkp.second_twitter.database.dao.CommentingDAO;
import ir.mkp.second_twitter.database.dao.FollowingFollowerDAO;
import ir.mkp.second_twitter.database.dao.HashtagDAO;
import ir.mkp.second_twitter.database.dao.PostDAO;
import ir.mkp.second_twitter.database.dao.PostLikeDAO;
import ir.mkp.second_twitter.database.dao.PostingDAO;
import ir.mkp.second_twitter.database.dao.ReplyDAO;
import ir.mkp.second_twitter.database.dao.ReplyLikeDAO;
import ir.mkp.second_twitter.database.dao.ReplyingDAO;
import ir.mkp.second_twitter.database.dao.UserDAO;
import ir.mkp.second_twitter.database.dataHelper.DBHandler;

public class DataManager {
    private static DataManager dataManger;

    private UserDAO userDAO;
    private CommentDAO commentDAO;
    private CommentingDAO commentingDAO;
    private PostDAO postDAO;
    private PostingDAO postingDAO;
    private ReplyDAO replyDAO;
    private ReplyingDAO replyingDAO;
    private BlockDAO blockDAO;
    private FollowingFollowerDAO followingFollowerDAO;
    private PostLikeDAO postLikeDAO;
    private HashtagDAO hashtagDAO;
    private ReplyLikeDAO replyLikeDAO;
    private CommentLikeDAO commentLikeDAO;


    private final SQLiteDatabase db;

    private DataManager(Context context) {
        DBHandler openHelper = new DBHandler(context);
        db = openHelper.getWritableDatabase();
        initial();
    }

    private void initial() {
        userDAO = new UserDAO(db);
        commentDAO = new CommentDAO(db);
        commentingDAO = new CommentingDAO(db);
        postDAO = new PostDAO(db);
        postingDAO = new PostingDAO(db);
        replyDAO = new ReplyDAO(db);
        replyingDAO = new ReplyingDAO(db);
        blockDAO = new BlockDAO(db);
        followingFollowerDAO = new FollowingFollowerDAO(db);
        postLikeDAO = new PostLikeDAO(db);
        hashtagDAO = new HashtagDAO(db);
        replyLikeDAO = new ReplyLikeDAO(db);
        commentLikeDAO = new CommentLikeDAO(db);
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public CommentDAO getCommentDAO() {
        return commentDAO;
    }

    public CommentingDAO getCommentingDAO() {
        return commentingDAO;
    }

    public PostDAO getPostDAO() {
        return postDAO;
    }

    public PostingDAO getPostingDAO() {
        return postingDAO;
    }

    public ReplyDAO getReplyDAO() {
        return replyDAO;
    }

    public ReplyingDAO getReplyingDAO() {
        return replyingDAO;
    }

    public BlockDAO getBlockDAO() {
        return blockDAO;
    }

    public FollowingFollowerDAO getFollowingFollowerDAO() {
        return followingFollowerDAO;
    }

    public PostLikeDAO getPostLikeDAO() {
        return postLikeDAO;
    }

    public HashtagDAO getHashtagDAO() {
        return hashtagDAO;
    }

    public ReplyLikeDAO getReplyLikeDAO() {
        return replyLikeDAO;
    }

    public CommentLikeDAO getCommentLikeDAO() {
        return commentLikeDAO;
    }

    public static DataManager createDateManager(Context context) {
        if (dataManger == null)
            dataManger = new DataManager(context);
        return dataManger;
    }

}
