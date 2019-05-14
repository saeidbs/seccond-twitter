package ir.mkp.second_twitter.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import ir.mkp.second_twitter.R;
import ir.mkp.second_twitter.activities.adapter.recyclerView.UserAdapter;
import ir.mkp.second_twitter.module.User;
import ir.mkp.second_twitter.utility.Values;

public class FollowerListActivity extends BaseActivity {
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follower_following);
        initial();
        setAction();
    }

    private void initial(){
        recyclerView = findViewById(R.id.follower_following_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        UserAdapter adapter = new UserAdapter();
        adapter.setList(Values.dataManager.getFollowingFollowerDAO().getFollowers(new User(getIntent().getLongExtra(USER_ID,0))));
        recyclerView.setAdapter(adapter);
    }

    private void setAction(){

    }
}
