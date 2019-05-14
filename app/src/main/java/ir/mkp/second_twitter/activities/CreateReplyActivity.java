package ir.mkp.second_twitter.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ir.mkp.second_twitter.R;
import ir.mkp.second_twitter.module.Reply;
import ir.mkp.second_twitter.utility.Values;

public class CreateReplyActivity extends BaseActivity {
    private EditText reply;
    private Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_comment);
        reply = findViewById(R.id.comment_activity_edit_text);
        reply.setHint("Reply");
        button = findViewById(R.id.comment_activity_button);
        button.setText("Reply");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (reply.getText().toString().isEmpty()) {
                    showToast("fill comment");
                    return;
                }
                Reply rp = new Reply(Values.tempComment, Values.loginUser, reply.getText().toString());
                Values.dataManager.getReplyDAO().add(rp);
                finish();
            }
        });
    }
}
