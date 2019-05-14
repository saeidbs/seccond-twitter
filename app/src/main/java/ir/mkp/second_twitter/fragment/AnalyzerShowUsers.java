package ir.mkp.second_twitter.fragment;

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

import java.util.List;

import ir.mkp.second_twitter.R;
import ir.mkp.second_twitter.activities.adapter.recyclerView.UserAdapter;
import ir.mkp.second_twitter.module.User;
import ir.mkp.second_twitter.utility.Values;

public class AnalyzerShowUsers extends Fragment {



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_analyzer_show_users, container, false);
        final Button followerFollowing=root.findViewById(R.id.fragment_analyzer_button_follower_following);
        Button userFromRegister=root.findViewById(R.id.fragment_analyzer_button_user_from_register);
        final RecyclerView recyclerView=root.findViewById(R.id.fragment_analyzer_recyclerview);
        final UserAdapter followerFollwingAdapter=new UserAdapter();


        // List<User> list=new ArrayList<>();
//        test.block("ffasafsafs");
//        test.block("asfasfakcdasknjvaknjdsvjnknjksdvjknasdkjnvjknsdavkjnadsf");
//        test.block("dsk;f;kadmlkfadlkmdfalmkads;ffn3jq w;efqwfc");
//        HashtagAdapter adapter=new HashtagAdapter();
//        adapter.setList(test);
        followerFollowing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            List<User>list=Values.dataManager.getFollowingFollowerDAO().getUsersFollowFollowBack();
                followerFollwingAdapter.setList(list);

            }
        });

        userFromRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<User>list=Values.dataManager.getUserDAO().getActiveUsers();
                followerFollwingAdapter.setList(list);
            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(followerFollwingAdapter);


        return root;
    }
}
