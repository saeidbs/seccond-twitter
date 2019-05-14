package ir.mkp.second_twitter.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import ir.mkp.second_twitter.R;
import ir.mkp.second_twitter.activities.adapter.recyclerView.PostAdapter;
import ir.mkp.second_twitter.module.User;
import ir.mkp.second_twitter.utility.Values;

public class BioActivity extends BaseActivity {

    private User user;

    private enum FollowState {
        FOLLOW,
        NOT_FOLLOW;

        public static FollowState getState(boolean isFollow){
            if (isFollow)
                return FOLLOW;
            return NOT_FOLLOW;
        }

        @Override
        public String toString() {
            switch (this){
                case NOT_FOLLOW:
                    return "Follow";
                case FOLLOW:
                    return "UnFollow";
            }
            return super.toString();
        }
    }

    private enum BlockState {
        BLOCK,
        NOT_BLOCK;

        public static BlockState getState(boolean isFollow){
            if (isFollow)
                return BLOCK;
            return NOT_BLOCK;
        }

        @Override
        public String toString() {
            switch (this){
                case NOT_BLOCK:
                    return "Block";
                case BLOCK:
                    return "UnBlock";
            }
            return super.toString();
        }
    }

    private FollowState followState = FollowState.NOT_FOLLOW;
    private BlockState blockState = BlockState.NOT_BLOCK;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_bio);
        TextView postCounter = findViewById(R.id.activity_bio_Text_view_post_counter);
        TextView followerCounter = findViewById(R.id.activity_bio_Text_view_follower_counter);
        TextView followingCounter = findViewById(R.id.activity_bio_Text_view_following_counter);
        LinearLayout followerLayout = findViewById(R.id.activity_bio_follower_layout);
        LinearLayout followingLayout = findViewById(R.id.activity_bio_following_layout);
        TextView bioGraphy = findViewById(R.id.activity_bio_text_view_bio);
        final Button followButton = findViewById(R.id.activity_bio_button_follow);
        final Button blockButton = findViewById(R.id.activity_bio_button_block);
        RecyclerView recyclerView = findViewById(R.id.activity_bio_recyclerview);

        user = new User(getIntent().getLongExtra(USER_ID, 0));
        if (user.getId() == Values.loginUser.getId()) {
            followButton.setVisibility(View.GONE);
            blockButton.setVisibility(View.GONE);
        }

        bioGraphy.setText(user.getBio());

        followState = FollowState.getState(Values.dataManager.getFollowingFollowerDAO().isFollow(Values.loginUser, user));
        followButton.setText(followState.toString());

        blockState = BlockState.getState(Values.dataManager.getBlockDAO().isBlock(Values.loginUser, user));
        blockButton.setText(blockState.toString());

        PostAdapter adapter = new PostAdapter();
        adapter.setList(Values.dataManager.getPostDAO().getUserPost(user));
        postCounter.setText(adapter.getItemCount() + "");
        followerCounter.setText(Values.dataManager.getFollowingFollowerDAO().followerCounter(user) + "");
        followingCounter.setText(Values.dataManager.getFollowingFollowerDAO().followingCounter(user) + "");


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        blockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (blockState){
                    case BLOCK:
                        Values.dataManager.getBlockDAO().unBlock(Values.loginUser, user);
                        blockState = BlockState.NOT_BLOCK;
                        break;
                    case NOT_BLOCK:
                        Values.dataManager.getBlockDAO().block(user, Values.loginUser);
                        blockState = BlockState.BLOCK;
                        break;
                }
                blockButton.setText(blockState.toString());
            }
        });

        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (followState){
                    case FOLLOW:
                        Values.dataManager.getFollowingFollowerDAO().unFollow(Values.loginUser, user);
                        followState = FollowState.NOT_FOLLOW;
                        break;
                    case NOT_FOLLOW:
                        Values.dataManager.getFollowingFollowerDAO().add(user, Values.loginUser);
                        followState = FollowState.FOLLOW;
                        break;
                }
                followButton.setText(followState.toString());

            }
        });

        followerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BioActivity.this, FollowerListActivity.class);
                intent.putExtra(BaseActivity.USER_ID, user.getId());
                startActivity(intent);
            }
        });

        followingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BioActivity.this, FollowingListActivity.class);
                intent.putExtra(BaseActivity.USER_ID, user.getId());
                startActivity(intent);
            }
        });
    }
}
