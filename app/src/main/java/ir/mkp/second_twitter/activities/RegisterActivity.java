package ir.mkp.second_twitter.activities;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import ir.mkp.second_twitter.R;
import ir.mkp.second_twitter.module.User;
import ir.mkp.second_twitter.utility.Values;

public class RegisterActivity extends BaseActivity {

    private EditText email_et;
    private EditText username_et;
    private EditText password_et;
    private EditText configPassword_et;
    private EditText secureQuestion_et;
    private EditText secureAnswer_et;
    private EditText bio_et;
    private Button register_btn;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_step01);
        initial();
        setAction();
    }

    private void initial(){
        email_et = findViewById(R.id.register_email_edit_text);
        username_et = findViewById(R.id.register_username_edit_text);
        password_et = findViewById(R.id.register_password_edit_text);
        configPassword_et = findViewById(R.id.register_config_password_edit_text);
        register_btn = findViewById(R.id.register_register_button);
        secureQuestion_et = findViewById(R.id.register_secure_question_edit_text);
        secureAnswer_et = findViewById(R.id.register_secure_question_answer_edit_text);
        bio_et = findViewById(R.id.register_bio_edit_text);
        radioGroup = findViewById(R.id.register_radio_group);
    }

    private void setAction(){
        secureAnswer_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE){
                    register_btn.performClick();
                    return true;
                }
                return false;
            }
        });

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkPassword(view)) {
                    if (!checkFild()) {
                    setProgressBarVisibility(View.VISIBLE);
                        setProgressBarVisibility(View.GONE);
                        return;
                    }
                    if (Values.dataManager.getUserDAO().haveEmail(email_et.getText().toString())) {
                        showToast("email is excist");
                        return;
                    }
                    if (Values.dataManager.getUserDAO().haveUsername(username_et.getText().toString())) {
                        showToast("username is exsist");
                        return;
                    }
                    // TODO Masoud 1/20/2019: (logic) do register stuff
                    User.Role role = User.Role.NORMAL;
                    switch (radioGroup.getCheckedRadioButtonId()) {
                        case R.id.register_analyser_radio_button:
                            role = User.Role.ANALYSER;
                            break;
                        case R.id.register_admin_radio_button:
                            role = User.Role.ADMIN;
                            break;
                    }
                    User user = new User(email_et.getText().toString(),
                            username_et.getText().toString(),
                            password_et.getText().toString(),
                            role,
                            bio_et.getText().toString(),
                            secureQuestion_et.getText().toString(),
                            secureAnswer_et.getText().toString());

                    Values.dataManager.getUserDAO().add(user);
                    finish();
                }

            }

        });
    }

    @Override
    protected void progressBarGoneListener(ProgressBar progressBar) {
        email_et.setEnabled(true);
        username_et.setEnabled(true);
        password_et.setEnabled(true);
        configPassword_et.setEnabled(true);
        register_btn.setEnabled(true);
    }

    @Override
    protected void progressBarVisibleListener(ProgressBar progressBar) {
        email_et.setEnabled(false);
        username_et.setEnabled(false);
        password_et.setEnabled(false);
        configPassword_et.setEnabled(false);
        register_btn.setEnabled(false);
    }

    private boolean checkFild(){
        if (email_et.getText().toString().isEmpty()) {
            showToast("inter email");
            return false;
        }
        if (username_et.getText().toString().isEmpty()) {
            showToast("inter username");
            return false;
        }
        if (password_et.getText().toString().isEmpty()){
            showToast("inter pass");
            return false;
        }
        if (secureQuestion_et.getText().toString().isEmpty()){
            showToast("fill question");
            return false;
        }
        if (secureAnswer_et.getText().toString().isEmpty()){
            showToast("fill answer");
            return false;
        }

        String pass = password_et.getText().toString();
        if (configPassword_et.getText().toString().isEmpty()){
            showToast("inter conf pass");
            return false;
        }
        String confPass = password_et.getText().toString();
        if (!confPass.equals(pass)){
            showToast("pass is not config");
            return false;
        }
        return true;
    }
    private boolean checkPassword(View view){

        String string=password_et.getText().toString();
        if (!string.matches(configPassword_et.getText().toString())){
           // showToast("passwords not match");
            Toast.makeText(view.getContext(),"passwords not match",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(string.length()>=8 && string.matches("[0-9]*[a-zA-Z]+[0-9]+[a-zA-Z]*")) {
            //showToast("please your password have word and number and length bigger than 8");

            return true;
        }
        Toast.makeText(view.getContext(),"please your password have word and number and length bigger than 8",Toast.LENGTH_SHORT).show();
        return false;


    }
}
