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

public class ManagerDetectCheatAndHotUsers extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_manager_detect_cheaters_and_hot_users, container, false);

        Button detectCheaters=root.findViewById(R.id.fragment_manager_detect_cheaters_and_hot_users_detect_cheaters);
        Button hotUsers=root.findViewById(R.id.fragment_manager_detect_cheaters_and_hot_users_show_hot_users);
        RecyclerView recyclerView=root.findViewById(R.id.fragment_manager_detect_cheaters_and_hot_users_recyclerview);
        final UserAdapter adapter=new UserAdapter();
//        HashtagAdapter adapter=new HashtagAdapter();

//        List<String> test=new ArrayList<>();
////        test.block("ffasafsafs");
////        test.block("asfasfakcdasknjvaknjdsvjnknjksdvjknasdkjnvjknsdavkjnadsf");
////        test.block("dsk;f;kadmlkfadlkmdfalmkads;ffn3jq w;efqwfc");
////
////        adapter.setList(test);
////        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
////        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);
        // TODO: saeidbahmani 1/22/19 6:42 PM () fill button onclick listeners
        detectCheaters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<User> list=Values.dataManager.getUserDAO().detectCheaters();
                adapter.setList(list);
////        recyclerView.setAdapter(adapter);
            }
        });

        hotUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<User> list=Values.dataManager.getUserDAO().getHotUsers();
                adapter.setList(list);
            }
        });



        return root;
    }

}
