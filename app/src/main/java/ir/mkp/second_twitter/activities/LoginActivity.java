package ir.mkp.second_twitter.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import ir.mkp.second_twitter.R;
import ir.mkp.second_twitter.module.User;
import ir.mkp.second_twitter.utility.Values;

public class LoginActivity extends BaseActivity{
    private EditText email_et;
    private EditText password_et;
    private Button login_btn;
    private TextView forgetPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initial();
        setAction();
    }

    private void initial(){
        email_et = findViewById(R.id.login_email_edit_text);
        password_et = findViewById(R.id.login_password_edit_text);
        login_btn = findViewById(R.id.login_login_button);
        forgetPass = findViewById(R.id.login_forget_text_view);
    }

    private void setAction(){
        password_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE){
                    login_btn.performClick();
                    return true;
                }
                return false;
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkFilld())
                    return;

                User login = Values.dataManager.getUserDAO().login(email_et.getText().toString(),
                        password_et.getText().toString());
                if (login == null){
                    showToast("login error");
                    return;
                }
                Values.loginUser = login;
                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email_et.getText().toString().isEmpty()){
                    showToast("fill email");
                    return;
                }

                Intent intent = new Intent(LoginActivity.this, ForgetPassQuestion.class);
                intent.putExtra(ForgetPassQuestion.USER_EMAIL, email_et.getText().toString());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void progressBarGoneListener(ProgressBar progressBar) {
        super.progressBarGoneListener(progressBar);
        email_et.setEnabled(true);
        password_et.setEnabled(true);
        login_btn.setEnabled(true);
    }

    @Override
    protected void progressBarVisibleListener(ProgressBar progressBar) {
        super.progressBarVisibleListener(progressBar);
        email_et.setEnabled(false);
        password_et.setEnabled(false);
        login_btn.setEnabled(false);
    }

    private boolean checkFilld(){
        if (email_et.getText().toString().isEmpty()){
            showToast("fill email");
            return false;
        }
        if (password_et.getText().toString().isEmpty()){
            showToast("fill password");
            return false;
        }
        return true;
    }
}
