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

import ir.mkp.second_twitter.R;
import ir.mkp.second_twitter.activities.adapter.recyclerView.PostAdapter;
import ir.mkp.second_twitter.utility.Values;

public class FollowingPostFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.following_post_fragment, container, false);
        RecyclerView rv = root.findViewById(R.id.following_post_rv);

        PostAdapter adapter = new PostAdapter();

        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this.getContext()));
        adapter.setList(Values.dataManager.getPostDAO().get100PostsFromUserFollowing(Values.loginUser.getId()));
        return root;
    }
}
