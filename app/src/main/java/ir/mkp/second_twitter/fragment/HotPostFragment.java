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

public class HotPostFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.activity_post_list, container, false);

        PostAdapter adapter = new PostAdapter();
        RecyclerView rv = root.findViewById(R.id.post_list_rv);
        rv.setLayoutManager(new LinearLayoutManager(root.getContext()));
        rv.setAdapter(adapter);
        adapter.setList(Values.dataManager.getPostDAO().getHotPostsForExplorer());

        return root;
    }
}
