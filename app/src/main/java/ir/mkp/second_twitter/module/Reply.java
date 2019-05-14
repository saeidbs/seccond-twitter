package ir.mkp.second_twitter.module;

import ir.mkp.second_twitter.utility.Values;

public class Reply extends Sender {

    private Comment comment;

    public Reply(Long userID,Long commentID,Long ReplyID){

        ID=ReplyID;
        this.comment=new Comment(commentID);
        this.user=new User(userID);

        // TODO: saeidbahmani 1/23/19 2:23 PM () get text
        this.text = Values.dataManager.getReplyDAO().getText(ID);
    }

    public Reply(Comment comment, User user, String text){
        this.comment= comment;
        this.text = text;
        this.user = user;
    }

    public Comment getComment() {
        return comment;
    }
}
