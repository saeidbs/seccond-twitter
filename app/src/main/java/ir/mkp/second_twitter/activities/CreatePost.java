package ir.mkp.second_twitter.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ir.mkp.second_twitter.R;
import ir.mkp.second_twitter.activities.adapter.recyclerView.CreatePostHashtagAdapter;
import ir.mkp.second_twitter.module.Post;
import ir.mkp.second_twitter.utility.Values;

public class CreatePost extends BaseActivity {
    private RecyclerView hashtagListRV;
    private Button addHashtagBt;
    private Button addPostBt;
    private EditText hashtagET;
    private EditText postET;
    private CreatePostHashtagAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        initial();
        setAction();
    }

    private void initial(){
        hashtagListRV = findViewById(R.id.create_post_hashtag_list_rv);

        hashtagET = findViewById(R.id.create_post_hashtag_edit_text);
        postET = findViewById(R.id.create_post_text_edit_text);

        addPostBt = findViewById(R.id.create_post_post_button);
        addHashtagBt = findViewById(R.id.create_post_add_hashtag_button);

        adapter = new CreatePostHashtagAdapter();
    }

    private void setAction(){
        hashtagET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE){
                    addHashtagBt.performClick();
                    return true;
                }
                return false;
            }
        });

        hashtagListRV.setLayoutManager(new LinearLayoutManager(this));
        hashtagListRV.setAdapter(adapter);

        addHashtagBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hashtagET.getText().toString().isEmpty())
                    return;
                adapter.addHashtag(hashtagET.getText().toString());
            }
        });

        addPostBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Post post = new Post(Values.loginUser, postET.getText().toString(), adapter.getHashTagList());
                if (post.getHashtagList().size() < 2){
                    showToast("please block some hash tags");
                    return;
                }

                Values.dataManager.getPostDAO().add(post);
                finish();
            }
        });
    }
}
