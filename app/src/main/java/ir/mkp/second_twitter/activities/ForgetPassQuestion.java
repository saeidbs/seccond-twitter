package ir.mkp.second_twitter.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ir.mkp.second_twitter.R;
import ir.mkp.second_twitter.utility.Values;

public class ForgetPassQuestion extends BaseActivity {
    public static final String USER_EMAIL = "email";

    private EditText question;
    private EditText answer;
    private Button button;

    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);
        initial();
        setAction();
    }

    private void initial() {
        email = getIntent().getStringExtra(USER_EMAIL);

        question = findViewById(R.id.forget_pass_first_edit_text);
        question.setEnabled(false);
        question.setText(Values.dataManager.getUserDAO().getQuestion(email));

        answer = findViewById(R.id.forget_pass_second_edit_text);
        answer.setHint("answer");

        button = findViewById(R.id.forget_pass_button);
        button.setText("CHECK");
    }

    private void setAction() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (answer.getText().toString().isEmpty()) {
                    showToast("enter email");
                    return;
                }

                if (!Values.dataManager.getUserDAO().checkAnswer(email, answer.getText().toString())){
                    showToast("answer is't correct");
                    return;
                }

                Intent intent = new Intent(ForgetPassQuestion.this, ChangePass.class);
                intent.putExtra(ChangePass.USER_EMAIL, email);
                startActivity(intent);
                finish();
            }
        });
    }
}
