package ir.mkp.second_twitter.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ir.mkp.second_twitter.R;
import ir.mkp.second_twitter.module.Comment;
import ir.mkp.second_twitter.utility.Values;

public class CreateCommentActivity extends BaseActivity {
    private EditText comment;
    private Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_comment);
        comment = findViewById(R.id.comment_activity_edit_text);
        button = findViewById(R.id.comment_activity_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (comment.getText().toString().isEmpty()){
                    showToast("fill comment");
                    return;
                }
                Comment com = new Comment(comment.getText().toString(),
                        Values.tempPost.getID(),
                        Values.loginUser.getId());
                Values.dataManager.getCommentDAO().add(com);
                finish();
            }
        });
    }
}
