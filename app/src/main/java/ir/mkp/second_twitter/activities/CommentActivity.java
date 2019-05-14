package ir.mkp.second_twitter.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ir.mkp.second_twitter.R;
import ir.mkp.second_twitter.activities.adapter.recyclerView.ReplyAdapter;
import ir.mkp.second_twitter.module.Comment;
import ir.mkp.second_twitter.module.Reply;
import ir.mkp.second_twitter.utility.Values;

public class CommentActivity extends BaseActivity {
    private Comment comment;
    private TextView post_tv;
    private TextView sender;
    private TextView replyLabel;
    private TextView likeCounter;
    private ImageView like;
    private ImageView replyImg;
    private RecyclerView rv;
    private State state;

    enum State{
        Like,
        UnLike;

        public static State getState(boolean isLike){
            if (isLike)
                return Like;
            return UnLike;
        }

        public int getImage(){
            if (this == Like)
                return R.drawable.like_fill_red_24dp;
            return R.drawable.like_stork_gray_24dp;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        this.comment = Values.tempComment;
        post_tv = findViewById(R.id.post_activity_post_text_view);
        sender = findViewById(R.id.post_activity_sender_text_view);
        like = findViewById(R.id.post_activity_like_image_view);
        likeCounter = findViewById(R.id.post_activity_like_count_text_view);
        replyImg = findViewById(R.id.post_activity_comment_image_view);
        rv = findViewById(R.id.post_activity_comment_recycler_view);
        replyLabel = findViewById(R.id.post_activity_label);
        replyLabel.setText("Replies");

        post_tv.setText(comment.getText());
        sender.setText(comment.getUser().getUsername());

        // TODO Masoud 1/24/2019: () fix it
//        state = State.getState(Values.dataManager.getPostLikeDAO().isLike(Values.loginUser, this.post));
//        like.setImageResource(state.getImage());
//        likeCounter.setText(Values.dataManager.getPostLikeDAO().likeCounter(post) + "");

        List<Reply> replies = Values.dataManager.getReplyingDAO().getReplays(comment);
        ReplyAdapter adapter = new ReplyAdapter();
        adapter.setList(replies);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

        replyImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommentActivity.this, CreateReplyActivity.class);
                startActivity(intent);
            }
        });

        likeCounter.setText(Values.dataManager.getCommentLikeDAO().likeCounter(comment) + "");
        state = State.getState(Values.dataManager.getCommentLikeDAO().isLike(Values.loginUser, comment));
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (state){
                    case Like:
                        Values.dataManager.getCommentLikeDAO().delete(Values.loginUser, comment);
                        state = State.UnLike;
                        break;
                    case UnLike:
                        Values.dataManager.getCommentLikeDAO().add(Values.loginUser, comment);
                        state = State.Like;
                        break;
                }
                like.setImageResource(state.getImage());
                likeCounter.setText(Values.dataManager.getCommentLikeDAO().likeCounter(comment) + "");
            }
        });
        if (Values.dataManager.getBlockDAO().isBlockByDirection(Values.loginUser, Values.tempComment.getUser())){
            like.setEnabled(false);
        }
    }
}
