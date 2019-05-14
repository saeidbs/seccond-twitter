package ir.mkp.second_twitter.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ir.mkp.second_twitter.R;

public class FirstActivity extends BaseActivity {

    private Button login_btn;
    private Button register_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        initial();
        setAction();
    }

    private void initial(){
        login_btn = findViewById(R.id.first_login_button);
        register_btn = findViewById(R.id.first_register_button);
    }

    private void setAction(){
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(FirstActivity.this, RegisterActivity.class);
                FirstActivity.this.startActivity(startIntent);
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent= new Intent(FirstActivity.this, LoginActivity.class);
                FirstActivity.this.startActivity(startIntent);
            }
        });
    }
}
