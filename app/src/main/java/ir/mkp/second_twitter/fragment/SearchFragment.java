package ir.mkp.second_twitter.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import ir.mkp.second_twitter.R;
import ir.mkp.second_twitter.activities.adapter.recyclerView.HashtagAdapter;
import ir.mkp.second_twitter.activities.adapter.recyclerView.UserAdapter;
import ir.mkp.second_twitter.database.dataHelper.tables.HashtagTable;
import ir.mkp.second_twitter.module.User;
import ir.mkp.second_twitter.utility.Values;

public class SearchFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.fragment_main_search, container, false);
        final RecyclerView recyclerView =  root.findViewById(R.id.fragment_main_search_recyclerview);
        final EditText editText=root.findViewById(R.id.fragment_main_search_edit_text);

        final UserAdapter userAdapter = new UserAdapter();
        final HashtagAdapter hashtagAdapter = new HashtagAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE){
                    String serachText = textView.getText().toString();

                    if (serachText.startsWith("#")){
                        List<String> test = Values.dataManager.getHashtagDAO().search(serachText.substring(1));
                        recyclerView.setAdapter(hashtagAdapter);
                        hashtagAdapter.setList(test);
                        return true;
                    }else{
                        List<User> test = Values.dataManager.getUserDAO().searchByUsername(textView.getText().toString());
                        recyclerView.setAdapter(userAdapter);
                        userAdapter.setList(test);
                        return true;
                    }
                }
                return false;
            }
        });
        // TODO: saeidbahmani 1/21/19 7:42 PM () moghei ke edite text kar mikone badesh bayad set list kard adapter ro





        return root;
    }

}
