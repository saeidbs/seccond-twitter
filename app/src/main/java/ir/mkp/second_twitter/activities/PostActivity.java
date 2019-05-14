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
import ir.mkp.second_twitter.activities.adapter.recyclerView.CommentAdapter;
import ir.mkp.second_twitter.activities.adapter.recyclerView.HashtagAdapter;
import ir.mkp.second_twitter.module.Comment;
import ir.mkp.second_twitter.module.Post;
import ir.mkp.second_twitter.utility.Values;

public class PostActivity extends BaseActivity {
    private Post post;
    private TextView post_tv;
    private TextView sender;
    private TextView likeCounter;
    private ImageView like;
    private ImageView comment;
    private RecyclerView commentRv;
    private RecyclerView hashtagRV;
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

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        this.post = Values.tempPost;
        post_tv = findViewById(R.id.post_activity_post_text_view);
        sender = findViewById(R.id.post_activity_sender_text_view);
        like = findViewById(R.id.post_activity_like_image_view);
        likeCounter = findViewById(R.id.post_activity_like_count_text_view);
        comment = findViewById(R.id.post_activity_comment_image_view);
        commentRv = findViewById(R.id.post_activity_comment_recycler_view);
        hashtagRV = findViewById(R.id.post_activity_hash_tag_rv);

        post_tv.setText(post.getText());
        sender.setText(post.getUser().getUsername());
        state = State.getState(Values.dataManager.getPostLikeDAO().isLike(Values.loginUser, this.post));
        like.setImageResource(state.getImage());
        likeCounter.setText(Values.dataManager.getPostLikeDAO().likeCounter(post) + "");

        final CommentAdapter adapter = new CommentAdapter();
        List<Comment> comments = Values.dataManager.getCommentingDAO().getComments(this.post);
        commentRv.setLayoutManager(new LinearLayoutManager(this));
        commentRv.setAdapter(adapter);
        adapter.setList(comments);

        HashtagAdapter hashtagAdapter = new HashtagAdapter();
        hashtagAdapter.setList(post.getHashtagList());
        hashtagRV.setAdapter(hashtagAdapter);
        hashtagRV.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (state){
                    case Like:
                        Values.dataManager.getPostLikeDAO().delete(Values.loginUser, post);
                        state = State.UnLike;
                        break;
                    case UnLike:
                        Values.dataManager.getPostLikeDAO().add(Values.loginUser, post);
                        state = State.Like;
                        break;
                }
                like.setImageResource(state.getImage());
                likeCounter.setText(Values.dataManager.getPostLikeDAO().likeCounter(post) + "");
            }
        });

        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Values.tempPost = post;
                Intent intent = new Intent(PostActivity.this, CreateCommentActivity.class);
                startActivity(intent);
            }
        });

        if (post.getUser().getId() == Values.loginUser.getId())
            like.setEnabled(false);
    }
}
