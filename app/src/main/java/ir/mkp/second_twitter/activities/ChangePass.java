package ir.mkp.second_twitter.activities;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ir.mkp.second_twitter.R;
import ir.mkp.second_twitter.utility.Values;

public class ChangePass extends BaseActivity {
    public static final String USER_EMAIL = "email";

    private EditText pass;
    private EditText confPass;
    private Button change;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);
        initial();
        setAction();
    }

    private void initial(){
        pass = findViewById(R.id.forget_pass_first_edit_text);
        pass.setHint("Password");
        pass.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);

        confPass = findViewById(R.id.forget_pass_second_edit_text);
        confPass.setHint("config password");
        confPass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

        change = findViewById(R.id.forget_pass_button);
        change.setText("Change");
    }

    private void setAction(){
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pass.getText().toString().isEmpty() || confPass.getText().toString().isEmpty()){
                    showToast("fill parameters");
                    return;
                }

                if (!pass.getText().toString().equals(confPass.getText().toString())){
                    showToast("config password is incorrect");
                    return;
                }

                Values.dataManager.getUserDAO().changePass(getIntent().getStringExtra(USER_EMAIL), pass.getText().toString());
                finish();
            }
        });
    }
}
