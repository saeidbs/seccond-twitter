package ir.mkp.second_twitter.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import ir.mkp.second_twitter.R;
import ir.mkp.second_twitter.activities.BaseActivity;
import ir.mkp.second_twitter.activities.FollowerListActivity;
import ir.mkp.second_twitter.activities.FollowingListActivity;
import ir.mkp.second_twitter.activities.adapter.recyclerView.PostAdapter;
import ir.mkp.second_twitter.utility.Values;

public class BioFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_bio, container, false);

        TextView postCounter = root.findViewById(R.id.activity_bio_Text_view_post_counter);
        TextView followerCounter = root.findViewById(R.id.activity_bio_Text_view_follower_counter);
        TextView followingCounter = root.findViewById(R.id.activity_bio_Text_view_following_counter);
        LinearLayout followerLayout = root.findViewById(R.id.activity_bio_follower_layout);
        LinearLayout followingLayout = root.findViewById(R.id.activity_bio_following_layout);
        TextView bioGraphy = root.findViewById(R.id.activity_bio_text_view_bio);
        Button followButton = root.findViewById(R.id.activity_bio_button_follow);
        Button blockButton = root.findViewById(R.id.activity_bio_button_block);
        RecyclerView recyclerView = root.findViewById(R.id.activity_bio_recyclerview);
        followButton.setVisibility(View.GONE);
        blockButton.setVisibility(View.GONE);

        PostAdapter adapter = new PostAdapter();
        adapter.setList(Values.dataManager.getPostDAO().getUserPost(Values.loginUser));
        postCounter.setText(adapter.getItemCount() + "");
        followerCounter.setText(Values.dataManager.getFollowingFollowerDAO().followerCounter(Values.loginUser) + "");
        followingCounter.setText(Values.dataManager.getFollowingFollowerDAO().followingCounter(Values.loginUser) + "");
        bioGraphy.setText(Values.loginUser.getBio());
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);

        followerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BioFragment.this.getContext(), FollowerListActivity.class);
                intent.putExtra(BaseActivity.USER_ID, Values.loginUser.getId());
                BioFragment.this.getContext().startActivity(intent);
            }
        });

        followingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BioFragment.this.getContext(), FollowingListActivity.class);
                intent.putExtra(BaseActivity.USER_ID, Values.loginUser.getId());
                BioFragment.this.getContext().startActivity(intent);
            }
        });

        return root;
    }
}