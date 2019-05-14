package ir.mkp.second_twitter.module;

import ir.mkp.second_twitter.utility.Values;

public class Comment extends Sender {

    private Post post;

    public Comment(Long commentID){
        this.ID = commentID;

    }

    public Comment(Long commentID,Long postID,Long userID){

        ID=commentID;
        this.post = new Post(postID);
        this.user = new User(userID);
        text=Values.dataManager.getCommentDAO().getText(commentID);
    }

    public Comment(String text, Long postID, Long userID){
        this.text = text;
        this.post = new Post(postID);
        this.user = new User(userID);
    }

    public Post getPost() {
        return post;
    }
}
