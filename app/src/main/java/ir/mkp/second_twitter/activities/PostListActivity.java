package ir.mkp.second_twitter.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import ir.mkp.second_twitter.R;
import ir.mkp.second_twitter.activities.adapter.recyclerView.PostAdapter;
import ir.mkp.second_twitter.utility.Values;

public class PostListActivity extends BaseActivity {
    public static final String HashTag = "hashtag";
    private RecyclerView rv;
    private PostAdapter adapter = new PostAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);
        initial();
        setAction();
    }

    private void initial() {
        rv = findViewById(R.id.post_list_rv);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter.setList(Values.dataManager.getHashtagDAO().getPosts(getIntent().getStringExtra(HashTag)));
    }

    private void setAction() {

    }
}
