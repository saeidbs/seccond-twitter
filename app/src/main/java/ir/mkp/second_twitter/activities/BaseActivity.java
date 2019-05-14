package ir.mkp.second_twitter.activities;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import ir.mkp.second_twitter.R;

public abstract class BaseActivity extends AppCompatActivity {
    public static final String USER_ID = "user_id";

    protected Toolbar toolbar;
    private ProgressBar progressBar;

    private static boolean checkPermission(String permission, Activity activity) {
        int result = ContextCompat.checkSelfPermission(activity, permission);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        progressBar = findViewById(R.id.progress_bar);
//        toolbar = findViewById(R.id.tool_bar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    private static boolean checkAndRequestPermission(String permission, int code, Activity activity) {
        if (checkPermission(permission, activity)) {
            return true;
        }
        ActivityCompat.requestPermissions(activity,
                new String[]{permission},
                code);
        return checkPermission(permission, activity);
    }

    protected boolean checkPermission(String permission) {
        return checkPermission(permission, this);
    }

    protected boolean checkAndRequestPermission(String permission, int code) {
        return checkAndRequestPermission(permission, code, this);
    }

    protected final void setProgressBarVisibility(final int state) {
        if (progressBar == null)
            return;
        if (state != View.GONE && state != View.INVISIBLE && state != View.VISIBLE)
            return;
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(state);
                switch (state) {
                    case View.GONE:
                        progressBarGoneListener(progressBar);
                        break;
                    case View.INVISIBLE:
                        progressBarInvisibleListener(progressBar);
                        break;
                    case View.VISIBLE:
                        progressBarVisibleListener(progressBar);
                        break;
                }
            }
        });
    }

    protected void progressBarGoneListener(ProgressBar progressBar) {
        progressBarGoneListener();
    }

    protected void progressBarInvisibleListener(ProgressBar progressBar) {
        progressBarInvisibleListener();

    }

    protected void progressBarVisibleListener(ProgressBar progressBar) {
        progressBarVisibleListener();
    }

    protected final void setToolbarVisibility(final int state) {
        if (toolbar == null)
            return;
        if (state != View.GONE && state != View.INVISIBLE && state != View.VISIBLE)
            return;

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                toolbar.setVisibility(state);
            }
        });


    }


    public ProgressBar getProgressBar() {
        return progressBar;
    }

    @Override
    protected void onStop() {
        if (progressBar != null)
            progressBar.setVisibility(View.GONE);
        super.onStop();
    }

    protected void progressBarGoneListener() {

    }

    protected void progressBarInvisibleListener() {

    }

    protected void progressBarVisibleListener() {

    }

    protected void showToast(final String toastStr) {

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BaseActivity.this, toastStr, Toast.LENGTH_SHORT).show();

            }
        });
    }
}
